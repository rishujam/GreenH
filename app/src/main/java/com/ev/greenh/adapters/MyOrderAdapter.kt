package com.ev.greenh.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.R
import com.ev.greenh.databinding.ItemMyorderBinding
import com.ev.greenh.models.uimodels.MyOrder

class MyOrderAdapter(
    val list:List<MyOrder>,
    val listener:OrderDetails
) :RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>(){

    inner class MyOrderViewHolder(val binding:ItemMyorderBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        init {
            binding.myOrder.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val pos  = adapterPosition
            val orderId = list[pos].orderId
            if(pos!=RecyclerView.NO_POSITION){
                listener.onOrderClick(orderId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyOrderViewHolder {
        return MyOrderViewHolder(ItemMyorderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val myOrder = list[position]
        holder.binding.apply {
            itemName.text = myOrder.plantName
            if(myOrder.deliveryStatus=="Order Placed" || myOrder.deliveryStatus=="Order Placed To Nursery" || myOrder.deliveryStatus=="Order on way" || myOrder.deliveryStatus=="Cancel Requested"){
                orderStatus.text = "In Transit"
                dateDelivered.text = "Est time: ${myOrder.deliveryDate.split(",")[0]}"
                imageView5.setImageResource(R.drawable.ic_transit)
            }
            if(myOrder.deliveryStatus=="Order Rejected from nursery" || myOrder.deliveryStatus=="Cancelled"){
                orderStatus.text = "Cancelled"
                dateDelivered.visibility = View.GONE
                orderStatus.setTextColor(Color.RED)
                imageView5.setImageResource(R.drawable.ic_order_cancelled)
            }
            if(myOrder.deliveryStatus=="Order Delivered"){
                orderStatus.text = "Delivered"
                orderStatus.text = "On: ${myOrder.deliveryDate.split(",")[0]}"
                imageView5.setImageResource(R.drawable.ic_delivered)
            }
            Glide.with(root).load(myOrder.plantPhoto).into(itemImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OrderDetails{
        fun onOrderClick(orderId:String)
    }
}