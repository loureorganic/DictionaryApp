package com.example.dictionaryapp.di.modules

import com.example.dictionaryapp.repository.DictionaryRepository
import com.example.dictionaryapp.repository.RepositoryDictionary
import com.example.dictionaryapp.repository.retrofit.RetrofitInstance
import com.example.dictionaryapp.screens.home.viewmodel.HomeViewModel
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
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
    fun provideDictionaryRepository(retrofit: RetrofitInstance): RepositoryDictionary {
        return DictionaryRepository(retrofit)
    }

    @Singleton
    @Provides
    fun provideViewModel(repository: DictionaryRepository): ViewModelHome {
        return HomeViewModel(repository)
    }


}

