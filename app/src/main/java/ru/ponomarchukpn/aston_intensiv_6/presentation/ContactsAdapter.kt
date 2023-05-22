package ru.ponomarchukpn.aston_intensiv_6.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import ru.ponomarchukpn.aston_intensiv_6.R
import ru.ponomarchukpn.aston_intensiv_6.entity.ContactItem

class ContactsAdapter : ListAdapter<ContactItem, ContactsAdapter.ContactsViewHolder>(
    ContactsDiffCallback
) {

    var onContactClick: ((ContactItem) -> Unit)? = null
    var onContactLongClick: ((ContactItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = getItem(position)
        with(holder) {
            fullName.text = String.format("%s %s", contact.firstName, contact.lastName)
            phone.text = contact.phoneNumber
            contactId.text = contact.id.toString()
            Picasso.get().load(contact.imageUrl).into(contactImage)

            itemView.setOnClickListener {
                onContactClick?.invoke(contact)
            }
            itemView.setOnLongClickListener {
                onContactLongClick?.invoke(contact)
                true
            }
        }
    }

    class ContactsViewHolder(itemView: View) : ViewHolder(itemView) {
        val contactImage: ImageView = itemView.findViewById(R.id.main_contact_image)
        val fullName: TextView = itemView.findViewById(R.id.main_tv_full_name)
        val phone: TextView = itemView.findViewById(R.id.main_tv_phone)
        val contactId: TextView = itemView.findViewById(R.id.main_tv_id)
    }
}
