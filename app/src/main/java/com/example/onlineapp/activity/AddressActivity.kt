package com.example.onlineapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.onlineapp.R
import com.example.onlineapp.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences:SharedPreferences
    private lateinit var totalCost:String
    private lateinit var allids:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences=this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost=intent.getStringExtra("totalCost")!!
        allids=intent.getStringExtra("productids")!!
        Log.d("kali","linux is $allids")

        loadUserInfo()
        binding.proceed.setOnClickListener { 
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userPin.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userRoom.text.toString()
            )
        }
    }

    private fun validateData(
        number: String,
        name: String,
        pinCode: String,
        city: String,
        state: String,
        room: String
    ) {
        if (number.isEmpty() || state.isEmpty() || pinCode.isEmpty() || 
            city.isEmpty() || state.isEmpty() || room.isEmpty())
            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show()
        else
            storeData(pinCode,city,state,room)
    }

    private fun storeData(
        pinCode: String,
        city: String,
        state: String,
        room: String
    ) {
        val map= hashMapOf<String,Any>()
        map["roomNo"]=room
        map["state"]=state
        map["city"]=city
        map["pincode"]=pinCode
        
        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b=Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                b.putString("productids",allids)
                val intent= Intent(this, CheckoutActivity::class.java)

                intent.putExtras(b)

                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this, "Something went SORRY", Toast.LENGTH_SHORT).show()

            }
    }

    private fun loadUserInfo() {
        

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","12")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userRoom.setText(it.getString("roomNo"))
                binding.userState.setText(it.getString("state"))
                binding.userCity.setText(it.getString("city"))
                binding.userPin.setText(it.getString("pincode"))
                
            }.addOnFailureListener {

            }
    }
}