package com.godfrey.dictationapp.activity.worddictation

sealed class WordDictationViewCommand {
    data object FinishDictationCommand : WordDictationViewCommand()
}