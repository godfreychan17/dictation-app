package compoment.recycler.wordrecycler

interface ItemStatusListener {
    fun onWordChange(word: String, item: WordItem)
}