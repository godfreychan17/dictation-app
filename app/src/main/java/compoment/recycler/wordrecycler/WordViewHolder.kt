package compoment.recycler.wordrecycler

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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
                mListener.onWordChange(textView.text.toString(), textView.tag as WordItem)
            }
            false
        }
    }

    fun updateContent(item: WordItem) {
        wordEditTextView.tag = item
        if (item.type == WordItem.ItemType.Word) {
            wordEditTextView.setText(item.word)
        } else {
            wordEditTextView.setText("")
        }
    }
}