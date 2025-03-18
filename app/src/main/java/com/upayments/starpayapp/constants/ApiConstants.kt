package com.upayments.starpayapp.constants

object Services {

    object AppIdentification {
        // const val appBundleIdentifier = "com.u-payments.starpay"    // DEV Bundle ID
        const val appBundleIdentifier = "com.upayments.starpayapp"    // PROD Bundle ID

        // const val appTeamIdentifier = "P4ZTA89494"    // DEV Key (Vitor Enes)
        const val appTeamIdentifier = "P7TLZDT867"    // PROD Key (UPayments)

        const val mtlsCsrDN = "StarPayMobileClient"
    }

    object Urls {

        // DEV
        const val backendBaseUrl = "https://argo-dev.u-payments.com/api/v1/" // WILL BE ARGO SERVICE

        // DEV
        const val incodeBaseUrl = "https://demo-api.incodesmile.com/"
        const val INCODE_API_KEY = "e466fce268af02ba0660013c4a064030dc214887"
        const val INCODE_FLOW_ID = "66cda16b454355d4e6e32000"

        const val attestServiceBaseUrl = "https://servicesrunner-dev.u-payments.com:16143/api/v1/" // NOTARY SERVICE
        const val alchemistServiceUrl = "https://servicesrunner-dev.u-payments.com:16201/api/v1/" // ALCHEMIST SERVICE
        const val janusServiceUrl = "https://servicesrunner-dev.u-payments.com:16455/" // JANUS SERVICE
        const val galleryServiceUrl = "https://servicesrunner-dev.u-payments.com:16399/" // GALLERY SERVICE

        const val apiBaseUrl = "https://constellation-dev.u-payments.com:9443/api/v1/"

        // PROD
        /*
        const val backendBaseUrl = "https://argo.u-payments.com/api/v1/" // WILL BE ARGO SERVICE

        const val incodeBaseUrl = "https://saas-api.incodesmile.com/"
        const val attestServiceBaseUrl = "https://servicesrunner.u-payments.com:16143/api/v1/" // NOTARY SERVICE
        const val alchemistServiceUrl = "https://servicesrunner.u-payments.com:16201/api/v1/" // ALCHEMIST SERVICE
        const val janusServiceUrl = "https://servicesrunner.u-payments.com:16455/" // JANUS SERVICE
        const val galleryServiceUrl = "https://servicesrunner.u-payments.com:16399/" // GALLERY SERVICE

        const val apiBaseUrl = "https://constellation.u-payments.com:9443/api/v1/"
        */
    }

    object Endpoints {

        const val cardHolderData = "cardholders"
        const val cardData = "cards"
        const val cardSensitiveData="data"
        // const val cardBalance = "balance"
        const val cardBalance = "balance-accounts/balance"

        const val transactions = "transactions"

        /*
         NOTARY SERVICE ENDPOINTS
         */
        const val notaryRequestChallenge = "challenge"
        const val notaryAttestationValidate = "integrity/validate"

        /*
         ALCHEMIST SERVICE ENDPOINTS
         */
        const val alchemistExchangeKeys = "keys/exchange"

        /*
         JANUS SERVICE ENDPOINTS
         */
        const val janusClient = "api/v1/client"
        const val janusOAuthToken = "oauth/token"

        /*
         GALLERY SERVICE ENDPOINTS
         */
        const val galleryCertificateRequest = "api/v1/certificate/sign"

        /*
         INCODE SERVICE ENDPOINTS
         */
        const val getIncodeCreds = "api/v1/kyc"
        const val incodeGetToken = "0/omni/start"
        const val incodeGetOcrData = "0/omni/get/ocr-data?id="

        /*
         NOTIFICATIONS & CONFIRMATION ENDPOINT
         */
        const val requestPushNotification = "notify/push"
        // const val updatePushNotificationsId = "notify/push/updateid"
        const val updatePushNotificationsId = "notifications/push/update"
        // const val verifyPhoneCode = "notify/push/verify"

        //const val requestMailNotification = "notify/email"
        const val requestMailNotification = "notifications/email/confirmation"
        const val requestPhoneNotification = "notifications/mobile/confirmation"
        //const val verifyMailCode = "notify/email/verify"
        const val verifyMailCode = "verifications/email"
        const val verifyPhoneCode = "verifications/mobile"

        const val verifyActivationCode = "verifications/activation"

        /*
         USER & MANAGEMENT ENDPOINTS
         */
        const val authenticate = "users/login"

        const val users = "users"

        // const val setPassword = "password"
        const val setPassword = "users/management/password"
        // const val setAccessCode = "accesscode"
        const val setAccessCode = "users/management/access-code"
        // const val setAuthorizationCode = "authorizationcode"
        const val setAuthorizationCode = "users/management/authorization-code"

        const val validateRecoveryData = "users/management/recovery/verify/data"
        const val requestConfirmationData = "users/management/recovery/request/data"
        const val retrieveFullRecoveryData = "users/management/recovery/retrieve/data"
        const val finishRecoveryRequest = "users/management/recovery/finish"
    }
}

object AppDefaults {

    object values {

        const val appClientName = "StarPay"
        const val apiClientId = "738422109335851730"
        const val apiClientIdHeader = "X-Api-Client-Id"
        const val appActivationContactEmail = "activaciones@starpay.com.ar"
        const val appCardsContactEmail = "tarjetas@starpay.com.ar"
        const val appPhoneContactNumber = "+5455555123456"
        const val appPhoneContactNumberLabel = "+54 555 55 123 456"

    }

}

object PushNotificationsService {

    object defaults {

        const val appID = "27eb950e-ef47-44ad-8daf-468fa9cc5d29"    // THIS KEY IS FOR THE DEV APP
        // const val appID = "4495d8ee-143a-4afc-aafe-e6a26facf25b"    // PROD OneSignal Key

    }

}

object IncodeDefaults {

    object values {

        const val apiVersionHeader = "api-version"
        const val apiVersion = "1.0"
        const val apiKeyHeader = "x-api-key"
        const val tokenHeader = "X-Incode-Hardware-Id"
        const val minimunPassingScore = 85.0
    }

}