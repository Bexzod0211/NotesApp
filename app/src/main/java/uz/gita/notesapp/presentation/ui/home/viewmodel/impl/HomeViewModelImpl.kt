package uz.gita.notesapp.presentation.ui.home.viewmodel.impl

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.presentation.ui.home.viewmodel.HomeViewModel

class HomeViewModelImpl : ViewModel(), HomeViewModel {
    private val repository = AppRepository.getInstance()
    override val notesLiveData: MutableLiveData<List<NoteData>> = MutableLiveData()
    override val openAddScreenLiveData: MutableLiveData<NoteData?> = MutableLiveData()
    override val openActionsDialogLiveData: MutableLiveData<NoteData> = MutableLiveData()
    override val openDeleteDialog: MutableLiveData<NoteData> = MutableLiveData()
    override val openPaletteDialog: MutableLiveData<Int> = MutableLiveData()
    override val placeHolderLiveData: MutableLiveData<Int> = MutableLiveData()

    init {

        notesLiveData.value = repository.getAllNotes()
    }

    override fun btnAddClicked() {
        openAddScreenLiveData.value = null
    }

    override fun onResume() {
        val notes = repository.getAllNotes()
        Log.d("TTT", "Viewmodel: notes size = ${notes.size}")
        placeHolderVisibility()
        notesLiveData.value = notes
    }

    override fun searchNotes(query: String) {
        if (query == "") {
            Log.d("TTT", "$query")
            notesLiveData.value = repository.getAllNotes()
        } else {
            notesLiveData.value = repository.searchNotes(query)
        }
    }


    override fun itemLongClicked(note: NoteData) {
        openActionsDialogLiveData.value = note
    }

    override fun updatePin(note: NoteData) {
        if (note.pinned == 1) {
            repository.setUnpinned(note.id)
        } else {
            repository.setPinned(note.id)
        }
        notesLiveData.value = repository.getAllNotes()
    }

    override fun btnDeleteClicked(note: NoteData) {
        openDeleteDialog.value = note
    }

    override fun moveToTrash(id: Int) {
        repository.moveToTrash(id)
        placeHolderVisibility()
        notesLiveData.value = repository.getAllNotes()
    }

    override fun btnChangeColorClicked(id: Int) {
        openPaletteDialog.value = id
    }

    override fun updateBackground(id: Int, bgColor: Int) {
        repository.updateBackground(id, bgColor)
        notesLiveData.value = repository.getAllNotes()
    }

    override fun itemClicked(note: NoteData) {
        openAddScreenLiveData.value = note
    }

    private fun placeHolderVisibility() {
        if (repository.getAllNotes().isEmpty()) {
            placeHolderLiveData.value = View.VISIBLE
        } else {
            placeHolderLiveData.value = View.GONE
        }
    }

}