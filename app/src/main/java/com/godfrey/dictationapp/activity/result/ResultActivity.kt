package com.godfrey.dictationapp.activity.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.godfrey.dictationapp.R

class ResultActivity : AppCompatActivity() {
    private lateinit var resultViewModel: ResultViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        resultViewModel = ViewModelProvider(this)[ResultViewModel::class.java]
    }
}