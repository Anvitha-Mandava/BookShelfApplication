package com.example.bookshelf.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookshelf.data.models.CountryInfo
import com.example.bookshelf.ui.common.STATUS_MESSAGE
import com.example.bookshelf.ui.common.validateInput
import com.example.bookshelf.ui.viewModels.UserViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        viewModel.loadCountries()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this) { countries ->
            viewModel.defaultCountry.observe(this) { defaultCountry ->
                setupCountryDropdown(countries, defaultCountry)
            }
        }

        viewModel.statusMessage.observe(this) { message ->
            if (message == STATUS_MESSAGE.SIGNUP_SUCCESS.msg) {
                navigateToLoginScreen(null)
            } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateToLoginScreen(v: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun setupCountryDropdown(countries: List<CountryInfo>, defaultCountry: CountryInfo) {
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_dropdown_item_1line, countries.map(CountryInfo::country)
        )
        binding.countryAutoCompleteTextView.apply {
            setAdapter(adapter)
            setText(defaultCountry.country, false)
        }
    }

    fun attemptSignUp(v: View) {
        if (validateAllInputs()) {
            viewModel.signUp(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.countryAutoCompleteTextView.text.toString()
            )
        }
    }

    private fun validateAllInputs(): Boolean = listOf(
        binding.nameTextInputLayout to getString(R.string.error_field_required),
        binding.emailTextInputLayout to getString(R.string.error_invalid_email),
        binding.passwordTextInputLayout to getString(R.string.error_invalid_password),
        binding.countryTextInputLayout to getString(R.string.error_field_required)
    ).all { (inputLayout, errorMessage) ->
        inputLayout.validateInput(errorMessage) { input ->
            when (inputLayout) {
                binding.emailTextInputLayout -> input.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
                    input
                ).matches()

                binding.passwordTextInputLayout -> input.isNotBlank() && viewModel.isValidPassword(
                    input
                )

                else -> input.isNotBlank()
            }
        }
    }
}