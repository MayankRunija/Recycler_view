data class DirectoryItem(
    val name: String,
    val level: Int,
    val isFolder: Boolean,
    var isExpanded: Boolean = false,
    var subItems: List<DirectoryItem> = emptyList(),
    var parent: DirectoryItem? = null,
    var isLoaded: Boolean = false
)