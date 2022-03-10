package org.martellina.task5_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.facebook.drawee.backends.pipeline.Fresco
import org.martellina.task5_fragments.FragmentContacts.Companion.TAG_FC
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), FragmentContacts.ContactClickListener, FragmentDetails.SaveButtonClickListener, DeleteContact {

    private var isTablet = false
    lateinit var contactsList: ArrayList<Person>
    private lateinit var contactListDeletedItem: ArrayList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactClickListener = this
        deleteContact = this

        contactsList = ArrayList()

        for (i in 0 .. 101) {
            contactsList.add(i, Person("name$i", "surname$i", "123456$i", "https://picsum.photos/id/$i/100/100", i))
        }

        isTablet = applicationContext.resources.getBoolean(R.bool.isTablet)

        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, FragmentContacts.newInstance(contactsList), TAG_FC)
            commit()
        }
    }

    override fun onContactClicked(person: Person) {

        if (isTablet) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container1, FragmentDetails.newInstance(person))
                addToBackStack(FragmentDetails.TAG_FD)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_container, FragmentDetails.newInstance(person))
                addToBackStack(FragmentDetails.TAG_FD)
                commit()
            }
        }
    }

    override fun onSaveButtonClicked(newPerson: Person) {
        var count = 0
        for (i in contactsList) {
            if (i.id == newPerson.id) {
                break
            } else {
                count++
            }
        }
        contactsList[count] = newPerson
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, FragmentContacts.newInstance(contactsList))
            commit()
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as? BackPressedListener
        if (currentFragment?.onBackPressed() != false) {
            super.onBackPressed()
        } else {
            moveTaskToBack(true)
            exitProcess(-1)
        }
    }

    override fun deleteContact(id: Int) {

        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Delete item $id?")
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                contactListDeletedItem = contactsList
                contactListDeletedItem.removeIf { it.id == id }
                supportFragmentManager.beginTransaction().run {
                    replace(R.id.fragment_container, FragmentContacts.newInstance(contactListDeletedItem))
                    commit()
                }
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        lateinit var contactClickListener: FragmentContacts.ContactClickListener
        lateinit var deleteContact: DeleteContact
    }
}