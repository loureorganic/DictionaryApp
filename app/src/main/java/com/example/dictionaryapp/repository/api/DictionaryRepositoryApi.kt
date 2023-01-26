package com.example.dictionaryapp.repository.api

import com.example.dictionaryapp.model.WordModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryRepositoryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word: String
    ): WordModel

}