package com.godfrey.dictationapp.activity.wordsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import compoment.recycler.wordrecycler.WordItem

class WordSettingViewModel : ViewModel() {

    private val _observer = MutableLiveData<WordSettingViewCommand>()
    val observer: LiveData<WordSettingViewCommand> get() = _observer

    private val wordSettingViewModel = WordSettingModel()
    init {
        loadData()
    }

    private fun loadData() {

    }

    fun onResumeActivity() {
        _observer.postValue(WordSettingViewCommand.ReloadWordListCommand(wordSettingViewModel.wordList))
    }

    fun onCreateNewWord(word: String, wordItem: WordItem) {
        wordItem.type = WordItem.ItemType.Word
        wordItem.word = word
        wordSettingViewModel.wordList.add(WordItem())
        _observer.postValue(WordSettingViewCommand.ReloadWordListCommand(wordSettingViewModel.wordList))
    }

    fun onUpdateCurrentWord(word: String, wordItem: WordItem) {
        if (word.isNotEmpty()) {
            wordItem.word = word
        } else {
            _observer.postValue(
                WordSettingViewCommand.ShowAlertMessage(
                    wordSettingViewModel.alertMsgWordEmpty,
                    wordSettingViewModel.alertOkBtn,
                ) {
                    _observer.postValue(WordSettingViewCommand.ReloadWordListCommand(wordSettingViewModel.wordList))
                },
            )
        }
    }

    fun didPressConfirmBtn() {
        _observer.postValue(WordSettingViewCommand.ProcessToTextDictationActivityCommand)
    }
}