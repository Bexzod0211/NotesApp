package uz.gita.notesapp.presentation.ui.saved


import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.ScreenSavedBinding

class SavedScreen : Fragment(R.layout.screen_saved) {
    private val binding by viewBinding(ScreenSavedBinding::bind)




}

/*private var _binding: FragmentSavedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedViewModelImpl =
            ViewModelProvider(this).get(SavedViewModelImpl::class.java)

        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/