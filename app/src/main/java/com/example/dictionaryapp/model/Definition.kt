package com.example.dictionaryapp.model

import java.io.Serializable

data class Definition(
    val antonyms: List<Any>? = null,
    val definition: String? = null,
    val example: String? = null,
    val synonyms: List<Any>? = null
) : Serializable