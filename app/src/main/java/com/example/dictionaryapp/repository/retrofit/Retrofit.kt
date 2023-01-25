package com.example.dictionaryapp.repository.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    val baseUrl = "https://api.imgflip.com"

    fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("baseUrl")
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DictionaryRepositoryImpl by lazy {
        initRetrofit().create(DictionaryRepositoryImpl::class.java)
    }
}
