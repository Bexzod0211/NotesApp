package uz.gita.notesapp.domain.repository

import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.ColorData
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.data.source.local.AppDatabase
import uz.gita.notesapp.data.source.local.entites.NoteEntity

class AppRepository {
    private val noteDao = AppDatabase.getInstance().getNoteDao()
    val colorsList = ArrayList<ColorData>()


    companion object {
        private lateinit var instance:AppRepository

        fun init(){
            if (!::instance.isInitialized){
                instance = AppRepository()
            }
        }
        fun getInstance():AppRepository = instance
    }

    init {
        loadColors()
    }

    fun insertNote(noteData: NoteData){
        noteDao.insertNote(noteData.toEntity())
    }

    fun updateNote(noteData: NoteData){
        noteDao.updateNote(noteData.toEntity())
    }

    fun getAllNotes():List<NoteData>{
        return noteDao.getAllNotes()
    }

    fun getAllNotesInTrash():List<NoteData>{
        return noteDao.getAllNotesInTrash()
    }

    fun searchNotes(query:String):List<NoteData>{
        return noteDao.searchNotesByQuery(query)
    }

    fun moveToTrash(id:Int){
        noteDao.moveToTrash(id)
    }

    fun deleteNote(note:NoteData){
        noteDao.delete(note.toEntity())
    }

    fun deleteAllNotesInTrash(){
        noteDao.deleteAllNotesInTrash()
    }

    fun setPinned(id:Int){
        noteDao.setPinned(id)
    }

    fun setUnpinned(id:Int){
        noteDao.setUnpinned(id)
    }

    fun restore(id:Int){
        noteDao.restore(id)
    }

    private fun loadColors(){
        colorsList.add(ColorData(1,R.color.color1))
        colorsList.add(ColorData(2,R.color.color2))
        colorsList.add(ColorData(3,R.color.color3))
        colorsList.add(ColorData(4,R.color.color4))
        colorsList.add(ColorData(5,R.color.color5))
        colorsList.add(ColorData(6,R.color.color6))
        colorsList.add(ColorData(7,R.color.color7))
        colorsList.add(ColorData(8,R.color.color8))
        colorsList.add(ColorData(9,R.color.color9))
        colorsList.add(ColorData(10,R.color.color10))
        colorsList.add(ColorData(11,R.color.color11))
        colorsList.add(ColorData(12,R.color.color12))
        colorsList.add(ColorData(13,R.color.color13))
        colorsList.add(ColorData(14,R.color.color14))
        colorsList.add(ColorData(15,R.color.color15))
        colorsList.add(ColorData(16,R.color.color16))
        colorsList.add(ColorData(17,R.color.black))
        colorsList.add(ColorData(18,R.color.color17))
        colorsList.add(ColorData(19,R.color.white))
    }

    fun updateBackground(id:Int,bgColor:Int){
        noteDao.updateBackground(id,bgColor)
    }
}