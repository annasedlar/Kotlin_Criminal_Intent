package com.bignerdranch.android.criminalintent2

import android.content.Context
import java.lang.IllegalStateException
import java.util.*
import Crime

class CrimeLab private constructor(context: Context) {

    private val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 9) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
    }

    fun getCrimes(): List<Crime> {
        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        return crimes.find {
            it.id == id
        }
    }

    fun addCrime(crime: Crime) {
        crimes.add(crime)
    }

    companion object {
        fun initialize(context: Context) {
            INSTANCE = CrimeLab(context)
        }

        fun get(): CrimeLab {
            return INSTANCE ?: throw IllegalStateException("CrimeLab must be initialized")
        }

        private var INSTANCE: CrimeLab? = null
    }
}