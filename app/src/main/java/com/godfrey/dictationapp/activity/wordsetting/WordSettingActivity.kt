package com.godfrey.dictationapp.activity.wordsetting

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.textdictation.TextDictationActivity
import compoment.recycler.wordrecycler.ItemStatusListener
import compoment.recycler.wordrecycler.WordItem
import compoment.recycler.wordrecycler.WordViewAdapter

class WordSettingActivity : AppCompatActivity(), ItemStatusListener {
    private lateinit var wordSettingViewModel: WordSettingViewModel

    private lateinit var wordRecyclerView: RecyclerView
    private lateinit var confirmBtn: Button

    private val wordViewAdapter = WordViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordsetting)

        wordSettingViewModel = ViewModelProvider(this)[WordSettingViewModel::class.java]
        wordSettingViewModel.observer.observe(this) {
            when (it) {
                is WordSettingViewCommand.ReloadWordListCommand -> {
                    wordViewAdapter.updateItems(it.wordList)
                    wordViewAdapter.notifyDataSetChanged()
                }

                is WordSettingViewCommand.ShowAlertMessage -> {
                    showAlertBox(
                        it.msgResID,
                        it.positiveBtnResID,
                        it.action,
                    )
                }

                WordSettingViewCommand.ProcessToTextDictationActivityCommand -> {
                    startActivity(Intent(this, TextDictationActivity::class.java))
                }
            }
        }
        initUI()
    }

    override fun onBackPressed() {
// Override back function and does nothing
    }

    override fun onResume() {
        super.onResume()
        wordSettingViewModel.onResumeActivity()
    }

    private fun initUI() {
        wordRecyclerView = findViewById(R.id.wordSettingRecyclerView)
        wordRecyclerView.layoutManager = LinearLayoutManager(this)
        wordRecyclerView.adapter = wordViewAdapter

        confirmBtn = findViewById(R.id.wordSettingConfirmBtn)
        confirmBtn.setOnClickListener {
            wordSettingViewModel.didPressConfirmBtn()
        }
    }

    private fun showAlertBox(msgResID: Int, positiveBtnResID: Int, action: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setMessage(getString(msgResID))
            setPositiveButton(positiveBtnResID) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                action()
            }
        }
        var alertDialog = builder.create()
        alertDialog.show()
    }

    //region ItemStatusListener
    override fun onWordChange(word: String, item: WordItem) {
        when (item.type) {
            WordItem.ItemType.Word -> {
                wordSettingViewModel.onUpdateCurrentWord(word, item)
            }

            WordItem.ItemType.Empty -> {
                wordSettingViewModel.onCreateNewWord(word, item)
            }
        }
    }

    //endregion
}
