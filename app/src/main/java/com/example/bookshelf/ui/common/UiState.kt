package com.example.bookshelf.ui.common

import androidx.lifecycle.MutableLiveData

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val exception: Exception) : UiState()
}

fun <T> MutableLiveData<UiState>.setLoading() {
    this.value = UiState.Loading
}

fun <T> MutableLiveData<UiState>.setSuccess() {
    this.value = UiState.Success
}

fun <T> MutableLiveData<UiState>.setError(exception: Exception) {
    this.value = UiState.Error(exception)
}

