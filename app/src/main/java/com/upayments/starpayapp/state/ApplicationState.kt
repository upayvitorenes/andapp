package com.upayments.starpayapp.state

import androidx.lifecycle.ViewModel
import com.upayments.starpayapp.constants.AppKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber


object ApplicationState : ViewModel() {

    private val _state = MutableStateFlow(ApplicationStateData())
    val state: StateFlow<ApplicationStateData> = _state.asStateFlow()

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            val isRegistered = SecureStorage.getString(AppKeys.AppState.USER_IS_REGISTERED)?.toBoolean() ?: false
            val isInitialized = SecureStorage.getBool(AppKeys.AppState.APP_IS_INITIALIZED)
            val isOnboarded = SecureStorage.getBool(AppKeys.AppState.USER_IS_ONBOARDED)
            val confirmedActivationCode = SecureStorage.getBool(AppKeys.AppState.USER_CONFIRMED_ACTIVATION_CODE)
            val isActivated = SecureStorage.getBool(AppKeys.AppState.USER_IS_ACTIVATED)
            val cancelledOnboarding = SecureStorage.getBool(AppKeys.AppState.USER_CANCELLED_ONBOARDING)
            val names = SecureStorage.getString(AppKeys.UserKeys.USER_NAMES) ?: ""
            val appPassword = SecureStorage.getString(AppKeys.UserKeys.USER_APP_PASSWORD) ?: ""
            val appAccessPin = SecureStorage.getString(AppKeys.UserKeys.USER_APP_ACCESS_PIN) ?: ""
            val appAuthorizationPin = SecureStorage.getString(AppKeys.UserKeys.USER_APP_AUTHORIZATION_PIN) ?: ""
            val userCardHolderId = SecureStorage.getString(AppKeys.UserKeys.USER_CARDHOLDER_ID) ?: ""
            val userCardId = SecureStorage.getString(AppKeys.UserKeys.USER_CARD_ID) ?: ""
            val hasValidSession = SecureStorage.getBool(AppKeys.AppState.USER_HAS_VALID_SESSION)
            val isAuthenticated = SecureStorage.getBool(AppKeys.AppState.USER_IS_AUTHENTICATED)
            val wasInBackground = SecureStorage.getBool(AppKeys.AppState.APP_WAS_IN_BACKGROUND)
            val disableBiometrics = SecureStorage.getBool(AppKeys.AppState.APP_TEMPORARILY_DISABLE_BIOMETRICS)

