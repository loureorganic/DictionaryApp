package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.screens.login.repository.LoginRepository
import com.example.dictionaryapp.screens.login.repository.RepositoryLogin
import com.example.dictionaryapp.screens.login.services.LoginService
import com.example.dictionaryapp.screens.login.services.LoginServices
import com.example.dictionaryapp.screens.login.LoginViewModel
import com.example.dictionaryapp.screens.login.ViewModelLogin
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginRepository(
        databaseAuthenticator: FirebaseAuth
    ): RepositoryLogin {
        return LoginRepository(databaseAuthenticator)
    }

    @Singleton
    @Provides
    fun provideLoginServices(repository: LoginRepository): LoginService {
        return LoginServices(repository)
    }

    @Singleton
    @Provides
    fun provideLoginViewModel(services: LoginServices): ViewModelLogin {
        return LoginViewModel(services)
    }

}