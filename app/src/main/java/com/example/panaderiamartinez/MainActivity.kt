package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import android.content.Context
import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent

import androidx.compose.runtime.*

import androidx.navigation.NavHostController
import androidx.navigation.compose.*

import com.example.panaderiamartinez.ui.theme.Panadería_MartinezTheme

import com.google.firebase.auth.FirebaseAuth

//////////////////////////////////////////////////////////////
// 🍞 MAIN ACTIVITY PRO
//////////////////////////////////////////////////////////////

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            ////////////////////////////////////////////////////
            // 🎨 THEME
            ////////////////////////////////////////////////////
            Panadería_MartinezTheme {

                ////////////////////////////////////////////////////
                // 🧭 NAV CONTROLLER
                ////////////////////////////////////////////////////
                val navController =
                    rememberNavController()

                ////////////////////////////////////////////////////
                // 🔥 FIREBASE
                ////////////////////////////////////////////////////
                val auth =
                    FirebaseAuth.getInstance()

                ////////////////////////////////////////////////////
                // 👤 CURRENT USER
                ////////////////////////////////////////////////////
                val currentUser =
                    auth.currentUser

                ////////////////////////////////////////////////////
                // 💾 SESSION
                ////////////////////////////////////////////////////
                val sharedPreferences =
                    getSharedPreferences(
                        "panaderia_session",
                        Context.MODE_PRIVATE
                    )

                ////////////////////////////////////////////////////
                // 👤 DATOS USUARIO
                ////////////////////////////////////////////////////
                var nombreUsuario by remember {

                    mutableStateOf("")
                }

                var rolUsuario by remember {

                    mutableStateOf("")
                }

                ////////////////////////////////////////////////////
                // 🔄 RECUPERAR SESIÓN
                ////////////////////////////////////////////////////
                LaunchedEffect(Unit) {

                    nombreUsuario =
                        sharedPreferences.getString(
                            "nombre",
                            ""
                        ) ?: ""

                    rolUsuario =
                        sharedPreferences.getString(
                            "rol",
                            ""
                        ) ?: ""
                }

                ////////////////////////////////////////////////////
                // 🚀 START DESTINATION
                ////////////////////////////////////////////////////
                val startDestination =

                    if (currentUser != null) {

                        "dashboard"

                    } else {

                        "welcome"
                    }

                ////////////////////////////////////////////////////
                // ⏱ DOBLE BACK
                ////////////////////////////////////////////////////
                var backPressedTime by remember {

                    mutableStateOf(0L)
                }

                ////////////////////////////////////////////////////
                // 🔙 BACK HANDLER GLOBAL
                ////////////////////////////////////////////////////
                BackHandler {

                    val currentRoute =
                        navController.currentBackStackEntry
                            ?.destination
                            ?.route

                    //////////////////////////////////////////////////
                    // 🏠 DASHBOARD
                    //////////////////////////////////////////////////
                    if (
                        currentRoute == "dashboard"
                    ) {

                        val currentTime =
                            System.currentTimeMillis()

                        if (
                            currentTime - backPressedTime
                            < 2000
                        ) {

                            finish()

                        } else {

                            backPressedTime =
                                currentTime

                            Toast.makeText(

                                this@MainActivity,

                                "Presiona nuevamente para salir",

                                Toast.LENGTH_SHORT

                            ).show()
                        }

                    }

                    //////////////////////////////////////////////////
                    // 🌟 WELCOME
                    //////////////////////////////////////////////////
                    else if (
                        currentRoute == "welcome"
                    ) {

                        val currentTime =
                            System.currentTimeMillis()

                        if (
                            currentTime - backPressedTime
                            < 2000
                        ) {

                            finish()

                        } else {

                            backPressedTime =
                                currentTime

                            Toast.makeText(

                                this@MainActivity,

                                "Presiona nuevamente para salir",

                                Toast.LENGTH_SHORT

                            ).show()
                        }
                    }

                    //////////////////////////////////////////////////
                    // 🔐 LOGIN
                    //////////////////////////////////////////////////
                    else if (
                        currentRoute == "login"
                    ) {

                        navController.navigate(
                            "welcome"
                        ) {

                            popUpTo("welcome") {

                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }

                    //////////////////////////////////////////////////
                    // 📝 REGISTER
                    //////////////////////////////////////////////////
                    else if (
                        currentRoute == "register"
                    ) {

                        navController.navigate(
                            "welcome"
                        ) {

                            popUpTo("welcome") {

                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }

                    //////////////////////////////////////////////////
                    // 📱 RESTO
                    //////////////////////////////////////////////////
                    else {

                        navController.navigate(
                            "dashboard"
                        ) {

                            popUpTo("dashboard") {

                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }
                }

                ////////////////////////////////////////////////////
                // 🚀 NAV HOST
                ////////////////////////////////////////////////////
                NavHost(

                    navController = navController,

                    startDestination = startDestination
                ) {

                    //////////////////////////////////////////////////
                    // 🌟 WELCOME
                    //////////////////////////////////////////////////
                    composable("welcome") {

                        WelcomeScreen(

                            onLoginClick = {

                                navController.navigate(
                                    "login"
                                ) {

                                    launchSingleTop = true
                                }
                            },

                            onRegisterClick = {

                                navController.navigate(
                                    "register"
                                ) {

                                    ////////////////////////////////////////////////////
                                    // 🧹 LIMPIAR HISTORIAL AUTH
                                    ////////////////////////////////////////////////////
                                    popUpTo("welcome") {

                                        inclusive = false
                                    }

                                    ////////////////////////////////////////////////////
                                    // 🔥 EVITAR DUPLICADOS
                                    ////////////////////////////////////////////////////
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 🔐 LOGIN
                    //////////////////////////////////////////////////
                    composable("login") {

                        LoginScreen(

                            onLoginSuccess = { nombre, rol ->

                                //////////////////////////////////////////////////
                                // 💾 SESIÓN
                                //////////////////////////////////////////////////
                                nombreUsuario = nombre
                                rolUsuario = rol

                                sharedPreferences
                                    .edit()
                                    .putString(
                                        "nombre",
                                        nombre
                                    )
                                    .putString(
                                        "rol",
                                        rol
                                    )
                                    .apply()

                                //////////////////////////////////////////////////
                                // 🚀 DASHBOARD
                                //////////////////////////////////////////////////
                                navController.navigate(
                                    "dashboard"
                                ) {

                                    popUpTo("welcome") {

                                        inclusive = true
                                    }
                                }
                            },

                            onBackClick = {

                                navController.navigate(
                                    "welcome"
                                )
                            },

                            onRegisterClick = {

                                navController.navigate(
                                    "register"
                                ) {

                                    ////////////////////////////////////////////////////
                                    // 🧹 LIMPIAR HISTORIAL AUTH
                                    ////////////////////////////////////////////////////
                                    popUpTo("welcome") {

                                        inclusive = false
                                    }

                                    ////////////////////////////////////////////////////
                                    // 🔥 EVITAR DUPLICADOS
                                    ////////////////////////////////////////////////////
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 📝 REGISTER
                    //////////////////////////////////////////////////
                    composable("register") {

                        RegisterScreen(

                            onBackClick = {

                                navController.navigate(
                                    "welcome"
                                )
                            },

                            onCreateAccountClick = {

                                navController.navigate(
                                    "login"
                                ) {

                                    ////////////////////////////////////////////////////
                                    // 🧹 LIMPIAR HISTORIAL AUTH
                                    ////////////////////////////////////////////////////
                                    popUpTo("welcome") {

                                        inclusive = false
                                    }

                                    ////////////////////////////////////////////////////
                                    // 🔥 EVITAR DUPLICADOS
                                    ////////////////////////////////////////////////////
                                    launchSingleTop = true
                                }
                            },

                            onLoginClick = {

                                navController.navigate(
                                    "login"
                                ) {

                                    ////////////////////////////////////////////////////
                                    // 🧹 LIMPIAR HISTORIAL AUTH
                                    ////////////////////////////////////////////////////
                                    popUpTo("welcome") {

                                        inclusive = false
                                    }

                                    ////////////////////////////////////////////////////
                                    // 🔥 EVITAR DUPLICADOS
                                    ////////////////////////////////////////////////////
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 🏠 DASHBOARD
                    //////////////////////////////////////////////////
                    composable("dashboard") {

                        DashboardScreen(

                            nombre = nombreUsuario,

                            rol = rolUsuario,

                            //////////////////////////////////////////////////
                            // 🚪 LOGOUT
                            //////////////////////////////////////////////////
                            onLogoutClick = {

                                //////////////////////////////////////////////////
                                // 🔥 LOGOUT FIREBASE
                                //////////////////////////////////////////////////
                                auth.signOut()

                                //////////////////////////////////////////////////
                                // 🧹 LIMPIAR SESIÓN
                                //////////////////////////////////////////////////
                                sharedPreferences
                                    .edit()
                                    .clear()
                                    .apply()

                                //////////////////////////////////////////////////
                                // 🔄 RESET
                                //////////////////////////////////////////////////
                                nombreUsuario = ""
                                rolUsuario = ""

                                //////////////////////////////////////////////////
                                // 🚀 WELCOME
                                //////////////////////////////////////////////////
                                navController.navigate(
                                    "welcome"
                                ) {

                                    popUpTo(0)

                                    launchSingleTop = true
                                }
                            },

                            //////////////////////////////////////////////////
                            // 📦 INSUMOS
                            //////////////////////////////////////////////////
                            onInsumosClick = {

                                navigateTo(
                                    navController,
                                    "insumos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 🍞 PRODUCTOS
                            //////////////////////////////////////////////////
                            onProductosClick = {

                                navigateTo(
                                    navController,
                                    "productos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 🧾 PEDIDOS
                            //////////////////////////////////////////////////
                            onPedidosClick = {

                                navigateTo(
                                    navController,
                                    "pedidos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 👀 VER PEDIDOS
                            //////////////////////////////////////////////////
                            onVerPedidosClick = {

                                navigateTo(
                                    navController,
                                    "verPedidos"
                                )
                            },

                            //////////////////////////////////////////////////
// 👥 USERS
//////////////////////////////////////////////////
                            onUsersClick = {

                                navigateTo(
                                    navController,
                                    "usuarios"
                                )
                            },

//////////////////////////////////////////////////
// ❌ OBSOLETO
//////////////////////////////////////////////////
                            onMovimientosClick = {}
                        )
                    }

                    //////////////////////////////////////////////////
                    // 📦 INSUMOS
                    //////////////////////////////////////////////////
                    composable("insumos") {

                        InsumosScreen(

                            rol = rolUsuario,

                            onInicio = {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            },

                            onProductos = {

                                navigateTo(
                                    navController,
                                    "productos"
                                )
                            },

                            onPedidos = {

                                navigateTo(
                                    navController,
                                    "pedidos"
                                )
                            },

                            onVerPedidos = {

                                navigateTo(
                                    navController,
                                    "verPedidos"
                                )
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 🍞 PRODUCTOS
                    //////////////////////////////////////////////////
                    composable("productos") {

                        ProductosScreen(

                            rol = rolUsuario,

                            onInicio = {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            },

                            onInsumos = {

                                navigateTo(
                                    navController,
                                    "insumos"
                                )
                            },

                            onPedidos = {

                                navigateTo(
                                    navController,
                                    "pedidos"
                                )
                            },

                            onVerPedidos = {

                                navigateTo(
                                    navController,
                                    "verPedidos"
                                )
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 🧾 PEDIDOS
                    //////////////////////////////////////////////////
                    composable("pedidos") {

                        PedidosScreen(

                            rol = rolUsuario,

                            onInicio = {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            },

                            onInsumos = {

                                navigateTo(
                                    navController,
                                    "insumos"
                                )
                            },

                            onProductos = {

                                navigateTo(
                                    navController,
                                    "productos"
                                )
                            },

                            onVerPedidos = {

                                navigateTo(
                                    navController,
                                    "verPedidos"
                                )
                            }
                        )
                    }

                    //////////////////////////////////////////////////
                    // 👀 VER PEDIDOS
                    //////////////////////////////////////////////////
                    composable("verPedidos") {

                        VerPedidosScreen(

                            rol = rolUsuario,

                            onInicio = {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            },

                            onInsumos = {

                                navigateTo(
                                    navController,
                                    "insumos"
                                )
                            },

                            onProductos = {

                                navigateTo(
                                    navController,
                                    "productos"
                                )
                            },

                            onPedidos = {

                                navigateTo(
                                    navController,
                                    "pedidos"
                                )
                            }
                        )
                    }

//////////////////////////////////////////////////
// 👥 USERS
//////////////////////////////////////////////////
                    composable("usuarios") {

                        //////////////////////////////////////////////////
                        // 🔐 SOLO ADMIN
                        //////////////////////////////////////////////////
                        if (rolUsuario != "Administrador") {

                            LaunchedEffect(Unit) {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            }

                            return@composable
                        }

                        UsersScreen(

                            //////////////////////////////////////////////////
                            // 👤 USUARIO ACTUAL
                            //////////////////////////////////////////////////
                            currentUserName = nombreUsuario,

                            //////////////////////////////////////////////////
                            // 🏠 DASHBOARD
                            //////////////////////////////////////////////////
                            onInicio = {

                                navigateTo(
                                    navController,
                                    "dashboard"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 📦 INSUMOS
                            //////////////////////////////////////////////////
                            onInsumos = {

                                navigateTo(
                                    navController,
                                    "insumos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 🍞 PRODUCTOS
                            //////////////////////////////////////////////////
                            onProductos = {

                                navigateTo(
                                    navController,
                                    "productos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 🧾 PEDIDOS
                            //////////////////////////////////////////////////
                            onPedidos = {

                                navigateTo(
                                    navController,
                                    "pedidos"
                                )
                            },

                            //////////////////////////////////////////////////
                            // 👀 VER PEDIDOS
                            //////////////////////////////////////////////////
                            onVerPedidos = {

                                navigateTo(
                                    navController,
                                    "verPedidos"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////////////////
// 🚀 NAVEGACIÓN LIMPIA
//////////////////////////////////////////////////////////////

fun navigateTo(

    navController: NavHostController,

    route: String
) {

    navController.navigate(route) {

        //////////////////////////////////////////////////////
        // 🧹 LIMPIAR HISTORIAL INTERMEDIO
        //////////////////////////////////////////////////////
        popUpTo("dashboard") {

            inclusive = false
        }

        //////////////////////////////////////////////////////
        // 🔥 EVITAR DUPLICADOS
        //////////////////////////////////////////////////////
        launchSingleTop = true

        //////////////////////////////////////////////////////
        // ♻️ RESTAURAR ESTADO
        //////////////////////////////////////////////////////
        restoreState = true
    }
}