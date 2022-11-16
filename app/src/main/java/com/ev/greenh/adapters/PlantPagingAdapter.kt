package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.ItemPlantBinding
import com.ev.greenh.models.Plant

class PlantPagingAdapter: PagingDataAdapter<Plant, PlantPagingAdapter.PlantPagingViewHolder>(COMPARATOR){

    inner class PlantPagingViewHolder (val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Plant>() {
            override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantPagingViewHolder {
        return PlantPagingViewHolder(ItemPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private var onItemClickListener: ((Plant) -> Unit)? = null

    override fun onBindViewHolder(holder: PlantPagingViewHolder, position: Int) {
        val plant = getItem(position)
        if(plant!=null){
            holder.binding.apply {
                tvPlantName.text = plant.name
                Glide.with(root).load(plant.imageLocation).into(thumbnail)
                plantItem.setOnClickListener {
                    onItemClickListener?.let { it(plant) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Plant) -> Unit) {
        onItemClickListener = listener
    }

}