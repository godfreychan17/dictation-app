package compoment.recycler.wordrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R

class WordViewAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private var wordList = arrayListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_word_view, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentItem = wordList[position]
        holder.updateWord(currentItem)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }
}