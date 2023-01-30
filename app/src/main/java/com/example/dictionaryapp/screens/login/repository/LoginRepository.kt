package com.example.dictionaryapp.screens.login.repository

import com.example.dictionaryapp.model.UserLogin
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface RepositoryLogin {
    fun loginUser(user: UserLogin): Observable<Boolean>
}


@Singleton
class LoginRepository @Inject constructor(
    private val databaseAuthenticator: FirebaseAuth,
) : RepositoryLogin {

    override fun loginUser(user: UserLogin) = Observable.create<Boolean> { emitter ->
        databaseAuthenticator.signInWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                emitter.onNext(true)
            }
            .addOnFailureListener {
                emitter.onNext(false)
            }
    }
}