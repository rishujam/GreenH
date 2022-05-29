package com.ev.greenh

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ev.greenh.adapters.MyOrderDetailAdapter
import com.ev.greenh.adapters.PlantAdapter
import com.ev.greenh.databinding.OrderDetailsFragmentBinding
import com.ev.greenh.models.uimodels.MyOrderDetail
import com.ev.greenh.models.uimodels.PlantMyOrder
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar

class OrderDetailsFragment:Fragment(), PlantAdapter.OnItemClickListener {

    private var _binding:OrderDetailsFragmentBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderDetailsFragmentBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getString("orderId")

        viewModel.getSingleOrderDetail(orderId.toString(),getString(R.string.orders), getString(R.string.plant_sample_ref))

        viewModel.getSingleOrderDetails.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    if (it.data!=null){
                        setData(it.data)
                    }else{
                        Snackbar.make(binding.root,"Somethings went wrong",Snackbar.LENGTH_SHORT).show()
                    }
                    binding.progressBar4.visibility = View.INVISIBLE
                }
                is Resource.Error ->{
                    Snackbar.make(binding.root,"Somethings went wrong",Snackbar.LENGTH_SHORT).show()
                    binding.progressBar4.visibility = View.INVISIBLE
                }
                is Resource.Loading->{}
            }
        })

        binding.cancelOrder.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel your order ?")
                .setPositiveButton("Yes") { _, _ ->

                }
                .setNegativeButton("No") { _, _ ->

                }
                .create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setData(myOrderDetail:MyOrderDetail){
        binding.orderDate.text = myOrderDetail.deliveryDate.split(",")[0]
        binding.orderId.text = myOrderDetail.orderId
        binding.orderTotal.text = "₹${myOrderDetail.orderTotal}"
        binding.payMethod.text = if(myOrderDetail.paymentId=="") "Cash On Delivery" else "Paid Online"
        binding.shippingAddress.text = "${myOrderDetail.address.split("%")[0]}\n${myOrderDetail.address.split("%")[1]}"
        binding.itemsCost.text = "₹${myOrderDetail.itemsAmount}"
        binding.deliveryChargeOD.text = "₹${myOrderDetail.deliveryCharge}"
        binding.orderTotalOD.text = "₹${myOrderDetail.orderTotal}"
        setupRv(myOrderDetail.items)
    }

    private fun setupRv(list: List<PlantMyOrder>){
        val myOrderDetailAdapter = MyOrderDetailAdapter(list,this)
        binding.rvDetailOrderList.apply {
            adapter = myOrderDetailAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onItemClick(plantId: String) {
        val plantDetailsFragment = PlantDetailFragment()
        val bundle = Bundle()
        bundle.putString("ID",plantId)
        plantDetailsFragment.arguments = bundle
        (activity as MainActivity).setCurrentFragmentBack(plantDetailsFragment)
    }
}