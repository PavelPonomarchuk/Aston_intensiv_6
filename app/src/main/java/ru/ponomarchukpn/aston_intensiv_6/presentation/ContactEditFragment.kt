package ru.ponomarchukpn.aston_intensiv_6.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import ru.ponomarchukpn.aston_intensiv_6.R
import ru.ponomarchukpn.aston_intensiv_6.databinding.FragmentContactEditBinding

class ContactEditFragment : Fragment() {

    private var contactId = UNDEFINED_ID
    private var valuesLoaded = false

    private var _binding: FragmentContactEditBinding? = null
    private val binding: FragmentContactEditBinding
        get() = _binding ?: throw RuntimeException("FragmentContactEditBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[ContactEditViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreFlagLoaded(savedInstanceState)
        observeViewModel()
        setButtonSaveListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VALUES_LOADED, true)
    }

    private fun parseArguments() {
        val args = requireArguments()

        if (!args.containsKey(KEY_CONTACT_ID)) {
            throw RuntimeException("Argument contact id is absent")
        }
        contactId = args.getInt(KEY_CONTACT_ID)
    }

    private fun restoreFlagLoaded(state: Bundle?) {
        state?.let {
            valuesLoaded = it.getBoolean(KEY_VALUES_LOADED)
        }
    }

    private fun observeViewModel() {
        viewModel.loadContact(contactId)
        viewModel.contactLiveData.observe(viewLifecycleOwner) {
            if (!valuesLoaded) {
                it?.let {
                    binding.editName.setText(it.firstName)
                    binding.editSurname.setText(it.lastName)
                    binding.editPhone.setText(it.phoneNumber)
                }
            }
        }
    }

    private fun setButtonSaveListener() {
        binding.editButtonSave.setOnClickListener {
            onButtonSavePressed()
        }
    }

    private fun onButtonSavePressed() {
        val firstName = binding.editName.text.toString()
        val lastName = binding.editSurname.text.toString()
        val phoneNumber = binding.editPhone.text.toString()

        if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
            showRequireNotBlankToast()
        } else {
            viewModel.updateContact(firstName, lastName, phoneNumber, contactId)
            launchContactsListFragment()
        }
    }

    private fun showRequireNotBlankToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.edit_is_blank_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun launchContactsListFragment() {
        requireActivity().supportFragmentManager.popBackStack(
            ContactDetailsFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {

        private const val KEY_CONTACT_ID = "contactId"
        private const val KEY_VALUES_LOADED = "valuesLoaded"
        private const val UNDEFINED_ID = 0

        fun newInstance(contactId: Int) = ContactEditFragment().apply {
            arguments = bundleOf(KEY_CONTACT_ID to contactId)
        }
    }
}
