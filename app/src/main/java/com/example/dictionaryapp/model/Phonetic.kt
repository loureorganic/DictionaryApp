package com.example.dictionaryapp.model

import java.io.Serializable

data class Phonetic(
    val audio: String? = null,
    val sourceUrl: String? = null,
    val text: String? = null
) : Serializable