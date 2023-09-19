package com.example.whatsapp_clone.model

data class UserModel(
    val uid: String ?= "",
    val name: String ?= "",
    val number: String ?= "",
    val imageUrl: String ?= ""
)
