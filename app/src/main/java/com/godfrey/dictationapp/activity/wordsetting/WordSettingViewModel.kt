package com.godfrey.dictationapp.activity.wordsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordSettingViewModel : ViewModel() {

    private val _observer = MutableLiveData<WordSettingViewCommand>()
    val observer: LiveData<WordSettingViewCommand> get() = _observer
    init {
        loadData()
    }

    private fun loadData() {

    }

    fun didPressConfirmBtn() {
        _observer.postValue(WordSettingViewCommand.ProcessToTextDictationActivityCommand)
    }

}