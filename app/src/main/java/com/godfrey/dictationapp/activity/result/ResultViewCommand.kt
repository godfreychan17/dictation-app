package com.godfrey.dictationapp.activity.result

import compoment.recycler.wordrecycler.WordItem

sealed class ResultViewCommand {

    data class RefreshRecyclerView(val wordList: ArrayList<WordItem>) : ResultViewCommand()

    data object QuitToMainPage : ResultViewCommand()

    data object RetryDictation: ResultViewCommand()

}