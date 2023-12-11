package com.godfrey.dictationapp.activity.wordsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import repository.WordRepository

class WordSettingViewModel : ViewModel() {

    private val repository = WordRepository()
    private val _observer = MutableLiveData<WordSettingViewCommand>()
    val observer: LiveData<WordSettingViewCommand> get() = _observer
    init {
        loadData()
    }

    private fun loadData() {
        repository.fetchDummyWord()
    }
}