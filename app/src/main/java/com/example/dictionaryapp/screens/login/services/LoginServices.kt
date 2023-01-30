package com.example.dictionaryapp.screens.login.services

import androidx.core.util.PatternsCompat
import com.example.dictionaryapp.model.UserLogin
import com.example.dictionaryapp.screens.login.repository.LoginRepository
import com.example.dictionaryapp.screens.login.utils.LoginConstants
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface LoginService {
    fun loginUser(userLogin: UserLogin): Observable<Boolean>
    fun dataValidation(user: UserLogin): String
}

@Singleton
class LoginServices @Inject constructor(private val repository: LoginRepository) : LoginService {


    override fun dataValidation(user: UserLogin): String {
        return if (user.password.isEmpty() && user.password.length >= 6) {
            LoginConstants.EMPTY_PASSWORD
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(user.email).matches()) {
            LoginConstants.INVALID_EMAIL
        } else {
            LoginConstants.VALID
        }
    }

    override fun loginUser(userLogin: UserLogin): Observable<Boolean> {
        return repository.loginUser(userLogin)
    }

}