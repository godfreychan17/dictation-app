package com.godfrey.dictationapp.activity.textdictation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextDictationViewModel : ViewModel() {
    private val _observer = MutableLiveData<TextDictationViewCommand>()
    val observer: LiveData<TextDictationViewCommand> get() = _observer

    fun didPressNextBtn() {
        _observer.postValue(TextDictationViewCommand.FinishDictationCommand)
    }

}