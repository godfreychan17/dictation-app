package com.godfrey.dictationapp.activity.wordsetting

import com.godfrey.dictationapp.R
import compoment.recycler.wordrecycler.WordItem


data class WordSettingModel(
    var wordList: ArrayList<WordItem> = arrayListOf(WordItem())
) {
    val alertMsgWordEmpty = R.string.alertMsgWordCantBeEmpty
    val alertOkBtn = R.string.alertOkBtn
    var wordListStatus = WordListStatus.Idle

    enum class WordListStatus {
        Idle,
        FinishInput,
    }
}