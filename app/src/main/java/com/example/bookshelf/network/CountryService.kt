package com.example.bookshelf.network

import com.example.bookshelf.data.models.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("v1/countries")
    suspend fun getCountries(): Response<CountryResponse>
}

