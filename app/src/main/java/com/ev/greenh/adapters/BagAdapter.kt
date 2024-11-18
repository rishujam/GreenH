package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.ItemBagBinding
import com.ev.greenh.shop.data.model.ResPlant

class BagAdapter(
    val map: Map<ResPlant,String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BagAdapter.BagViewHolder>(){

    inner class BagViewHolder(val binding: ItemBagBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener{
        init {
            binding.edit.setOnClickListener(this)
            binding.btnRemove.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val posi =adapterPosition
            if(posi!=RecyclerView.NO_POSITION){
                if(p0?.id==binding.btnRemove.id){
                    listener.onItemClick(posi,1)
                }
                if(p0?.id==binding.edit.id){
                    listener.onItemClick(posi,2)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        return BagViewHolder(ItemBagBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.binding.apply {
            val plant = map.keys.toList()[position]
            val value = map.values.toList()[position].split(",")
            tvQuantity.text = "Quantity: ${value[0]}"
            tvItemName.text = plant.name
            tvPrice.text = "₹${value[1]}"
//            Glide.with(root).load(plant.imageLocation).into(thumbnail)
        }
    }

    override fun getItemCount(): Int {
        return map.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,id:Int)
    }
}