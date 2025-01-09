package com.example.c35c_crud.repository

import com.example.c35c_crud.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

//    {
//    "success" : true
//     "message" : "Register success"
//    }

    fun login(email: String, password: String,
              callback: (Boolean, String) -> Unit)

//    {
//    "success" : true
//     "message" : "Register success"
//     "userId" : "1234"
//    }

    fun signup(email: String, password: String,
               callback: (Boolean, String, String) -> Unit)

    fun addUserToDatabase(
        userId: String, userModel: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun forgetPassword(email:String,
                       callback: (Boolean, String) -> Unit)

    fun getCurrentUser() : FirebaseUser?

}