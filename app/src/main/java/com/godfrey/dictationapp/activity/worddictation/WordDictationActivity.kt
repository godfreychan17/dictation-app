package com.godfrey.dictationapp.activity.worddictation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.result.ResultActivity

class WordDictationActivity : AppCompatActivity() {
    private lateinit var wordDictationViewModel: WordDictationViewModel

    private lateinit var playWordBtn: Button
    private lateinit var previousBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var quitDictationBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textdictation)
        initUI()

        wordDictationViewModel = ViewModelProvider(this)[WordDictationViewModel::class.java]
        wordDictationViewModel.observer.observe(this) {
            when (it) {
                WordDictationViewCommand.FinishDictationCommand -> {
                    startActivity(Intent(this, ResultActivity::class.java))
                }
            }
        }
    }

    private fun initUI() {
        playWordBtn = findViewById(R.id.dictationPlayBtn)

        previousBtn = findViewById(R.id.dictationPreviousBtn)

        nextBtn = findViewById(R.id.dictationNextBtn)
        nextBtn.setOnClickListener {
            wordDictationViewModel.didPressNextBtn()
        }
        quitDictationBtn = findViewById(R.id.dictationEndTestBtn)
    }
}