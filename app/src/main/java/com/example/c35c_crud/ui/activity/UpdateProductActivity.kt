package com.example.c35c_crud.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.c35c_crud.R
import com.example.c35c_crud.databinding.ActivityUpdateProductBinding
import com.example.c35c_crud.model.ProductModel
import com.example.c35c_crud.repository.ProductRepositoryImpl
import com.example.c35c_crud.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProductBinding

    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)


//      if you need to get model passed from another activity
//        var products : ProductModel? = intent.getParcelableExtra("products")
//
//        products.let {
//            binding.updateProductName.setText(it?.productName.toString())
//            binding.updateProductPrice.setText(it?.price.toString())
//            binding.updateProductDesc.setText(it?.productDesc.toString())
//        }

        var id: String = intent.getStringExtra("productId").toString()
        productViewModel.getProductById(id)

        productViewModel.products.observe(this) {
            binding.updateProductName.setText(it?.productName.toString())
            binding.updateProductPrice.setText(it?.price.toString())
            binding.updateProductDesc.setText(it?.productDesc.toString())
        }

        binding.btnUpdate.setOnClickListener {
            val newProductName = binding.updateProductName.text.toString()
            val newProductPrice = binding.updateProductPrice.text.toString().toInt()
            val newProductNDesc = binding.updateProductDesc.text.toString()

            var updatedMap = mutableMapOf<String, Any>()

            updatedMap["productName"] = newProductName
            updatedMap["productDesc"] = newProductNDesc
            updatedMap["price"] = newProductPrice

            productViewModel.updateProduct(id, updatedMap) {
                            success, message ->
                if (success) {
                    Toast.makeText(this@UpdateProductActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateProductActivity, message, Toast.LENGTH_LONG).show()

                }
            }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}