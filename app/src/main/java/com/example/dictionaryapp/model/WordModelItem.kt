package com.example.dictionaryapp.model

import java.io.Serializable

data class WordModelItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
) : Serializable