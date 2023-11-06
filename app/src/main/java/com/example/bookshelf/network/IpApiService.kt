package com.example.bookshelf.network

import com.example.bookshelf.data.models.IpInfo
import retrofit2.Response
import retrofit2.http.GET

interface IpApiService {
    @GET("json")
    suspend fun getUserCountry(): Response<IpInfo>
}

