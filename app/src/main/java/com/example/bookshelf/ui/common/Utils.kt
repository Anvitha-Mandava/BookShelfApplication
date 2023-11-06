package com.example.bookshelf.ui.common

import com.google.android.material.textfield.TextInputLayout

enum class STATUS_MESSAGE(val msg: String) {
    SIGNUP_SUCCESS("Sign Up Successful"), LOGIN_SUCCESS("User authenticated"), ERROR("An error occurred")
}

inline fun TextInputLayout.validateInput(
    errorMessage: String, validator: (String) -> Boolean
): Boolean {
    editText?.let { editText ->
        error = if (validator(editText.text.toString())) null else errorMessage
        return error == null
    }
    return false
}