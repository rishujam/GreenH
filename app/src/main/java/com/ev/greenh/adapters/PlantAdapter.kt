package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.ItemPlantBinding
import com.ev.greenh.models.Plant

class PlantAdapter(
    val list:List<Plant>,
    private val listener: OnItemClickListener
) :RecyclerView.Adapter<PlantAdapter.PlantViewHolder>(){

    inner class PlantViewHolder (val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.plant.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val posi =adapterPosition
            if(posi!=RecyclerView.NO_POSITION){
                listener.onItemClick(posi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(ItemPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.binding.apply {
            tvPrice.text = "₹${list[position].price}"
            Glide.with(holder.binding.root).load(list[position].imageLocation).into(holder.binding.thumbnail)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}