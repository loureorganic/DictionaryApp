package com.example.dictionaryapp.screens.register.repository

import android.util.Log
import com.example.dictionaryapp.screens.register.model.UserRegister
import com.example.dictionaryapp.utils.ApplicationConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface RepositoryRegister {
    fun createUserAccount(user: UserRegister): Observable<Boolean>
}

@Singleton
class RegisterRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : RepositoryRegister {


    override fun createUserAccount(user: UserRegister) = Observable.create<Boolean> { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val timestamp = System.currentTimeMillis()

                val uid = firebaseAuth.uid

                val hashMap: HashMap<String, Any?> = HashMap()
                hashMap["uid"] = uid
                hashMap["email"] = user.email
                hashMap["name"] = user.name
                hashMap["password"] = user.password
                hashMap["profileImage"] = ""
                hashMap["userType"] = "user"
                hashMap["timestamp"] = timestamp

                val ref = firebaseDatabase.getReference(
                    ApplicationConstants.FIREBASE_USERS
                )
                if (uid != null) {
                    ref.child(uid).setValue(hashMap)
                        .addOnSuccessListener {
                            emitter.onNext(true)
                        }
                        .addOnFailureListener {
                            emitter.onNext(false)
                        }
                }
            }.addOnFailureListener {
                Log.i("FIREBASE ERROR", "Error $it")
            }
    }
}