package com.example.bookshelf.repository

import com.example.bookshelf.data.models.CountryInfo
import com.example.bookshelf.network.ConnectivityUtil.isNetworkAvailable
import com.example.bookshelf.util.ApiException
import com.example.bookshelf.util.NoConnectivityException
import com.example.bookshelf.network.IpApiService
import javax.inject.Inject

class IpApiProvider @Inject constructor(val ipApiService: IpApiService) {
    suspend fun getDefaultCountry(): CountryInfo {
        if (!isNetworkAvailable()) {
            throw NoConnectivityException()
        }
        val response = ipApiService.getUserCountry()
        if (response.isSuccessful) {
            val country = response.body()?.country ?: throw Exception("Country not found")
            val countryCode = response.body()?.countryCode ?: ""
            return CountryInfo(country, countryCode)
        } else {
            throw ApiException(response.message())
        }
    }
}