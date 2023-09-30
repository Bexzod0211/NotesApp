package uz.gita.notesapp.data.model

import uz.gita.notesapp.R
import uz.gita.notesapp.data.source.local.entites.NoteEntity
import java.io.Serializable
import java.util.*

data class NoteData(
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: Date = Date(),
    val inTrash: Int = 0,
    val pinned:Int = 0,
    val bgColor:Int = R.color.white
): Serializable {
    fun toEntity() = NoteEntity(id, title, content, date, inTrash,pinned,bgColor)
}
