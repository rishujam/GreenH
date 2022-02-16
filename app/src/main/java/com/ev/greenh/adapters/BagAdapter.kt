package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.SheetQuantityBinding
import com.ev.greenh.models.Plant
import com.ev.greenh.util.visible

class BagAdapter(
    val map: Map<Plant,String>
) : RecyclerView.Adapter<BagAdapter.BagViewHolder>(){

    inner class BagViewHolder(val binding: SheetQuantityBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        return BagViewHolder(SheetQuantityBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        holder.binding.apply {
            textView4.visible(false)
            pbQuantity.visible(false)
            quantityAdd.text = "Remove"
            quantity.text = map.values.toList()[position]
            val plant = map.keys.toList()[position]
            quantityName.text = plant.name
            quantityPrice.text = "₹${plant.price}"
            Glide.with(root).load(plant.imageLocation).into(quantityImage)
            quantityPlus.setOnClickListener {
                var start = quantity.text.toString().toInt()
                start++
                quantity.text = start.toString()
                quantityPrice.text = "₹${plant.price.toInt()*start}"
            }
            quantityMinus.setOnClickListener {
                var start = quantity.text.toString().toInt()
                if(start>1){
                    start--
                    quantity.text = start.toString()
                    quantityPrice.text = "₹${plant.price.toInt()*start}"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return map.size
    }
}