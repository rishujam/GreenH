package com.example.ui.detail

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.core.ui.hide
import com.core.ui.show
import com.example.domain.model.Plant
import com.example.ui.R
import com.example.ui.databinding.FragmentPlantDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


internal class PlantDetailFragment : Fragment() {

    private var _binding: FragmentPlantDetailsBinding? = null
    private val binding get() = _binding
    private lateinit var dialog: Dialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val args: PlantDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantId = args.plantDetailArgs.id
        val plant = args.plantDetailArgs.plant
        setupData(plant)
//        binding?.bottomSheetInflator?.bottomSheetContainer?.let {
//            bottomSheetBehavior = BottomSheetBehavior.from(it)
//            val density = resources.displayMetrics.density
//            val peekHeight = (55 * density).toInt()
//            bottomSheetBehavior.peekHeight = peekHeight
//            val frag = BottomSheetFrag()
//            childFragmentManager.beginTransaction().apply {
//                replace(R.id.flBottomSheet, frag)
//                commit()
//            }
//        }

//        viewModel.success.observe(viewLifecycleOwner, Observer {
//            when(it.getContentIfNotHandled()){
//                is Resource.Error ->{
//                    Toast.makeText(context, "Error Adding Item", Toast.LENGTH_SHORT).show()
//                    dialog.setCancelable(true)
//                }
//                is Resource.Success ->{
//                    Snackbar.make(binding.root, "Item Added to cart", Snackbar.LENGTH_SHORT).show()
//                    dialog.dismiss()
//                }
//                is Resource.Loading->{}
//                else ->{}
//            }
//        })

        binding?.backBtn?.setOnClickListener {
//            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding?.addToCart?.setOnClickListener {
//            showDialog()
        }
        binding?.playVideo?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("plantId", plantId)
//            val videoFrag = PlantVideoFragment()
//            videoFrag.arguments = bundle
//            (activity as MainActivity).setCurrentFragmentBack(videoFrag)
        }
    }

    private fun setupData(plant: Plant) {
        binding?.apply {
            plantName.show()
            plantName.text = plant.name
            tPrive.text = "₹${plant.price}"
//            tvSunlight.text = plant.sunlight.value
//            tvWater.text = plant.water.value
            Glide.with(root)
//                .setDefaultRequestOptions(
//                    RequestOptions()
//                        .placeholder(R.drawable.load)
//                        .error(R.drawable.load)
//                )
                .load(plant.imageUrl).listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbPlantDetail.hide()
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(plantImage)
            plantImage.clipToOutline = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun showDialog() {
//        dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.sheet_quantity)
//        val tvQuantity: TextView = dialog.findViewById(R.id.quantity)
//        val tvName: TextView = dialog.findViewById(R.id.quantityName)
//        val tvPrice:TextView = dialog.findViewById(R.id.quantityPrice)
//        val image:ImageView = dialog.findViewById(R.id.quantityImage)
//        val add:Button = dialog.findViewById(R.id.quantityAdd)
//        val quantityPlus:ImageButton = dialog.findViewById(R.id.quantityPlus)
//        val quantityMinus:ImageButton = dialog.findViewById(R.id.quantityMinus)
//        val pb:ProgressBar = dialog.findViewById(R.id.pbQuantity)
//
//        tvName.text = plant.name
//        tvPrice.text = "₹${plant.price}"
//        tvQuantity.text = "1"
//        dialog.show()
//        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setGravity(Gravity.BOTTOM)
//
////        Glide.with(dialog.context).load(plant.imageLocation).into(image)
//
//
//        pb.visible(false)
//        add.setOnClickListener {
//            pb.visible(true)
//            dialog.setCancelable(false)
////            viewModel.readUid()
////            viewModel.uid.observe(viewLifecycleOwner, Observer {
////                when(it){
////                    is Resource.Error -> {
////                        pb.visible(false)
////                        dialog.setCancelable(true)
////                    }
////                    is Resource.Success -> {
////                        val email = it.data
////                        if(email!=null){
////                            viewModel.addPlantToBag(
////                                plantId,
////                                email,
////                                getString(R.string.cart),
////                                tvQuantity.text.toString()
////                            )
////                        }else{
////                            Toast.makeText(context, "Error Adding Item", Toast.LENGTH_SHORT).show()
////                            pb.visible(false)
////                        }
////                    }
////                    is Resource.Loading -> {}
////                    else ->{}
////                }
////            })
//
//        }
//        quantityMinus.setOnClickListener {
//            var currQuantity = tvQuantity.text.toString().toInt()
//            if(currQuantity>1){
//                currQuantity--
//                tvQuantity.text = "$currQuantity"
//                tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
//            }
//        }
//        quantityPlus.setOnClickListener {
//            var currQuantity = tvQuantity.text.toString().toInt()
//            currQuantity++
//            tvQuantity.text = "$currQuantity"
//            tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
//        }
//    }
}

