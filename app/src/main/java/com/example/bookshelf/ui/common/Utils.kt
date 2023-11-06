package com.example.bookshelf.ui.common

import android.content.Context
import com.example.bookshelf.MyApplication
import com.google.android.material.textfield.TextInputLayout
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

enum class STATUS_MESSAGE(val msg: String) {
    SIGNUP_SUCCESS("Sign Up Successful"), LOGIN_SUCCESS("User authenticated"), ERROR("An error occurred")
}

fun TextInputLayout.validate(
    errorMessage: String, validator: (String) -> Boolean
): Boolean {
    val text = editText?.text.toString()
    error = if (validator(text)) null else errorMessage
    return error == null
}

fun getContext(): Context = MyApplication.instance.applicationContext

fun getString(strRes: Int): String {
    return getContext().getString(strRes)
}

fun Long.getYearFromTimestamp() = ZonedDateTime.ofInstant(
    Instant.ofEpochSecond(this), ZoneId.systemDefault()
).year