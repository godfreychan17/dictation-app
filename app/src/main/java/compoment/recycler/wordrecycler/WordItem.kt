package compoment.recycler.wordrecycler
data class WordItem(
    var word: String = "",
    var type: ItemType = ItemType.Empty,
) {
    enum class ItemType {
        Empty,
        Word,
    }
}