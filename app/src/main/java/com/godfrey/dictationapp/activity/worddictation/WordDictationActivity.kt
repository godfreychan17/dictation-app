package com.godfrey.dictationapp.activity.worddictation

import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.result.ResultActivity
import com.godfrey.dictationapp.activity.util.AppConstant
import java.util.Locale

class WordDictationActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var wordDictationViewModel: WordDictationViewModel

    private lateinit var speechWordBtn: ImageButton
    private lateinit var previousBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var quitDictationBtn: Button

    private lateinit var progressTxtView : TextView

    private lateinit var textToSpeech: TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worddictation)
        initUI()

        val wordList = intent.getStringArrayExtra(AppConstant.intentWordListKey)
        textToSpeech = TextToSpeech(this, this)

        wordDictationViewModel = ViewModelProvider(this)[WordDictationViewModel::class.java]
        wordDictationViewModel.observer.observe(this) {
            when (it) {
                is WordDictationViewCommand.FinishDictationCommand -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(AppConstant.intentWordListKey, it.wordList.toTypedArray())
                    startActivity(intent)
                }

                is WordDictationViewCommand.RefreshViewCommand -> {
                    progressTxtView.text = " ${(it.index + 1)} / ${it.total}"
                    previousBtn.visibility = if (it.index == 0) {
                        View.INVISIBLE
                    } else {
                        View.VISIBLE
                    }

                    nextBtn.text = if (it.index + 1 == it.total) {
                        getString(R.string.finishBtnTitle)
                    } else {
                        getString(R.string.nextBtnTitle)
                    }

                }

                is WordDictationViewCommand.SpeechWord -> {
                    speakText(it.word)
                }
            }
        }
        wordDictationViewModel.onViewInit(wordList)
    }

    private fun initUI() {
        speechWordBtn = findViewById(R.id.dictationSpeechBtn)
        speechWordBtn.setOnClickListener {
            wordDictationViewModel.didPressSpeechWordBtn()
        }

        previousBtn = findViewById(R.id.dictationPreviousBtn)
        previousBtn.setOnClickListener {
            wordDictationViewModel.didPressPreviousBtn()
        }

        nextBtn = findViewById(R.id.dictationNextBtn)
        nextBtn.setOnClickListener {
            wordDictationViewModel.didPressNextBtn()
        }
        quitDictationBtn = findViewById(R.id.dictationEndTestBtn)
        quitDictationBtn.setOnClickListener {
            wordDictationViewModel.didPressQuitBtn()
        }

        progressTxtView = findViewById(R.id.dictationProgressTxtView)
    }

    private fun speakText(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.UK)
            if (result != TextToSpeech.LANG_MISSING_DATA &&
                result != TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                wordDictationViewModel.onInitTextToSpeechSuccess()
            } else {
                wordDictationViewModel.onInitTextToSpeechMissingLanguage(Locale.UK.displayName)
            }
        } else {
// Initialization failed
            wordDictationViewModel.onInitTextToSpeechFail()
        }
    }
}