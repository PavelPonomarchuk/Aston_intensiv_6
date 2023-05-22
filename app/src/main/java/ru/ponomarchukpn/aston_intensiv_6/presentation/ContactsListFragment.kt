package ru.ponomarchukpn.aston_intensiv_6.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ponomarchukpn.aston_intensiv_6.R
import ru.ponomarchukpn.aston_intensiv_6.databinding.FragmentContactsListBinding
import ru.ponomarchukpn.aston_intensiv_6.utils.dpToIntegerPixels

class ContactsListFragment : Fragment() {

    private var _binding: FragmentContactsListBinding? = null
    private val binding: FragmentContactsListBinding
        get() = _binding ?: throw RuntimeException("FragmentContactsListBinding == null")

    private val adapter = ContactsAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this)[ContactsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAdapter()
        addItemsDecoration()
        setSearchViewListener()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureAdapter() {
        binding.mainRecycler.adapter = adapter.apply {
            onContactClick = {
                launchDetailsFragment(it.id)
            }
            onContactLongClick = {
                viewModel.removeAndUpdate(it.id)
            }
        }
    }

    private fun addItemsDecoration() {
        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.main_divider)
        binding.mainRecycler.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                divider?.let {
                    setDrawable(it)
                }
            }
        )
        binding.mainRecycler.addItemDecoration(
            SpaceItemDecoration(requireContext().dpToIntegerPixels(VALUE_ITEM_SPACE_DP))
        )
    }

    private fun setSearchViewListener() {
        val listener = object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterContacts(newText)
                return true
            }
        }
        binding.mainSearch.setOnQueryTextListener(listener)
    }

    private fun observeViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailsFragment(id: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ContactDetailsFragment.newInstance(id))
            .addToBackStack(ContactDetailsFragment.NAME)
            .commit()
    }

    companion object {

        private const val VALUE_ITEM_SPACE_DP = 5

        fun newInstance() = ContactsListFragment()
    }
}
