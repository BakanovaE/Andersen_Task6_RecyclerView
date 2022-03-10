package org.martellina.task5_fragments

import android.graphics.Rect
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlin.collections.ArrayList

class ContactsAdapter(private val contactsList: ArrayList<Person>)
    : RecyclerView.Adapter<ContactsAdapter.PersonViewHolder>(), Filterable {

    var contactsFilterList = contactsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = contactsFilterList[position]
        holder.bind(item)
    }

    override fun getItemCount() = contactsFilterList.size

    class PersonViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        private val  name: TextView
        private val surname: TextView
        private val number: TextView
        private val image: SimpleDraweeView

        init {
            name = view.findViewById(R.id.name)
            surname = view.findViewById(R.id.surname)
            number = view.findViewById(R.id.number)
            image = view.findViewById(R.id.simpleDraweeView)
        }

        fun bind(person: Person) {
            name.text = person.name
            surname.text = person.surname
            number.text = person.number
            image.setImageURI(Uri.parse(person.imgUrl))
            itemView.setOnClickListener {
                MainActivity.contactClickListener.onContactClicked(person)
            }
            itemView.setOnLongClickListener {
                MainActivity.deleteContact.deleteContact(person.id)
                true
            }
        }
    }

    class Decorator: RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = 100
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                contactsFilterList = if (charSearch.isEmpty()) {
                    contactsList
                } else {
                    val resultList = ArrayList<Person>()
                    for (row in contactsList) {
                        if (row.name.lowercase().contains(charSearch.lowercase())
                            || row.surname.lowercase().contains(charSearch.lowercase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactsFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactsFilterList = results?.values as ArrayList<Person>
                notifyDataSetChanged()

            }

        }
    }

}