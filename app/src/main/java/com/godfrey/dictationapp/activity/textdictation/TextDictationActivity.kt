package com.godfrey.dictationapp.activity.textdictation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.result.ResultActivity

class TextDictationActivity : AppCompatActivity() {
    private lateinit var textDictationViewModel: TextDictationViewModel

    private lateinit var playWordBtn: Button
    private lateinit var previousBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var quitDictationBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textdictation)
        initUI()

        textDictationViewModel = ViewModelProvider(this)[TextDictationViewModel::class.java]
        textDictationViewModel.observer.observe(this) {
            when (it) {
                TextDictationViewCommand.FinishDictationCommand -> {
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
            textDictationViewModel.didPressNextBtn()
        }
        quitDictationBtn = findViewById(R.id.dictationEndTestBtn)
    }
}