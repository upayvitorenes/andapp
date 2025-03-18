package com.upayments.starpayapp.state

import com.upayments.starpayapp.state.models.CardDataModel
import com.upayments.starpayapp.state.models.UserBalanceModel

data class ApplicationStateData(
    val appIsInitialized: Boolean = false,
    val userIsRegistered: Boolean = false,
    val userIsOnboarded: Boolean = false,
    val userConfirmedActivationCode: Boolean = false,
    val userIsActivated: Boolean = false,
    val userCancelledOnboarding: Boolean = false,
    val userNames: String = "",
    val password: String = "",
    val accessPin: String = "",
    val authorizationPin: String = "",
    val newAccessPin: String = "",
    val newAuthorizationPin: String = "",
    val cardHolderId: String = "",
    val cardId: String = "",
    val userHasValidSession: Boolean = false,
    val authenticated: Boolean = false,
    val pinEntryRetriesRemaining: Int = 2,
    val authPinEntryRetriesRemaining: Int = 2,
    val userCardData: CardDataModel = CardDataModel,
    val userBalance: UserBalanceModel = UserBalanceModel,
    val appWasInBackground: Boolean = false,
    val tempDisableBiometrics: Boolean = false,
    val isRecoveringAccount: Boolean = false
)