package uz.gita.notesapp.presentation.dialog

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.DialogActionsBinding
import uz.gita.notesapp.utils.setTransparentBackground

class ActionsDialog(private val name:String) : BottomSheetDialogFragment(R.layout.dialog_actions) {

    private val binding by viewBinding(DialogActionsBinding::bind)
    private lateinit var onPinButtonClickListener:()->Unit
    private lateinit var onDeleteButtonClickListener:()->Unit
    private lateinit var onChangeColorButtonClickListener:()->Unit
    private lateinit var onRestoreButtonClickListener:()->Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        if (name == "home"){
            binding.btnRestore.visibility = View.GONE
        }else if (name == "trash") {
            binding.btnPin.visibility = View.GONE
            binding.btnChangeColor.visibility = View.GONE
        }
        attachClickListeners()
    }
    private fun attachClickListeners(){
        binding.btnHide.setOnClickListener {
            dismiss()
        }
        binding.btnPin.setOnClickListener{
            onPinButtonClickListener.invoke()
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            onDeleteButtonClickListener.invoke()
            dismiss()
        }

        binding.btnChangeColor.setOnClickListener {
            onChangeColorButtonClickListener.invoke()
            dismiss()
        }

        binding.btnRestore.setOnClickListener {
            onRestoreButtonClickListener.invoke()
            dismiss()
        }
    }

    fun setOnPinButtonClickListener(block:()->Unit){
        onPinButtonClickListener = block
    }

    fun setOnDeleteButtonClickListener(block:()->Unit){
        onDeleteButtonClickListener = block
    }

    fun setOnChangeColorButtonClickListener(block:()->Unit){
        onChangeColorButtonClickListener = block
    }

    fun setOnRestoreBtnClickListener(block: () -> Unit){
        onRestoreButtonClickListener = block
    }
}