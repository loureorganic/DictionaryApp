package com.example.dictionaryapp.screens.register.model

data class UserRegister(
    val name: String,
    val email : String,
    val password : String,
    val confirmPassword: String,
)