package com.example.onlineapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.onlineapp.MainActivity
import com.example.onlineapp.R
import com.example.onlineapp.RoomDB.AppDatabase
import com.example.onlineapp.RoomDB.ProductModel
import com.example.onlineapp.databinding.ActivityCheckoutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutActivity : AppCompatActivity() {
    private lateinit var builder:AlertDialog
    private lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder= AlertDialog.Builder(this)
            .setTitle("Loading..")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()
        uploadData()


    }

    private fun uploadData() {
        val id=intent.getStringExtra("productids")!!.split(",").toTypedArray()
        val k=id.toMutableList()
        k.removeLast()
        for (currentId in k!!){
            Log.d("kalil","kkk $currentId")
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String) {

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {


                val dao=AppDatabase.getDatabase(this).productDao()
                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(productId))
                }

                saveData(it.getString("productName")
                    ,it.getString("productSP")
                ,productId)
            }

//        Log.d("hellobrother","this run in site$productId")
    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val data= hashMapOf<String, Any>()
        data["name"]=name!!
        data["price"]=price!!
        data["productId"]=productId!!
        data["status"]="Ordered"
        data["userId"]=preferences.getString("number","")!!

        val firestore=Firebase.firestore.collection("allOrders")
        val key=firestore.document().id
        data["orderId"]=key

        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "order placed", Toast.LENGTH_SHORT).show()
            builder.dismiss()
            startActivity(Intent(this,MainActivity::class.java))

        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}