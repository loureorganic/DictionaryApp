package com.example.dictionaryapp.model

import java.io.Serializable

data class WordModelItem(
    val license: License? = null,
    val meanings: List<Meaning>? = null,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>? = null,
    val sourceUrls: List<String>? = null,
    val word: String? = null
) : Serializable