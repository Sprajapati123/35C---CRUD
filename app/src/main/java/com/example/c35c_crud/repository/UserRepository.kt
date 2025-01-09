package com.example.c35c_crud.repository

interface UserRepository {

//    {
//    "success" : true
//     "message" : "Register success"
//    }

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)

//    {
//    "success" : true
//     "message" : "Register success"
//     "userId" : "1234"
//    }

    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit)

    fun addUserToDatabase()

    fun forgetPassword()

    fun getCurrentUser()

}