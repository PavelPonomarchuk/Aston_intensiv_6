package ru.ponomarchukpn.aston_intensiv_6.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem
import ru.ponomarchukpn.aston_intensiv_6.repository.ContactsRepository

class ContactDetailsViewModel : ViewModel() {

    private val _contactLiveData = MutableLiveData<ContactItem?>()
    val contactLiveData: LiveData<ContactItem?>
        get() = _contactLiveData

    fun loadContact(contactId: Int) {
        ContactsRepository.getContact(contactId).also {
            _contactLiveData.value = it
        }
    }
}
