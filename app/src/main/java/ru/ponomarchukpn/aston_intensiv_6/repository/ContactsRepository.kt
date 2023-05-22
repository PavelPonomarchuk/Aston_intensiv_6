package ru.ponomarchukpn.aston_intensiv_6.repository

import android.net.Uri
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem

object ContactsRepository {

    private const val BASE_URL = "https://dummyimage.com/100x100/000/fff.png"
    private const val PARAM_TEXT = "text"

    private val contactsList = mutableListOf<ContactItem>()

    init {
        fillContacts()
    }

    fun getList() = contactsList.sortedBy { it.id }

    fun getContact(contactId: Int) = contactsList.find { it.id == contactId }

    fun updateContact(contact: ContactItem) {
        contactsList.find { it.id == contact.id }?.let {
            val newContact = ContactItem(
                contact.firstName,
                contact.lastName,
                contact.phoneNumber,
                it.imageUrl,
                it.id
            )
            contactsList.remove(it)
            contactsList.add(newContact)
        }
    }

    fun deleteContact(contactId: Int) {
        contactsList.removeIf { it.id == contactId }
    }

    private fun fillContacts() {
        IntRange(1, 150).forEach { contactId ->
            ContactItem(
                String.format("Иван%s", contactId),
                "Иванов",
                "84951234455",
                generateImageUrl(contactId),
                contactId
            ).also {
                contactsList.add(it)
            }
        }
    }

    private fun generateImageUrl(contactId: Int): String {
        val uri = Uri.parse(BASE_URL)
            .buildUpon()
            .appendQueryParameter(PARAM_TEXT, contactId.toString())
            .build()
        return uri.toString()
    }
}
