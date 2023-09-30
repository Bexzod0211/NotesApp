package uz.gita.notesapp.presentation.dialog

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import uz.gita.notesapp.databinding.DialogChangeColorBinding
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.presentation.adapter.ColorAdapter

class ChooseColorDialog(context:Context,resources: Resources) : AlertDialog(context) {
    private lateinit var binding:DialogChangeColorBinding
    private val adapter by lazy { ColorAdapter(resources) }
    private val repository = AppRepository.getInstance()
    private var selectedColorResId = 0
    private lateinit var onBtnChooseClickListener:(Int)->Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChangeColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter.submitList(repository.colorsList)
        binding.apply {
            recyclerColors.adapter = adapter
            recyclerColors.layoutManager = GridLayoutManager(context,5)
        }

        adapter.setOnItemClickListener {
            selectedColorResId = it
        }

        attachClickListeners()
    }

    private fun attachClickListeners(){
        binding.apply {
            btnCancel.setOnClickListener {
                cancel()
            }
            btnChoose.setOnClickListener {
                if (selectedColorResId == 0){
                    Toast.makeText(context, "Choose color", Toast.LENGTH_SHORT).show()
                }else {
                    onBtnChooseClickListener.invoke(selectedColorResId)
                }
            }
        }
    }


    fun setOnBtnChooseClickListener(block:(Int)->Unit){
        onBtnChooseClickListener = block
    }

}