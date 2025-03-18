package com.upayments.starpayapp

import android.app.Application
import com.upayments.starpayapp.state.ApplicationState
import com.upayments.starpayapp.state.SecureStorage
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StarPayApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initializing globally-available Secure Storage
        SecureStorage.init(this)

        // Initializing globally-available ApplicationState
        ApplicationState.initialize()
    }

}