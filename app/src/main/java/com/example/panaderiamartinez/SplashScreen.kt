package com.example.panaderiamartinez

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {

    //////////////////////////////////////////////////////
    // ⏱️ ESPERA 2 SEGUNDOS
    //////////////////////////////////////////////////////
    LaunchedEffect(Unit) {
        delay(2000)
        onFinish()
    }

    //////////////////////////////////////////////////////
    // 🎨 UI
    //////////////////////////////////////////////////////
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E0C3)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}

