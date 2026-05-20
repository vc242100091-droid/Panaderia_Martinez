package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch

import com.google.firebase.firestore.FirebaseFirestore

//////////////////////////////////////////////////////////////
// 👤 MODELO USUARIO
//////////////////////////////////////////////////////////////

data class UsuarioModel(

    val id: String = "",

    val nombre: String = "",

    val correo: String = "",

    val rol: String = "Empleado"
)

//////////////////////////////////////////////////////////////
// 👥 USERS SCREEN PRO FINAL
//////////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(

    //////////////////////////////////////////////////////////
    // 🔐 USUARIO ACTUAL
    //////////////////////////////////////////////////////////
    currentUserName: String,

    //////////////////////////////////////////////////////////
    // 🔙 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onInicio: () -> Unit,
    onInsumos: () -> Unit,
    onProductos: () -> Unit,
    onPedidos: () -> Unit,
    onVerPedidos: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🔥 FIRESTORE
    //////////////////////////////////////////////////////////
    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 📦 ESTADOS
    //////////////////////////////////////////////////////////

    var usuarios by remember {

        mutableStateOf<List<UsuarioModel>>(emptyList())
    }

    //////////////////////////////////////////////////////////
    // 🔍 BUSCADOR
    //////////////////////////////////////////////////////////
    var search by remember {

        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 🗑 ELIMINAR
    //////////////////////////////////////////////////////////
    var usuarioEliminar by remember {

        mutableStateOf<UsuarioModel?>(null)
    }

    //////////////////////////////////////////////////////////
    // ✏️ EDITAR
    //////////////////////////////////////////////////////////
    var usuarioEditar by remember {

        mutableStateOf<UsuarioModel?>(null)
    }

    //////////////////////////////////////////////////////////
    // 📝 NUEVO NOMBRE
    //////////////////////////////////////////////////////////
    var nuevoNombre by remember {

        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 🔄 CAMBIAR ROL
    //////////////////////////////////////////////////////////
    var usuarioCambiarRol by remember {

        mutableStateOf<UsuarioModel?>(null)
    }

    //////////////////////////////////////////////////////////
    // 🛡 NUEVO ROL
    //////////////////////////////////////////////////////////
    var nuevoRolSeleccionado by remember {

        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 🍞 SNACKBAR
    //////////////////////////////////////////////////////////
    val snackbarHostState = remember {

        SnackbarHostState()
    }

    //////////////////////////////////////////////////////////
    // 🚀 COROUTINE
    //////////////////////////////////////////////////////////
    val scope = rememberCoroutineScope()

    //////////////////////////////////////////////////////////
    // 🔥 OBTENER USUARIOS
    //////////////////////////////////////////////////////////
    LaunchedEffect(Unit) {

        db.collection("empleados")
            .addSnapshotListener { value, _ ->

                if (value != null) {

                    usuarios = value.documents.map {

                        UsuarioModel(

                            id = it.id,

                            nombre = it.getString("nombre") ?: "",

                            correo = it.getString("usuario") ?: "",

                            rol = it.getString("rol") ?: "Empleado"
                        )
                    }
                }
            }
    }

    //////////////////////////////////////////////////////////
    // 🔍 FILTRAR
    //////////////////////////////////////////////////////////
    val usuariosFiltrados = usuarios.filter {

        it.nombre.contains(search, true)
                ||
                it.correo.contains(search, true)
    }

    //////////////////////////////////////////////////////////
    // 📊 CONTADORES
    //////////////////////////////////////////////////////////
    val totalAdmins = usuarios.count {

        it.rol == "Administrador"
    }

    val totalEmpleados = usuarios.count {

        it.rol == "Empleado"
    }

    //////////////////////////////////////////////////////////
    // 🖼 UI
    //////////////////////////////////////////////////////////
    Scaffold(

        snackbarHost = {

            SnackbarHost(snackbarHostState)
        },

        bottomBar = {

            BottomBarPro(

                selected = "usuarios",

                onInicio = onInicio,

                onInsumos = onInsumos,

                onProductos = onProductos,

                onPedidos = onPedidos,

                onVerPedidos = onVerPedidos,

                rol = "Administrador"
            )
        },

        containerColor = Color(0xFFF5E0C3)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            //////////////////////////////////////////////////
            // 👥 HEADER
            //////////////////////////////////////////////////
            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Person,

                    contentDescription = null,

                    tint = Color(0xFF8D6E63),

                    modifier = Modifier
                        .size(34.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Column {

                    Text(

                        text = "Usuarios",

                        fontSize = 32.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Text(

                        text =
                            "Gestión de empleados y roles",

                        color = Color.Gray,

                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            //////////////////////////////////////////////////
            // 📊 STATS
            //////////////////////////////////////////////////
            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                StatsCard(
                    title = "Usuarios",
                    value = usuarios.size.toString()
                )

                StatsCard(
                    title = "Admins",
                    value = totalAdmins.toString()
                )

                StatsCard(
                    title = "Empleados",
                    value = totalEmpleados.toString()
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

//////////////////////////////////////////////////
// 🔍 BUSCADOR
//////////////////////////////////////////////////
            OutlinedTextField(

                value = search,

                onValueChange = {

                    search = it
                },

                modifier = Modifier.fillMaxWidth(),

                leadingIcon = {

                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                },

                placeholder = {

                    Text("Buscar usuario...")
                },

                shape = RoundedCornerShape(16.dp),

                singleLine = true,

                //////////////////////////////////////////////////
                // 🎨 COLORES PERSONALIZADOS
                //////////////////////////////////////////////////
                colors = OutlinedTextFieldDefaults.colors(

                    focusedBorderColor = Color(0xFFD4C15F),

                    unfocusedBorderColor = Color(0xFFD4C15F),

                    focusedTextColor = Color(0xFF4E342E),

                    unfocusedTextColor = Color(0xFF4E342E),

                    cursorColor = Color(0xFF4E342E),

                    focusedLeadingIconColor = Color.Gray,

                    unfocusedLeadingIconColor = Color.Gray
                )
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            //////////////////////////////////////////////////
            // 👥 LISTA
            //////////////////////////////////////////////////
            LazyColumn(

                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                items(usuariosFiltrados) { usuario ->

                    UsuarioCardPro(

                        usuario = usuario,

                        currentUserName =
                            currentUserName,

                        //////////////////////////////////////////////////
                        // ✏️ EDITAR
                        //////////////////////////////////////////////////
                        onEditar = {

                            usuarioEditar = usuario

                            nuevoNombre =
                                usuario.nombre
                        },

                        //////////////////////////////////////////////////
                        // 🔄 CAMBIAR ROL
                        //////////////////////////////////////////////////
                        onCambiarRol = { nuevoRol ->

                            //////////////////////////////////////////////////
                            // 🚫 NO CAMBIARSE SU PROPIO ROL
                            //////////////////////////////////////////////////
                            if (
                                usuario.nombre ==
                                currentUserName
                            ) {

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "No puedes cambiar tu propio rol"
                                        )
                                }

                                return@UsuarioCardPro
                            }

                            //////////////////////////////////////////////////
                            // 🔒 PROTEGER ÚLTIMO ADMIN
                            //////////////////////////////////////////////////
                            if (
                                usuario.rol ==
                                "Administrador"
                                &&
                                nuevoRol ==
                                "Empleado"
                                &&
                                totalAdmins <= 1
                            ) {

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "Debe existir al menos un administrador"
                                        )
                                }

                                return@UsuarioCardPro
                            }

                            usuarioCambiarRol =
                                usuario

                            nuevoRolSeleccionado =
                                nuevoRol
                        },

                        //////////////////////////////////////////////////
                        // 🗑 ELIMINAR
                        //////////////////////////////////////////////////
                        onEliminar = {

                            //////////////////////////////////////////////////
                            // 🚫 NO ELIMINARSE
                            //////////////////////////////////////////////////
                            if (
                                usuario.nombre ==
                                currentUserName
                            ) {

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "No puedes eliminar tu propia cuenta"
                                        )
                                }

                                return@UsuarioCardPro
                            }

                            //////////////////////////////////////////////////
                            // 🔒 PROTEGER ÚLTIMO ADMIN
                            //////////////////////////////////////////////////
                            if (
                                usuario.rol ==
                                "Administrador"
                                &&
                                totalAdmins <= 1
                            ) {

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "No puedes eliminar el último administrador"
                                        )
                                }

                                return@UsuarioCardPro
                            }

                            usuarioEliminar = usuario
                        }
                    )
                }

                item {

                    Spacer(
                        modifier = Modifier.height(90.dp)
                    )
                }
            }
        }
    }

    //////////////////////////////////////////////////////////
    // ✏️ EDITAR NOMBRE
    //////////////////////////////////////////////////////////
    if (usuarioEditar != null) {

        AlertDialog(

            onDismissRequest = {

                usuarioEditar = null
            },

            title = {

                Text("Editar usuario")
            },

            text = {

                OutlinedTextField(

                    value = nuevoNombre,

                    onValueChange = {

                        nuevoNombre = it
                    },

                    label = {

                        Text("Nombre")
                    }
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        db.collection("empleados")
                            .document(usuarioEditar!!.id)
                            .update(
                                "nombre",
                                nuevoNombre
                            )

                        scope.launch {

                            snackbarHostState
                                .showSnackbar(
                                    "Se cambió el nombre exitosamente"
                                )
                        }

                        usuarioEditar = null
                    }
                ) {

                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        usuarioEditar = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }

    //////////////////////////////////////////////////////////
    // 🔄 CAMBIAR ROL
    //////////////////////////////////////////////////////////
    if (usuarioCambiarRol != null) {

        AlertDialog(

            onDismissRequest = {

                usuarioCambiarRol = null
            },

            title = {

                Text("Cambiar rol")
            },

            text = {

                Text(
                    "¿Estás seguro de cambiarle el rol a este usuario?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        db.collection("empleados")
                            .document(
                                usuarioCambiarRol!!.id
                            )
                            .update(
                                "rol",
                                nuevoRolSeleccionado
                            )

                        scope.launch {

                            snackbarHostState
                                .showSnackbar(
                                    "Se cambió el rol exitosamente"
                                )
                        }

                        usuarioCambiarRol = null
                    }
                ) {

                    Text("Aceptar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        usuarioCambiarRol = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }

    //////////////////////////////////////////////////////////
    // 🗑 ELIMINAR
    //////////////////////////////////////////////////////////
    if (usuarioEliminar != null) {

        AlertDialog(

            onDismissRequest = {

                usuarioEliminar = null
            },

            title = {

                Text("Eliminar usuario")
            },

            text = {

                Text(
                    "¿Deseas eliminar este usuario?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        db.collection("empleados")
                            .document(usuarioEliminar!!.id)
                            .delete()

                        scope.launch {

                            snackbarHostState
                                .showSnackbar(
                                    "Usuario eliminado exitosamente"
                                )
                        }

                        usuarioEliminar = null
                    }
                ) {

                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        usuarioEliminar = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}

//////////////////////////////////////////////////////////////
// 👤 CARD PREMIUM
//////////////////////////////////////////////////////////////

@Composable
fun UsuarioCardPro(

    usuario: UsuarioModel,

    currentUserName: String,

    onEditar: () -> Unit,

    onCambiarRol: (String) -> Unit,

    onEliminar: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFFF8F5F2)
        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 8.dp
        )
    ) {

        Column(

            modifier = Modifier.padding(18.dp)
        ) {

            //////////////////////////////////////////////////
            // 👤 HEADER
            //////////////////////////////////////////////////
            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                //////////////////////////////////////////////////
                // 🔥 AVATAR
                //////////////////////////////////////////////////
                Box(

                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8D6E63)),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(

                        text = usuario.nombre
                            .take(1)
                            .uppercase(),

                        color = Color.White,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize = 22.sp
                    )
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column(

                    modifier = Modifier.weight(1f)
                ) {

                    Text(

                        text = usuario.nombre,

                        fontSize = 22.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Text(

                        text = usuario.correo,

                        color = Color.Gray,

                        fontSize = 13.sp
                    )
                }

                //////////////////////////////////////////////////
                // ✏️ EDITAR
                //////////////////////////////////////////////////
                IconButton(

                    onClick = onEditar
                ) {

                    Icon(

                        Icons.Default.Edit,

                        contentDescription = null,

                        tint = Color(0xFF8D6E63)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            //////////////////////////////////////////////////
            // 🛡 BADGE
            //////////////////////////////////////////////////
            Surface(

                color =
                    if (
                        usuario.rol ==
                        "Administrador"
                    )
                        Color(0xFF4CAF50)
                            .copy(alpha = 0.15f)
                    else
                        Color(0xFFFF9800)
                            .copy(alpha = 0.15f),

                shape = RoundedCornerShape(12.dp)
            ) {

                Text(

                    text = "Rol: ${usuario.rol}",

                    modifier = Modifier.padding(

                        horizontal = 12.dp,

                        vertical = 8.dp
                    ),

                    color =
                        if (
                            usuario.rol ==
                            "Administrador"
                        )
                            Color(0xFF2E7D32)
                        else
                            Color(0xFFEF6C00),

                    fontWeight =
                        FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            //////////////////////////////////////////////////
            // 🔘 BOTONES
            //////////////////////////////////////////////////
            Row(

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                OutlinedButton(

                    onClick = {

                        onCambiarRol(
                            "Administrador"
                        )
                    },

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Text("Admin")
                }

                OutlinedButton(

                    onClick = {

                        onCambiarRol(
                            "Empleado"
                        )
                    },

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Text("Empleado")
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                //////////////////////////////////////////////////
                // 🗑 ELIMINAR
                //////////////////////////////////////////////////
                IconButton(

                    onClick = onEliminar
                ) {

                    Icon(

                        Icons.Default.Delete,

                        contentDescription = null,

                        tint = Color.Red
                    )
                }
            }

            //////////////////////////////////////////////////
            // 🔥 SESIÓN ACTUAL
            //////////////////////////////////////////////////
            AnimatedVisibility(

                visible =
                    usuario.nombre ==
                            currentUserName
            ) {

                Text(

                    text = "Sesión actual",

                    color = Color(0xFF1976D2),

                    fontWeight =
                        FontWeight.Bold,

                    modifier = Modifier.padding(
                        top = 10.dp
                    )
                )
            }
        }
    }
}

//////////////////////////////////////////////////////////////
// 📊 CARD ESTADÍSTICAS
//////////////////////////////////////////////////////////////

@Composable
fun StatsCard(

    title: String,

    value: String
) {

    Card(

        modifier = Modifier.width(105.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color.White
        ),

        shape = RoundedCornerShape(20.dp)
    ) {

        Column(

            modifier = Modifier.padding(14.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(

                text = value,

                fontWeight = FontWeight.Bold,

                fontSize = 24.sp,

                color = Color(0xFF6D4C41)
            )

            Text(

                text = title,

                color = Color.Gray,

                fontSize = 12.sp
            )
        }
    }
}

