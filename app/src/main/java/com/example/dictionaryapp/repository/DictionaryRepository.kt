package com.example.dictionaryapp.repository

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
    suspend fun getWordList(): Observable<ArrayList<Word>>
}

@Singleton
class DictionaryRepository @Inject constructor(
    private val retrofit: RetrofitInstance,
    private val firebaseDatabase: FirebaseDatabase
) : RepositoryDictionary {
    override suspend fun getWord(word: String) = retrofit.api.getWord(word)

    override suspend fun addList(word: Word) {
        firebaseDatabase.getReference("Words").child(word.id.toString()).setValue(word).await()
    }

    override suspend fun getWordList() = Observable.create<ArrayList<Word>> { emitter ->
        //firebaseDatabase.getReference("Words").ref.orderByChild("word").startAt(wordInitial).endAt(wordFinal)
        val arraylistWord = arrayListOf<Word>()
        firebaseDatabase.getReference("Words").limitToFirst(20)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(Word::class.java)
                            user?.let { arraylistWord.add(it) }
                        }
                        emitter.onNext(arraylistWord)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }
}