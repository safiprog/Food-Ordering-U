package com.example.onlineapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineapp.R
import com.example.onlineapp.adapter.AllOrderAdapter
import com.example.onlineapp.databinding.FragmentMoreBinding
import com.example.onlineapp.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    private lateinit var list:ArrayList<AllOrderModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMoreBinding.inflate(layoutInflater)
        list= ArrayList()

        val preferences=requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        Firebase.firestore.collection("allOrders").whereEqualTo("userId",preferences.getString("number",""))
            .get().addOnSuccessListener {
            list.clear()
            for(doc in it){
                val data=doc.toObject(AllOrderModel::class.java)
                list.add(data)
            }
            binding.recyclerviewPro.adapter= AllOrderAdapter(list,requireContext())
        }
        return binding.root
    }


}