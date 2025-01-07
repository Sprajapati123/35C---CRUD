package com.example.c35c_crud.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.c35c_crud.R
import com.example.c35c_crud.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener {

            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()

           auth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener {
               if(it.isSuccessful){
                   var userId = auth.currentUser?.uid
                   Toast.makeText(this@RegisterActivity,
                       "Register success",
                       Toast.LENGTH_LONG
                       ).show()
               }else{
                   Toast.makeText(this@RegisterActivity,
                       it.exception?.message.toString(),
                       Toast.LENGTH_LONG
                   ).show()
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