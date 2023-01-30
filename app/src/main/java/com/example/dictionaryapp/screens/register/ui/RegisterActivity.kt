package com.example.dictionaryapp.screens.register.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.databinding.ActivityRegisterBinding
import com.example.dictionaryapp.screens.home.ui.MainActivity
import com.example.dictionaryapp.screens.register.model.UserRegister
import com.example.dictionaryapp.screens.register.utils.RegisterConstants
import com.example.dictionaryapp.screens.register.viewmodel.ViewModelRegister
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var user: UserRegister

    @Inject
    lateinit var viewModel: ViewModelRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    override fun onStart() {
        super.onStart()

        binding.registerBtn.setOnClickListener {
            validateData()
        }
    }


    @SuppressLint("CheckResult")
    private fun validateData() {
        user = UserRegister(
            name = binding.nameEt.text.toString().trim(),
            email = binding.emailEt.text.toString().trim(),
            password = binding.passwordEt.text.toString().trim(),
            confirmPassword = binding.passwordEt.text.toString().trim()
        )

        val result = viewModel.dataValidation(user)

        if (result == RegisterConstants.NAME_EMPTY) {
            Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show()
        } else if (result == RegisterConstants.EMAIL_INVALID) {
            Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        } else if (result == RegisterConstants.PASSWORD_EMPTY) {
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show()
        } else if (result == RegisterConstants.CONFIRM_PASSWORD_EMPTY) {
            Toast.makeText(this, "Confirm password...", Toast.LENGTH_SHORT).show()
        } else if (result == RegisterConstants.PASSWORD_NOT_MATCH) {
            Toast.makeText(this, "Password doesn't match...", Toast.LENGTH_SHORT).show()
        } else {
            createUserAccount(user)
        }
    }

    private fun createUserAccount(user: UserRegister) {
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        viewModel.createUserAccount(user)

        viewModel.errorCreateAccountLiveData.observe(this) { error ->
            if (error) {
                Toast.makeText(
                    this,
                    "Error on trying to create your account, try later soon!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.booleanCreateAccountLiveData.observe(this) { response ->
                        redirectUserDashBoard(response)
                }
            }
        }

    }

    private fun redirectUserDashBoard(accepted: Boolean) {

        progressDialog.setMessage("Saving user info...")

        if (accepted) {
            Toast.makeText(this, "Account created...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed saving user info account", Toast.LENGTH_SHORT).show()
        }
    }

}