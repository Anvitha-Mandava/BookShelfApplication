package com.example.bookshelf.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bookshelf.ui.common.STATUS_MESSAGE
import com.example.bookshelf.ui.viewModels.UserViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.emailEditText.setOnKeyListener { _, _, _ ->
            binding.emailTextInputLayout.error = null
            false
        }
        binding.passwordEditText.setOnKeyListener { _, _, _ ->
            binding.passwordTextInputLayout.error = null
            false
        }
    }

    private fun observeViewModel() {
        viewModel.statusMessage.observe(this) { message ->
            if (message == STATUS_MESSAGE.LOGIN_SUCCESS.msg) {
                navigateToListPage()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToListPage() {
        startActivity(Intent(this, BookListActivity::class.java))
        finish()
    }

    fun attemptLogin(v: View) {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextInputLayout.error = getString(R.string.error_invalid_email)
        }

        if (password.isEmpty() || password.length < 6) {
            binding.passwordTextInputLayout.error = getString(R.string.error_invalid_password)
        }
        viewModel.authenticateUser(email, password)
    }
}