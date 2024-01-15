package com.example.onlineapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineapp.R
import com.example.onlineapp.adapter.CategoryProductAdaptor
import com.example.onlineapp.adapter.ProductAdaptor
import com.example.onlineapp.databinding.ActivityCategoryBinding
import com.example.onlineapp.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("cate"))
    }

    private fun getProducts(category: String?) {


        val list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){

                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)

                }
                val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
                recyclerView.adapter= CategoryProductAdaptor(this,list)
            }
    }
}