package com.example.dictionaryapp.repository.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val baseUrl = "https://api.dictionaryapi.dev"

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DictionaryRepositoryApi by lazy {
        initRetrofit().create(DictionaryRepositoryApi::class.java)
    }
}
