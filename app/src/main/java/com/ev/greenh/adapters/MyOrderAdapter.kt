package com.ev.greenh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ev.greenh.databinding.ItemMyorderBinding
import com.ev.greenh.models.Order
import com.ev.greenh.models.Plant

class MyOrderAdapter(
    val list:List<Order>,
    val context:Context
) :RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>(){

    inner class MyOrderViewHolder(val binding:ItemMyorderBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyOrderViewHolder {
        return MyOrderViewHolder(ItemMyorderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        val items = list[position].items
        val map = mutableMapOf<Plant,String>()
        val plantId = items


    }

    override fun getItemCount(): Int {
        return list.size
    }
}