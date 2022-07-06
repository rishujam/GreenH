package com.ev.greenh.ui.plants

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentPlantDetailBinding
import com.ev.greenh.models.Plant
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar


class PlantDetailFragment: Fragment() {

    private var _binding: FragmentPlantDetailBinding?=null
    private val binding get() = _binding!!
    private lateinit var plantId:String
    private lateinit var viewModel: PlantViewModel
    private lateinit var plant: Plant

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantDetailBinding.inflate(inflater,container,false)
        plantId = arguments?.getString("ID").toString()
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.plantResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.pbPlantDetail.visible(true)
                }
                is Resource.Success -> {
                    val data = it.data
                    if(data!=null){
                        plant = it.data
                        setupData(data)
                    }
                    binding.pbPlantDetail.visible(false)
                }
                is Resource.Error ->{
                    binding.pbPlantDetail.visible(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getSinglePlant(getString(R.string.plant_sample_ref),plantId)

//        binding.buy.setOnClickListener {
//            val buyFragment = DirectBuyFragment()
//            val bundle = Bundle()
//            bundle.putString("plantIdBF",plantId)
//            buyFragment.arguments = bundle
//            (activity as MainActivity).setCurrentFragmentBack(buyFragment)
//        }

        binding.backButton.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

        binding.addToCart.setOnClickListener {
            showDialog()
        }
        binding.playVideo.setOnClickListener {
            if(plant.videoLink!=""){
                binding.videoView.visibility=  View.VISIBLE
                binding.videoView.setVideoPath("https://firebasestorage.googleapis.com/v0/b/fir-yt-a8191.appspot.com/o/samplevideo%2Fsnow.mp4?alt=media&token=77e8fe2f-b005-4a0a-b6e2-61778a039e43")
                binding.pbPlantDetail.visibility = View.VISIBLE
                binding.videoView.setOnPreparedListener {
                    binding.pbPlantDetail.visibility = View.INVISIBLE
                    it.start()
                }
                binding.videoView.setOnCompletionListener {
                    binding.videoView.visibility = View.GONE
                }
            }else{
                Toast.makeText(context, "No Video Available", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupData(plant: Plant){
        binding.pbPlantDetail.visible(true)
        binding.plantName.text = plant.name
        binding.tPrive.text = "₹${plant.price}"
        binding.tvSunlight.text = plant.sunlight
        binding.tvWater.text = plant.water
        binding.tvDescription.text = plant.description
        Glide.with(binding.root).load(plant.imageLocation).into(binding.plantImage)
        binding.pbPlantDetail.visible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.sheet_quantity)
        val tvQuantity: TextView = dialog.findViewById(R.id.quantity)
        val tvName: TextView = dialog.findViewById(R.id.quantityName)
        val tvPrice:TextView = dialog.findViewById(R.id.quantityPrice)
        val image:ImageView = dialog.findViewById(R.id.quantityImage)
        val add:Button = dialog.findViewById(R.id.quantityAdd)
        val quantityPlus:ImageButton = dialog.findViewById(R.id.quantityPlus)
        val quantityMinus:ImageButton = dialog.findViewById(R.id.quantityMinus)
        val pb:ProgressBar = dialog.findViewById(R.id.pbQuantity)

        tvName.text = plant.name
        tvPrice.text = "₹${plant.price}"
        tvQuantity.text = "1"
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        Glide.with(dialog.context).load(plant.imageLocation).into(image)

        pb.visible(false)
        add.setOnClickListener {
            pb.visible(true)
            dialog.setCancelable(false)
            viewModel.readUid()
            viewModel.uid.observe(viewLifecycleOwner, Observer {
                when(it.getContentIfNotHandled()){
                    is Resource.Error -> {
                        pb.visible(false)
                        Toast.makeText(context, "Error Adding Item", Toast.LENGTH_SHORT).show()
                        dialog.setCancelable(true)
                    }
                    is Resource.Success -> {
                        val email = it.peekContent().data
                        if(email!=null){
                            viewModel.addPlantToBag(
                                plantId,
                                email,
                                getString(R.string.cart),
                                tvQuantity.text.toString()
                            )
                            Snackbar.make(binding.root, "Item Added to cart", Snackbar.LENGTH_SHORT).show()
                            pb.visible(false)
                            dialog.dismiss()
                        }else{
                            Toast.makeText(context, "Error Adding Item", Toast.LENGTH_SHORT).show()
                            pb.visible(false)
                        }
                    }
                    is Resource.Loading -> {}
                    else ->{}
                }
            })

        }
        quantityMinus.setOnClickListener {
            var currQuantity = tvQuantity.text.toString().toInt()
            if(currQuantity>1){
                currQuantity--
                tvQuantity.text = "$currQuantity"
                tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
            }
        }
        quantityPlus.setOnClickListener {
            var currQuantity = tvQuantity.text.toString().toInt()
            currQuantity++
            tvQuantity.text = "$currQuantity"
            tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
        }
    }
}