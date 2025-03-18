package com.upayments.starpayapp.state

import com.upayments.starpayapp.constants.AppKeys
import com.upayments.starpayapp.state.enums.Genders
import com.upayments.starpayapp.state.enums.IncodeOnboardingResult
import com.upayments.starpayapp.state.enums.MaritalStatuses
import com.upayments.starpayapp.state.enums.OnboardingPhase
import com.upayments.starpayapp.state.enums.OnboardingPhaseStatus
import kotlin.math.roundToInt





interface OnboardingProgressContract {
    val phases: List<OnboardingPhase>
    val currentPhaseIndex: Int
    val phasesCount: Int get() = phases.size
}





// object OnboardingProgressState {
object OnboardingProgressState : OnboardingProgressContract {

    override var phases: MutableList<OnboardingPhase> = mutableListOf(
        OnboardingPhase(1, "Inicio", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(2, "Verificación de Identidad", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(3, "Aceptación de Contrato", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(4, "Aceptación de Términos", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(5, "Aceptación de Política de Privacidad", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(6, "Datos Adicionales y Confirmaciones", OnboardingPhaseStatus.DISABLED, true),
        OnboardingPhase(7, "Confirmación Final", OnboardingPhaseStatus.DISABLED, true)
    )

    var isMovingForward: Boolean = true

    // var incodeAuthToken: String = "" (commented out in Swift)
    var kycAuthToken: String = ""

    // var onboardingEmailConfirmationCode: String = "" (commented out in Swift)
    var emailConfirmationCode: String = ""

    // var onboardingPhoneConfirmationCode: String = "" (commented out in Swift)
    var mobilePhoneConfirmationCode: String = ""

    var userCancelledOnboarding: Boolean = false

    // --- Computed properties ---

    override val currentPhaseIndex: Int
        get() = when {
            phases.all { it.status == OnboardingPhaseStatus.PENDING } -> 0
            phases.all { it.status == OnboardingPhaseStatus.DISABLED } -> -1
            phases.all { it.status == OnboardingPhaseStatus.COMPLETED } -> phases.size
            else -> phases.indexOfFirst { it.status == OnboardingPhaseStatus.ONGOING }.let { if (it >= 0) it else 0 }
        }

    override val phasesCount: Int
        get() = phases.size

    val isFinalPhase: Boolean
        get() = currentPhaseIndex == phases.size

    val overallProgress: Int
        get() {
            val completedPhases = phases.count { it.status == OnboardingPhaseStatus.COMPLETED }
            val progress = completedPhases.toDouble() / phases.size.toDouble()
            return (progress * 100).roundToInt()
        }

    // --- Phase navigation methods ---

    fun setUserCancelledOnboarding() {
        // In Swift these updates were dispatched to the main thread.
        userCancelledOnboarding = true
        setKycOnboardingSessionCompleted(false)
        setKycOnboardingSessionResult(IncodeOnboardingResult.CANCELLED)
    }

    fun moveToNextPhase() {
        val current = currentPhaseIndex
        if (current < phasesCount) {
            updatePhaseStatus(current, OnboardingPhaseStatus.COMPLETED)
        } else {
            return
        }
        if (current + 1 < phasesCount) {
            updatePhaseStatus(current + 1, OnboardingPhaseStatus.ONGOING)
        }
    }

    fun moveToPreviousPhase() {
        val current = currentPhaseIndex
        if (current >= 0) {
            updatePhaseStatus(current, OnboardingPhaseStatus.PENDING)
        } else {
            return
        }
        var previousIndex = current - 1
        while (previousIndex >= 0) {
            val previousPhase = phases[previousIndex]
            if (previousPhase.canBeBacktracked) {
                updatePhaseStatus(previousIndex, OnboardingPhaseStatus.ONGOING)
                break
            } else {
                updatePhaseStatus(previousIndex, OnboardingPhaseStatus.PENDING)
            }
            previousIndex--
        }
    }

    fun resetPhases() {
        phases = mutableListOf(
            OnboardingPhase(1, "Inicio", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(2, "Verificación de Identidad", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(3, "Aceptación de Contrato", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(4, "Aceptación de Términos", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(5, "Aceptación de Política de Privacidad", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(6, "Datos Adicionales y Confirmaciones", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(7, "Confirmación Final", OnboardingPhaseStatus.DISABLED, true)
        )
    }

    fun initTracking() {
        phases = mutableListOf(
            OnboardingPhase(1, "Inicio", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(2, "Verificación de Identidad", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(3, "Aceptación de Contrato", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(4, "Aceptación de Términos", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(5, "Aceptación de Política de Privacidad", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(6, "Datos Adicionales y Confirmaciones", OnboardingPhaseStatus.PENDING, true),
            OnboardingPhase(7, "Confirmación Final", OnboardingPhaseStatus.PENDING, true)
        )
    }

    private fun updatePhaseStatus(phaseIndex: Int, status: OnboardingPhaseStatus) {
        if (phaseIndex !in phases.indices) return
        phases[phaseIndex].status = status

        if (status == OnboardingPhaseStatus.ONGOING) {
            phases.forEachIndexed { index, phase ->
                if (index != phaseIndex && phase.status == OnboardingPhaseStatus.ONGOING) {
                    phases[index].status = OnboardingPhaseStatus.PENDING
                }
            }
        }
    }

    // --- SecureStorage-backed properties and their setters/unsetters ---

    private var uDnaId: String = ""
    private var kycSessionOngoing: Boolean = false
    private var kycOnboardingSessionCompleted: Boolean = false
    private var kycOnboardingSessionResult: IncodeOnboardingResult = IncodeOnboardingResult.NOT_STARTED
    private var kycSessionId: String = ""

    private var userNames: String = ""
    private var userLastNames: String = ""
    private var userBirthday: String = ""
    private var userFiscalId: String = ""
    private var userEmail: String = ""
    private var userMobilePhone: String = ""

    private var userAddress: String = ""

    private var userAddressStreet: String = ""
    private var userAddressNumber: String = ""
    private var userAddressComplement: String = ""
    private var userAddressAdministrativeRegion: String = ""
    private var userAddressCity: String = ""
    private var userAddressPostalCode: String = ""
    private var userAddressCountryCode: String = ""

    private var userGender: Genders = Genders.OTHER
    private var userMaritalStatus: MaritalStatuses = MaritalStatuses.SINGLE

    private var userDataIsSet: Boolean = false
    private var pushNotificationsSubscriptionId: String = ""
    private var userConfirmedEmail: Boolean = false
    private var userConfirmedPhoneNumber: Boolean = false
    private var userAcceptedContract: Boolean = false
    private var userAcceptedTerms: Boolean = false
    private var userAcceptedPrivacy: Boolean = false


    fun setUDnaId(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.UDNA_ID)
        uDnaId = value
    }
    fun unSetUDnaId() {
        SecureStorage.delete(AppKeys.UserKeys.UDNA_ID)
        uDnaId = ""
    }

    fun setKycSessionOngoing(value: Boolean) {
        SecureStorage.setBool(AppKeys.KycKeys.KYC_SESSION_ONGOING, value)
        kycSessionOngoing = value
    }
    fun unSetKycSessionOngoing() {
        SecureStorage.delete(AppKeys.KycKeys.KYC_SESSION_ONGOING)
        kycSessionOngoing = false
    }

    fun setKycOnboardingSessionCompleted(value: Boolean) {
        SecureStorage.setBool(AppKeys.KycKeys.KYC_SESSION_COMPLETED, value)
        kycOnboardingSessionCompleted = value
    }
    fun unSetKycOnboardingSessionCompleted() {
        SecureStorage.delete(AppKeys.KycKeys.KYC_SESSION_COMPLETED)
        kycOnboardingSessionCompleted = false
    }

    fun setKycOnboardingSessionResult(completedAs: IncodeOnboardingResult) {
        when (completedAs) {
            IncodeOnboardingResult.CANCELLED -> SecureStorage.setString("cancelled", AppKeys.KycKeys.KYC_SESSION_RESULT)
            IncodeOnboardingResult.SUCCESS -> SecureStorage.setString("success", AppKeys.KycKeys.KYC_SESSION_RESULT)
            IncodeOnboardingResult.FLAGGED -> SecureStorage.setString("flagged", AppKeys.KycKeys.KYC_SESSION_RESULT)
            IncodeOnboardingResult.FAILED -> SecureStorage.setString("failed", AppKeys.KycKeys.KYC_SESSION_RESULT)
            IncodeOnboardingResult.NOT_STARTED -> SecureStorage.setString("notStarted", AppKeys.KycKeys.KYC_SESSION_RESULT)
        }
        kycOnboardingSessionResult = completedAs
    }
    fun unSetKycOnboardingSessionResult() {
        SecureStorage.delete(AppKeys.KycKeys.KYC_SESSION_RESULT)
        kycOnboardingSessionResult = IncodeOnboardingResult.NOT_STARTED
    }

    fun setKycSessionId(value: String) {
        SecureStorage.setString(value, AppKeys.KycKeys.KYC_SESSION_ID)
        kycSessionId = value
    }
    fun unSetKycSessionId() {
        SecureStorage.delete(AppKeys.KycKeys.KYC_SESSION_ID)
        kycSessionId = ""
    }

    fun setUserNames(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_NAMES)
        userNames = value
    }
    fun unSetUserNames() {
        SecureStorage.delete(AppKeys.UserKeys.USER_NAMES)
        userNames = ""
    }

    fun setUserLastNames(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_LAST_NAMES)
        userLastNames = value
    }
    fun unSetUserLastNames() {
        SecureStorage.delete(AppKeys.UserKeys.USER_LAST_NAMES)
        userLastNames = ""
    }

    fun setUserBirthday(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_BIRTHDAY)
        userBirthday = value
    }
    fun unSetUserBirthday() {
        SecureStorage.delete(AppKeys.UserKeys.USER_BIRTHDAY)
        userBirthday = ""
    }

    fun setUserFiscalId(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_FISCAL_ID)
        userFiscalId = value
    }
    fun unSetUserFiscalId() {
        SecureStorage.delete(AppKeys.UserKeys.USER_FISCAL_ID)
        userFiscalId = ""
    }

    fun setUserEmail(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_EMAIL)
        userEmail = value
    }
    fun unSetUserEmail() {
        SecureStorage.delete(AppKeys.UserKeys.USER_EMAIL)
        userEmail = ""
    }

    fun setUserMobilePhone(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_MOBILE_PHONE_NUMBER)
        userMobilePhone = value
    }
    fun unSetUserMobilePhone() {
        SecureStorage.delete(AppKeys.UserKeys.USER_MOBILE_PHONE_NUMBER)
        userMobilePhone = ""
    }

    fun setUserAddress(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS)
        userAddress = value
    }
    fun unSetUserAddress() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS)
        userAddress = ""
    }

    fun setUserAddressStreet(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_STREET)
        userAddressStreet = value
    }
    fun unSetUserAddressStreet() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_STREET)
        userAddressStreet = ""
    }

    fun setUserAddressNumber(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_NUMBER)
        userAddressNumber = value
    }
    fun unSetUserAddressNumber() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_NUMBER)
        userAddressNumber = ""
    }

