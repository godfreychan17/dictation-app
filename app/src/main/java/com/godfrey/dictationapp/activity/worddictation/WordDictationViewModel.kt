package com.godfrey.dictationapp.activity.worddictation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordDictationViewModel : ViewModel() {
    private val _observer = MutableLiveData<WordDictationViewCommand>()
    val observer: LiveData<WordDictationViewCommand> get() = _observer

    fun didPressNextBtn() {
        _observer.postValue(WordDictationViewCommand.FinishDictationCommand)
    }

}