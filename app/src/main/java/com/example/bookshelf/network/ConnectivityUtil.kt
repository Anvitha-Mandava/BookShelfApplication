package com.example.bookshelf.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.bookshelf.ui.common.getContext

object ConnectivityUtil {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}