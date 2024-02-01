package com.godfrey.dictationapp.activity.wordsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import compoment.recycler.wordrecycler.WordItem

class WordSettingViewModel : ViewModel() {

    private val _observer = MutableLiveData<WordSettingViewCommand>()
    val observer: LiveData<WordSettingViewCommand> get() = _observer

    private val wordSettingModel = WordSettingModel()
    init {
        loadData()
    }

    private fun loadData() {

    }

    fun onResumeActivity() {
        _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingModel.wordList))
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
                            wordSettingModel.alertMsgWordEmpty,
                            wordSettingModel.alertOkBtn,
                        ) {
                            _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingModel.wordList))
                        },
                    )
                }
            }

            WordItem.ItemType.Empty -> {
                if (word.isNotEmpty()) {
                    createNewWord(word, wordItem)
                    wordSettingModel.wordListStatus = WordSettingModel.WordListStatus.FinishInput
                    _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingModel.wordList))
                } else {
                    _observer.postValue(
                        WordSettingViewCommand.ShowAlertMessage(
                            wordSettingModel.alertMsgWordEmpty,
                            wordSettingModel.alertOkBtn,
                        ) {
                            _observer.postValue(WordSettingViewCommand.UpdateAndReloadWordList(wordSettingModel.wordList))
                        },
                    )
                }
            }

            WordItem.ItemType.DisplayOnly -> {

            }
        }
    }

    private fun createNewWord(word: String, wordItem: WordItem) {
        wordItem.type = WordItem.ItemType.Word
        wordItem.word = word
        wordSettingModel.wordList.add(WordItem())
    }

    private fun shuffleWordList() : List<String>{
        val wordList = arrayListOf("String", "Word", "Help", "Happy", "Good", "Communication")// (wordSettingViewModel.wordList.filter { it.type == WordItem.ItemType.Word}.map { it.word } as ArrayList)
        return wordList.shuffled()
    }

    fun didPressConfirmBtn() {
        val shuffledWordList = shuffleWordList()
        _observer.postValue(WordSettingViewCommand.ProcessToTextDictationActivityCommand(shuffledWordList))
    }

    fun onWordListRecyclerViewFinishUpdate() {
        if (wordSettingModel.wordListStatus == WordSettingModel.WordListStatus.FinishInput) {
            wordSettingModel.wordListStatus = WordSettingModel.WordListStatus.Idle
            _observer.postValue(
                WordSettingViewCommand.RequestFocusItemFromRecyclerView(
                    wordSettingModel.wordList.size - 1
                )
            )
        }
    }
}