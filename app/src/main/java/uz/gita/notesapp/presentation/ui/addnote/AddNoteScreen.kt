package uz.gita.notesapp.presentation.ui.addnote

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.ScreenAddNoteBinding
import uz.gita.notesapp.presentation.dialog.ChooseColorDialog
import uz.gita.notesapp.presentation.ui.addnote.viewmodel.AddViewModel
import uz.gita.notesapp.presentation.ui.addnote.viewmodel.impl.AddViewModelImpl
import java.util.Date

class AddNoteScreen : Fragment(R.layout.screen_add_note) {
    private val binding by viewBinding(ScreenAddNoteBinding::bind)
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()
    private val args: AddNoteScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickListeners()

        binding.apply {
            inputContent.setPadding(16, 16, 8, 8)
            inputContent.setPlaceholder("Input text here")
            inputContent.setFontSize(32)
        }

        setTags()

        viewModel.toastLiveData.observe(viewLifecycleOwner, toastObserver)
        viewModel.openChooseColorDialogLiveData.observe(viewLifecycleOwner, openChooseColorDialogObserver)
        viewModel.setTxtColorToRichEditor.observe(viewLifecycleOwner, setTxtColorToRichEditorObserver)

        binding.inputContent.focusEditor()

        args.note?.let {
            if (it.title != "No title") {
                binding.inputTitle.setText(it.title)
            }
            binding.inputContent.html = it.content
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }


    private val toastObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val openChooseColorDialogObserver = Observer<Unit> {
        val dialog = ChooseColorDialog(requireContext(), resources)
        dialog.setOnBtnChooseClickListener {
            viewModel.btnChooseColorClicked(it)
            dialog.cancel()
        }
        dialog.show()
    }

    private fun attachClickListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            actionBold.setOnClickListener {
                changeImageViewTint(it.tag as Boolean, actionBold)
                inputContent.setBold()
            }
            actionItalic.setOnClickListener {
                changeImageViewTint(it.tag as Boolean, actionItalic)
                inputContent.setItalic()
            }
            actionStrikethrough.setOnClickListener {
                changeImageViewTint(it.tag as Boolean, actionStrikethrough)
                inputContent.setStrikeThrough()
            }
            actionUnderline.setOnClickListener {
                changeImageViewTint(it.tag as Boolean, actionUnderline)
                inputContent.setUnderline()
            }
            /*  actionSubscript.setOnClickListener {
                  changeImageViewTint(it.tag as Boolean,actionSubscript)
                  inputContent.setSubscript()
              }
              actionSuperscript.setOnClickListener {
                  changeImageViewTint(it.tag as Boolean,actionSuperscript)
                  inputContent.setSuperscript()
              }*/
            actionUndo.setOnClickListener {
                inputContent.undo()
            }
            actionRedo.setOnClickListener {
                inputContent.redo()
            }
            actionTxtColor.setOnClickListener {
                viewModel.btnTextColorClicked()
            }

            inputContent.setOnTextChangeListener {
                if (it == "") {
                    setUnselectedToButtons()
                }
            }
            actionSave.setOnClickListener {
                save()
                findNavController().popBackStack()
            }
        }


    }

    private fun save() {
        var content = ""
        binding.apply {
            content = if (inputContent.html.isNullOrEmpty()) {
                ""
            } else {
                inputContent.html.toString()
            }
            var id = 0
            var date = Date()
            var inTrash = 0
            var pinned = 0
            var bgColor = R.color.white
            args.note?.let {
                id = it.id
                date = it.date
                inTrash = it.inTrash
                pinned = it.pinned
                bgColor = it.bgColor
            }

            viewModel.btnBackClicked(id, inputTitle.text.toString(), content, date, inTrash, pinned, bgColor)
        }
    }

    private fun setUnselectedToButtons() {
        binding.apply {
            setTags()
            actionBold.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_rich_buttons))
            actionItalic.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_rich_buttons))
            actionStrikethrough.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_rich_buttons))
            actionUnderline.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_rich_buttons))
        }
    }

    private fun changeImageViewTint(tag: Boolean, img: ImageButton) {
        if (tag) {
            img.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_rich_buttons))
        } else {
            img.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
        }
        img.tag = !tag
    }

    private fun setTags() {
        binding.apply {
            actionBold.tag = false
            actionItalic.tag = false
            actionStrikethrough.tag = false
            actionUnderline.tag = false
        }
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

    }

    private val setTxtColorToRichEditorObserver = Observer<Int> {
        binding.inputContent.setTextColor(resources.getColor(it))
    }

}