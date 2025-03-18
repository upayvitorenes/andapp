package com.upayments.starpayapp.main_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.android.play.core.integrity.StandardIntegrityManager.PrepareIntegrityTokenRequest
import com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenRequest
import com.google.gson.Gson
import com.upayments.starpayapp.AppEntryPoint
import com.upayments.starpayapp.R
import com.upayments.starpayapp.components.ActivityIndicator
import com.upayments.starpayapp.components.AppLogo
import com.upayments.starpayapp.constants.AppKeys
import com.upayments.starpayapp.constants.Constants
import com.upayments.starpayapp.constants.Services
import com.upayments.starpayapp.main_views.models.AppInitViewModel
import com.upayments.starpayapp.network.IntegrityRequest
import com.upayments.starpayapp.network.IntegrityResponse
import com.upayments.starpayapp.network.NotaryService
import com.upayments.starpayapp.state.ApplicationState
import com.upayments.starpayapp.state.SecureStorage
import com.upayments.starpayapp.ui.theme.AppFont
import com.upayments.starpayapp.ui.theme.AppStrongAccent
import com.upayments.starpayapp.ui.theme.PrimaryBackground
import com.upayments.starpayapp.ui.theme.PrimaryForeground
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import java.security.SecureRandom
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun AppInitView(navController: NavController) {

    val viewModel: AppInitViewModel = hiltViewModel()
    var integrityCheckResult by remember { mutableStateOf<Boolean?>(null) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val tilePainter = painterResource(id = R.drawable.tile_bkg)

        if (integrityCheckResult == false) {
            NoOnboardingPossibleView(navController)
        } else {
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
                        .width(240.dp)
                        .padding(vertical = 80.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                ActivityIndicator()
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(vertical = 40.dp),
                    text = "¡Te damos la bienvenida!",
                    style = TextStyle(
                        fontFamily = AppFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = AppStrongAccent
                )
                Text(
                    modifier = Modifier.padding(bottom = 80.dp),
                    text = "Estamos iniciando tu Aplicación.\nPor favor, espera unos segundos.",
                    style = TextStyle(
                        fontFamily = AppFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    ),
                    color = PrimaryForeground
                )
            }
        }
    }

    /**
     * Generates a nonce that can be used in the integrity check process
     */
    fun generateSecureNonce(): String {
        val random = SecureRandom()
        return BigInteger(240, random).toString(32)
    }

    suspend fun requestClassicIntegrityTokenSuspend(
        integrityManager: IntegrityManager,
        request: IntegrityTokenRequest
    ): String {
        return suspendCoroutine { continuation ->
            integrityManager.requestIntegrityToken(request)
                .addOnSuccessListener { result ->
                    continuation.resume(result.token())
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    val context = LocalContext.current
    val gcProjectNumber = Constants.GOOGLE_CLOUD_PROJECT_NUMBER

    val networkHelper = remember {
        EntryPointAccessors.fromApplication(context, AppEntryPoint::class.java).networkHelper()
    }
    val coroutineScope = rememberCoroutineScope()

    val appState by ApplicationState.state.collectAsState()

    suspend fun handleFirstLaunch() {
        Timber.tag("APP_INIT").d("Assessing Application is in first launch...")
    }

    suspend fun assessIntegrity() {

        Timber.tag("APP_INIT").d("At APP_INIT - Assessing Device and App Integrity")
        // Timber.tag("APP_INIT").d("At APP_INIT, you have: ${SecureStorage.getString("test")}")

        val keyId = SecureStorage.getString(AppKeys.AppState.APP_ATTEST_KEY)

        if (!keyId.isNullOrEmpty()) {
            Timber.tag("APP_INIT").d("KeyID found in SecureStorage: $keyId")
        } else {
            Timber.tag("APP_INIT").d("No stored KeyID found in SecureStorage (new instance?")
        }

        withContext(Dispatchers.IO) {

            val standardIntegrityManager = IntegrityManagerFactory.createStandard(context)

            standardIntegrityManager.prepareIntegrityToken(
                PrepareIntegrityTokenRequest.builder()
                    .setCloudProjectNumber(gcProjectNumber)
                    .build()
            ).addOnSuccessListener { tokenProvider ->

                val nonce = generateSecureNonce()

                tokenProvider.request(
                    StandardIntegrityTokenRequest.builder()
                        .setRequestHash(nonce)
                        .build()
                ).addOnSuccessListener { result ->
                    Timber.tag("APP INIT INTEGRITY").d("integrityToken: ${result.token()}")
                    Timber.tag("APP INIT INTEGRITY").d("Calling NOTARY Service now...")

                    coroutineScope.launch(Dispatchers.IO) {
                        val service = networkHelper.getService(NotaryService::class.java, Services.Urls.attestServiceBaseUrl)
                        val checkResult: Result<IntegrityResponse> = networkHelper.makeRequest { service.integrityCheck(
                            // TODO: Remember to remove this INTEGRITY_FLOW_ID to final version so that the integrity check really happens!
                            IntegrityRequest(nonce, result.token(), Constants.INTEGRITY_FLOW_ID, keyId)
                        ) }

                        Timber.tag("NOTARY SERVICE").d("NOTARY Responded...")

                        checkResult.fold(
                            onSuccess = { response ->
                                val jsonString = Gson().toJson(response)
                                Timber.tag("INTEGRITY REPORT RESULT").d(jsonString)

                                if (response.result) {
                                    withContext(Dispatchers.Main) {
                                        Timber.tag("APP_INIT").d("INTEGRITY CHECK PASSED - This App Key ID is: ${response.key_id}")
                                        Timber.tag("APP_INIT").d("Storing KeyID in SecureStorage (might be overwriting previous value)...")
                                        SecureStorage.setString(AppKeys.AppState.APP_ATTEST_KEY, response.key_id)

                                        integrityCheckResult = true
                                        // navController.navigate("onboard_user")
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        integrityCheckResult = false
                                    }
                                }

                            },
                            onFailure = { exception ->
                                Timber.tag("INTEGRITY REPORT ERROR").e("NOTARY ERROR: ${exception.message}")
                                withContext(Dispatchers.Main) {
                                    integrityCheckResult = false
                                }
                            }
                        )
                    }
                }.addOnFailureListener { exception ->
                    Timber.tag("APP INIT INTEGRITY").e(exception)
                    coroutineScope.launch(Dispatchers.Main) {
                        integrityCheckResult = false
                    }
                }

            }.addOnFailureListener { exception ->
                Timber.tag("APP INIT ERROR IN INTEGRITY").e(exception)
                coroutineScope.launch(Dispatchers.Main) {
                    integrityCheckResult = false
                }
            }

        }

    }

    /**
     * Assess the Application and Device Integrity as soon as possible
     */
    LaunchedEffect(Unit) {
        if (!appState.appIsInitialized) {
            Timber.tag("APP_INIT").d("No ApplicationIsInitialized flag set, so handling...")
            handleFirstLaunch()
            assessIntegrity()
        } else {
            Timber.tag("APP_INIT").d("ApplicationIsInitialized flag set, assuming all is good.")
            Timber.tag("APP_INIT").d("Handling the Public Key Exchange...")
            val publicKeyExchangeResult = viewModel.handlePublicKeyExchange()
            if (publicKeyExchangeResult) {
                Timber.tag("APP_INIT").d("Public Key Exchange was SUCCESSFUL, continuing App Initialization...")
                // TODO: Move to next step - maybe by setting a value that triggers the next LaunchedEffect?
            } else {
                Timber.tag("APP_INIT").e("Public Key Exchange was UNSUCCESSFUL, aborting App Initialization...")
                // TODO: Show error message to user
            }
        }
    }

    /**
     * Once we have an integrity check result, handle the Public Key Exchange with Alchemist Service
     */
    LaunchedEffect(integrityCheckResult) {
        if (integrityCheckResult == true) {
            Timber.tag("APP_INIT").d("Integrity Check PASSED, continuing App Initialization...")
            Timber.tag("APP_INIT").d("Handling the Public Key Exchange...")
            val publicKeyExchangeResult = viewModel.handlePublicKeyExchange()
            if (publicKeyExchangeResult) {
                Timber.tag("APP_INIT").d("Public Key Exchange was SUCCESSFUL, continuing App Initialization...")
                // TODO: Move to next step - maybe by setting a value that triggers the next LaunchedEffect?
            } else {
                Timber.tag("APP_INIT").e("Public Key Exchange was UNSUCCESSFUL, aborting App Initialization...")
                // TODO: Show error message to user
            }


            // TODO: This is only for testing - remove later when the process is complete
            navController.navigate("onboard_user")
            /*
            ApplicationState.setAppIsInitialized(true)
            */
        }
    }

}