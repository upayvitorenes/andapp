package com.upayments.starpayapp.network

import com.upayments.starpayapp.constants.Services
import com.upayments.starpayapp.state.models.ServerKeyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KeyApiService {
    @POST(Services.Endpoints.alchemistExchangeKeys)
    suspend fun exchangeKeys(
        @Body payload: Map<String, String>
    ): Response<ServerKeyResponse>
}