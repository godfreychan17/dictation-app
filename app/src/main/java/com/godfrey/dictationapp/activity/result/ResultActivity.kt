package com.godfrey.dictationapp.activity.result

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.util.AppConstant
import compoment.recycler.wordrecycler.ItemStatusListener
import compoment.recycler.wordrecycler.WordItem
import compoment.recycler.wordrecycler.WordViewAdapter

class ResultActivity : AppCompatActivity(), ItemStatusListener {
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var retryBtn: Button
    private lateinit var finishBtn: Button
    private lateinit var resultRecyclerView: RecyclerView
    private val wordViewAdapter = WordViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val wordList = intent.getStringArrayExtra(AppConstant.intentWordListKey)

        retryBtn = findViewById(R.id.resultRetryBtn)
        retryBtn.setOnClickListener {
            resultViewModel.didPressRetryBtn()
        }

        finishBtn = findViewById(R.id.resultFinishBtn)
        finishBtn.setOnClickListener {
            resultViewModel.didPressFinishBtn()
        }

        resultRecyclerView = findViewById(R.id.resultRecyclerView)
        resultRecyclerView.layoutManager = LinearLayoutManager(this)
        resultRecyclerView.adapter = wordViewAdapter

        resultViewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        resultViewModel.initView(wordList)
        resultViewModel.observer.observe(this) {
            when (it) {
                is ResultViewCommand.RefreshRecyclerView -> {
                    wordViewAdapter.updateItems(it.wordList)
                }

                ResultViewCommand.QuitToMainPage -> {
                }

                ResultViewCommand.RetryDictation -> {
                    this.finish()
                }
            }
        }

    }

    override fun onWordUpdate(word: String, item: WordItem) {
    }

    override fun onWordFinishChange(word: String, item: WordItem) {
    }
}