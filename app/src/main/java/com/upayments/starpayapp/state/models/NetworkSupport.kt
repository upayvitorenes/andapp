package com.upayments.starpayapp.state.models

data class ServerKeyResponse(
    val message: String,
    val data: ServerKeyData
)

data class ServerKeyData(
    val id: Long,
    val server_public_key: String
)