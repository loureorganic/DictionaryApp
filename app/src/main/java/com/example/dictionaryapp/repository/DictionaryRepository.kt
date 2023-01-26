package com.example.dictionaryapp.repository

import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.model.WordModel
import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface RepositoryDictionary {
    suspend fun getWord(word: String): WordModel
    suspend fun addList(word: Word)
}

@Singleton
class DictionaryRepository @Inject constructor(
    private val retrofit: RetrofitInstance,
    private val firebaseDatabase: FirebaseDatabase
) :
    RepositoryDictionary {
    override suspend fun getWord(word: String) = retrofit.api.getWord(word)
    override suspend fun addList(word: Word) {
        firebaseDatabase.getReference("Words").child(word.id.toString()).setValue(word.word).await()
    }


}