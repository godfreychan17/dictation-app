package com.godfrey.dictationapp.activity.wordsetting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.textdictation.TextDictationActivity
import compoment.recycler.wordrecycler.WordViewAdapter

class WordSettingActivity : AppCompatActivity() {
    private lateinit var wordSettingViewModel: WordSettingViewModel

    private lateinit var wordRecyclerView: RecyclerView
    private lateinit var confirmBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordsetting)

        wordSettingViewModel = ViewModelProvider(this)[WordSettingViewModel::class.java]
        wordSettingViewModel.observer.observe(this) {
            when (it) {
                WordSettingViewCommand.ReloadWordListCommand -> {
                }

                WordSettingViewCommand.ProcessToTextDictationActivityCommand -> {
                    startActivity(Intent(this, TextDictationActivity::class.java))
                }
            }
        }
        initUI()
    }

    private fun initUI() {
        wordRecyclerView = findViewById(R.id.wordSettingRecyclerView)
        wordRecyclerView.layoutManager = LinearLayoutManager(this)
        wordRecyclerView.adapter = WordViewAdapter()

        confirmBtn = findViewById(R.id.wordSettingConfirmBtn)
        confirmBtn.setOnClickListener {
            wordSettingViewModel.didPressConfirmBtn()
        }
    }
}
