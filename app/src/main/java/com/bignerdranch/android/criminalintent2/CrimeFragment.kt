package com.bignerdranch.android.criminalintent2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import Crime
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.util.*

private const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {
    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var firstButton: Button
    private lateinit var lastButton: Button
    private lateinit var crimeList: List<Crime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeLab.get().getCrime(crimeId) ?: Crime()
        val crimeLab = CrimeLab.get()
        crimeList = crimeLab.getCrimes()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_crime, container, false)

        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        firstButton = view.findViewById(R.id.first_button)
        lastButton = view.findViewById(R.id.last_button)

        firstButton.setOnClickListener {
            val intent = CrimePagerActivity.newIntent(requireContext(), crimeList[0].id)
            startActivity(intent)
        }

        lastButton.setOnClickListener {
            val lastIndex = crimeList.lastIndex
            val intent = CrimePagerActivity.newIntent(requireContext(), crimeList[lastIndex].id )
            startActivity(intent)
        }

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        titleField.apply {
            setText(crime.title)
            addTextChangedListener(titleWatcher)
        }

        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }

        solvedCheckBox.apply {
            isChecked = crime.isSolved
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }

        return view
    }

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }

            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
}