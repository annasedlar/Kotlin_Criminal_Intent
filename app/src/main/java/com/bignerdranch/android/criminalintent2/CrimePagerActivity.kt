package com.bignerdranch.android.criminalintent2

import android.os.Bundle
import android.support.v4.view.ViewPager
import Crime
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import java.util.*

private const val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent2.crime_id"

class CrimePagerActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var crimes: List<Crime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID)

        viewPager = findViewById(R.id.crime_view_pager)
        viewPager.apply {
            crimes = CrimeLab.get().getCrimes()
            adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

                override fun getItem(position: Int): Fragment {
                    val crime = crimes[position]
                    return CrimeFragment.newInstance(crime.id)
                }

                override fun getCount(): Int {
                    return crimes.size
                }
            }

            for (i in crimes.indices) {
                if (crimes[i].id == crimeId) {
                    currentItem = i
                    break
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context, crimeId: UUID): Intent {
            return Intent(context, CrimePagerActivity::class.java).apply {
                putExtra(EXTRA_CRIME_ID, crimeId)
            }
        }
    }
}