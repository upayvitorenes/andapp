package com.upayments.starpayapp.main_views

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upayments.starpayapp.R
import com.upayments.starpayapp.components.AppLogo
import com.upayments.starpayapp.ui.theme.AppFont
import com.upayments.starpayapp.ui.theme.AppStrongAccent
import com.upayments.starpayapp.ui.theme.PrimaryBackground
import com.upayments.starpayapp.ui.theme.PrimaryButtonBkg
import com.upayments.starpayapp.ui.theme.PrimaryForeground

@Composable
fun NoOnboardingPossibleView(navController: NavController) {

    val activity = LocalActivity.current

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

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .padding(horizontal = 60.dp),
                text = "Lo sentimos, pero tu Dispositivo, o la versión de la Aplicación que has instalado, no cumplen con los requisitos de Seguridad de StarPay.",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    fontFamily = AppFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                color = AppStrongAccent
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 40.dp),
                text = "Por este motivo, no podemos continuar con el proceso de Registro de tu Cuenta StarPay. Si crees que esto es un error, contacta con el equipo de StarPay al e-mail:",
                style = TextStyle(
                    fontFamily = AppFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                ),
                color = PrimaryForeground
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 60.dp),
                text = "soporte@starpay.com.ar",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    fontFamily = AppFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                color = AppStrongAccent
            )

            Button(
                modifier = Modifier
                    .width(260.dp)
                    .padding(top = 80.dp),
                onClick = {
                    activity?.finishAffinity()
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
                    text ="Cerrar",
                    color = PrimaryForeground
                )
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoOnboardingPossibleViewPreview() {
    NoOnboardingPossibleView(navController = rememberNavController())
}