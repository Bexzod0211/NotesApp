package uz.gita.notesapp.presentation.ui.trash

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ScreenTrashBinding
import uz.gita.notesapp.presentation.adapter.TrashAdapter
import uz.gita.notesapp.presentation.dialog.ActionsDialog
import uz.gita.notesapp.presentation.ui.trash.viewmodel.TrashViewModel
import uz.gita.notesapp.presentation.ui.trash.viewmodel.impl.TrashViewModelImpl


class TrashScreen : Fragment(R.layout.screen_trash) {

    private val binding by viewBinding(ScreenTrashBinding::bind)
    private val viewModel:TrashViewModel by viewModels<TrashViewModelImpl>()
    private val adapter by lazy { TrashAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openActionsDialogLiveData.observe(this,openActionsDialogObserver)
        viewModel.confirmDeleteAllLiveData.observe(this,confirmDeleteAllLiveDataObserver)
        viewModel.confirmRestoreDialogLiveData.observe(this,confirmRestoreDialogObserver)
        viewModel.confirmDeleteDialogLiveData.observe(this,confirmDeleteDialogObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            recyclerTrash.adapter = adapter
            recyclerTrash.layoutManager = GridLayoutManager(requireContext(),2)
        }

        adapter.setOnLongItemClickListener {
            viewModel.itemLongClicked(it)
        }

        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_options,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.btn_delete_all->{
                        viewModel.btnDeleteAllClicked()
                    }
                }
                return true
            }

        },viewLifecycleOwner)


        viewModel.notesLiveData.observe(viewLifecycleOwner,notesObserver)
        viewModel.placeHolderLiveData.observe(viewLifecycleOwner,placeHolderObserver)


    }

    private val confirmRestoreDialogObserver = Observer<NoteData> {
        AlertDialog.Builder(requireContext())
            .setTitle("Restore")
            .setMessage("Are you sure want to restore item ${it.title}?")
            .setPositiveButton("Yes") {d,p0->
                viewModel.restoreNote(it)
                d.cancel()
            }
            .setNegativeButton("No"){d,p0->
                d.cancel()
            }
            .show()
    }

    private val confirmDeleteDialogObserver = Observer<NoteData> {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete forever")
            .setMessage("Are you sure want to delete forever item ${it.title}?")
            .setPositiveButton("Yes") {d,p0->
                viewModel.deleteNote(it)
                d.cancel()
            }
            .setNegativeButton("No"){d,p0->
                d.cancel()
            }
            .show()
    }
    private val openActionsDialogObserver = Observer<NoteData> {
        val dialog = ActionsDialog("trash")
        dialog.setOnDeleteButtonClickListener {
            viewModel.btnDeleteClicked(it)
        }
        dialog.setOnRestoreBtnClickListener {
            viewModel.btnRestoreClicked(it)
        }
        dialog.show(childFragmentManager,"actions")

    }

    private val notesObserver = Observer<List<NoteData>>{
        adapter.submitList(it)
    }

    private val placeHolderObserver = Observer<Int> {
        binding.txtEmpty.visibility = it
    }

    private val confirmDeleteAllLiveDataObserver = Observer<Unit> {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete all")
            .setMessage("Are you sure want to delete all notes in trash?")
            .setPositiveButton("Yes") {d,p0->
                viewModel.clearTrash()
                d.cancel()
            }
            .setNegativeButton("No"){d,p0->
                d.cancel()
            }
            .show()
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu,inflater)
        inflater.inflate(R.menu.menu_options,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.btn_delete_all){
            viewModel.btnDeleteAllClicked()
            true
        }else {
            super.onOptionsItemSelected(item)
        }
    }*/

}

/*private var _binding: FragmentTrashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val trashViewModel =
            ViewModelProvider(this).get(TrashViewModel::class.java)

        _binding = FragmentTrashBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/