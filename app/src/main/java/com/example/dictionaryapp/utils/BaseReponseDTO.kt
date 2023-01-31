package com.example.dictionaryapp.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


abstract class BaseResponseDto(
    var error: BaseErrorDto? = null
) : Parcelable

data class BaseErrorDto(
    val code: String? = null,
    val title: String? = null,
    val message: String = "Não foi possível completar sua solicitação, por favor, tente novamente.",
    @SerializedName("default-message") val defaultMessage: String?= null
) : Serializable
