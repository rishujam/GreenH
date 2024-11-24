package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.ItemBagBuyBinding

class BagBuyAdapter(
) : RecyclerView.Adapter<BagBuyAdapter.BagBuyViewHolder>(){

    inner class BagBuyViewHolder(val binding:ItemBagBuyBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagBuyViewHolder {
        return BagBuyViewHolder(ItemBagBuyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BagBuyViewHolder, position: Int) {
        holder.binding.apply {
//            val plant = map.keys.toList()[position]
//            tvQuantityBB.text = "Quantity: ${map.values.toList()[position].split(",")[0]}"
//            tvItemNameBB.text = plant.name
//            tvPriceBB.text = "₹${plant.price}"
//            Glide.with(root).load(plant.imageLocation).into(thumbnailBB)
        }
    }

    override fun getItemCount(): Int {
        return 0
    }
}