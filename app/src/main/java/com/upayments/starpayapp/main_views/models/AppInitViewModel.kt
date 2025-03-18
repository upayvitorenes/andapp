package com.upayments.starpayapp.main_views.models

import androidx.lifecycle.ViewModel
import com.upayments.starpayapp.services.KeyExchangeService
import com.upayments.starpayapp.state.SecureStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppInitViewModel @Inject constructor(
    private val keyExchangeService: KeyExchangeService
) : ViewModel() {

    suspend fun handlePublicKeyExchange(): Boolean {
        Timber.tag("APP_INIT").d("Handling Application Public Key Exchange now...")

        val savedPublicKeyRegistration = SecureStorage.getString("ServerKeysRegistrationId")

        return if (savedPublicKeyRegistration != null) {
            Timber.tag("APP_INIT").d("This Application already registered a Public Key with Alchemist with ID: $savedPublicKeyRegistration")
            true
        } else {
            Timber.tag("APP_INIT").d("This Application has not yet registered a Public Key with Alchemist - submitting...")
            keyExchangeService.submitPublicKeyToServer()
        }
    }
}