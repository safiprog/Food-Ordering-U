package com.example.onlineapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineapp.activity.ProductDetailsActivity
import com.example.onlineapp.databinding.ItemCategoryProductLayoutBinding
import com.example.onlineapp.model.AddProductModel

class CategoryProductAdaptor(val context:Context,val list:ArrayList<AddProductModel>):RecyclerView.Adapter<CategoryProductAdaptor.CategoryProductAdaptorVH>() {

    class CategoryProductAdaptorVH(val binding: ItemCategoryProductLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductAdaptorVH {
        val binding=ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductAdaptorVH(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductAdaptorVH, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView3)
        holder.binding.textView2.text=list[position].productName
        holder.binding.textView3.text=list[position].productSP
        holder.itemView.setOnClickListener {
            val intent=Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
    }
}