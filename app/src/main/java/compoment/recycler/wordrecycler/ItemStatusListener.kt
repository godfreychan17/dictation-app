package compoment.recycler.wordrecycler

interface ItemStatusListener {

    fun onWordUpdate(word: String, item: WordItem)
    fun onWordFinishChange(word: String, item: WordItem)
}