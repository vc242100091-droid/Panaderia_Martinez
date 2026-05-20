package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.List

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

//////////////////////////////////////////////////////////////
// 🍞 BOTTOM BAR FINAL
//////////////////////////////////////////////////////////////

@Composable
fun BottomBarPro(

    //////////////////////////////////////////////////////////
    // 📍 PANTALLA ACTIVA
    //////////////////////////////////////////////////////////
    selected: String,

    //////////////////////////////////////////////////////////
    // 🧭 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onInicio: () -> Unit,

    onInsumos: () -> Unit,

    onProductos: () -> Unit,

    onPedidos: () -> Unit,

    onVerPedidos: () -> Unit,

    //////////////////////////////////////////////////////////
    // 🔐 ROL
    //////////////////////////////////////////////////////////
    rol: String
) {

    NavigationBar(

        //////////////////////////////////////////////////////
        // 🎨 COLOR FONDO
        //////////////////////////////////////////////////////
        containerColor = Color(0xFFF1ECE8)
    ) {

        //////////////////////////////////////////////////////
        // 🏠 INICIO
        //////////////////////////////////////////////////////
        NavigationBarItem(

            selected = selected == "inicio",

            onClick = onInicio,

            icon = {

                Icon(
                    Icons.Default.Home,
                    contentDescription = null
                )
            },

            label = {

                Text(

                    text = "Inicio",

                    fontSize = 9.sp,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis
                )
            },

            alwaysShowLabel = true,

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color.White,

                selectedTextColor = Color(0xFF4E342E),

                indicatorColor = Color(0xFF6D5D1E),

                unselectedIconColor = Color(0xFFBDB39F),

                unselectedTextColor = Color(0xFFBDB39F)
            )
        )

        //////////////////////////////////////////////////////
        // 📦 INSUMOS
        //////////////////////////////////////////////////////
        NavigationBarItem(

            selected = selected == "insumos",

            onClick = onInsumos,

            icon = {

                Icon(
                    Icons.Default.Menu,
                    contentDescription = null
                )
            },

            label = {

                Text(

                    text = "Insumos",

                    fontSize = 9.sp,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis
                )
            },

            alwaysShowLabel = true,

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color.White,

                selectedTextColor = Color(0xFF4E342E),

                indicatorColor = Color(0xFF6D5D1E),

                unselectedIconColor = Color(0xFFBDB39F),

                unselectedTextColor = Color(0xFFBDB39F)
            )
        )

        //////////////////////////////////////////////////////
        // 🍞 PRODUCTOS
        //////////////////////////////////////////////////////
        NavigationBarItem(

            selected = selected == "productos",

            onClick = onProductos,

            icon = {

                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null
                )
            },

            label = {

                Text(

                    text = "Productos",

                    fontSize = 8.sp,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis
                )
            },

            alwaysShowLabel = true,

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color.White,

                selectedTextColor = Color(0xFF4E342E),

                indicatorColor = Color(0xFF6D5D1E),

                unselectedIconColor = Color(0xFFBDB39F),

                unselectedTextColor = Color(0xFFBDB39F)
            )
        )

        //////////////////////////////////////////////////////
        // 🧾 PEDIDOS
        // 🔥 SOLO ADMIN
        //////////////////////////////////////////////////////
        if (rol == "Administrador") {

            NavigationBarItem(

                selected = selected == "pedidos",

                onClick = onPedidos,

                icon = {

                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                },

                label = {

                    Text(

                        text = "Pedidos",

                        fontSize = 9.sp,

                        maxLines = 1,

                        overflow = TextOverflow.Ellipsis
                    )
                },

                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = Color.White,

                    selectedTextColor = Color(0xFF4E342E),

                    indicatorColor = Color(0xFF6D5D1E),

                    unselectedIconColor = Color(0xFFBDB39F),

                    unselectedTextColor = Color(0xFFBDB39F)
                )
            )
        }

        //////////////////////////////////////////////////////
        // 👀 VER PEDIDOS
        //////////////////////////////////////////////////////
        NavigationBarItem(

            selected = selected == "verPedidos",

            onClick = onVerPedidos,

            icon = {

                Icon(
                    Icons.Default.List,
                    contentDescription = null
                )
            },

            label = {

                Text(

                    text = "Ver pedidos",

                    fontSize = 8.sp,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis
                )
            },

            alwaysShowLabel = true,

            colors = NavigationBarItemDefaults.colors(

                selectedIconColor = Color.White,

                selectedTextColor = Color(0xFF4E342E),

                indicatorColor = Color(0xFF6D5D1E),

                unselectedIconColor = Color(0xFFBDB39F),

                unselectedTextColor = Color(0xFFBDB39F)
            )
        )
    }
}

