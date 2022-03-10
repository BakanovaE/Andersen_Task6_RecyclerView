package org.martellina.task5_fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.facebook.drawee.view.SimpleDraweeView

class FragmentDetails: Fragment(R.layout.fragment_details) {

    private lateinit var nameDetail: EditText
    private lateinit var surnameDetail: EditText
    private lateinit var numberDetail: EditText

    private lateinit var buttonSave: Button

    private lateinit var image: SimpleDraweeView

    private lateinit var saveButtonClickListener: SaveButtonClickListener
    private lateinit var person: Person

    override fun onAttach(context: Context) {
        super.onAttach(context)
        saveButtonClickListener = context as SaveButtonClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameDetail = view.findViewById(R.id.name_d)
        surnameDetail = view.findViewById(R.id.surname_d)
        numberDetail = view.findViewById(R.id.number_d)

        buttonSave = view.findViewById(R.id.button_save)

        image = view.findViewById(R.id.simpleDraweeView)

        person = requireArguments().getParcelable(PERSON_EXTRA)!!
        val id = requireArguments().getInt(ID_EXTRA)

        nameDetail.setText(person.name)
        surnameDetail.setText(person.surname)
        numberDetail.setText(person.number)
        image.setImageURI(Uri.parse(person.imgUrl))

        buttonSave.setOnClickListener {
            val newPerson = Person(nameDetail.text.toString(), surnameDetail.text.toString(), numberDetail.text.toString(), person.imgUrl, person.id)
            saveButtonClickListener.onSaveButtonClicked(newPerson)
        }
    }

    companion object {
        const val TAG_FD = "FRAGMENT_DETAILS"

        private const val ID_EXTRA = "ID_EXTRA"
        private const val PERSON_EXTRA = "PERSON_EXTRA"

        fun newInstance(person: Person): FragmentDetails {
            return FragmentDetails().also {
                it.arguments = Bundle().apply {
                    putParcelable(PERSON_EXTRA, person)
                    putInt(ID_EXTRA, person.id)
                }
            }
        }
    }

    interface SaveButtonClickListener {
        fun onSaveButtonClicked(newPerson: Person)
    }

}