            _state.value = _state.value.copy(
                appIsInitialized = isInitialized,
                userIsRegistered = isRegistered,
                userIsOnboarded = isOnboarded,
                userConfirmedActivationCode = confirmedActivationCode,
                userIsActivated = isActivated,
                userCancelledOnboarding = cancelledOnboarding,
                userNames = names,
                password = appPassword,
                accessPin = appAccessPin,
                authorizationPin = appAuthorizationPin,
                cardHolderId = userCardHolderId,
                cardId = userCardId,
                userHasValidSession = hasValidSession,
                authenticated = isAuthenticated,
                appWasInBackground = wasInBackground,
                tempDisableBiometrics = disableBiometrics
            )
        }
    }

    private fun updateState(update: ApplicationStateData.() -> ApplicationStateData) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = _state.value.update()
        }
    }

    fun setAppIsInitialized(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.APP_IS_INITIALIZED, value)
            copy(appIsInitialized = value)
        }
    }

    fun setUserIsRegistered(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_IS_REGISTERED, value)
            copy(userIsRegistered = value)
        }
    }

    fun setUserIsOnboarded(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_IS_ONBOARDED, value)
            copy(userIsOnboarded = value)
        }
    }

    fun setUserConfirmedActivationCode(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_CONFIRMED_ACTIVATION_CODE, value)
            copy(userConfirmedActivationCode = value)
        }
    }

    fun setUserIsActivated(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_IS_ACTIVATED, value)
            copy(userIsActivated = value)
        }
    }

    fun setUserCancelledOnboarding(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_CANCELLED_ONBOARDING, value)
            copy(userCancelledOnboarding = value)
        }
    }

    fun setUserNames(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_NAMES, value)
            copy(userNames = value)
        }
    }

    fun setPassword(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_APP_PASSWORD, value)
            copy(password = value)
        }
    }

    fun setAccessPin(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_APP_ACCESS_PIN, value)
            copy(accessPin = value)
        }
    }

    fun setAuthorizationPin(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_APP_AUTHORIZATION_PIN, value)
            copy(authorizationPin = value)
        }
    }

    fun setCardHolderId(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_CARDHOLDER_ID, value)
            copy(cardHolderId = value)
        }
    }

    fun setCardId(value: String) {
        updateState {
            SecureStorage.setString(AppKeys.UserKeys.USER_CARD_ID, value)
            copy(cardId = value)
        }
    }

    fun setUserHasValidSession(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_HAS_VALID_SESSION, value)
            copy(userHasValidSession = value)
        }
    }

    fun setAuthenticated(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.USER_IS_AUTHENTICATED, value)
            copy(authenticated = value)
        }
    }

    fun setAppWasInBackground(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.APP_WAS_IN_BACKGROUND, value)
            copy(appWasInBackground = value)
        }
    }

    fun setTempDisableBiometrics(value: Boolean) {
        updateState {
            SecureStorage.setBool(AppKeys.AppState.APP_TEMPORARILY_DISABLE_BIOMETRICS, value)
            copy(tempDisableBiometrics = value)
        }
    }





    fun updateUserLoginData(userName: String, password: String, userHasValidSession: Boolean) {
        Timber.tag("APPLICATION STATE").d("Valid login. Setting session as VALID.")
        setUserHasValidSession(true)
    }

    fun updateCardHolderIds(cardHolderId: String, cardId: String) {
        Timber.tag("APPLICATION STATE").d("In Application State, updating CardHolderId and CardId.")
        Timber.tag("APPLICATION STATE").d("CardHolderId: {$cardHolderId} - CardId: {$cardId}")

        CoroutineScope(Dispatchers.IO).launch {
            setCardHolderId(cardHolderId)
            setCardId(cardId)
        }
    }


    /*
    fun setUserIsRegistered(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.setString(AppKeys.AppState.USER_IS_REGISTERED, value.toString())
            _state.value = _state.value.copy(userIsRegistered = value)
        }
    }

    fun setAppIsInitialized(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.setBool(AppKeys.AppState.APP_IS_INITIALIZED, value)
            _state.value = _state.value.copy(appIsInitialized = value)
        }
    }

    fun unSetAppIsInitialized() {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.delete(AppKeys.AppState.APP_IS_INITIALIZED)
            _state.value = _state.value.copy(appIsInitialized = false)
        }
    }

    fun setUserNames(value: String) {
        _state.value = _state.value.copy(userNames = value)
    }

    fun setCardId(value: String) {
        _state.value = _state.value.copy(cardId = value)
    }
    */
}











