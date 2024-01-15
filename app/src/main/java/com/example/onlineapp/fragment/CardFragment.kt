package com.example.onlineapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineapp.R
import com.example.onlineapp.RoomDB.AppDatabase
import com.example.onlineapp.RoomDB.ProductModel
import com.example.onlineapp.activity.AddressActivity
import com.example.onlineapp.activity.CategoryActivity
import com.example.onlineapp.adapter.CardAdaptor
import com.example.onlineapp.databinding.FragmentCardBinding

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding

    private lateinit var list:ArrayList<String>
    private lateinit var listId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCardBinding.inflate(layoutInflater)

        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",false )
        editor.apply()

        val dao=AppDatabase.getDatabase(requireContext()).productDao()

        list=ArrayList()
        listId=""

        dao.getAllProducts().observe(requireActivity()){
            binding.cardRecycler.adapter=CardAdaptor(requireContext(),it)
            list.clear()
            listId=""
            for (data in it){
                list.add(data.productId)
                listId+=data.productId
                listId+=","
            }
            totalCost(it)
        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {

        var total=0
        for (item in data!!){
            total+=item.productSp!!.toInt()
            binding.TotalItem.text="Total item in cart is ${data.size}"
            binding.TotalCost.text="Total Cost :$total"

            binding.checkbox.setOnClickListener {
                val intent= Intent(requireContext(), AddressActivity::class.java)
                val b=Bundle()
                b.putStringArrayList("productIds",list)
                b.putString("totalCost",total.toString())
                b.putString("productids",listId)
                Log.d("Safi hamza ","my list $list")

                intent.putExtras(b)
                startActivity(intent)
            }
        }
    }

}