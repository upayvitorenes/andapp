package com.upayments.starpayapp.state.enums

enum class OnboardingPhaseStatus {
    DISABLED,
    PENDING,
    ONGOING,
    COMPLETED
}

data class OnboardingPhase(
    val id: Int,
    val title: String,
    var status: OnboardingPhaseStatus,
    val canBeBacktracked: Boolean
)

enum class IncodeOnboardingResult {
    SUCCESS,
    FLAGGED,
    FAILED,
    CANCELLED,
    NOT_STARTED
}

enum class OnboardingField {
    USER_NAMES,
    USER_LASTNAMES,
    USER_BIRTHDAY,
    USER_FISCAL_ID,
    USER_EMAIL,
    USER_MOBILE_PHONE,
    USER_ADDRESS,
    USER_ADDRESS_STREET,
    USER_ADDRESS_NUMBER,
    USER_ADDRESS_COMPLEMENT,
    USER_ADDRESS_ADMINISTRATIVE_REGION,
    USER_ADDRESS_CITY,
    USER_ADDRESS_POSTAL_CODE,
    USER_ADDRESS_COUNTRY_CODE
}