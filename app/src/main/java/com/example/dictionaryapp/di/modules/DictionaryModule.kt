package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import org.koin.dsl.module


val dictionaryModule = module {

    factory {
        val retrofitHelper = RetrofitInstance()
        retrofitHelper.initRetrofit()
    }

}