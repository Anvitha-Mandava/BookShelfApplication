package com.example.bookshelf.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.dao.UserDao
import com.example.bookshelf.data.entities.User
import com.example.bookshelf.data.models.CountryInfo
import com.example.bookshelf.repository.CountryProvider
import com.example.bookshelf.repository.IpApiProvider
import com.example.bookshelf.ui.common.STATUS_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val countryProvider: CountryProvider,
    private val userDao: UserDao,
    private val ipApiProvider: IpApiProvider
) : ViewModel() {
    var statusMessage = MutableLiveData<String>()
    var countries = MutableLiveData<List<CountryInfo>>()
    var defaultCountry = MutableLiveData<CountryInfo>()

    fun loadCountries() {
        viewModelScope.launch {
            try {
                countries.value = countryProvider.getCountries()
                defaultCountry.value = ipApiProvider.getDefaultCountry()
            } catch (e: Exception) {
                statusMessage.value = e.message
            }
        }
    }

    fun signUp(name: String, email: String, password: String, country: String) {
        //ideally should hash and store the password
        viewModelScope.launch {
            try {
                userDao.insertUser(
                    User(
                        name = name, email = email, password = password, country = country
                    )
                )
                statusMessage.value = STATUS_MESSAGE.SIGNUP_SUCCESS.msg
            } catch (e: Exception) {
                statusMessage.value = e.message ?: STATUS_MESSAGE.ERROR.msg
            }
        }
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userDao.findByEmail(email)
                statusMessage.value = if (user != null && password == user.password) {
                    STATUS_MESSAGE.LOGIN_SUCCESS.msg
                } else {
                    "Authentication failed"
                }
            } catch (e: Exception) {
                statusMessage.value = e.message ?: STATUS_MESSAGE.ERROR.msg
            }
        }
    }

    fun isValidPassword(password: String) =
        password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*()_+=|<>?{}\\[\\]~-]).{8,}$".toRegex())
}