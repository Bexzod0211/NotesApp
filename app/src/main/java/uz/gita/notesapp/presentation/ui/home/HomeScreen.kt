package uz.gita.notesapp.presentation.ui.home


import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ScreenHomeBinding
import uz.gita.notesapp.presentation.adapter.NoteAdapter
import uz.gita.notesapp.presentation.dialog.ActionsDialog
import uz.gita.notesapp.presentation.dialog.ChooseColorDialog
import uz.gita.notesapp.presentation.ui.home.viewmodel.HomeViewModel
import uz.gita.notesapp.presentation.ui.home.viewmodel.impl.HomeViewModelImpl

class HomeScreen : Fragment(R.layout.screen_home) {

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val adapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddScreenLiveData.observe(this, openAddScreenObserver)

        viewModel.openActionsDialogLiveData.observe(this,openActionsDialogObserver)
        viewModel.openDeleteDialog.observe(this,openDeleteDialogObserver)
        viewModel.openPaletteDialog.observe(this,openPaletteDialogObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onResume()

        adapter.setOnLongItemClickListener {
            viewModel.itemLongClicked(it)
        }

        adapter.setOnItemClickListener {
            viewModel.itemClicked(it)
        }

        binding.apply {
            recyclerMain.adapter = adapter
            recyclerMain.layoutManager = GridLayoutManager(requireContext(), 2)
        }




        attachClickListeners()

        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)
        viewModel.placeHolderLiveData.observe(viewLifecycleOwner,placeHolderObserver)
    }

    private val placeHolderObserver = Observer<Int> {
        binding.txtPlaceholder.visibility = it
    }

    private val openDeleteDialogObserver = Observer<NoteData> {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setMessage("Are you sure want to move to trash item ${it.title} ?")
            .setPositiveButton("Yes"){d,p0->
                viewModel.moveToTrash(it.id)
                d.cancel()
            }
            .setNegativeButton("No"){d,p0->
                d.cancel()
            }
            .show()
    }

    private val openActionsDialogObserver = Observer<NoteData> {
        val dialog = ActionsDialog("home")
        dialog.setOnPinButtonClickListener {
            viewModel.updatePin(it)
        }
        dialog.setOnDeleteButtonClickListener {
            viewModel.btnDeleteClicked(it)
        }
        dialog.setOnChangeColorButtonClickListener {
            viewModel.btnChangeColorClicked(it.id)
        }
        dialog.show(childFragmentManager,"action")
    }

    private val notesObserver = Observer<List<NoteData>> {
        Log.d("TTT",  "Observer: notes size = ${it.size}")
        adapter.submitList(it)
    }

    private val openPaletteDialogObserver = Observer<Int> {id->
        val dialog = ChooseColorDialog(requireContext(),resources)
        dialog.setOnBtnChooseClickListener { bgColor->
            viewModel.updateBackground(id,bgColor)
            dialog.cancel()
        }
        dialog.show()
    }

    private fun attachClickListeners() {
        binding.btnAdd.setOnClickListener {
            viewModel.btnAddClicked()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNotes(query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("TTT","onQueryTextChange")
                viewModel.searchNotes(newText?:"")
                return true
            }

        })
    }

    private val openAddScreenObserver = Observer<NoteData?> {
        findNavController().navigate(HomeScreenDirections.actionNavHomeToAddNoteScreen(it))
    }

}


