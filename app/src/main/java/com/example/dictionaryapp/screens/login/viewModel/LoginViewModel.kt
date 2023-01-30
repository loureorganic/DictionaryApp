package com.example.dictionaryapp.screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.model.UserLogin
import com.example.dictionaryapp.screens.login.services.LoginServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


interface ViewModelLogin {
    val booleanLoginAccountLiveData: MutableLiveData<Boolean>
    val errorLoginAccountLiveData: MutableLiveData<Boolean>
    fun loginUser(userLogin: UserLogin)
    fun dataValidation(user: UserLogin): String
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val services: LoginServices) : ViewModel(),
    ViewModelLogin {

    private val loginAccountLiveData = MutableLiveData<Boolean>()
    override val booleanLoginAccountLiveData: MutableLiveData<Boolean> = loginAccountLiveData

    private val errLoginAccountLiveData = MutableLiveData<Boolean>()
    override val errorLoginAccountLiveData: MutableLiveData<Boolean> = errLoginAccountLiveData

    @SuppressLint("CheckResult")
    override fun loginUser(userLogin: UserLogin) {
        services.loginUser(userLogin = userLogin)
            .subscribe({ result ->
                errLoginAccountLiveData.postValue(false)
                booleanLoginAccountLiveData.postValue(result)
            }, { error ->
                errorLoginAccountLiveData.postValue(true)
                Log.e("ERROR", "LoginViewModel $error")
            })
    }

    override fun dataValidation(user: UserLogin): String {
        return services.dataValidation(user = user)
    }
}