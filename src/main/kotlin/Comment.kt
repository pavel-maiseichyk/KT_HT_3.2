data class Comment(
        val noteId: Int,
        val commentId: Int,
        val replyTo: Int,
        val message: String,
        var isDeleted: Boolean
)