package com.example.dictionaryapp.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ViewModelHome {
    fun getWord(word: String)
    fun addList(word: Word)
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DictionaryRepository) : ViewModel(),
    ViewModelHome {
    override fun getWord(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repository.getWord(word) }
                .onSuccess {
                    val result = it
                }
                .onFailure {
                    val error = it
                }
        }
    }

    override fun addList(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repository.addList(word) }
                .onSuccess { val result = it }
                .onFailure { val error = it }
    }
}


}