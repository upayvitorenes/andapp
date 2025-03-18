package com.upayments.starpayapp.onboarding

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.incode.welcome_sdk.CommonConfig
import com.incode.welcome_sdk.FlowConfig
import com.incode.welcome_sdk.IncodeWelcome
import com.incode.welcome_sdk.SessionConfig
import com.incode.welcome_sdk.listeners.OnboardingSessionListener
import com.incode.welcome_sdk.modules.IdScan
import com.incode.welcome_sdk.modules.SelfieScan
import com.incode.welcome_sdk.results.DocumentValidationResult
import com.incode.welcome_sdk.results.FaceMatchResult
import com.incode.welcome_sdk.results.IdProcessResult
import com.incode.welcome_sdk.results.IdScanResult
import com.incode.welcome_sdk.results.SelfieScanResult
import com.incode.welcome_sdk.ui.camera.id_validation.base.DocumentType
import com.upayments.starpayapp.R
import com.upayments.starpayapp.components.AppLogo
import com.upayments.starpayapp.constants.Constants
import com.upayments.starpayapp.ui.theme.PrimaryBackground
import timber.log.Timber

@Composable
// fun OnboardingWelcomeView(navController: NavController) {
fun OnboardingWelcomeView() {

    // TODO: Get the credentials from the appropriate service, not from "Constants"
    val sessionConfig: SessionConfig = SessionConfig.Builder()
        .setConfigurationId(Constants.INCODE_FLOW_ID)
        // .setRegionIsoCode("CL")
        .build()

    IncodeWelcome.getInstance().setupOnboardingSession(
        sessionConfig,
        object : OnboardingSessionListener {
            override fun onOnboardingSessionCreated(
                token: String?,
                interviewId: String?,
                region: String?
            ) {
                // Onboarding Session Created successfully
            }

            override fun onError(error: Throwable) {}
            override fun onUserCancelled() {}
        })

    val flowConfig: FlowConfig = FlowConfig.Builder()
        .addID(
            IdScan.Builder()
                .setIdType(IdScan.IdType.ID)
                .setShowIdTutorials(true)
                .build())
        .addSelfieScan(SelfieScan.Builder().build())
        .addFaceMatch()
        .build()

    val onboardingListener: IncodeWelcome.OnboardingListener = object : IncodeWelcome.OnboardingListener() {

        override fun onOnboardingSessionCreated(
            token: String?,
            interviewId: String?,
            region: String
        ) {
            super.onOnboardingSessionCreated(token, interviewId, region)
            Timber.tag("INCODE INIT").d("onOnboardingSessionCreated was completed.")
            Timber.tag("INCODE INIT - TOKEN:").d(token)
            Timber.tag("INCODE INIT - INTERVIEW ID:").d(interviewId)
            Timber.tag("INCODE INIT - REGION:").d(region)
        }

        /*
        override fun onOnboardingSessionCreated(token: String?, interviewId: String?, region: String?) {
            Timber.tag("INCODE INIT").d("onOnboardingSessionCreated was completed.")
            Timber.tag("INCODE INIT - TOKEN:").d(token)
            Timber.tag("INCODE INIT - INTERVIEW ID:").d(interviewId)
            Timber.tag("INCODE INIT - REGION:").d(region)
        }
        */

        override fun onIntroCompleted() {
            // Intro screen completed
        }

        override fun onIdFrontCompleted(frontIdScanResult: IdScanResult) {
            Timber.tag("INCODE ID SCAN - FRONT").d("onIdFrontCompleted was completed.")
            Timber.tag("INCODE ID SCAN - FRONT - RESULT:").d(frontIdScanResult.toString())
        }

        override fun onIdBackCompleted(backIdScanResult: IdScanResult) {
            Timber.tag("INCODE ID SCAN - BACK").d("onIdBackCompleted was completed.")
            Timber.tag("INCODE ID SCAN - BACK - RESULT:").d(backIdScanResult.toString())
        }

        override fun onIdProcessed(idProcessResult: IdProcessResult) {
            super.onIdProcessed(idProcessResult)
            Timber.tag("INCODE ID SCAN - PROCESSED").d("onIdProcessed was completed.")
            Timber.tag("INCODE ID SCAN - PROCESSED:").d(idProcessResult.toString())
        }

        /*
        override fun onIdProcessed(idProcessResult: IdProcessResult?) {
            Timber.tag("INCODE ID SCAN - PROCESSED").d("onIdProcessed was completed.")
            Timber.tag("INCODE ID SCAN - PROCESSED:").d(idProcessResult.toString())
        }
        */

        override fun onDocumentValidationCompleted(
            documentType: DocumentType,
            documentValidationResult: DocumentValidationResult
        ) {
            super.onDocumentValidationCompleted(documentType, documentValidationResult)
            Timber.tag("INCODE ID SCAN - PROCESSED").d("onDocumentValidationCompleted was completed.")
        }

        /*
        override fun onDocumentValidationCompleted(
            documentType: DocumentType?,
            result: DocumentValidationResult?
        ) {
            // Document validation completed
        }
        */

        override fun onSelfieScanCompleted(selfieScanResult: SelfieScanResult) {
            // Selfie scan completed
        }

        override fun onFaceMatchCompleted(faceMatchResult: FaceMatchResult) {
            Timber.tag("INCODE FACE MATCH").d("onFaceMatchCompleted was completed")
            Timber.tag("INCODE FACE MATCH - RESULT:").d(faceMatchResult.toString())
        }

        override fun onUserConsentCompleted() {
            // User consent complete
        }

        override fun onSuccess() {
            Timber.tag("INCODE SUCCESS").d("Onboarding Session was completed.")
        }

        override fun onError(error: Throwable) {
            Timber.tag("INCODE ERROR").d("Onboarding Session was failed.")
            Timber.tag("INCODE ERROR:").d(error.toString())
        }

        override fun onUserCancelled() {
            Timber.tag("INCODE CANCELLED").d("Onboarding Session was cancelled.")
        }
    }

    val context = LocalContext.current

    val activity = context as? Activity ?: error("Should be hosted in an Activity context")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) { innerPadding ->
        val tilePainter = painterResource(id = R.drawable.tile_bkg)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackground)
                .paint(
                    painter = tilePainter,
                    contentScale = ContentScale.Crop
                )
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppLogo(
                modifier = Modifier
                    .width(160.dp)
                    .padding(top = 20.dp, bottom = 10.dp)
            )

            // OnboardingProgressIndicator()

            Spacer(modifier = Modifier.weight(1f))
        }
    }

    LaunchedEffect(Unit) {

        val localizationCode = "es_ES"

        IncodeWelcome.getInstance().setCommonConfig(
            CommonConfig.Builder()
                .setLocalizationLanguage(localizationCode)
                .setShowCloseButton(true)
                .setShowExitConfirmation(true)
                .build()
        )

        IncodeWelcome.getInstance().startOnboarding(
        //IncodeWelcome.getInstance().startWorkflow(
            activity,
            sessionConfig,
            flowConfig,
            onboardingListener
        )
    }

}