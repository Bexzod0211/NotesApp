package uz.gita.notesapp.data.source.local.dao

import androidx.room.*
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.data.source.local.entites.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note:NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE inTrash = 0 ORDER BY pinned DESC")
    fun getAllNotes():List<NoteData>

    @Query("SELECT * FROM notes WHERE notes.inTrash = 1")
    fun getAllNotesInTrash():List<NoteData>

    @Query("SELECT * FROM notes WHERE inTrash = 0 AND title like :query or content like :query")
    fun searchNotesByQuery(query:String):List<NoteData>

    @Query("UPDATE notes SET inTrash = 1 WHERE notes.id = :id")
    fun moveToTrash(id:Int)

    @Query("UPDATE notes SET pinned = 1 WHERE notes.id = :id")
    fun setPinned(id:Int)

    @Query("UPDATE notes SET pinned = 0 WHERE notes.id = :id")
    fun setUnpinned(id:Int)

    @Delete
    fun delete(note: NoteEntity)

    @Query("DELETE FROM notes WHERE inTrash = 1")
    fun deleteAllNotesInTrash()

    @Query("UPDATE notes SET inTrash = 0 WHERE notes.id = :id")
    fun restore(id:Int)

    @Query("UPDATE notes SET bgColor = :bgColor WHERE notes.id = :id")
    fun updateBackground(id:Int,bgColor:Int)

}