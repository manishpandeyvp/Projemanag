package com.example.projemanag.models

data class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val mobileNum: Long = 0,
    val fcmToken: String = ""
)