package uz.gita.notesapp.presentation.ui.addnote.viewmodel

import androidx.lifecycle.MutableLiveData
import java.util.Date

interface AddViewModel {
    val toastLiveData:MutableLiveData<String>
    val openChooseColorDialogLiveData:MutableLiveData<Unit>
    val setTxtColorToRichEditor:MutableLiveData<Int>

    fun btnTextColorClicked()
    fun btnBackClicked(id:Int,title:String,content:String,date:Date,inTrash:Int,pinned:Int,bgColor:Int)
    fun btnChooseColorClicked(colorResId:Int)

}