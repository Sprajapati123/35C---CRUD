package com.example.c35c_crud.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.c35c_crud.R
import com.example.c35c_crud.databinding.ActivityRegisterBinding
import com.example.c35c_crud.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener {

            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var firstName = binding.registerFname.text.toString()
            var lastName = binding.registerLName.text.toString()
            var address = binding.registerAddress.text.toString()
            var contact = binding.registerContact.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var userId = auth.currentUser?.uid

                        var userModel = UserModel(
                            userId.toString(),
                            firstName, lastName,
                            address, contact,email
                        )

                        ref.child(userId.toString()).setValue(userModel).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Register success",
                                    Toast.LENGTH_LONG
                                ).show()
                            }else{
                                Toast.makeText(
                                    this@RegisterActivity,
                                    it.exception?.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
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