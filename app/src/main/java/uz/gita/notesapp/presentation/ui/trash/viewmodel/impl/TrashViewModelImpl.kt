package uz.gita.notesapp.presentation.ui.trash.viewmodel.impl

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.presentation.ui.trash.viewmodel.TrashViewModel

class TrashViewModelImpl: ViewModel(),TrashViewModel {
    private val repository = AppRepository.getInstance()

    override val notesLiveData: MutableLiveData<List<NoteData>> = MutableLiveData()
    override val openActionsDialogLiveData: MutableLiveData<NoteData> = MutableLiveData()
    override val placeHolderLiveData: MutableLiveData<Int> = MutableLiveData()
    override val confirmDeleteDialogLiveData: MutableLiveData<NoteData> = MutableLiveData()
    override val confirmRestoreDialogLiveData: MutableLiveData<NoteData> = MutableLiveData()
    override val confirmDeleteAllLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun itemLongClicked(note: NoteData) {
        openActionsDialogLiveData.value = note
    }

    override fun btnRestoreClicked(note: NoteData) {
        confirmRestoreDialogLiveData.value = note
    }

    override fun btnDeleteClicked(note: NoteData) {
        confirmDeleteDialogLiveData.value = note
    }

    override fun btnDeleteAllClicked() {
        confirmDeleteAllLiveData.value = Unit
    }

    override fun restoreNote(note: NoteData) {
        repository.restore(note.id)
        loadData()
    }

    override fun deleteNote(note: NoteData) {
        repository.deleteNote(note)
        loadData()
    }

    override fun clearTrash() {
        repository.deleteAllNotesInTrash()
        loadData()
    }

    init {
       loadData()
    }


    private fun loadData(){
        notesLiveData.value = repository.getAllNotesInTrash()
        if (repository.getAllNotesInTrash().isEmpty()){
            placeHolderLiveData.value = View.VISIBLE
        }else {
            placeHolderLiveData.value = View.GONE

        }
    }

}