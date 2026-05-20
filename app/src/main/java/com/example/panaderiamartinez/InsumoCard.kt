package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.background

//////////////////////////////////////////////////////////////
// 🔥 DETECTAR GESTOS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

//////////////////////////////////////////////////////////////
// 🔥 POINTER INPUT
//////////////////////////////////////////////////////////////

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//////////////////////////////////////////////////////////////
// 🔥 COROUTINES
//////////////////////////////////////////////////////////////

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//////////////////////////////////////////////////////////////
// 🍞 CARD PROFESIONAL INSUMO
//////////////////////////////////////////////////////////////

@Composable
fun InsumoCardPro(

    //////////////////////////////////////////////////////////
    // 📦 INSUMO
    //////////////////////////////////////////////////////////
    insumo: InsumoModel,

    //////////////////////////////////////////////////////////
    // 🔐 ROL
    //////////////////////////////////////////////////////////
    rol: String,

    //////////////////////////////////////////////////////////
    // ✅ ACTUALIZAR STOCK
    //////////////////////////////////////////////////////////
    onActualizar: (Int) -> Unit,

    //////////////////////////////////////////////////////////
    // 🗑️ ELIMINAR
    //////////////////////////////////////////////////////////
    onEliminar: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🔢 CANTIDAD TEMPORAL
    //////////////////////////////////////////////////////////
    var cantidadTemporal by remember {

        mutableIntStateOf(insumo.cantidad)
    }

    //////////////////////////////////////////////////////////
    // 🎨 COLOR STOCK
    //////////////////////////////////////////////////////////
    val colorStock = when {

        cantidadTemporal > 30 ->
            Color(0xFF4CAF50)

        cantidadTemporal > 10 ->
            Color(0xFFFF9800)

        else ->
            Color(0xFFF44336)
    }

    //////////////////////////////////////////////////////////
    // ⚠️ TEXTO ESTADO
    //////////////////////////////////////////////////////////
    val estadoTexto = when {

        cantidadTemporal > 30 ->
            "Stock normal"

        cantidadTemporal > 10 ->
            "Stock bajo"

        else ->
            "Stock crítico"
    }

    //////////////////////////////////////////////////////////
    // 🪪 CARD
    //////////////////////////////////////////////////////////
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F3EF)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        //////////////////////////////////////////////////////
        // 📦 CONTENIDO
        //////////////////////////////////////////////////////
        Column(

            modifier = Modifier.padding(18.dp)
        ) {

            //////////////////////////////////////////////////
            // 📛 NOMBRE
            //////////////////////////////////////////////////
            Text(

                text = insumo.nombre,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            //////////////////////////////////////////////////
            // 🔢 CANTIDAD
            //////////////////////////////////////////////////
            Text(

                text =
                    "$cantidadTemporal ${insumo.unidad}",

                fontSize = 18.sp,

                color = Color.Black
            )

            Spacer(modifier = Modifier.height(14.dp))

            //////////////////////////////////////////////////
            // 📊 BARRA PROGRESO
            //////////////////////////////////////////////////
            LinearProgressIndicator(

                progress = {
                    (cantidadTemporal / 100f)
                        .coerceIn(0f, 1f)
                },

                color = colorStock,

                trackColor = Color.LightGray,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.height(12.dp))

            //////////////////////////////////////////////////
            // ⚠️ ESTADO STOCK
            //////////////////////////////////////////////////
            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                //////////////////////////////////////////////////
                // 🔴 CÍRCULO
                //////////////////////////////////////////////////
                Box(

                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            colorStock,
                            CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                //////////////////////////////////////////////////
                // 📝 TEXTO
                //////////////////////////////////////////////////
                Text(

                    text = estadoTexto,

                    color = colorStock,

                    fontWeight = FontWeight.Bold
                )
            }

            //////////////////////////////////////////////////
            // 🔥 CONTROLES ADMIN
            //////////////////////////////////////////////////
            if (rol == "Administrador") {

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                //////////////////////////////////////////////////
                // 🔥 FILA CONTROLES
                //////////////////////////////////////////////////
                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    //////////////////////////////////////////////////
                    // ➖ ➕
                    //////////////////////////////////////////////////
                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        //////////////////////////////////////////////////
                        // ➖ RESTAR
                        //////////////////////////////////////////////////
                        HoldButton(

                            text = "-",

                            color = Color.Gray,

                            onClick = {

                                if (
                                    cantidadTemporal > 0
                                ) {

                                    cantidadTemporal--
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.width(5.dp)
                        )

                        //////////////////////////////////////////////////
                        // 🔢 CANTIDAD
                        //////////////////////////////////////////////////
                        Text(

                            text =
                                cantidadTemporal.toString(),

                            fontSize = 20.sp,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        //////////////////////////////////////////////////
                        // ➕ AGREGAR
                        //////////////////////////////////////////////////
                        HoldButton(

                            text = "+",

                            color =
                                Color(0xFF795548),

                            onClick = {

                                cantidadTemporal++
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // ✏️ ACTUALIZAR
                    //////////////////////////////////////////////////
                    Button(

                        onClick = {

                            onActualizar(
                                cantidadTemporal
                            )
                        },

                        colors =
                            ButtonDefaults.buttonColors(

                                containerColor =
                                    Color(0xFF6D4C41)
                            )
                    ) {

                        Text("Actualizar")
                    }

                    //////////////////////////////////////////////////
                    // 🗑️ ELIMINAR
                    //////////////////////////////////////////////////
                    IconButton(

                        onClick = onEliminar
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Delete,

                            contentDescription =
                                "Eliminar",

                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////////////////
// 🔥 BOTÓN HOLD PROFESIONAL
//////////////////////////////////////////////////////////////

@Composable
fun HoldButton(

    //////////////////////////////////////////////////////////
    // 🔤 TEXTO
    //////////////////////////////////////////////////////////
    text: String,

    //////////////////////////////////////////////////////////
    // 🎨 COLOR
    //////////////////////////////////////////////////////////
    color: Color,

    //////////////////////////////////////////////////////////
    // ⚡ ACCIÓN
    //////////////////////////////////////////////////////////
    onClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🚀 COROUTINE
    //////////////////////////////////////////////////////////
    val scope = rememberCoroutineScope()

    //////////////////////////////////////////////////////////
    // 🔥 JOB
    //////////////////////////////////////////////////////////
    var job by remember {

        mutableStateOf<Job?>(null)
    }

    //////////////////////////////////////////////////////////
    // 🔘 BOTÓN
    //////////////////////////////////////////////////////////
    Surface(

        modifier = Modifier

            .size(52.dp)

            //////////////////////////////////////////////////
            // 👆 GESTOS
            //////////////////////////////////////////////////
            .pointerInput(Unit) {

                detectTapGestures(

                    //////////////////////////////////////////////////
                    // 👆 CLICK NORMAL
                    //////////////////////////////////////////////////
                    onTap = {

                        onClick()
                    },

                    //////////////////////////////////////////////////
                    // 🔥 MANTENER PRESIONADO
                    //////////////////////////////////////////////////
                    onPress = {

                        //////////////////////////////////////////////////
                        // 🚀 LOOP
                        //////////////////////////////////////////////////
                        job = scope.launch {

                            //////////////////////////////////////////////////
                            // ⏳ PEQUEÑO DELAY
                            //////////////////////////////////////////////////
                            delay(300)

                            //////////////////////////////////////////////////
                            // 🔁 REPETIR
                            //////////////////////////////////////////////////
                            while (true) {

                                onClick()

                                delay(60)
                            }
                        }

                        //////////////////////////////////////////////////
                        // ⏳ ESPERAR SOLTAR
                        //////////////////////////////////////////////////
                        tryAwaitRelease()

                        //////////////////////////////////////////////////
                        // 🛑 DETENER LOOP
                        //////////////////////////////////////////////////
                        job?.cancel()
                    }
                )
            },

        shape = CircleShape,

        color = color
    ) {

        //////////////////////////////////////////////////////
        // 🔤 TEXTO BOTÓN
        //////////////////////////////////////////////////////
        Box(

            contentAlignment = Alignment.Center
        ) {

            Text(

                text = text,

                color = Color.White,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold
            )
        }
    }
}
