package com.example.dictionaryapp.screens.register.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.screens.register.model.UserRegister
import com.example.dictionaryapp.screens.register.services.ServicesRegister
import javax.inject.Inject

interface ViewModelRegister {
    val booleanCreateAccountLiveData: MutableLiveData<Boolean>
    val errorCreateAccountLiveData: MutableLiveData<Boolean>
    fun createUserAccount(user: UserRegister)
    fun dataValidation(user: UserRegister): String
}

class RegisterViewModel @Inject constructor(private val services : ServicesRegister) : ViewModel(), ViewModelRegister {

    private val createAccountLiveData = MutableLiveData<Boolean>()
    override val booleanCreateAccountLiveData: MutableLiveData<Boolean> = createAccountLiveData

    private val errorAccountLiveData = MutableLiveData<Boolean>()
    override val errorCreateAccountLiveData: MutableLiveData<Boolean> = errorAccountLiveData

    override fun dataValidation(user: UserRegister): String {
        return services.dataValidation(user = user)
    }

    @SuppressLint("CheckResult")
    override fun createUserAccount(user: UserRegister) {
        val response = services.createUserAccount(user = user)
        response.subscribe({ result ->
            errorCreateAccountLiveData.postValue(false)
            booleanCreateAccountLiveData.postValue(result)
        }, { error ->
            errorCreateAccountLiveData.postValue(true)
            Log.e("ERROR", "RegisterViewModel $error")
        })
    }

}