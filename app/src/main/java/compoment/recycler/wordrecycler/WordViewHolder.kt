package compoment.recycler.wordrecycler

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R

class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val wordTextView: EditText = itemView.findViewById(R.id.wordItemTextField)

    fun updateWord(word: String) {
        wordTextView.setText(word)
    }
}