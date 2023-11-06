package com.example.bookshelf.repository

import com.example.bookshelf.data.models.CountryInfo
import com.example.bookshelf.network.ConnectivityUtil.isNetworkAvailable
import com.example.bookshelf.util.ApiException
import com.example.bookshelf.util.NoConnectivityException
import com.example.bookshelf.network.CountryService
import javax.inject.Inject

class CountryProvider @Inject constructor(
    private val countryService: CountryService
) {

    suspend fun getCountries(): List<CountryInfo> {
        if (!isNetworkAvailable()) {
            throw NoConnectivityException()
        }
        val response = countryService.getCountries()
        if (response.isSuccessful) {
            return response.body()?.data?.values?.toList() ?: emptyList()
        } else {
            throw ApiException(response.message())
        }
    }
}
