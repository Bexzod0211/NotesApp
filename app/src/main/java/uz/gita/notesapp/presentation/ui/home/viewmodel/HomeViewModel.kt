package uz.gita.notesapp.presentation.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import uz.gita.notesapp.data.model.NoteData

interface HomeViewModel {
    val notesLiveData:MutableLiveData<List<NoteData>>
    val openAddScreenLiveData:MutableLiveData<NoteData?>
    val openActionsDialogLiveData:MutableLiveData<NoteData>
    val openDeleteDialog:MutableLiveData<NoteData>
    val openPaletteDialog:MutableLiveData<Int>
    val placeHolderLiveData:MutableLiveData<Int>

    fun btnAddClicked()
    fun onResume()
    fun searchNotes(query:String)
    fun itemLongClicked(note:NoteData)
    fun updatePin(note: NoteData)
    fun btnDeleteClicked(note: NoteData)
    fun moveToTrash(id: Int)
    fun btnChangeColorClicked(id:Int)
    fun updateBackground(id: Int,bgColor:Int)
    fun itemClicked(note:NoteData)

}