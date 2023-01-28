package com.example.dictionaryapp.model

import java.io.Serializable

data class Phonetic(
    val audio: String,
    val sourceUrl: String,
    val text: String
) : Serializable