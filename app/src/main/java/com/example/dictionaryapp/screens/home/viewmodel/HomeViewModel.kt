package com.example.dictionaryapp.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.model.WordModelItem
import com.example.dictionaryapp.repository.DictionaryRepository
import com.example.dictionaryapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ViewModelHome {
    fun getWord(wordList: ArrayList<Word>)
    fun addList(word: Word)
    fun getList(startAt: String)
    val listResult: LiveData<ArrayList<String>>
    val wordResponse: LiveData<ArrayList<WordModelItem>>
    val wordResponseState: LiveData<State<Unit>>
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DictionaryRepository) : ViewModel(),
    ViewModelHome {


    private val _listResult = MutableLiveData<ArrayList<String>>()
    override val listResult: LiveData<ArrayList<String>> = _listResult

    private val _wordResponse = MutableLiveData<ArrayList<WordModelItem>>()
    override val wordResponse: LiveData<ArrayList<WordModelItem>> = _wordResponse

    private val _listResultState = MutableLiveData<State<Unit>>()
    val listResultState: LiveData<State<Unit>> = _listResultState

    private val _wordResponseState = MutableLiveData<State<Unit>>()
    override val wordResponseState: LiveData<State<Unit>> = _wordResponseState


    override fun getWord(wordList: ArrayList<Word>) {
        var wordList1: ArrayList<WordModelItem> = arrayListOf<WordModelItem>()
        viewModelScope.launch(Dispatchers.IO) {
            _wordResponseState.postValue(State.Loading())
            wordList.map {
                runCatching { it.word?.let { it1 -> repository.getWord(it1) } }
                    .onSuccess { w ->
                        wordList1.add(w!![0])
                        //_wordResponseState.postValue(State.Success(Unit))
                    }
                    .onFailure { error ->
                        //_wordResponseState.postValue(State.Error(error))
                    }
            }
            _wordResponse.postValue(wordList1)
        }

    }


    override fun addList(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repository.addList(word) }
                .onSuccess { val result = it }
                .onFailure { val error = it }
        }
    }

    override fun getList(startAt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listResultState.postValue(State.Loading())
            repository.getWordList(startAt).subscribe({ result ->
                _listResultState.postValue(State.Success(Unit))
                getWord(result)
            }, { error ->
                _listResultState.postValue(State.Error(error))
            })
        }
    }


}