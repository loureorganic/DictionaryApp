package com.example.dictionaryapp.repository

import android.util.Log
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.model.WordModel
import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


interface RepositoryDictionary {
    suspend fun getWord(word: String): WordModel
    suspend fun addList(word: Word)
    suspend fun getWordList(): Observable<ArrayList<String>>
}

@Singleton
class DictionaryRepository @Inject constructor(
    private val retrofit: RetrofitInstance,
    private val firebaseDatabase: FirebaseDatabase
) : RepositoryDictionary {
    override suspend fun getWord(word: String) = retrofit.api.getWord(word)

    override suspend fun addList(word: Word) {
        firebaseDatabase.getReference("Words").child(word.id.toString()).setValue(word.word).await()
    }

    override suspend fun getWordList() = Observable.create<ArrayList<String>> { emitter ->

        firebaseDatabase.getReference("Words").ref.limitToFirst(15)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    emitter.onNext(dataSnapshot.value as ArrayList<String>)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onNext(arrayListOf())
                    Log.i("FIREBASE ERROR", "Error ${databaseError.message}")
                }
            })
    }
}