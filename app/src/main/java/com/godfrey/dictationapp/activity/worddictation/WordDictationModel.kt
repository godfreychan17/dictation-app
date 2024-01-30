package com.godfrey.dictationapp.activity.worddictation

import kotlinx.coroutines.processNextEventInCurrentThread

data class WordDictationModel(
    val wordList: List<String> = listOf(),
) {
    var currentIndex = 0

    fun reset() {
        currentIndex = 0
    }

    fun getWordByIndex(idx: Int): String {
        return wordList[idx]
    }
}