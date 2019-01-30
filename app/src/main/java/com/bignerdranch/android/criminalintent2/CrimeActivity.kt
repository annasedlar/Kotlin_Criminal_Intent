package com.bignerdranch.android.criminalintent2
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

private const val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent2.crime_id"


class CrimeActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }

    companion object {
        fun newIntent(context: Context, crimeId: UUID) : Intent {
            return Intent(context, CrimeActivity::class.java).apply {
                putExtra(EXTRA_CRIME_ID, crimeId)
            }
        }
    }

}
