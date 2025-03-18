package com.upayments.starpayapp

import com.upayments.starpayapp.network.NetworkHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppEntryPoint {
    fun networkHelper(): NetworkHelper
}