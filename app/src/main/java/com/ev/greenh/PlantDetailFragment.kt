package com.ev.greenh

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ev.greenh.models.Plant
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.*
import com.ev.greenh.databinding.FragmentPlantDetailBinding
import com.ev.greenh.util.visible


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
                    if(it.data!=null){
                        plant = it.data!!
                        setupData(plant)
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

        binding.buy.setOnClickListener {

        }

        binding.addToCart.setOnClickListener {
            showDialog()
        }
    }

    private fun setupData(plant: Plant){
        binding.pbPlantDetail.visible(true)
        binding.tName.text = plant.name
        binding.tPrive.text = "₹${plant.price}"
        binding.height.text= "Height above soil: ${plant.height} cm"
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

        Glide.with(dialog.context).load(plant.imageLocation).into(image)
        tvName.text = plant.name
        tvPrice.text = "₹${plant.price}"
        tvQuantity.text = "1"
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        pb.visible(false)
        add.setOnClickListener {
            pb.visible(true)
            dialog.setCancelable(false)
            viewModel.readEmail()
            viewModel.email.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Resource.Error -> {
                        pb.visible(false)
                        Toast.makeText(context, "Error Adding Item", Toast.LENGTH_SHORT).show()
                        dialog.setCancelable(true)
                    }
                    is Resource.Success -> {
                        pb.visible(false)
                        viewModel.addPlantToBag(
                            plantId,
                            it.data!!,
                            getString(R.string.cart),
                            tvQuantity.text.toString()
                        )
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    is Resource.Loading -> {}
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