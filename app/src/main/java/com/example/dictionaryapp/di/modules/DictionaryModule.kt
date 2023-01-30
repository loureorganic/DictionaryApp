package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.repository.DictionaryRepository
import com.example.dictionaryapp.repository.RepositoryDictionary
import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import com.example.dictionaryapp.screens.home.viewmodel.HomeViewModel
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {

    @Singleton
    @Provides
    fun provideDictionaryRepository(
        retrofit: RetrofitInstance,
        firebaseDatabase: FirebaseDatabase
    ): RepositoryDictionary {
        return DictionaryRepository(retrofit, firebaseDatabase)
    }

    @Singleton
    @Provides
    fun provideViewModel(repository: DictionaryRepository): ViewModelHome {
        return HomeViewModel(repository)
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabaseInstance(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}

