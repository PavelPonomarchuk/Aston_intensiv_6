package ru.ponomarchukpn.aston_intensiv_6.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem
import ru.ponomarchukpn.aston_intensiv_6.repository.ContactsRepository

class ContactEditViewModel : ViewModel() {

    private val _contactLiveData = MutableLiveData<ContactItem?>()
    val contactLiveData: LiveData<ContactItem?>
        get() = _contactLiveData

    fun loadContact(contactId: Int) {
        ContactsRepository.getContact(contactId).also {
            _contactLiveData.value = it
        }
    }

    fun updateContact(firstName: String, lastName: String, phoneNumber: String, id: Int) {
        ContactsRepository.updateContact(ContactItem(firstName, lastName, phoneNumber, id = id))
    }
}
