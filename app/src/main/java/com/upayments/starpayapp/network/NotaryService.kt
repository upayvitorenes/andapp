package com.upayments.starpayapp.network

import com.upayments.starpayapp.constants.Constants
import com.upayments.starpayapp.constants.Services
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class IntegrityRequest(
    val hash: String,
    val integrity_token: String,
    val flow_id: String,
    val key_id: String? = null)

data class IntegrityResponse(
    val result: Boolean,
    val key_id: String,
)

/*
data class IntegrityResponse(
    val status: String,
    val data: TokenPayloadExternal
)

data class TokenPayloadExternal(
    val tokenPayloadExternal: RequestDetails
)

data class RequestDetails(
    val requestDetails: RequestPackageDetails,
    val appIntegrity: AppIntegrity,
    val deviceIntegrity: DeviceIntegrity,
    val accountDetails: AccountDetails
)

data class RequestPackageDetails(
    val requestPackageName: String,
    val timestampMillis: String,
    val requestHash: String
)

data class AppIntegrity(
    val appRecognitionVerdict: String,
    val packageName: String,
    val certificateSha256Digest: List<String>,
    val versionCode: String
)

data class DeviceIntegrity(
    val deviceRecognitionVerdict: List<String>
)

data class AccountDetails(
    val appLicensingVerdict: String
)
*/

interface NotaryService {
    @POST(Services.Endpoints.notaryAttestationValidate)
    suspend fun integrityCheck(@Body request: IntegrityRequest): Response<IntegrityResponse>
}