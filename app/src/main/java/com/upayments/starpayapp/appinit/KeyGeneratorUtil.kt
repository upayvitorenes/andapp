package com.upayments.starpayapp.appinit

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.upayments.starpayapp.constants.Services
import java.security.KeyPair
import java.security.KeyPairGenerator
import kotlin.jvm.Throws

object KeyGeneratorUtil {

    @Throws(Exception::class)
    fun generateKeyPair(): Pair<String, String> {
        val alias = Services.AppIdentification.appBundleIdentifier

        val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
                    or KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(2048)
            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .build()

        keyPairGenerator.initialize(spec)
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()

        val publicKeyBytes = keyPair.public.encoded
        val publicKeyBase64 = Base64.encodeToString(publicKeyBytes, Base64.DEFAULT)

        return Pair(publicKeyBase64, alias)
    }

}