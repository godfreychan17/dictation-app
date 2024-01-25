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
        _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingViewModel.wordList))
    }

    fun onUpdateWordItem(word: String, wordItem: WordItem) {
        if (word.isNotEmpty()) {
            //Only update the word if it is not empty
            wordItem.word = word
        }
    }

    fun onFinishInputWordItem(word: String, wordItem: WordItem) {
        when (wordItem.type) {
            WordItem.ItemType.Word -> {
                if (word.isNotEmpty()) {
                    wordItem.word = word
                } else {
                    _observer.postValue(
                        WordSettingViewCommand.ShowAlertMessage(
                            wordSettingViewModel.alertMsgWordEmpty,
                            wordSettingViewModel.alertOkBtn,
                        ) {
                            _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingViewModel.wordList))
                        },
                    )
                }
            }

            WordItem.ItemType.Empty -> {
                if (word.isNotEmpty()) {
                    createNewWord(word, wordItem)
                    wordSettingViewModel.wordListStatus = WordSettingModel.WordListStatus.FinishInput
                    _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingViewModel.wordList))
                } else {
                    _observer.postValue(
                        WordSettingViewCommand.ShowAlertMessage(
                            wordSettingViewModel.alertMsgWordEmpty,
                            wordSettingViewModel.alertOkBtn,
                        ) {
                            _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingViewModel.wordList))
                        },
                    )
                }
            }
        }
    }

    private fun createNewWord(word: String, wordItem: WordItem) {
        wordItem.type = WordItem.ItemType.Word
        wordItem.word = word
        wordSettingViewModel.wordList.add(WordItem())
    }

    private fun shuffleWordList() : ArrayList<String>{
        val wordList = (wordSettingViewModel.wordList.filter { it.type == WordItem.ItemType.Word}.map { it.word } as ArrayList)
        wordList.shuffled()
        return wordList
    }

    fun didPressConfirmBtn() {
        val shuffledWordList = shuffleWordList()
        _observer.postValue(WordSettingViewCommand.ProcessToTextDictationActivityCommand(shuffledWordList))
    }

    fun onWordListRecyclerViewFinishUpdate() {
        if (wordSettingViewModel.wordListStatus == WordSettingModel.WordListStatus.FinishInput) {
            wordSettingViewModel.wordListStatus = WordSettingModel.WordListStatus.Idle
            _observer.postValue(
                WordSettingViewCommand.RequestFocusItemFromRecyclerView(
                    wordSettingViewModel.wordList.size - 1
                )
            )
        }
    }
}