package com.ev.greenh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ev.greenh.databinding.ItemPlantBinding
import com.ev.greenh.models.Order

class PlantAdapter:RecyclerView.Adapter<PlantAdapter.PlantViewHolder>(){

    inner class PlantViewHolder (val binding: ItemPlantBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(ItemPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private var onItemClickListener: ((Order) -> Unit)? = null

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = differ.currentList[position]
        holder.binding.apply {
//            tvPlantName.text = plant.name
//            Glide.with(root).load(plant.imageLocation).into(thumbnail)
            plantItem.setOnClickListener {
                onItemClickListener?.let { it(plant) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onItemClickListener = listener
    }

}