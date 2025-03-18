package com.upayments.starpayapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.upayments.starpayapp.R

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