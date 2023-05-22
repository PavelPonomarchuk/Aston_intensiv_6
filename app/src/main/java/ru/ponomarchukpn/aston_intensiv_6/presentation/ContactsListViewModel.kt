package ru.ponomarchukpn.aston_intensiv_6.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem
import ru.ponomarchukpn.aston_intensiv_6.repository.ContactsRepository

class ContactsListViewModel : ViewModel() {

    private val _contactsLiveData = MutableLiveData<List<ContactItem>>()
    val contactsLiveData: LiveData<List<ContactItem>>
        get() = _contactsLiveData

    init {
        _contactsLiveData.value = ContactsRepository.getList()
    }

    fun filterContacts(query: String?) {
        query?.let {
            ContactsRepository.getList().filter {
                it.firstName.contains(query, true) || it.lastName.contains(query, true)
            }.also {
                _contactsLiveData.value = it
            }
        }
        if (query == null || query.isBlank()) {
            _contactsLiveData.value = ContactsRepository.getList()
        }
    }

    fun removeAndUpdate(id: Int) {
        ContactsRepository.deleteContact(id)
        _contactsLiveData.value = ContactsRepository.getList()
    }
}
