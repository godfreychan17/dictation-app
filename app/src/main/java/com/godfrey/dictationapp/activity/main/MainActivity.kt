package com.godfrey.dictationapp.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.wordsetting.WordSettingActivity



class MainActivity : AppCompatActivity() {

    private lateinit var startTestBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        startTestBtn = findViewById(R.id.mainStartBtn)
        startTestBtn.setOnClickListener {
            startActivity(Intent(this, WordSettingActivity::class.java))
        }
    }
}


