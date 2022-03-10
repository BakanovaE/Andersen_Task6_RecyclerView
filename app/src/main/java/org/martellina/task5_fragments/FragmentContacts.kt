package org.martellina.task5_fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class FragmentContacts: Fragment(R.layout.fragment_contacts), BackPressedListener {

    private lateinit var contactClickListener: ContactClickListener
    private lateinit var recyclerView: RecyclerView

    lateinit var contactsList: ArrayList<Person>

    private lateinit var searchView: SearchView

    private lateinit var adapter: ContactsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contactClickListener = context as ContactClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsList = requireArguments().getParcelableArrayList(LIST_EXTRA)!!

        adapter = ContactsAdapter(contactsList)
        recyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        recyclerView.run {
            recyclerView.addItemDecoration(dividerItemDecoration)
            addItemDecoration(ContactsAdapter.Decorator())
        }

        searchView = view.findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LIST_EXTRA, contactsList)
    }

    override fun onBackPressed(): Boolean = false

    companion object {
        const val TAG_FC = "TAG_FC"
        const val LIST_EXTRA = "LIST_EXTRA"

        fun newInstance(contactsList: ArrayList<Person>) = FragmentContacts().also {
            it.arguments = Bundle().apply {
                putParcelableArrayList(LIST_EXTRA, contactsList)
            }
        }

    }

    interface ContactClickListener {
        fun onContactClicked(person: Person)
    }

}