package com.example.admin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.databinding.ItemHomeBinding

/*
 * Created by Sudhanshu Kumar on 25/11/24.
 */

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    private val list = mutableListOf<String>()

    fun addData(list: List<String>) {
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.apply {
            tvTitleHomeAdapter.text = list[position]
            root.setOnClickListener {
                onItemClickListener?.let { it(tvTitleHomeAdapter.text.toString()) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}