package com.upayments.starpayapp.constants

object Constants {

    const val GOOGLE_CLOUD_PROJECT_NUMBER = 785511551848
    // FOR INTEGRITY DEBUG, SET THE SAME VALUE HERE THAT IS CONFIGURED IN THE NOTARY SERVICE
    const val INTEGRITY_FLOW_ID = "debug_play_integrity_pass"


    // FOR DEV ENVIRONMENT
    // const val NOTARY_SERVICE_URL = "https://servicesrunner-dev.u-payments.com:16143/api/v1/"
    // FOR PRODUCTION ENVIRONMENT
    // const val NOTARY_SERVICE_URL = "https://servicesrunner.u-payments.com:16143/api/v1/"
    // const val NOTARY_SERVICE_INTEGRITY_CHECK_ENDPOINT = "integrity/validate"

    // FOR DEV ENVIRONMENT
    // const val ALCHEMIST_SERVICE_URL = "https://servicesrunner-dev.u-payments.com:16201/api/v1/"
    // FOR PRODUCTION ENVIRONMENT
    // const val ALCHEMIST_SERVICE_URL = "https://servicesrunner.u-payments.com:16201/api/v1/"
    // const val ALCHEMIST_SERVICE_EXCHANGE_KEYS_ENDPOINT = "keys/exchange"

    // FOR DEV ENVIRONMENT
    const val INCODE_API_URL = "https://demo-api.incodesmile.com"
    const val INCODE_API_KEY = "e466fce268af02ba0660013c4a064030dc214887"
    const val INCODE_FLOW_ID = "66cda16b454355d4e6e32000"

}