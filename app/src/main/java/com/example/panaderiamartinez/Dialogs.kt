package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//////////////////////////////////////////////////////////////
// 🗑️ DIALOG ELIMINAR INSUMO
//////////////////////////////////////////////////////////////

@Composable
fun EliminarInsumoDialog(

    //////////////////////////////////////////////////////////
    // 🧾 MOSTRAR
    //////////////////////////////////////////////////////////
    visible: Boolean,

    //////////////////////////////////////////////////////////
    // ❌ CANCELAR
    //////////////////////////////////////////////////////////
    onDismiss: () -> Unit,

    //////////////////////////////////////////////////////////
    // ✅ CONFIRMAR
    //////////////////////////////////////////////////////////
    onConfirm: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 👀 SOLO SI ES VISIBLE
    //////////////////////////////////////////////////////////
    if (visible) {

        AlertDialog(

            //////////////////////////////////////////////////
            // ❌ CERRAR
            //////////////////////////////////////////////////
            onDismissRequest = onDismiss,

            //////////////////////////////////////////////////
            // 🎨 COLOR
            //////////////////////////////////////////////////
            containerColor = Color(0xFFF8F3EF),

            //////////////////////////////////////////////////
            // 🗑️ ICONO
            //////////////////////////////////////////////////
            icon = {

                Icon(
                    imageVector = Icons.Default.Delete,

                    contentDescription = null,

                    tint = Color.Red
                )
            },

            //////////////////////////////////////////////////
            // 📛 TÍTULO
            //////////////////////////////////////////////////
            title = {

                Text(
                    text = "Eliminar insumo",

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )
            },

            //////////////////////////////////////////////////
            // 📝 MENSAJE
            //////////////////////////////////////////////////
            text = {

                Text(
                    text =
                        "¿Estás seguro de que quieres eliminar este producto del stock?"
                )
            },

            //////////////////////////////////////////////////
            // ✅ BOTÓN ACEPTAR
            //////////////////////////////////////////////////
            confirmButton = {

                TextButton(
                    onClick = onConfirm
                ) {

                    Text(
                        text = "Eliminar",

                        color = Color.Red,

                        fontWeight = FontWeight.Bold
                    )
                }
            },

            //////////////////////////////////////////////////
            // ❌ CANCELAR
            //////////////////////////////////////////////////
            dismissButton = {

                TextButton(
                    onClick = onDismiss
                ) {

                    Text(
                        text = "Cancelar",

                        color = Color.Gray
                    )
                }
            }
        )
    }
}

//////////////////////////////////////////////////////////////
// ✏️ DIALOG ACTUALIZAR
//////////////////////////////////////////////////////////////

@Composable
fun ActualizarDialog(

    //////////////////////////////////////////////////////////
    // 👀 VISIBLE
    //////////////////////////////////////////////////////////
    visible: Boolean,

    //////////////////////////////////////////////////////////
    // ❌ CERRAR
    //////////////////////////////////////////////////////////
    onDismiss: () -> Unit,

    //////////////////////////////////////////////////////////
    // ✅ ACTUALIZAR
    //////////////////////////////////////////////////////////
    onConfirm: () -> Unit
) {

    if (visible) {

        AlertDialog(

            onDismissRequest = onDismiss,

            containerColor = Color(0xFFF8F3EF),

            title = {

                Text(
                    text = "Actualizar stock",

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )
            },

            text = {

                Text(
                    text = "¿Deseas actualizar este producto?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = onConfirm
                ) {

                    Text(
                        text = "Actualizar",

                        color = Color(0xFF795548),

                        fontWeight = FontWeight.Bold
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = onDismiss
                ) {

                    Text(
                        text = "Cancelar",

                        color = Color.Gray
                    )
                }
            }
        )
    }
}

//////////////////////////////////////////////////////////////
// ✅ SNACKBAR MODERNO
//////////////////////////////////////////////////////////////

@Composable
fun SuccessSnackbar(

    //////////////////////////////////////////////////////////
    // 📝 MENSAJE
    //////////////////////////////////////////////////////////
    message: String
) {

    Snackbar(

        //////////////////////////////////////////////////////
        // 🎨 COLOR
        //////////////////////////////////////////////////////
        containerColor = Color(0xFF4CAF50),

        contentColor = Color.White,

        action = {

            //////////////////////////////////////////////////
            // ✔ ICONO
            //////////////////////////////////////////////////
            Icon(
                imageVector =
                    Icons.Default.CheckCircle,

                contentDescription = null,

                tint = Color.White
            )
        }
    ) {

        //////////////////////////////////////////////////////
        // 📝 TEXTO
        //////////////////////////////////////////////////////
        Text(

            text = message,

            fontSize = 15.sp,

            fontWeight = FontWeight.SemiBold
        )
    }
}

