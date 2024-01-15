package com.example.onlineapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.onlineapp.R
import com.example.onlineapp.adapter.CategoryAdapter
import com.example.onlineapp.adapter.ProductAdaptor
import com.example.onlineapp.databinding.FragmentHomeBinding
import com.example.onlineapp.model.AddProductModel
import com.example.onlineapp.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)

        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if (preference.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment_to_cardFragment)
        getCategory()
        getSliderImage()
        getProducts()
        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }

    private fun getProducts() {
        val list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){

                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)

                }
                Log.d("hamzahero", "getData: ${list.toString()}")
//                binding.categoryRecycler.adapter=CategoryAdapter(requireContext(),list)
                  binding.ProductRecycler.adapter=ProductAdaptor(requireContext(),list)
            }
    }

    private fun getCategory() {
        val list=ArrayList<CategoryModel>()
        Firebase.firestore.collection("category")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){

                    val data=doc.toObject(CategoryModel::class.java)
                    list.add(data!!)

                }
                Log.d("hamzahero", "getData: ${list.toString()}")
                binding.categoryRecycler.adapter=CategoryAdapter(requireContext(),list)
            }
    }

}