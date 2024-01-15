package com.example.onlineapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.onlineapp.MainActivity
import com.example.onlineapp.R
import com.example.onlineapp.RoomDB.AppDatabase
import com.example.onlineapp.RoomDB.ProductDao
import com.example.onlineapp.RoomDB.ProductModel
import com.example.onlineapp.databinding.ActivityProductDetailsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailsBinding.inflate(layoutInflater)
        getProductDetails(intent.getStringExtra("id"))
        setContentView(binding.root )
    }

    private fun getProductDetails(proId: String?) {
        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list=it.get("productImages")as ArrayList<String>
                val name=it.getString("productName")
                val productSp=it.getString("productSP")
                val productDes=it.getString("productDescription")
                val coverImage=it.getString("productCoverImg")
                 binding.ProductName.text=name
                binding.ProductSp.text=productSp
                binding.ProductDis.text=productDes

                val slideList=ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data,ScaleTypes.CENTER_CROP))
                }
                cartAction(proId,name,productSp,coverImage)
                binding.imageSlider.setImageList(slideList )
            }.addOnFailureListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT)

            }

    }

    private fun cartAction(proId: String, name: String?, productSp: String?, coverImage: String?) {
        val productDao=AppDatabase.getDatabase(this).productDao()
        if (productDao.isExit(proId) != null){
            binding.addCard.text="Go to Cart"
            }else{
            binding.addCard.text="Add to Cart"

        }
        binding.addCard.setOnClickListener {
            if (productDao.isExit(proId) != null){
                binding.addCard.text="Go to Cart"
                openCart()
            }else{
                addToCart(productDao,proId,name,productSp,coverImage)

            }
        }

    }

    private fun addToCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverImage: String?
    ) {
        val data=ProductModel( proId,name,coverImage,productSp)
        lifecycleScope.launch (Dispatchers.IO){
            productDao.insertProduct(data)
            binding.addCard.text="Go to Cart"
        }
    }

    private fun openCart() {
        val preference=this.getSharedPreferences("info", MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}