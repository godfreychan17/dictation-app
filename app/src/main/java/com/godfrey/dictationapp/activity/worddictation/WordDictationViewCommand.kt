package com.godfrey.dictationapp.activity.worddictation

sealed class WordDictationViewCommand {

    data class RefreshViewCommand(val index: Int, val total: Int) : WordDictationViewCommand()
    data class SpeechWord(val word: String): WordDictationViewCommand()
    data class FinishDictationCommand(val wordList: List<String>) : WordDictationViewCommand()
}