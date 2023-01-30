package com.example.dictionaryapp.screens.login.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dictionaryapp.databinding.ActivityLoginBinding
import com.example.dictionaryapp.model.UserLogin
import com.example.dictionaryapp.screens.home.ui.MainActivity
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import com.example.dictionaryapp.screens.login.ViewModelLogin
import com.example.dictionaryapp.screens.login.utils.LoginConstants
import com.example.dictionaryapp.screens.register.ui.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    @Inject
    lateinit var viewModel: ViewModelLogin


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            val user = UserLogin(
                email = binding.emailEt.text.toString().trim(),
                password = binding.passwordEt.text.toString().trim()
            )
            validateData(user = user)
        }

    }

    @SuppressLint("CheckResult")
    private fun validateData(user: UserLogin) {

        val result = viewModel.dataValidation(user)

        if (result == LoginConstants.INVALID_EMAIL) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
        } else if (result == LoginConstants.EMPTY_PASSWORD) {
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show()
        } else if (result == LoginConstants.VALID) {
            loginAccount(user)
        }
    }


    private fun loginAccount(user: UserLogin) {
        viewModel.loginUser(user)
        viewModel.errorLoginAccountLiveData.observe(this) { result ->
            if (result) {
                Toast.makeText(
                    this,
                    "Error on trying to log in, try later soon!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.booleanLoginAccountLiveData.observe(this) { logged ->
                    redirectDashBoardUser(logged = logged)
                }
            }
        }
    }

    private fun redirectDashBoardUser(logged: Boolean) {
        if (logged) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Email or password are invalid", Toast.LENGTH_SHORT).show()
        }
    }


}