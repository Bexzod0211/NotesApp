package uz.gita.notesapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.notesapp.data.source.local.converter.DateConverter
import uz.gita.notesapp.data.source.local.dao.NoteDao
import uz.gita.notesapp.data.source.local.entites.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getNoteDao():NoteDao

    companion object {
        private lateinit var instance:AppDatabase

        fun init(context:Context){
            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(context,AppDatabase::class.java,"notes.db")
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance():AppDatabase = instance
    }
}