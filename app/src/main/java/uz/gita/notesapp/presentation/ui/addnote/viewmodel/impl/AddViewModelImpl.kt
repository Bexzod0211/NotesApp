package uz.gita.notesapp.presentation.ui.addnote.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.presentation.ui.addnote.viewmodel.AddViewModel
import java.util.*

class AddViewModelImpl : ViewModel(),AddViewModel {
    private val repository = AppRepository.getInstance()

    override val toastLiveData: MutableLiveData<String> = MutableLiveData()
    override val openChooseColorDialogLiveData: MutableLiveData<Unit> = MutableLiveData()
    override val setTxtColorToRichEditor: MutableLiveData<Int> = MutableLiveData()

    override fun btnTextColorClicked() {
        openChooseColorDialogLiveData.value = Unit
    }

    override fun btnBackClicked(id: Int, title: String, content: String, date: Date, inTrash: Int, pinned: Int, bgColor: Int) {
        var _title = title
        if (content.trim().isNotEmpty()){
            if (title.trim().isEmpty()){
                _title = "No title"
            }
            repository.insertNote(NoteData(id = id, title = _title, content = content,date,inTrash,pinned,bgColor))
            toastLiveData.value = "Saved!"
        }
        else {
            toastLiveData.value = "Nothing saved!"
        }
    }

    override fun btnChooseColorClicked(colorResId: Int) {
        setTxtColorToRichEditor.value = colorResId
    }


}