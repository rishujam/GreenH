package com.ev.greenh.ui.order

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ev.greenh.R
import com.ev.greenh.adapters.BagAdapter
import com.ev.greenh.databinding.FragmentBagBinding
import com.ev.greenh.models.Plant
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel

class BagFragment: Fragment(), BagAdapter.OnItemClickListener {

    private var _binding: FragmentBagBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private val totalLive = MutableLiveData<Int>()
    private lateinit var bagAdapter: BagAdapter
    private lateinit var email:String
    private var isCompleted = MutableLiveData(false)
    private var isClickedUpdate = false
    private var isClickedRemove  = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readUid()

        totalLive.observe(viewLifecycleOwner, Observer {
            binding.tvTotal.text = "₹${it}"
        })

        viewModel.uid.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val user = it.data
                    if(user!=null){
                        email = user
                        viewModel.getBagItems(getString(R.string.cart),getString(R.string.plant_sample_ref),email)
                    }else{
                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading ->{}
                is Resource.Error -> {
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                    Log.e("BagFrag: email",it.message.toString())
                }
                else -> {}
            }
        })

        viewModel.bagItems.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    val data = it.peekContent().data
                    if(data!=null) setupRv(data)
                    binding.pbBag.visible(false)
                }
                is Resource.Error -> {
                    binding.pbBag.visible(false)
                    Toast.makeText(context, it.peekContent().message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
                else -> {}
            }
        })

        viewModel.success.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success ->{
                    if(isClickedUpdate){
                        isCompleted.value = true
                        Toast.makeText(context, "Quantity Updated", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    if(isClickedUpdate){
                        isCompleted.value = true
                        Toast.makeText(context,"Error: ${it.peekContent().message}",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {}
                else ->{}
            }
        })

        viewModel.deleteBagItem.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    binding.pbBag.visible(false)
                    if(isClickedRemove){
                        Toast.makeText(context, "Item Removed Successfully", Toast.LENGTH_SHORT).show()
                        val ft = (activity as MainActivity).supportFragmentManager
                        ft.beginTransaction().detach(this).commitNow()
                        ft.beginTransaction().attach(this).commitNow()
                    }
                }
                is Resource.Error ->{
                    binding.pbBag.visible(false)
                    if(isClickedRemove){
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {}
            }
        })

        binding.btnCheckout.setOnClickListener {
            if(bagAdapter.map.isNotEmpty()){
                val bagBuyFragment = BagBuyFragment()
                (activity as MainActivity).setCurrentFragmentBack(bagBuyFragment)
            }else{
                Toast.makeText(context, "You don't have any plant in bag", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRv(map:Map<Plant,String>){
        var total = 0
        for(i in map.values.toList()){
            total +=i.split(",")[1].toInt()
        }
        totalLive.value = total
        bagAdapter = BagAdapter(map,this)
        binding.rvBagItems.apply {
            adapter = bagAdapter
            layoutManager = LinearLayoutManager(context)
        }
        if(map.isEmpty()){
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(position: Int,id:Int) {
        val plant = bagAdapter.map.keys.toList()[position]
        if(id==1){
            isClickedRemove = true
            binding.pbBag.visible(true)
            viewModel.deleteItemFromBag(email,getString(R.string.cart),plant.id)
            binding.pbBag.visible(false)
        }
        if(id==2){
            val quantity = bagAdapter.map.getValue(plant).split(",")[0]
            showDialog(plant,quantity)
        }
    }

    private fun showDialog(plant:Plant,quantity:String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.sheet_quantity)
        val tvQuantity: TextView = dialog.findViewById(R.id.quantity)
        val tvName: TextView = dialog.findViewById(R.id.quantityName)
        val tvPrice: TextView = dialog.findViewById(R.id.quantityPrice)
        val image: ImageView = dialog.findViewById(R.id.quantityImage)
        val add: Button = dialog.findViewById(R.id.quantityAdd)
        val quantityPlus: ImageButton = dialog.findViewById(R.id.quantityPlus)
        val quantityMinus: ImageButton = dialog.findViewById(R.id.quantityMinus)
        val pb: ProgressBar = dialog.findViewById(R.id.pbQuantity)

        Glide.with(dialog.context).load(plant.imageLocation).into(image)
        tvName.text = plant.name
        tvPrice.text = "₹${plant.price.toInt()*quantity.toInt()}"
        tvQuantity.text = quantity
        add.text = "Save"
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)

        pb.visible(false)

        isCompleted.observe(viewLifecycleOwner, Observer {
            if(isCompleted.value!!){
                isCompleted.value = false
                pb.visible(false)
                dialog.dismiss()
                val ft  = (activity as MainActivity).supportFragmentManager
                ft.beginTransaction().detach(this).commitNow()
                ft.beginTransaction().attach(this).commitNow()
            }
        })

        quantityMinus.setOnClickListener {
            var currQuantity = tvQuantity.text.toString().toInt()
            if(currQuantity>1){
                currQuantity--
                tvQuantity.text = "$currQuantity"
                tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
                totalLive.value = totalLive.value?.minus(plant.price.toInt())
            }
        }
        quantityPlus.setOnClickListener {
            var currQuantity = tvQuantity.text.toString().toInt()
            currQuantity++
            tvQuantity.text = "$currQuantity"
            tvPrice.text = "₹${plant.price.toInt()*currQuantity}"
            totalLive.value = totalLive.value?.plus(plant.price.toInt())
        }

        add.setOnClickListener {
            isClickedUpdate = true
            pb.visible(true)
            dialog.setCancelable(false)
            viewModel.updateQuantity(
                email,
                getString(R.string.cart),
                tvQuantity.text.toString().toInt(),
                plant.id
            )
        }
    }

    override fun onStop() {
        super.onStop()
        isClickedUpdate = false
        isClickedRemove = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}