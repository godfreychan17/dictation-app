package com.godfrey.dictationapp.activity.wordsetting

import compoment.recycler.wordrecycler.WordItem

sealed class WordSettingViewCommand {
    data class ReloadWordListCommand(val wordList: ArrayList<WordItem>) : WordSettingViewCommand()
    data class ShowAlertMessage(val msgResID: Int, val positiveBtnResID: Int, val action: () -> Unit) : WordSettingViewCommand()
    data object ProcessToTextDictationActivityCommand : WordSettingViewCommand()
}
