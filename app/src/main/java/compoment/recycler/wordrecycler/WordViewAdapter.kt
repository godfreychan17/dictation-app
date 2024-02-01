package compoment.recycler.wordrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.godfrey.dictationapp.R

class WordViewAdapter(private var mListener: ItemStatusListener) : RecyclerView.Adapter<WordViewHolder>() {
    private var itemList = arrayListOf<WordItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_word_view, parent, false)
        return WordViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.updateContent(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItems(items: ArrayList<WordItem>) {
        itemList = items
        notifyDataSetChanged()
    }
}