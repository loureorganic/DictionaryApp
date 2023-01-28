package com.example.dictionaryapp.model

import java.io.Serializable

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<Any>
) : Serializable