package uz.gita.notesapp.app

import android.app.Application
import uz.gita.notesapp.data.source.local.AppDatabase
import uz.gita.notesapp.domain.repository.AppRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        AppRepository.init()
    }
}