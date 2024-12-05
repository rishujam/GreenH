package com.example.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.Plant
import com.example.ui.databinding.ItemPlantListBinding

/*
 * Created by Sudhanshu Kumar on 21/11/24.
 */

class PlantAdapter: RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(val binding: ItemPlantListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(
            ItemPlantListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = differ.currentList[position]
        holder.binding.apply {
            tvNameItemPlant.text = plant.name
            tvPriceItemPlant.text = "₹${plant.price}"
            Glide.with(this.root)
                .load(plant.imageUrl)
                .into(ivPlantItem)
//            root.setOnClickListener {
//                onItemClickListener?.let { it(plant, position) }
//            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Plant, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Plant, position: Int) -> Unit) {
        onItemClickListener = listener
    }
}