package com.example.dictionaryapp.utils

import com.google.gson.Gson
import retrofit2.HttpException

object errorHandling {

    fun convertErrorBody(throwable: HttpException): BaseResponseDto? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                Gson().fromJson(it, BaseResponseDto::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}