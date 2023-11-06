package com.example.bookshelf.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountryResponse(
    val status: String,
    @SerializedName("status-code") val statusCode: Int,
    val version: String,
    val access: String,
    val data: Map<String, CountryInfo>
) : Serializable
