package com.godfrey.dictationapp.activity.textdictation

sealed class TextDictationViewCommand {
    data object FinishDictationCommand : TextDictationViewCommand()
}