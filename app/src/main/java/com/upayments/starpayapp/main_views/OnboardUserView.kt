package com.upayments.starpayapp.main_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upayments.starpayapp.R
import com.upayments.starpayapp.components.AppLogo
import com.upayments.starpayapp.state.ApplicationState
import com.upayments.starpayapp.ui.theme.AppFont
import com.upayments.starpayapp.ui.theme.AppStrongAccent
import com.upayments.starpayapp.ui.theme.PrimaryBackground
import com.upayments.starpayapp.ui.theme.PrimaryButtonBkg
import com.upayments.starpayapp.ui.theme.PrimaryForeground

@Composable
fun OnboardUserView(navController: NavController) {

    val appState by ApplicationState.state.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    .width(240.dp)
                    .padding(vertical = 80.dp)
            )

            Text(text = if (appState.appIsInitialized) "App Initialized!" else "Not been initialized?")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .width(260.dp),
                onClick = {
                    navController.navigate("onboarding_welcome")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryButtonBkg
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    style = TextStyle(
                        fontFamily = AppFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    text ="Regístrate con StarPay",
                    color = PrimaryForeground
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = 40.dp),
                onClick = {
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "¿Ya tienes una cuenta StarPay?",
                    style = TextStyle(
                        fontFamily = AppFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = AppStrongAccent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardUserView() {
    val navController = rememberNavController() // Requires a preview-safe navigation approach
    OnboardUserView(navController)
}