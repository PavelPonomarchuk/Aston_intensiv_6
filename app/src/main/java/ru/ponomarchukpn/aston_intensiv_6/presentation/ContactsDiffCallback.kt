package ru.ponomarchukpn.aston_intensiv_6.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem

object ContactsDiffCallback : DiffUtil.ItemCallback<ContactItem>() {

    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }
}
