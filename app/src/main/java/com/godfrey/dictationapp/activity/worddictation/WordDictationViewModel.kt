package com.godfrey.dictationapp.activity.worddictation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordDictationViewModel : ViewModel() {
    private val _observer = MutableLiveData<WordDictationViewCommand>()
    val observer: LiveData<WordDictationViewCommand> get() = _observer
    private lateinit var model: WordDictationModel

    fun onViewInit(wordList: Array<String>?) {
        model = if (wordList != null) {
            WordDictationModel(wordList.toList())
        } else {
            WordDictationModel()
        }
        model.reset()
    }

    fun onInitTextToSpeechMissingLanguage(langName: String) {
// Handle language data missing or not supported
    }

    fun onInitTextToSpeechSuccess() {

    }

    fun onInitTextToSpeechFail() {

    }


    fun didPressNextBtn() {
        model.currentIndex += 1
        if (model.currentIndex >= model.wordList.size) {
            _observer.postValue(WordDictationViewCommand.FinishDictationCommand)
        } else {
            _observer.postValue(WordDictationViewCommand.RefreshViewCommand(model.currentIndex, model.wordList.size))
        }
    }

    fun didPressPreviousBtn() {
        model.currentIndex -= 1
        _observer.postValue(WordDictationViewCommand.RefreshViewCommand(model.currentIndex, model.wordList.size))
    }

    fun didPressQuitBtn() {

    }

    fun didPressSpeechWordBtn() {
        _observer.postValue(WordDictationViewCommand.SpeechWord(model.getWordByIndex(model.currentIndex)))
    }


}