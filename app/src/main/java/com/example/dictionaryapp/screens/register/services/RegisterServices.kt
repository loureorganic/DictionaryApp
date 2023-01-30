package com.example.dictionaryapp.screens.register.services

import android.util.Patterns
import com.example.dictionaryapp.screens.register.model.UserRegister
import com.example.dictionaryapp.screens.register.repository.RegisterRepository
import com.example.dictionaryapp.screens.register.utils.RegisterConstants
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface ServicesRegister {
    fun dataValidation(user: UserRegister): String
    fun createUserAccount(user: UserRegister): Observable<Boolean>
}

@Singleton
class RegisterServices @Inject constructor(private val repository: RegisterRepository) : ServicesRegister {

    override fun dataValidation(user: UserRegister): String {
        return if (user.name.isEmpty()) {
            RegisterConstants.NAME_EMPTY
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            RegisterConstants.EMAIL_INVALID
        } else if (user.password.isEmpty()) {
            RegisterConstants.PASSWORD_EMPTY
        } else if (user.confirmPassword.isEmpty()) {
            RegisterConstants.CONFIRM_PASSWORD_EMPTY
        } else if (user.password != user.confirmPassword) {
            RegisterConstants.PASSWORD_NOT_MATCH
        } else {
            RegisterConstants.VALID
        }
    }

    override fun createUserAccount(user: UserRegister): Observable<Boolean> {
        return repository.createUserAccount(user)
    }
}