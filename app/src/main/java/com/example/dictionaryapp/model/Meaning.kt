package com.example.dictionaryapp.model
import java.io.Serializable
data class Meaning(
    val antonyms: List<Any>? = null,
    val definitions: List<Definition>? = null,
    val partOfSpeech: String? = null,
    val synonyms: List<Any>? = null
) : Serializable