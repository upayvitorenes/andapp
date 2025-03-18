package com.upayments.starpayapp.constants

object AppKeys {

    object KycKeys {
        const val KYC_API_KEY = "KycApiKey"
        const val KYC_FLOW_ID = "KycFlowID"

        const val KYC_SESSION_ONGOING = "KycSessionOngoing"
        const val KYC_SESSION_ID = "KycSessionId"
        const val KYC_SESSION_COMPLETED = "KycSessionCompleted"
        const val KYC_SESSION_RESULT = "KycSessionResult"
    }

    object RecoveryKeys {
        const val USER_IS_RECOVERING_ACCOUNT = "UserIsRecoveringAccount"
        const val RECOVERY_USER_FISCAL_ID = "RecoveryUserFiscalId"
        const val RECOVERY_USER_EMAIL = "RecoveryUserEmail"
        const val RECOVERY_USER_MOBILE_PHONE = "RecoveryUserMobilePhone"
        const val RECOVERY_MOBILE_PHONE_CONFIRMED = "RecoveryMobilePhoneConfirmed"
    }

    object PushNotificationsKeys {
        const val SUBSCRIPTION_ID_KEY = "PushNotificationsSubscriptionId"
        const val EXTERNAL_ID_KEY = "PushNotificationsExternalId"
    }

    object UserKeys {

        const val UDNA_ID = "UDnaId"

        const val USER_NAMES = "UserNames"
        const val USER_LAST_NAMES = "UserLastNames"
        const val USER_BIRTHDAY = "UserBirthday"
        const val USER_FISCAL_ID = "UserFiscalId"
        const val USER_EMAIL = "UserEmail"
        const val USER_MOBILE_PHONE_NUMBER = "UserPhoneNumber"

        const val USER_GENDER = "UserGender"
        const val USER_MARITAL_STATUS = "UserMaritalStatus"

        const val USER_ADDRESS = "UserAddress"

        // ALL THESE ARE NEW
        const val USER_ADDRESS_STREET = "UserAddressStreet"
        const val USER_ADDRESS_NUMBER = "UserAddressNumber"
        const val USER_ADDRESS_COMPLEMENT = "UserAddressComplement"
        const val USER_ADDRESS_ADMINISTRATIVE_AREA = "UserAddressAdministrativeArea"
        const val USER_ADDRESS_CITY = "UserAddressCity"
        const val USER_ADDRESS_POSTAL_CODE = "UserAddressPostalCode"
        const val USER_ADDRESS_COUNTRY_CODE = "UserAddressCountryCode"

        const val USER_DATA_IS_SET = "UserDataIsSet"

        const val USER_CARDHOLDER_ID = "UserCardholderId"
        const val USER_CARDHOLDER_NAME = "UserCardholderName"
        const val USER_CARD_ID = "UserCardId"

        const val USER_CONFIRMED_EMAIL = "UserConfirmedEmail"
        const val USER_CONFIRMED_PHONE_NUMBER = "UserConfirmedPhoneNumber"

        const val USER_ACCEPTED_CONTRACT = "UserAcceptedContract"
        const val USER_ACCEPTED_TERMS = "UserAcceptedTerms"
        const val USER_ACCEPTED_PRIVACY = "UserAcceptedPrivacy"

        const val USER_APP_USERNAME = "UserAppUsername"
        const val USER_APP_PASSWORD = "UserAppPassword"

        const val USER_APP_ACCESS_PIN = "UserAccessPin"
        const val USER_APP_AUTHORIZATION_PIN = "UserAuthorizationPin"
    }

    object AppState {

        const val APP_ATTEST_KEY = "AttestKeyId"
        const val APP_IS_INITIALIZED = "AppIsInitialized"

        const val USER_IS_ONBOARDED = "UserIsOnboarded"
        const val USER_IS_REGISTERED = "UserIsRegistered"
        const val USER_CONFIRMED_ACTIVATION_CODE = "UserConfirmedActivationCode"
        const val USER_IS_ACTIVATED = "UserIsActivated"
        const val USER_CANCELLED_ONBOARDING = "UserCancelledOnboarding"

        const val USER_HAS_VALID_SESSION = "UserHasValidSession"
        const val USER_IS_AUTHENTICATED = "UserIsAuthenticated"

        const val APP_WAS_IN_BACKGROUND = "AppWasInBackground"
        const val APP_TEMPORARILY_DISABLE_BIOMETRICS = "AppTemporarilyDisableBiometrics" // new
    }

}