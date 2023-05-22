package ru.ponomarchukpn.aston_intensiv_6.entity

data class ContactItem(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    var imageUrl: String = "",
    var id: Int = 0
)
