package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

// Compose
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// 🔥 ANIMACIÓN
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.LaunchedEffect

// UI
import androidx.compose.ui.draw.clip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//////////////////////////////////////////////////////////////
// 🟤 WELCOME SCREEN PRO FINAL
//////////////////////////////////////////////////////////////

@Composable
fun WelcomeScreen(

    //////////////////////////////////////////////////////////
    // 🔐 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onLoginClick: () -> Unit,

    onRegisterClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🔥 ANIMACIÓN FADE-IN
    //////////////////////////////////////////////////////////
    var visible by remember {

        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        visible = true
    }

    //////////////////////////////////////////////////////////
    // ✨ ANIMACIÓN LOGO
    //////////////////////////////////////////////////////////
    val alpha = animateFloatAsState(

        targetValue = if (visible) 1f else 0f,

        label = ""
    )

    //////////////////////////////////////////////////////////
    // 🎨 CONTENEDOR PRINCIPAL
    //////////////////////////////////////////////////////////
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E0C3))
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween
    ) {

        //////////////////////////////////////////////////////
        // 🔝 CONTENIDO SUPERIOR
        //////////////////////////////////////////////////////
        Column(

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(80.dp)
            )

            //////////////////////////////////////////////////
            // 🏪 TÍTULO
            //////////////////////////////////////////////////
            Text(

                text = "Panadería Martínez",

                fontSize = 30.sp,

                fontWeight = FontWeight.Bold,

                letterSpacing = 1.sp,

                color = Color(0xFF4E342E)
            )

            //////////////////////////////////////////////////
            // ✨ SUBTÍTULO
            //////////////////////////////////////////////////
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(

                text = "Bienvenido al sistema artesanal",

                fontSize = 15.sp,

                color = Color(0xFF7A6A5C)
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            //////////////////////////////////////////////////
            // 🔥 LOGO PRO
            //////////////////////////////////////////////////
            Image(

                painter = painterResource(
                    id = R.drawable.logo1
                ),

                contentDescription = "Logo1",

                modifier = Modifier

                    //////////////////////////////////////////////////
                    // 📏 TAMAÑO
                    //////////////////////////////////////////////////
                    .size(160.dp)

                    //////////////////////////////////////////////////
                    // 🔲 BORDES
                    //////////////////////////////////////////////////
                    .clip(
                        RoundedCornerShape(30.dp)
                    )

                    //////////////////////////////////////////////////
                    // 🎨 FONDO
                    //////////////////////////////////////////////////
                    .background(Color.White)

                    //////////////////////////////////////////////////
                    // 📦 ESPACIO INTERNO
                    //////////////////////////////////////////////////
                    .padding(12.dp),

                //////////////////////////////////////////////////
                // ✨ ANIMACIÓN
                //////////////////////////////////////////////////
                alpha = alpha.value
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            //////////////////////////////////////////////////
            // 📦 TEXTO SISTEMA
            //////////////////////////////////////////////////
            Text(

                text = "Sistema de inventario",

                fontSize = 16.sp,

                color = Color.DarkGray
            )
        }

        //////////////////////////////////////////////////////
        // 🔘 BOTONES
        //////////////////////////////////////////////////////
        Column(

            modifier = Modifier.fillMaxWidth()
        ) {

            //////////////////////////////////////////////////
            // 🔐 LOGIN
            //////////////////////////////////////////////////
            Button(

                onClick = onLoginClick,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor = Color(0xFF6D4C41)
                )
            ) {

                Text(

                    text = "Iniciar sesión",

                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            //////////////////////////////////////////////////
            // 📝 REGISTER
            //////////////////////////////////////////////////
            OutlinedButton(

                onClick = onRegisterClick,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text(

                    text = "Crear cuenta",

                    color = Color(0xFF4E342E)
                )
            }
        }

        //////////////////////////////////////////////////////
        // 📌 FOOTER
        //////////////////////////////////////////////////////
        Text(

            text = "© 2026 Panadería Martínez · Gestión Artesanal",

            fontSize = 12.sp,

            color = Color.Gray
        )
    }
}

