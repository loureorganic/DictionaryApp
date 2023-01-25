package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): RetrofitInstance {
        return RetrofitInstance()
    }
}