/*
object ApplicationStat {

    private val _appIsInitialized = MutableStateFlow(false)
    val appIsInitialized: StateFlow<Boolean> = _appIsInitialized.asStateFlow()

    private val _userIsRegistered = MutableStateFlow(false)
    val userIsRegistered: StateFlow<Boolean> = _userIsRegistered.asStateFlow()

    private val _userIsOnboarded = MutableStateFlow(false)
    val userIsOnboarded: StateFlow<Boolean> = _userIsOnboarded.asStateFlow()
    private val _userConfirmedActivationCode = MutableStateFlow(false)
    val userConfirmedActivationCode: StateFlow<Boolean> = _userConfirmedActivationCode.asStateFlow()
    private val _userIsActivated = MutableStateFlow(false)
    val userIsActivated: StateFlow<Boolean> = _userIsActivated.asStateFlow()
    private val _userCancelledOnboarding = MutableStateFlow(false)
    val userCancelledOnboarding: StateFlow<Boolean> = _userCancelledOnboarding.asStateFlow()
    private val _userNames = MutableStateFlow("")
    val userNames: StateFlow<String> = _userNames.asStateFlow()
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()
    private val _accessPin = MutableStateFlow("")
    val accessPin: StateFlow<String> = _accessPin.asStateFlow()
    private val _authorizationPin = MutableStateFlow("")
    val authorizationPin: StateFlow<String> = _authorizationPin.asStateFlow()
    private val _newAccessPin = MutableStateFlow("")
    val newAccessPin: StateFlow<String> = _newAccessPin.asStateFlow()
    private val _newAuthorizationPin = MutableStateFlow("")
    val newAuthorizationPin: StateFlow<String> = _newAuthorizationPin.asStateFlow()
    private val _cardHolderId = MutableStateFlow("")
    val cardHolderId: StateFlow<String> = _cardHolderId.asStateFlow()
    private val _cardId = MutableStateFlow("")
    val cardId: StateFlow<String> = _cardId.asStateFlow()
    private val _userHasValidSession = MutableStateFlow(false)
    val userHasValidSession: StateFlow<Boolean> = _userHasValidSession.asStateFlow()
    private val _authenticated = MutableStateFlow(false)
    val authenticated: StateFlow<Boolean> = _authenticated.asStateFlow()
    private val _pinEntryRetriesRemaining = MutableStateFlow(2)
    val pinEntryRetriesRemaining: StateFlow<Int> = _pinEntryRetriesRemaining.asStateFlow()
    private val _authPinEntryRetriesRemaining = MutableStateFlow(2)
    val authPinEntryRetriesRemaining: StateFlow<Int> = _authPinEntryRetriesRemaining.asStateFlow()

    private val _userCardData = MutableStateFlow(CardDataModel)
    val userCardData: StateFlow<CardDataModel> = _userCardData.asStateFlow()
    private val _userBalance = MutableStateFlow(UserBalanceModel)
    val userBalance: StateFlow<UserBalanceModel> = _userBalance.asStateFlow()

    private val _appWasInBackground = MutableStateFlow(false)
    val appWasInBackground: StateFlow<Boolean> = _appWasInBackground.asStateFlow()
    private val _tempDisableBiometrics = MutableStateFlow(false)
    val tempDisableBiometrics: StateFlow<Boolean> = _tempDisableBiometrics.asStateFlow()
    private val _isRecoveringAccount = MutableStateFlow(false)
    val isRecoveringAccount: StateFlow<Boolean> = _isRecoveringAccount.asStateFlow()

    /**
     * Initializer
     */
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            val isRegistered = SecureStorage.getString(AppKeys.AppState.USER_IS_REGISTERED)?.toBoolean() ?: false
            _userIsRegistered.value = isRegistered

            val isInitialized = SecureStorage.getString(AppKeys.AppState.APP_IS_INITIALIZED)?.toBoolean() ?: false
            _appIsInitialized.value = isInitialized
        }
    }

    /**
     * SETTERS AND UN-SETTERS
     */
    fun setUserIsRegistered(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.setString(AppKeys.AppState.USER_IS_REGISTERED, value.toString())
            _userIsRegistered.value = value
        }
    }

    fun unSetUserIsRegistered() {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.delete(AppKeys.AppState.USER_IS_REGISTERED)
            _userIsRegistered.value = false
        }
    }

    fun setAppIsInitialized(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.setString(AppKeys.AppState.APP_IS_INITIALIZED, value.toString())
            _appIsInitialized.value = value
        }
    }

    fun unSetAppIsInitialized() {
        CoroutineScope(Dispatchers.IO).launch {
            SecureStorage.delete(AppKeys.AppState.APP_IS_INITIALIZED)
            _appIsInitialized.value = false
        }
    }
}
*/