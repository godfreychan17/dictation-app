package com.godfrey.dictationapp.activity.wordsetting

import compoment.recycler.wordrecycler.WordItem

sealed class WordSettingViewCommand {
    data class UpdateAndReloadWordList(val wordList: ArrayList<WordItem>) : WordSettingViewCommand()
    data class ShowAlertMessage(val msgResID: Int, val positiveBtnResID: Int, val action: () -> Unit) : WordSettingViewCommand()
    data object ProcessToTextDictationActivityCommand : WordSettingViewCommand()
    data class RequestFocusItemFromRecyclerView(val position: Int) : WordSettingViewCommand()
}
