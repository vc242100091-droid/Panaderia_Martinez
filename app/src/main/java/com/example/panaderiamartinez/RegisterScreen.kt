package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

// Compose
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

// Material
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons

// 🔥 ICONOS
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

// UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//////////////////////////////////////////////////////////////
// 🚀 REGISTER SCREEN PRO DEFINITIVO
//////////////////////////////////////////////////////////////
@Composable
fun RegisterScreen(

    //////////////////////////////////////////////////////////
    // 🔙 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onBackClick: () -> Unit,

    onCreateAccountClick: () -> Unit,

    onLoginClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🔥 ESTADOS
    //////////////////////////////////////////////////////////
    var nombre by remember { mutableStateOf("") }

    var usuario by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var confirmarPassword by remember { mutableStateOf("") }

    //////////////////////////////////////////////////////////
    // 👁️ MOSTRAR CONTRASEÑA
    //////////////////////////////////////////////////////////
    var passwordVisible by remember {

        mutableStateOf(false)
    }

    var confirmPasswordVisible by remember {

        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // ⏳ LOADING
    //////////////////////////////////////////////////////////
    var cargando by remember {

        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 💬 MENSAJES
    //////////////////////////////////////////////////////////
    var mensaje by remember {

        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 🔥 FIREBASE
    //////////////////////////////////////////////////////////
    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 🎨 CONTENEDOR PRINCIPAL
    //////////////////////////////////////////////////////////
    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E0C3))
    ) {

        //////////////////////////////////////////////////////
        // 🔥 CONTENIDO
        //////////////////////////////////////////////////////
        Column(

            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp)
        ) {

            //////////////////////////////////////////////////
            // 🔙 HEADER
            //////////////////////////////////////////////////
            Row(

                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(

                    text = "←",

                    fontSize = 24.sp,

                    modifier = Modifier
                        .clickable {

                            onBackClick()
                        }
                        .padding(end = 12.dp)
                )

                Text(

                    text = "Panadería Martínez",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            //////////////////////////////////////////////////
            // 🔥 CARD PRINCIPAL
            //////////////////////////////////////////////////
            Card(

                shape = RoundedCornerShape(28.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {

                    //////////////////////////////////////////////////
                    // 📝 TÍTULO
                    //////////////////////////////////////////////////
                    Text(

                        text = "Crear cuenta",

                        fontSize = 28.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(

                        text = "Únete al sistema artesanal",

                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    //////////////////////////////////////////////////
                    // 👤 NOMBRE
                    //////////////////////////////////////////////////
                    Text(

                        text = "NOMBRE",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = nombre,

                        onValueChange = {

                            nombre = it
                        },

                        singleLine = true,

                        leadingIcon = {

                            Icon(
                                Icons.Default.Person,
                                contentDescription = null
                            )
                        },

                        placeholder = {

                            Text("Juan Perez")
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),
                        //////////////////////////////////////////////////
                        // 🎨 COLORES
                        //////////////////////////////////////////////////
                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFFD4C15F),

                            unfocusedBorderColor = Color(0xFFB0A79F),

                            focusedTextColor = Color(0xFF4E342E),

                            unfocusedTextColor = Color(0xFF4E342E),

                            cursorColor = Color(0xFF4E342E),

                            focusedLeadingIconColor = Color.Gray,

                            unfocusedLeadingIconColor = Color.Gray,

                            focusedPlaceholderColor = Color.LightGray,

                            unfocusedPlaceholderColor = Color.LightGray
                        )

                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    //////////////////////////////////////////////////
                    // 📧 CORREO
                    //////////////////////////////////////////////////
                    Text(

                        text = "CORREO",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = usuario,

                        onValueChange = {

                            usuario = it
                        },

                        singleLine = true,

                        leadingIcon = {

                            Icon(
                                Icons.Default.Email,
                                contentDescription = null
                            )
                        },

                        placeholder = {

                            Text("correo@gmail.com")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),
                        //////////////////////////////////////////////////
                        // 🎨 COLORES
                        //////////////////////////////////////////////////
                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFFD4C15F),

                            unfocusedBorderColor = Color(0xFFB0A79F),

                            focusedTextColor = Color(0xFF4E342E),

                            unfocusedTextColor = Color(0xFF4E342E),

                            cursorColor = Color(0xFF4E342E),

                            focusedLeadingIconColor = Color.Gray,

                            unfocusedLeadingIconColor = Color.Gray,

                            focusedPlaceholderColor = Color.LightGray,

                            unfocusedPlaceholderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    //////////////////////////////////////////////////
                    // 🔒 CONTRASEÑA
                    //////////////////////////////////////////////////
                    Text(

                        text = "CONTRASEÑA",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = password,

                        onValueChange = {

                            password = it
                        },

                        singleLine = true,

                        leadingIcon = {

                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null
                            )
                        },

                        trailingIcon = {

                            IconButton(

                                onClick = {

                                    passwordVisible =
                                        !passwordVisible
                                }
                            ) {

                                Icon(

                                    imageVector =
                                        if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null
                                )
                            }
                        },

                        visualTransformation =
                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        placeholder = {

                            Text("********")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),
                        //////////////////////////////////////////////////
                        // 🎨 COLORES
                        //////////////////////////////////////////////////
                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFFD4C15F),

                            unfocusedBorderColor = Color(0xFFB0A79F),

                            focusedTextColor = Color(0xFF4E342E),

                            unfocusedTextColor = Color(0xFF4E342E),

                            cursorColor = Color(0xFF4E342E),

                            focusedLeadingIconColor = Color.Gray,

                            unfocusedLeadingIconColor = Color.Gray,

                            focusedPlaceholderColor = Color.LightGray,

                            unfocusedPlaceholderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    //////////////////////////////////////////////////
                    // 🔒 CONFIRMAR CONTRASEÑA
                    //////////////////////////////////////////////////
                    Text(

                        text = "CONFIRMAR CONTRASEÑA",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = confirmarPassword,

                        onValueChange = {

                            confirmarPassword = it
                        },

                        singleLine = true,

                        leadingIcon = {

                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null
                            )
                        },

                        trailingIcon = {

                            IconButton(

                                onClick = {

                                    confirmPasswordVisible =
                                        !confirmPasswordVisible
                                }
                            ) {

                                Icon(

                                    imageVector =
                                        if (confirmPasswordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,

                                    contentDescription = null
                                )
                            }
                        },

                        visualTransformation =
                            if (confirmPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        placeholder = {

                            Text("********")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp),
                        //////////////////////////////////////////////////
                        // 🎨 COLORES
                        //////////////////////////////////////////////////
                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFFD4C15F),

                            unfocusedBorderColor = Color(0xFFB0A79F),

                            focusedTextColor = Color(0xFF4E342E),

                            unfocusedTextColor = Color(0xFF4E342E),

                            cursorColor = Color(0xFF4E342E),

                            focusedLeadingIconColor = Color.Gray,

                            unfocusedLeadingIconColor = Color.Gray,

                            focusedPlaceholderColor = Color.LightGray,

                            unfocusedPlaceholderColor = Color.LightGray
                        )
                    )

                    //////////////////////////////////////////////////
                    // 💬 MENSAJES
                    //////////////////////////////////////////////////
                    if (mensaje.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(

                            text = mensaje,

                            color =
                                if (
                                    mensaje.contains("éxito")
                                )
                                    Color(0xFF2E7D32)
                                else
                                    Color.Red,

                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    //////////////////////////////////////////////////
                    // 🚀 CREAR CUENTA
                    //////////////////////////////////////////////////
                    Button(

                        onClick = {

                            //////////////////////////////////////////////////
                            // 🔥 VALIDACIONES
                            //////////////////////////////////////////////////
                            when {

                                nombre.isBlank() -> {

                                    mensaje =
                                        "Ingresa tu nombre"
                                }

                                usuario.isBlank() -> {

                                    mensaje =
                                        "Ingresa un correo"
                                }

                                password.length < 6 -> {

                                    mensaje =
                                        "La contraseña debe tener mínimo 6 caracteres"
                                }

                                password != confirmarPassword -> {

                                    mensaje =
                                        "Las contraseñas no coinciden"
                                }

                                else -> {

                                    cargando = true

                                    //////////////////////////////////////////////////
                                    // 🔥 VERIFICAR SI ES EL PRIMER USUARIO
                                    //////////////////////////////////////////////////
                                    db.collection("empleados")
                                        .get()
                                        .addOnSuccessListener { documentos ->

                                            //////////////////////////////////////////////////
                                            // 🔥 ROL AUTOMÁTICO
                                            //////////////////////////////////////////////////
                                            val rol =
                                                if (documentos.isEmpty)
                                                    "Administrador"
                                                else
                                                    "Empleado"

                                            //////////////////////////////////////////////////
                                            // 🔥 CREAR CUENTA FIREBASE
                                            //////////////////////////////////////////////////
                                            auth.createUserWithEmailAndPassword(
                                                usuario,
                                                password
                                            )
                                                .addOnSuccessListener {

                                                    val userId =
                                                        auth.currentUser?.uid

                                                    //////////////////////////////////////////////////
                                                    // 🔥 DATOS EMPLEADO
                                                    //////////////////////////////////////////////////
                                                    val empleado =
                                                        hashMapOf(

                                                            "nombre" to nombre,

                                                            "usuario" to usuario,

                                                            "rol" to rol
                                                        )

                                                    //////////////////////////////////////////////////
                                                    // 🔥 GUARDAR FIRESTORE
                                                    //////////////////////////////////////////////////
                                                    db.collection("empleados")
                                                        .document(userId!!)
                                                        .set(empleado)
                                                        .addOnSuccessListener {

                                                            cargando = false

                                                            mensaje =
                                                                "Cuenta creada con éxito"

                                                            onCreateAccountClick()
                                                        }
                                                        .addOnFailureListener {

                                                            cargando = false

                                                            mensaje =
                                                                "Error guardando usuario"
                                                        }
                                                }

                                                .addOnFailureListener {

                                                    cargando = false

                                                    mensaje =
                                                        it.message
                                                            ?: "Error desconocido"
                                                }
                                        }
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6D4C41)
                        )
                    ) {

                        //////////////////////////////////////////////////////
                        // ⏳ LOADING
                        //////////////////////////////////////////////////////
                        if (cargando) {

                            CircularProgressIndicator(

                                color = Color.White,

                                strokeWidth = 3.dp,

                                modifier = Modifier.size(22.dp)
                            )
                        } else {

                            Text(

                                text = "Crear cuenta",

                                color = Color.White,

                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    //////////////////////////////////////////////////
                    // 🔙 LOGIN
                    //////////////////////////////////////////////////
                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            "¿Ya tienes cuenta? "
                        )

                        Text(

                            text = "Iniciar sesión",

                            color = Color(0xFF6D4C41),

                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.clickable {

                                onLoginClick()
                            }
                        )
                    }
                }
            }
        }
    }
}