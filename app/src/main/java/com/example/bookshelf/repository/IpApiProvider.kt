package com.example.bookshelf.repository

import com.example.bookshelf.data.models.CountryInfo
import com.example.bookshelf.network.ConnectivityUtil
import com.example.bookshelf.util.ApiException
import com.example.bookshelf.util.NoConnectivityException
import com.example.bookshelf.network.IpApiService
import javax.inject.Inject

class IpApiProvider @Inject constructor(val ipApiService: IpApiService, val connectivityUtil: ConnectivityUtil) {
    suspend fun getDefaultCountry(): CountryInfo {
        if (!connectivityUtil.isNetworkAvailable()) {
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