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

// Material
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons

// Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock

// UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType

// Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//////////////////////////////////////////////////////////////
// 🔵 LOGIN SCREEN PRO FINAL
//////////////////////////////////////////////////////////////
@Composable
fun LoginScreen(

    //////////////////////////////////////////////////////////
    // ✅ LOGIN EXITOSO
    //////////////////////////////////////////////////////////
    onLoginSuccess: (String, String) -> Unit,

    //////////////////////////////////////////////////////////
    // ⬅️ VOLVER
    //////////////////////////////////////////////////////////
    onBackClick: () -> Unit,

    //////////////////////////////////////////////////////////
    // 📝 REGISTER
    //////////////////////////////////////////////////////////
    onRegisterClick: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🧠 STATES
    //////////////////////////////////////////////////////////
    var username by remember {

        mutableStateOf("")
    }

    var password by remember {

        mutableStateOf("")
    }

    var error by remember {

        mutableStateOf("")
    }

    var resetMessage by remember {

        mutableStateOf("")
    }

    var loading by remember {

        mutableStateOf(false)
    }

    var passwordVisible by remember {

        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🔥 FIREBASE
    //////////////////////////////////////////////////////////
    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 🟤 CONTENEDOR PRINCIPAL
    //////////////////////////////////////////////////////////
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E0C3))
            .statusBarsPadding()
            .padding(24.dp)
    ) {

        //////////////////////////////////////////////////////
        // 🔙 HEADER
        //////////////////////////////////////////////////////
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
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(

                text = "Panadería Martínez",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF4E342E)
            )
        }

        //////////////////////////////////////////////////////
        // 📏 ESPACIO
        //////////////////////////////////////////////////////
        Spacer(modifier = Modifier.height(28.dp))

        //////////////////////////////////////////////////////
        // 🟤 CARD PRINCIPAL
        //////////////////////////////////////////////////////
        Card(

            shape = RoundedCornerShape(28.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            //////////////////////////////////////////////////
            // 📦 CONTENIDO
            //////////////////////////////////////////////////
            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                //////////////////////////////////////////////////
                // 🏷️ TITULO
                //////////////////////////////////////////////////
                Text(

                    text = "Iniciar sesión",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = "Accede a tu cuenta",

                    color = Color.Gray
                )

                //////////////////////////////////////////////////
                // 📏 ESPACIO
                //////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(24.dp))

                //////////////////////////////////////////////////
                // 📧 EMAIL
                //////////////////////////////////////////////////
                Text(

                    text = "CORREO",

                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(

                    value = username,

                    onValueChange = {

                        username = it
                    },

                    placeholder = {

                        Text("correo@gmail.com")
                    },

                    leadingIcon = {

                        Icon(
                            Icons.Default.Email,
                            contentDescription = null
                        )
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    singleLine = true,
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
                // 📏 ESPACIO
                //////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(20.dp))

                //////////////////////////////////////////////////
                // 🔐 PASSWORD
                //////////////////////////////////////////////////
                Text(

                    text = "CONTRASEÑA",

                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(

                    value = password,

                    onValueChange = {

                        password = it
                    },

                    placeholder = {

                        Text("********")
                    },

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

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    singleLine = true,
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
                // 🔐 RECUPERAR CONTRASEÑA
                //////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(10.dp))

                Text(

                    text = "¿Olvidaste tu contraseña?",

                    color = Color(0xFF6D4C41),

                    fontWeight = FontWeight.SemiBold,

                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {

                            //////////////////////////////////////////////////
                            // ⚠️ VALIDAR EMAIL
                            //////////////////////////////////////////////////
                            if (username.isBlank()) {

                                error =
                                    "Ingresa tu correo primero"

                            } else {

                                //////////////////////////////////////////////////
                                // 📧 ENVIAR RECOVERY EMAIL
                                //////////////////////////////////////////////////
                                auth.sendPasswordResetEmail(
                                    username.trim()
                                )

                                    .addOnSuccessListener {

                                        resetMessage =
                                            "Correo de recuperación enviado"

                                        error = ""
                                    }

                                    .addOnFailureListener {

                                        error =
                                            "No se pudo enviar el correo"
                                    }
                            }
                        }
                )

                //////////////////////////////////////////////////
                // ❌ ERROR
                //////////////////////////////////////////////////
                if (error.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(

                        text = error,

                        color = Color.Red
                    )
                }

                //////////////////////////////////////////////////
                // ✅ SUCCESS RESET
                //////////////////////////////////////////////////
                if (resetMessage.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(

                        text = resetMessage,

                        color = Color(0xFF2E7D32)
                    )
                }

                //////////////////////////////////////////////////
                // 📏 ESPACIO
                //////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(28.dp))

                //////////////////////////////////////////////////
                // 🔥 LOGIN BUTTON
                //////////////////////////////////////////////////
                Button(

                    onClick = {

                        //////////////////////////////////////////////////
                        // 🧹 RESET MENSAJES
                        //////////////////////////////////////////////////
                        error = ""

                        resetMessage = ""

                        //////////////////////////////////////////////////
                        // ⚠️ VALIDACIONES
                        //////////////////////////////////////////////////
                        if (
                            username.isBlank() ||
                            password.isBlank()
                        ) {

                            error =
                                "Completa todos los campos"

                            return@Button
                        }

                        //////////////////////////////////////////////////
                        // ⏳ LOADING
                        //////////////////////////////////////////////////
                        loading = true

                        //////////////////////////////////////////////////
                        // 🔐 LOGIN FIREBASE
                        //////////////////////////////////////////////////
                        auth.signInWithEmailAndPassword(
                            username.trim(),
                            password
                        )

                            .addOnSuccessListener {

                                //////////////////////////////////////////////////
                                // 🆔 UID
                                //////////////////////////////////////////////////
                                val uid =
                                    auth.currentUser?.uid

                                //////////////////////////////////////////////////
                                // 🔥 BUSCAR DATOS
                                //////////////////////////////////////////////////
                                db.collection("empleados")
                                    .document(uid!!)
                                    .get()

                                    .addOnSuccessListener { doc ->

                                        loading = false

                                        val nombre =
                                            doc.getString("nombre")
                                                ?: ""

                                        val rol =
                                            doc.getString("rol")
                                                ?: "Empleado"

                                        //////////////////////////////////////////////////
                                        // 🚀 LOGIN SUCCESS
                                        //////////////////////////////////////////////////
                                        onLoginSuccess(
                                            nombre,
                                            rol
                                        )
                                    }
                            }

                            .addOnFailureListener {

                                loading = false

                                error =
                                    "Correo o contraseña incorrectos"
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
                    if (loading) {

                        CircularProgressIndicator(

                            color = Color.White,

                            strokeWidth = 3.dp,

                            modifier = Modifier.size(22.dp)
                        )

                    } else {

                        Text(

                            text = "Iniciar sesión",

                            color = Color.White,

                            fontSize = 16.sp
                        )
                    }
                }

                //////////////////////////////////////////////////
                // 📏 ESPACIO
                //////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(22.dp))

                //////////////////////////////////////////////////
                // 📝 REGISTER
                //////////////////////////////////////////////////
                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Text("¿No tienes cuenta? ")

                    Text(

                        text = "Crear cuenta",

                        color = Color(0xFF6D4C41),

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.clickable {

                            onRegisterClick()
                        }
                    )
                }
            }
        }
    }
}