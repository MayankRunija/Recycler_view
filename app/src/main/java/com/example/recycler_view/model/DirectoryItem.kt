enum class FileType { FOLDER, KOTLIN, XML, GRADLE, CONFIG }

data class DirectoryItem(
    val name: String,
    val level: Int,
    val isFolder: Boolean,
    var isExpanded: Boolean = false,
    val subItems: List<DirectoryItem> = emptyList()
)