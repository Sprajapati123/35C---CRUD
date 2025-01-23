package com.example.c35c_crud.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.c35c_crud.R
import com.example.c35c_crud.adapter.ProductAdapter
import com.example.c35c_crud.databinding.ActivityProductDashboardBinding
import com.example.c35c_crud.repository.ProductRepositoryImpl
import com.example.c35c_crud.viewmodel.ProductViewModel
import java.util.ArrayList

class ProductDashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDashboardBinding

    lateinit var productViewModel: ProductViewModel
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        adapter = ProductAdapter(this@ProductDashboardActivity,
            ArrayList())

        productViewModel.getAllProducts()

        productViewModel.allProducts.observe(this){products->
            products?.let {
                adapter.updateData(it)
            }
        }

        productViewModel.loading.observe(this){loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE

            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this@ProductDashboardActivity,
                AddProductActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = adapter.getProductId(viewHolder.adapterPosition)
                productViewModel.deleteProduct(id){
                    success,message->
                    if(success){

                    }else{

                    }
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}