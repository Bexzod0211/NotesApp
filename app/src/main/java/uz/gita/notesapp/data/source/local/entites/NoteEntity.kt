package uz.gita.notesapp.data.source.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.notesapp.R
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title: String,
    val content: String,
    val date: Date = Date(),
    val inTrash: Int = 0,
    val pinned:Int = 0,
    val bgColor:Int = R.color.white
)
