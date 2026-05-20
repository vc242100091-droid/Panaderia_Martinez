package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

// ✅ ICONOS
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//////////////////////////////////////////////////////////////
// 🍞 DASHBOARD SCREEN PRO
//////////////////////////////////////////////////////////////

@Composable
fun DashboardScreen(

    //////////////////////////////////////////////////////////
    // 👤 DATOS USUARIO
    //////////////////////////////////////////////////////////
    nombre: String,

    rol: String,

    //////////////////////////////////////////////////////////
    // 🚪 LOGOUT
    //////////////////////////////////////////////////////////
    onLogoutClick: () -> Unit,

    //////////////////////////////////////////////////////////
    // 📦 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onInsumosClick: () -> Unit,

    onProductosClick: () -> Unit,

    onPedidosClick: () -> Unit,

    onVerPedidosClick: () -> Unit,

    //////////////////////////////////////////////////////////
    // 👥 USERS SCREEN
    //////////////////////////////////////////////////////////
    onUsersClick: () -> Unit = {},

    //////////////////////////////////////////////////////////
    // ❌ YA NO SE USA
    //////////////////////////////////////////////////////////
    onMovimientosClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🚪 DIALOG CERRAR SESIÓN
    //////////////////////////////////////////////////////////
    var showLogoutDialog by remember {

        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🍞 SCAFFOLD
    //////////////////////////////////////////////////////////
    Scaffold(

        //////////////////////////////////////////////////////
        // 🧭 BOTTOM BAR
        //////////////////////////////////////////////////////
        bottomBar = {

            BottomBarPro(

                selected = "inicio",

                onInicio = {},

                onInsumos = onInsumosClick,

                onProductos = onProductosClick,

                onPedidos = onPedidosClick,

                onVerPedidos = onVerPedidosClick,

                rol = rol
            )
        }

    ) { padding ->

        //////////////////////////////////////////////////////
        // 📦 CONTENIDO
        //////////////////////////////////////////////////////
        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5E0C3))
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
        ) {

            //////////////////////////////////////////////////
            // 🍞 HEADER
            //////////////////////////////////////////////////
            Text(

                text = "🍞 Panadería Martínez",

                fontSize = 26.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                text = "Bienvenido, $nombre ($rol)",

                fontSize = 16.sp,

                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(26.dp))

            //////////////////////////////////////////////////
            // 📦 INSUMOS
            //////////////////////////////////////////////////
            DashboardCard(

                titulo = "Insumos",

                descripcion =
                    "Control de inventario e insumos",

                icono = Icons.Default.Menu,

                color = Color(0xFF6D4C41),

                onClick = onInsumosClick
            )

            //////////////////////////////////////////////////
            // 🍞 PRODUCTOS
            //////////////////////////////////////////////////
            DashboardCard(

                titulo = "Productos",

                descripcion =
                    "Producción diaria de panes",

                icono = Icons.Default.ShoppingCart,

                color = Color(0xFF8D6E63),

                onClick = onProductosClick
            )

            //////////////////////////////////////////////////
            // 🧾 PEDIDOS
            // 🔥 SOLO ADMIN
            //////////////////////////////////////////////////
            if (rol == "Administrador") {

                DashboardCard(

                    titulo = "Pedidos",

                    descripcion =
                        "Registrar nuevos pedidos",

                    icono = Icons.Default.ShoppingCart,

                    color = Color(0xFFA1887F),

                    onClick = onPedidosClick
                )
            }

            //////////////////////////////////////////////////
            // 👀 VER PEDIDOS
            //////////////////////////////////////////////////
            DashboardCard(

                titulo = "Ver pedidos",

                descripcion =
                    "Pedidos pendientes y entregados",

                icono = Icons.Default.List,

                color = Color(0xFF5D4037),

                onClick = onVerPedidosClick
            )

            //////////////////////////////////////////////////
            // 👥 USUARIOS
            // 🔥 SOLO ADMIN
            //////////////////////////////////////////////////
            if (rol == "Administrador") {

                DashboardCard(

                    titulo = "Usuarios",

                    descripcion =
                        "Gestión de empleados y roles",

                    icono = Icons.Default.Person,

                    color = Color(0xFF795548),

                    onClick = onUsersClick
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            //////////////////////////////////////////////////
            // 🚪 BOTÓN CERRAR SESIÓN PRO
            //////////////////////////////////////////////////
            OutlinedCard(

                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        showLogoutDialog = true
                    },

                shape = RoundedCornerShape(22.dp),

                colors = CardDefaults.outlinedCardColors(

                    containerColor = Color.White
                )
            ) {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    //////////////////////////////////////////////////
                    // 🚪 ICONO
                    //////////////////////////////////////////////////
                    Surface(

                        color = Color.Red.copy(alpha = 0.12f),

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ExitToApp,

                            contentDescription = null,

                            tint = Color.Red,

                            modifier = Modifier
                                .padding(14.dp)
                                .size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    //////////////////////////////////////////////////
                    // 📝 TEXTO
                    //////////////////////////////////////////////////
                    Column {

                        Text(

                            text = "Cerrar sesión",

                            fontWeight = FontWeight.Bold,

                            fontSize = 20.sp,

                            color = Color(0xFF4E342E)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(2.dp)
                        )

                        Text(

                            text =
                                "Salir de la cuenta actual",

                            color = Color.Gray,

                            fontSize = 13.sp
                        )
                    }
                }
            }

            //////////////////////////////////////////////////
            // 🔥 ESPACIO EXTRA
            //////////////////////////////////////////////////
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    //////////////////////////////////////////////////////////
    // 🚪 DIALOG CONFIRMAR LOGOUT
    //////////////////////////////////////////////////////////
    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {

                showLogoutDialog = false
            },

            //////////////////////////////////////////////////////
            // 📝 TÍTULO
            //////////////////////////////////////////////////////
            title = {

                Text("Cerrar sesión")
            },

            //////////////////////////////////////////////////////
            // 📝 MENSAJE
            //////////////////////////////////////////////////////
            text = {

                Text(
                    "¿Deseas cerrar la sesión actual?"
                )
            },

            //////////////////////////////////////////////////////
            // ✅ CONFIRMAR
            //////////////////////////////////////////////////////
            confirmButton = {

                TextButton(

                    onClick = {

                        showLogoutDialog = false

                        onLogoutClick()
                    }
                ) {

                    Text(

                        text = "Sí",

                        color = Color.Red
                    )
                }
            },

            //////////////////////////////////////////////////////
            // ❌ CANCELAR
            //////////////////////////////////////////////////////
            dismissButton = {

                TextButton(

                    onClick = {

                        showLogoutDialog = false
                    }
                ) {

                    Text("No")
                }
            }
        )
    }
}

//////////////////////////////////////////////////////////////
// 🍞 CARD DASHBOARD PRO
//////////////////////////////////////////////////////////////

@Composable
fun DashboardCard(

    titulo: String,

    descripcion: String,

    icono: androidx.compose.ui.graphics.vector.ImageVector,

    color: Color,

    onClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🎨 CARD
    //////////////////////////////////////////////////////////
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {

                onClick()
            },

        shape = RoundedCornerShape(26.dp),

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
        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            //////////////////////////////////////////////////
            // 🎨 ICONO
            //////////////////////////////////////////////////
            Surface(

                color = color.copy(alpha = 0.15f),

                shape = RoundedCornerShape(18.dp)
            ) {

                Icon(

                    imageVector = icono,

                    contentDescription = null,

                    tint = color,

                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            //////////////////////////////////////////////////
            // 📝 TEXTOS
            //////////////////////////////////////////////////
            Column {

                Text(

                    text = titulo,

                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = descripcion,

                    color = Color.Gray,

                    fontSize = 14.sp
                )
            }
        }
    }
}