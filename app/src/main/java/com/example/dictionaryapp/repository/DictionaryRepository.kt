package com.example.dictionaryapp.repository

import com.example.dictionaryapp.model.WordModel
import com.example.dictionaryapp.repository.retrofit.DictionaryRepositoryApi
import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

interface RepositoryDictionary {
    suspend fun getWord(word: String): WordModel
}

@Singleton
class DictionaryRepository @Inject constructor(private val retrofit: RetrofitInstance) :
    RepositoryDictionary {
    override suspend fun getWord(word: String) = retrofit.api.getWord(word)


}