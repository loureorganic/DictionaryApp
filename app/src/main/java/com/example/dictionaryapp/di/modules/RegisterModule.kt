package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.screens.register.repository.RegisterRepository
import com.example.dictionaryapp.screens.register.repository.RepositoryRegister
import com.example.dictionaryapp.screens.register.services.RegisterServices
import com.example.dictionaryapp.screens.register.services.ServicesRegister
import com.example.dictionaryapp.screens.register.viewmodel.RegisterViewModel
import com.example.dictionaryapp.screens.register.viewmodel.ViewModelRegister
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {


    @Singleton
    @Provides
    fun provideRegisterRepository(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase
    ): RepositoryRegister {
        return RegisterRepository(firebaseAuth, firebaseDatabase)
    }

    @Singleton
    @Provides
    fun provideRegisterServices(
        repository: RegisterRepository
    ): ServicesRegister {
        return RegisterServices(repository)
    }

    @Singleton
    @Provides
    fun provideRegisterViewModel(services: RegisterServices): ViewModelRegister {
        return RegisterViewModel(services)
    }


}