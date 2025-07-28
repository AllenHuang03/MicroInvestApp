package com.microinvest.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MicroInvestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide components here if needed
    }
}
