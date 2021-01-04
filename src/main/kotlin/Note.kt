data class Note(
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val privacyView: String = "",
    val privacyComment: String = "",
    var isDeleted: Boolean = false
)
