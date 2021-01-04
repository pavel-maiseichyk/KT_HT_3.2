import java.lang.RuntimeException
import kotlin.random.Random

class NotesCore {
    var notes: MutableMap<Int, Note> = mutableMapOf()
    var comments: MutableMap<Int, Comment> = mutableMapOf()

    class NoteNotFoundException(private val text: String) : RuntimeException(text)
    class CommentNotFoundException(private val text: String) : RuntimeException(text)

    fun add(
            title: String,
            text: String,
    ) {
        val newId = generateNoteId()
        notes.put(newId, Note(title = title, text = text, isDeleted = false, id = newId))
    }

    fun generateNoteId(): Int {
        val newId = Random.nextInt()
        for (note in notes) if (newId == note.value.id) generateNoteId()
        return newId
    }

    fun generateCommentId(): Int {
        val newId = Random.nextInt()
        for (comment in comments) if (newId == comment.value.commentId) generateCommentId()
        return newId
    }

    fun createComment(
            noteId: Int,
            replyTo: Int,
            message: String,
    ) {
        val newId = generateCommentId()
        comments.put(newId, Comment(noteId = noteId, replyTo = replyTo, message = message, isDeleted = false, commentId = newId))
    }

    fun delete(noteId: Int) {
        getNoteById(noteId).isDeleted = true
    }

    fun deleteComment(commentId: Int) {
        getCommentById(commentId).isDeleted = true
    }

    fun edit(noteId: Int, title: String,
             text: String) {
        for (note in notes) if (noteId == note.value.id) {
            getNoteById(noteId).copy(id = noteId, title = title, text = text)
        } else throw NoteNotFoundException("note wasn't found, lol")
    }

    fun editComment(commentId: Int, message: String) {
        for (comment in comments) if (commentId == comment.value.commentId) {
            getCommentById(commentId).copy(commentId = commentId, message = message)
        } else throw CommentNotFoundException("comment wasn't found, lol")
    }

    fun get(noteIds: String): List<String> = noteIds.split(",")

    fun getNoteById(noteId: Int): Note {
        for (note in notes) {
            if (noteId == note.value.id) return note.value
        }
        throw NoteNotFoundException("note wasn't found, lol")
    }

    fun getCommentById(commentId: Int): Comment {
        for (comment in comments) {
            if (commentId == comment.value.commentId) return comment.value
        }
        throw CommentNotFoundException("comment wasn't found, lol")
    }

    fun restoreComment(commentId: Int) {
        for (comment in comments) {
            if (commentId == comment.value.commentId) getCommentById(commentId).isDeleted = false
            else throw CommentNotFoundException("comment wasn't found, lol")
        }
    }
}