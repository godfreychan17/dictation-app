package com.godfrey.dictationapp.activity.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import compoment.recycler.wordrecycler.WordItem

class ResultViewModel : ViewModel() {

    private lateinit var model: ResultModel

    private val _observer = MutableLiveData<ResultViewCommand>()
    val observer: LiveData<ResultViewCommand> get() = _observer

    fun initView(wordList: Array<String>?) {
        var itemList = arrayListOf<WordItem>()
        wordList?.forEach {
            val item = WordItem(it, WordItem.ItemType.DisplayOnly)
            itemList.add(item)
        }
        model = ResultModel(itemList)
        _observer.postValue(ResultViewCommand.RefreshRecyclerView(model.wordList))
    }

    fun didPressRetryBtn() {

    }

    fun didPressFinishBtn() {

    }
}