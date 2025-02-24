package com.upayments.starpayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
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
import com.upayments.starpayapp.ui.theme.AppFont
import com.upayments.starpayapp.ui.theme.AppStrongAccent
import com.upayments.starpayapp.ui.theme.PrimaryBackground
import com.upayments.starpayapp.ui.theme.PrimaryForeground
import com.upayments.starpayapp.ui.theme.SecondaryForeground
import com.upayments.starpayapp.ui.theme.StarPayAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarPayAppTheme {
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
        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
                .width(100.dp),
        painter = painterResource(id = R.drawable.logo_starpay_azul),
        contentDescription = "StarPay Logo",
        // modifier = Modifier.size(300.dp)
        // modifier = Modifier.padding(horizontal = 40.dp)
    )
}

@Composable
fun ActivityIndicator() {

    CircularProgressIndicator(
        modifier = Modifier
            .padding(vertical = 40.dp)
            .width(42.dp),
        color = PrimaryForeground,
        trackColor = SecondaryForeground,
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StarPayAppTheme {

    }
}