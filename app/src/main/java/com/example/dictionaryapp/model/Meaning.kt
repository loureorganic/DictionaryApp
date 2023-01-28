package com.example.dictionaryapp.model
import java.io.Serializable
data class Meaning(
    val antonyms: List<Any>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<Any>
) : Serializable