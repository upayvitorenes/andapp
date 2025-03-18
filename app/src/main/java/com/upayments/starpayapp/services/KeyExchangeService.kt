package com.upayments.starpayapp.services

import com.upayments.starpayapp.appinit.KeyGeneratorUtil
import com.upayments.starpayapp.constants.AppKeys
import com.upayments.starpayapp.constants.Services
import com.upayments.starpayapp.network.KeyApiService
import com.upayments.starpayapp.network.NetworkHelper
import com.upayments.starpayapp.state.SecureStorage
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyExchangeService @Inject constructor(
    private val secureStorage: SecureStorage,
    private val networkHelper: NetworkHelper
) {
    suspend fun submitPublicKeyToServer(): Boolean {
        Timber.tag("KEY EXCHANGE SERVICE").d("Generating Key Pair...")
        try {
            val (publicKeyB64, privateKeyAlias) = KeyGeneratorUtil.generateKeyPair()
            secureStorage.setString("ApplicationPublicKey", publicKeyB64)
            secureStorage.setString("ApplicationPrivateKey", privateKeyAlias)
        } catch (e: Exception) {
            Timber.tag("KEY EXCHANGE SERVICE").e(e, "Error generating Application Key Pair")
            return false
        }

        val keyId = secureStorage.getString(AppKeys.AppState.APP_ATTEST_KEY)
        if (keyId.isNullOrEmpty()) {
            Timber.tag("KEY EXCHANGE SERVICE").e("Error retrieving integrity-checked Key ID from SecureStorage")
            return false
        }

        val publicKey = secureStorage.getString("ApplicationPublicKey")
        if (publicKey.isNullOrEmpty()) {
            Timber.tag("KEY EXCHANGE SERVICE").e("Error retrieving Application Public Key from SecureStorage")
            return false
        }

        val payload = mapOf(
            "key_id" to keyId,
            "public_key" to publicKey
        )

        val keyApiService = networkHelper.getService(
            KeyApiService::class.java,
            baseUrl = Services.Urls.alchemistServiceUrl
        )

        val result = networkHelper.makeRequest {
            keyApiService.exchangeKeys(payload)
        }

        return if (result.isSuccess) {
            val serverResponse = result.getOrNull() ?: run {
                Timber.tag("KEY EXCHANGE SERVICE").e("Error retrieving server response")
                return false
            }

            Timber.tag("KEY EXCHANGE SERVICE").d("Registration Id: ${serverResponse.data.id}")
            Timber.tag("KEY EXCHANGE SERVICE").d("Server Public Key: ${serverResponse.data.server_public_key}")

            secureStorage.setString("ServerKeysRegistrationId", serverResponse.data.id.toString())
            secureStorage.setString("ServerPublicKey", serverResponse.data.server_public_key)

            Timber.tag("KEY EXCHANGE SERVICE").d("Key successfully submitted to server")
            true
        } else {
            Timber.tag("KEY EXCHANGE SERVICE").e("Error submitting key to server")
            Timber.tag("KEY EXCHANGE SERVICE").e("${result.exceptionOrNull()}")
            false
        }
    }
}