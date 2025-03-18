package com.upayments.starpayapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.upayments.starpayapp.ui.theme.PrimaryForeground
import com.upayments.starpayapp.ui.theme.SecondaryForeground

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