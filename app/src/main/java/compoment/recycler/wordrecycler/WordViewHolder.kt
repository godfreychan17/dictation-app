package compoment.recycler.wordrecycler

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R

class WordViewHolder(itemView: View, mListener: ItemStatusListener) : RecyclerView.ViewHolder(itemView) {
    private val wordEditTextView: EditText = itemView.findViewById(R.id.wordItemTextField)

    init {
        wordEditTextView.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                mListener.onWordFinishChange(textView.text.toString(), textView.tag as WordItem)
            }
            false
        }

        wordEditTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, end: Int, count: Int) {
                mListener.onWordUpdate(p0.toString(), wordEditTextView.tag as WordItem)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    fun updateContent(item: WordItem) {
        wordEditTextView.tag = item
        if (item.type == WordItem.ItemType.Word) {
            wordEditTextView.setText(item.word)
        } else {
            wordEditTextView.setText("")
        }
    }

    fun focusItem() {
        wordEditTextView.requestFocus()
        val context = itemView.context
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(wordEditTextView, InputMethodManager.SHOW_IMPLICIT)
    }
}