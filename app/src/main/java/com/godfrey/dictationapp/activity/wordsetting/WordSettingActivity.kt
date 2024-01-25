package com.godfrey.dictationapp.activity.wordsetting

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R
import com.godfrey.dictationapp.activity.worddictation.WordDictationActivity
import compoment.recycler.wordrecycler.ItemStatusListener
import compoment.recycler.wordrecycler.WordItem
import compoment.recycler.wordrecycler.WordViewAdapter
import compoment.recycler.wordrecycler.WordViewHolder


class WordSettingActivity : AppCompatActivity(), ItemStatusListener, ViewTreeObserver.OnPreDrawListener {
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
                is WordSettingViewCommand.UpdateAndReloadWordList -> {
                    wordViewAdapter.updateItems(it.wordList)
                }

                is WordSettingViewCommand.ShowAlertMessage -> {
                    showAlertBox(
                        it.msgResID,
                        it.positiveBtnResID,
                        it.action,
                    )
                }

                is WordSettingViewCommand.RequestFocusItemFromRecyclerView -> {
                    val wordViewHolder = wordRecyclerView.findViewHolderForAdapterPosition(it.position)
                    if (wordViewHolder != null) {
                        (wordViewHolder as WordViewHolder).focusItem()
                    }
                }

                is WordSettingViewCommand.ProcessToTextDictationActivityCommand -> {
                    val intent = Intent(this, WordDictationActivity::class.java)
                    intent.putExtra("Words", it.wordList)
                    startActivity(intent)
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

    override fun onDestroy() {
        super.onDestroy()
        wordRecyclerView.viewTreeObserver.removeOnPreDrawListener(this)
    }

    private fun initUI() {
        wordRecyclerView = findViewById(R.id.wordSettingRecyclerView)
        wordRecyclerView.layoutManager = LinearLayoutManager(this)
        wordRecyclerView.adapter = wordViewAdapter
// Add listener to check is recycler view completed its layout
        wordRecyclerView.viewTreeObserver.addOnPreDrawListener(this)

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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    //region ItemStatusListener

    override fun onWordUpdate(word: String, item: WordItem) {
        wordSettingViewModel.onUpdateWordItem(word, item)
    }

    override fun onWordFinishChange(word: String, item: WordItem) {
        wordSettingViewModel.onFinishInputWordItem(word, item)
    }

    //endregion

    //region ViewTreeObserver.OnPreDrawListener
    override fun onPreDraw(): Boolean {
        wordSettingViewModel.onWordListRecyclerViewFinishUpdate()
        return true
    }
    //endregion
}
