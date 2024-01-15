package com.example.onlineapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineapp.activity.ProductDetailsActivity
import com.example.onlineapp.databinding.LayoutProductItemBinding
import com.example.onlineapp.model.AddProductModel

class ProductAdaptor(val context:Context,val list:ArrayList<AddProductModel>):
    RecyclerView.Adapter<ProductAdaptor.ProductViewHolder>() {

    class ProductViewHolder(val binding: LayoutProductItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data=list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.productName.text=data.productName
        holder.binding.productCategory.text=data.productCategory
        holder.binding.productPrice.text=data.productMRP
        holder.binding.productSpBtn.text=data.productSP

        holder.binding.button3.setOnClickListener {
            val intent= Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            val intent= Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
    }
}