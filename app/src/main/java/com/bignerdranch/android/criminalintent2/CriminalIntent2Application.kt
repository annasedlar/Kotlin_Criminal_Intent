package com.bignerdranch.android.criminalintent2

import android.app.Application

class CriminalIntent2Application: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeLab.initialize(this)
    }
}