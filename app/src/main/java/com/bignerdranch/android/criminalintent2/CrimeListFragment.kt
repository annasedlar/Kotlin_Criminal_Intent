package com.bignerdranch.android.criminalintent2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import Crime
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val titleTextView: TextView
        private val dateTextView: TextView
        private lateinit var crime: Crime
        private var contactPoliceButton: Button? = null
        private var solvedImageView: ImageView? = null

        init {
            itemView.setOnClickListener(this)
            titleTextView = itemView.findViewById(R.id.crime_title)
            dateTextView = itemView.findViewById(R.id.crime_date)
            contactPoliceButton = itemView.findViewById(R.id.call_police_button)
            solvedImageView = itemView.findViewById(R.id.solved_view)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            contactPoliceButton?.setOnClickListener{
                contactPoliceButton!!.text = "Dial 911"
                contactPoliceButton!!.width = 150
            }
            solvedImageView?.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        val crimeItemNormal : Int = 0
        val crimeItemSerious : Int = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(context)
            var view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)

            if( viewType == 1) {
                view = layoutInflater.inflate(R.layout.list_item_crime_serious, parent, false)
            }
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun getItemViewType(position: Int): Int {
            return if( position % 3 != 0 ) {
                crimeItemNormal
            } else {
                crimeItemSerious
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        val crimeLab = CrimeLab.get()
        val crimes = crimeLab.getCrimes()

        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

}