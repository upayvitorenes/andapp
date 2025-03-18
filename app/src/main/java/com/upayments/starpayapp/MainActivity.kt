package com.upayments.starpayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.incode.welcome_sdk.CommonConfig
import com.incode.welcome_sdk.IncodeWelcome
import com.upayments.starpayapp.constants.Constants
import com.upayments.starpayapp.navigation.AppNavigation
import com.upayments.starpayapp.ui.theme.StarPayAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Get the credentials from the appropriate service, not from "Constants"
        IncodeWelcome.Builder(this.application, Constants.INCODE_API_URL, Constants.INCODE_API_KEY)
            .setLoggingEnabled(true)
            // .setTestModeEnabled(true)
            .build()

        val commonConfig = CommonConfig.Builder()
            .setShowCloseButton(true)
            .setShowExitConfirmation(true)
            .setLocalizationLanguage("es_ES")
            .build()

        IncodeWelcome.getInstance().setCommonConfig(commonConfig)

        enableEdgeToEdge()

        setContent {
            StarPayAppTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StarPayAppTheme {
        AppNavigation()
    }
}