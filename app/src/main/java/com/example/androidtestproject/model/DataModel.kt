package com.example.androidtestproject.model

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("data") val userModel: List<UserModel>,
    val page: Int,
    val per_page: Int,
    val supportModel: SupportModel,
    val total: Int,
    val total_pages: Int
)