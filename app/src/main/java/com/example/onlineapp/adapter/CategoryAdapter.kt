package com.example.onlineapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineapp.R
import com.example.onlineapp.activity.CategoryActivity
import com.example.onlineapp.databinding.LayoutCategoryItemBinding
import com.example.onlineapp.model.CategoryModel


class CategoryAdapter(var context:Context,val list:ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.CateViewHodel>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateViewHodel {
        val view=LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false)
        return CateViewHodel(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CateViewHodel, position: Int) {
        holder.binding.textView.text=list[position].cate
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)
        holder.itemView.setOnClickListener{
            val intent=Intent(context,CategoryActivity::class.java)
            intent.putExtra("cate",list[position].cate)
            context.startActivity(intent)
        }
    }

    class CateViewHodel(itemView: View) :RecyclerView.ViewHolder(itemView){
        var binding=LayoutCategoryItemBinding.bind(itemView)

    }
}