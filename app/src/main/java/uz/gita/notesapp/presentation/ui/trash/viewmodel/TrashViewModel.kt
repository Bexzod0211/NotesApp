package uz.gita.notesapp.presentation.ui.trash.viewmodel

import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.model.NoteData


interface TrashViewModel {
    val notesLiveData:MutableLiveData<List<NoteData>>
    val openActionsDialogLiveData:MutableLiveData<NoteData>
    val placeHolderLiveData:MutableLiveData<Int>
    val confirmDeleteDialogLiveData:MutableLiveData<NoteData>
    val confirmRestoreDialogLiveData:MutableLiveData<NoteData>
    val confirmDeleteAllLiveData:MutableLiveData<Unit>

    fun itemLongClicked(note: NoteData)
    fun btnRestoreClicked(note:NoteData)
    fun btnDeleteClicked(note: NoteData)
    fun btnDeleteAllClicked()
    fun restoreNote(note: NoteData)
    fun deleteNote(note: NoteData)
    fun clearTrash()

}