package com.upayments.starpayapp.network

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun <T> makeRequest(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("NETWORK Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
                Result.failure(e)
        }
    }

    fun <T> getService(serviceClass: Class<T>, baseUrl: String): T {
        return apiClient.getService(serviceClass, baseUrl)
    }
}