    fun setUserAddressComplement(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_COMPLEMENT)
        userAddressComplement = value
    }
    fun unSetUserAddressComplement() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_COMPLEMENT)
        userAddressComplement = ""
    }

    fun setUserAddressAdministrativeRegion(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_ADMINISTRATIVE_AREA)
        userAddressAdministrativeRegion = value
    }
    fun unSetUserAddressAdministrativeRegion() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_ADMINISTRATIVE_AREA)
        userAddressAdministrativeRegion = ""
    }

    fun setUserAddressCity(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_CITY)
        userAddressCity = value
    }
    fun unSetUserAddressCity() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_CITY)
        userAddressCity = ""
    }

    fun setUserAddressPostalCode(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_POSTAL_CODE)
        userAddressPostalCode = value
    }
    fun unSetUserAddressPostalCode() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_POSTAL_CODE)
        userAddressPostalCode = ""
    }

    fun setUserAddressCountryCode(value: String) {
        SecureStorage.setString(value, AppKeys.UserKeys.USER_ADDRESS_COUNTRY_CODE)
        userAddressCountryCode = value
    }
    fun unSetUserAddressCountryCode() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ADDRESS_COUNTRY_CODE)
        userAddressCountryCode = ""
    }

    fun setUserGender(value: String) {
        val gender = Genders.fromRawValue(value)
        SecureStorage.setString(gender.rawValue, AppKeys.UserKeys.USER_GENDER)
        userGender = gender
    }
    fun unSetUserGender() {
        SecureStorage.delete(AppKeys.UserKeys.USER_GENDER)
        userGender = Genders.OTHER
    }

    fun setUserMaritalStatus(value: String) {
        val status = MaritalStatuses.fromRawValue(value)
        SecureStorage.setString(status.rawValue, AppKeys.UserKeys.USER_MARITAL_STATUS)
        userMaritalStatus = status
    }
    fun unSetUserMaritalStatus() {
        SecureStorage.delete(AppKeys.UserKeys.USER_MARITAL_STATUS)
        userMaritalStatus = MaritalStatuses.SINGLE
    }

    fun setUserDataIsSet(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_DATA_IS_SET, value)
        userDataIsSet = value
    }
    fun unSetUserDataIsSet() {
        SecureStorage.delete(AppKeys.UserKeys.USER_DATA_IS_SET)
        userDataIsSet = false
    }

    fun setPushNotificationsSubscriptionId(value: String) {
        SecureStorage.setString(value, AppKeys.PushNotificationsKeys.SUBSCRIPTION_ID_KEY)
        pushNotificationsSubscriptionId = value
    }
    fun unSetPushNotificationsSubscriptionId() {
        SecureStorage.delete(AppKeys.PushNotificationsKeys.SUBSCRIPTION_ID_KEY)
        pushNotificationsSubscriptionId = ""
    }

    fun setUserConfirmedEmail(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_CONFIRMED_EMAIL, value)
        userConfirmedEmail = value
    }
    fun unSetUserConfirmedEmail() {
        SecureStorage.delete(AppKeys.UserKeys.USER_CONFIRMED_EMAIL)
        userConfirmedEmail = false
    }

    fun setUserConfirmedPhoneNumber(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_CONFIRMED_PHONE_NUMBER, value)
        userConfirmedPhoneNumber = value
    }
    fun unSetUserConfirmedPhoneNumber() {
        SecureStorage.delete(AppKeys.UserKeys.USER_CONFIRMED_PHONE_NUMBER)
        userConfirmedPhoneNumber = false
    }

    fun setUserAcceptedContract(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_ACCEPTED_CONTRACT, value)
        userAcceptedContract = value
    }
    fun unSetUserAcceptedContract() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ACCEPTED_CONTRACT)
        userAcceptedContract = false
    }

    fun setUserAcceptedTerms(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_ACCEPTED_TERMS, value)
        userAcceptedTerms = value
    }
    fun unSetUserAcceptedTerms() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ACCEPTED_TERMS)
        userAcceptedTerms = false
    }

    fun setUserAcceptedPrivacy(value: Boolean) {
        SecureStorage.setBool(AppKeys.UserKeys.USER_ACCEPTED_PRIVACY, value)
        userAcceptedPrivacy = value
    }
    fun unSetUserAcceptedPrivacy() {
        SecureStorage.delete(AppKeys.UserKeys.USER_ACCEPTED_PRIVACY)
        userAcceptedPrivacy = false
    }

    // --- Private helper method ---
    private fun checkAcceptances() {
        userAcceptedContract = SecureStorage.getString(AppKeys.UserKeys.USER_ACCEPTED_CONTRACT) != null
        userAcceptedTerms = SecureStorage.getString(AppKeys.UserKeys.USER_ACCEPTED_TERMS) != null
        userAcceptedPrivacy = SecureStorage.getString(AppKeys.UserKeys.USER_ACCEPTED_PRIVACY) != null
    }

    // --- Initialization (runs when the singleton is first accessed) ---
    init {
        // print("***** OnboardingProgressState INIT running now...")
        uDnaId = SecureStorage.getString(AppKeys.UserKeys.UDNA_ID) ?: ""
        kycOnboardingSessionCompleted = SecureStorage.getBool(AppKeys.KycKeys.KYC_SESSION_COMPLETED)

        val storedValue = SecureStorage.getString(AppKeys.KycKeys.KYC_SESSION_RESULT)
        kycOnboardingSessionResult = when (storedValue) {
            "cancelled" -> IncodeOnboardingResult.CANCELLED
            "success" -> IncodeOnboardingResult.SUCCESS
            "flagged" -> IncodeOnboardingResult.FLAGGED
            "failed" -> IncodeOnboardingResult.FAILED
            "notStarted" -> IncodeOnboardingResult.NOT_STARTED
            else -> IncodeOnboardingResult.NOT_STARTED
        }

        kycSessionOngoing = SecureStorage.getBool(AppKeys.KycKeys.KYC_SESSION_ONGOING)
        kycSessionId = SecureStorage.getString(AppKeys.KycKeys.KYC_SESSION_ID) ?: ""
        userNames = SecureStorage.getString(AppKeys.UserKeys.USER_NAMES) ?: ""
        userLastNames = SecureStorage.getString(AppKeys.UserKeys.USER_LAST_NAMES) ?: ""
        userBirthday = SecureStorage.getString(AppKeys.UserKeys.USER_BIRTHDAY) ?: ""
        userFiscalId = SecureStorage.getString(AppKeys.UserKeys.USER_FISCAL_ID) ?: ""
        userEmail = SecureStorage.getString(AppKeys.UserKeys.USER_EMAIL) ?: ""
        userMobilePhone = SecureStorage.getString(AppKeys.UserKeys.USER_MOBILE_PHONE_NUMBER) ?: ""

        val genderString = SecureStorage.getString(AppKeys.UserKeys.USER_GENDER)
        println("@ONBOARDING STATE INIT - GOT STORED GENDER STRING: ${genderString ?: ""}")
        userGender = when (genderString) {
            "3" -> Genders.MALE
            "4" -> Genders.FEMALE
            "6" -> Genders.OTHER
            else -> Genders.OTHER
        }

        val maritalStatusString = SecureStorage.getString(AppKeys.UserKeys.USER_MARITAL_STATUS)
        println("@ONBOARDING STATE INIT - GOT STORED MARITAL STATUS STRING: ${maritalStatusString ?: ""}")
        userMaritalStatus = when (maritalStatusString) {
            "7" -> MaritalStatuses.SINGLE
            "8" -> MaritalStatuses.MARRIED
            "9" -> MaritalStatuses.DIVORCED
            "10" -> MaritalStatuses.WIDOW
            else -> MaritalStatuses.SINGLE
        }

        userAddress = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS) ?: ""
        userAddressStreet = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_STREET) ?: ""
        userAddressNumber = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_NUMBER) ?: ""
        userAddressComplement = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_COMPLEMENT) ?: ""
        userAddressAdministrativeRegion = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_ADMINISTRATIVE_AREA) ?: ""
        userAddressCity = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_CITY) ?: ""
        userAddressPostalCode = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_POSTAL_CODE) ?: ""
        userAddressCountryCode = SecureStorage.getString(AppKeys.UserKeys.USER_ADDRESS_COUNTRY_CODE) ?: ""

        userDataIsSet = SecureStorage.getBool(AppKeys.UserKeys.USER_DATA_IS_SET)
        pushNotificationsSubscriptionId = SecureStorage.getString(AppKeys.PushNotificationsKeys.SUBSCRIPTION_ID_KEY) ?: ""
        userConfirmedEmail = SecureStorage.getBool(AppKeys.UserKeys.USER_CONFIRMED_EMAIL)
        userConfirmedPhoneNumber = SecureStorage.getBool(AppKeys.UserKeys.USER_CONFIRMED_PHONE_NUMBER)
        userAcceptedContract = SecureStorage.getBool(AppKeys.UserKeys.USER_ACCEPTED_CONTRACT)
        userAcceptedTerms = SecureStorage.getBool(AppKeys.UserKeys.USER_ACCEPTED_TERMS)
        userAcceptedPrivacy = SecureStorage.getBool(AppKeys.UserKeys.USER_ACCEPTED_PRIVACY)
    }
}

