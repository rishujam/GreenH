package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.ItemMyorderDetailPlantBinding
import com.ev.greenh.models.uimodels.PlantMyOrder
import kotlin.math.cos

class MyOrderDetailAdapter(
    val list:List<PlantMyOrder>,
    val listener: OnItemClickListener
) :RecyclerView.Adapter<MyOrderDetailAdapter.OrderDetailPlantViewHolder>(){

    inner class OrderDetailPlantViewHolder(val binding:ItemMyorderDetailPlantBinding):RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.itemOrderDetail.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val posi =adapterPosition
            val plantId = list[posi].plantId
            if(posi!=RecyclerView.NO_POSITION){
                listener.onItemClick(plantId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailPlantViewHolder {
        return OrderDetailPlantViewHolder(ItemMyorderDetailPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OrderDetailPlantViewHolder, position: Int) {
        holder.binding.apply {
            itemName.text = list[position].plantName
            quantityMOD.text = "Quantity: ${list[position].quantity}"
            cost.text = "₹${list[position].plantPrice}"
            Glide.with(root).load(list[position].plantPhoto).into(itemImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(plantId:String)
    }
}