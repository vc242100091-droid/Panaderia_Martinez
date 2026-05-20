package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch

import com.google.firebase.firestore.FirebaseFirestore

//////////////////////////////////////////////////////////////
// 🍞 INSUMOS SCREEN PRO
//////////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsumosScreen(

    //////////////////////////////////////////////////////////
    // 🔐 ROL
    //////////////////////////////////////////////////////////
    rol: String,

    //////////////////////////////////////////////////////////
    // 🧭 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onInicio: () -> Unit = {},
    onProductos: () -> Unit = {},
    onPedidos: () -> Unit = {},
    onVerPedidos: () -> Unit = {}
) {

    //////////////////////////////////////////////////////////
    // 🔥 FIREBASE
    //////////////////////////////////////////////////////////
    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 📦 LISTA INSUMOS
    //////////////////////////////////////////////////////////
    val insumos = remember {
        mutableStateListOf<InsumoModel>()
    }

    //////////////////////////////////////////////////////////////
    // 🔍 BUSCADOR
   //////////////////////////////////////////////////////////////
    var searchText by remember {

        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // ➕ DIALOG NUEVO INSUMO
    //////////////////////////////////////////////////////////
    var showAddDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🗑️ DIALOG ELIMINAR
    //////////////////////////////////////////////////////////
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // ✏️ DIALOG ACTUALIZAR
    //////////////////////////////////////////////////////////
    var showUpdateDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 📦 INSUMO SELECCIONADO
    //////////////////////////////////////////////////////////
    var selectedInsumo by remember {
        mutableStateOf<InsumoModel?>(null)
    }

    //////////////////////////////////////////////////////////
    // 🔔 SNACKBAR
    //////////////////////////////////////////////////////////
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    //////////////////////////////////////////////////////////
    // 🚀 COROUTINE
    //////////////////////////////////////////////////////////
    val coroutineScope = rememberCoroutineScope()

    //////////////////////////////////////////////////////////
    // 🔥 FORMULARIO
    //////////////////////////////////////////////////////////
    var nombre by remember { mutableStateOf("") }

    var cantidad by remember { mutableStateOf(1) }

    var unidad by remember { mutableStateOf("Kg") }

    //////////////////////////////////////////////////////////
    // 🔽 UNIDADES
    //////////////////////////////////////////////////////////
    val unidades = listOf(
        "Kg",
        "Libras",
        "Sacos",
        "Bolsas",
        "Cajas",
        "Unidades"
    )

    //////////////////////////////////////////////////////////
    // 🔽 DROPDOWN
    //////////////////////////////////////////////////////////
    var expanded by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🔄 REALTIME FIREBASE
    //////////////////////////////////////////////////////////
    LaunchedEffect(Unit) {

        db.collection("insumos")
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {

                    insumos.clear()

                    snapshot.documents.forEach {

                        val item = InsumoModel(
                            id = it.id,
                            nombre = it.getString("nombre") ?: "",
                            cantidad = (
                                    it.getLong("cantidad")
                                        ?: 0
                                    ).toInt(),
                            unidad =
                                it.getString("unidad")
                                    ?: "Kg"
                        )

                        insumos.add(item)
                    }
                }
            }
    }

    //////////////////////////////////////////////////////////////
    // 🔍 INSUMOS FILTRADOS
    // ////////////////////////////////////////////////////////////
    val filteredInsumos = insumos.filter {

        it.nombre.contains(
            searchText,
            ignoreCase = true
        )
    }

    //////////////////////////////////////////////////////////
    // 🔥 UI PRINCIPAL
    //////////////////////////////////////////////////////////
    Scaffold(

        //////////////////////////////////////////////////////
        // 🔻 BOTTOM BAR
        //////////////////////////////////////////////////////
        bottomBar = {

            BottomBarPro(

                selected = "insumos",

                onInicio = onInicio,

                onInsumos = {},

                onProductos = onProductos,

                onPedidos = onPedidos,

                onVerPedidos = onVerPedidos,

                rol = rol
            )
        },

        //////////////////////////////////////////////////////
        // ➕ FAB
        //////////////////////////////////////////////////////
        floatingActionButton = {

            if (rol == "Administrador") {

                FloatingActionButton(

                    onClick = {
                        showAddDialog = true
                    },

                    containerColor =
                        Color(0xFF6D4C41)
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },

        //////////////////////////////////////////////////////
        // 🔔 SNACKBAR HOST
        //////////////////////////////////////////////////////
        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )
        },

        //////////////////////////////////////////////////////
        // 🎨 COLOR
        //////////////////////////////////////////////////////
        containerColor = Color(0xFFF5E0C3)

    ) { paddingValues ->

        //////////////////////////////////////////////////////
        // 🍞 CONTENIDO
        //////////////////////////////////////////////////////
        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5E0C3))
                .statusBarsPadding()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

//////////////////////////////////////////////////////////////
// 📦 HEADER PRO
//////////////////////////////////////////////////////////////
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                //////////////////////////////////////////////////////////
                // 📦 ICONO
                //////////////////////////////////////////////////////////
                Text(
                    text = "📦",
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.width(10.dp))

                //////////////////////////////////////////////////////////
                // 📝 TEXTOS
                //////////////////////////////////////////////////////////
                Column {

                    Text(
                        text = "Insumos",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4E342E)
                    )

                    Text(
                        text = "Gestión de inventario y materias primas",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

             //////////////////////////////////////////////////////////////
             // 🔍 BUSCADOR PRO
             //////////////////////////////////////////////////////////////
            OutlinedTextField(

                value = searchText,

                onValueChange = {

                    searchText = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {

                    Text("Buscar insumo...")
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },

                singleLine = true,

                shape = RoundedCornerShape(16.dp),

                //////////////////////////////////////////////////
                // 🎨 COLORES
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

            Spacer(modifier = Modifier.height(18.dp))

            //////////////////////////////////////////////////
            // 📦 LISTA INSUMOS
            //////////////////////////////////////////////////
            LazyColumn {

                items(filteredInsumos) { insumo ->

                    //////////////////////////////////////////////////////
                    // 🍞 CARD PRO
                    //////////////////////////////////////////////////////
                    InsumoCardPro(

                        //////////////////////////////////////////////////
                        // 📦 INSUMO
                        //////////////////////////////////////////////////
                        insumo = insumo,

                        //////////////////////////////////////////////////
                        // 🔐 ROL
                        //////////////////////////////////////////////////
                        rol = rol,

                        //////////////////////////////////////////////////
                        // ✅ ACTUALIZAR STOCK
                        //////////////////////////////////////////////////
                        onActualizar = { nuevaCantidad ->

                            //////////////////////////////////////////////////
                            // 📦 GUARDAR TEMPORAL
                            //////////////////////////////////////////////////
                            selectedInsumo = insumo.copy(
                                cantidad = nuevaCantidad
                            )

                            //////////////////////////////////////////////////
                            // 🔥 ABRIR DIALOG
                            //////////////////////////////////////////////////
                            showUpdateDialog = true
                        },

                        //////////////////////////////////////////////////
                        // 🗑️ ELIMINAR
                        //////////////////////////////////////////////////
                        onEliminar = {

                            //////////////////////////////////////////////////
                            // 📦 SELECCIONAR
                            //////////////////////////////////////////////////
                            selectedInsumo = insumo

                            //////////////////////////////////////////////////
                            // 🔥 MOSTRAR DIALOG
                            //////////////////////////////////////////////////
                            showDeleteDialog = true
                        }
                    )
                }

                //////////////////////////////////////////////////
                // 🔥 ESPACIO EXTRA
                //////////////////////////////////////////////////
                item {

                    Spacer(
                        modifier = Modifier.height(120.dp)
                    )
                }
            }
        }
    }

    //////////////////////////////////////////////////////////
    // ➕ DIALOG NUEVO INSUMO
    //////////////////////////////////////////////////////////
    if (showAddDialog) {

        AlertDialog(

            onDismissRequest = {
                showAddDialog = false
            },

            containerColor = Color(0xFFF8F3EF),

            //////////////////////////////////////////////////////
            // 📛 TÍTULO
            //////////////////////////////////////////////////////
            title = {

                Text(
                    text = "Nuevo insumo",

                    color = Color(0xFF4E342E),

                    fontWeight = FontWeight.Bold
                )
            },

            //////////////////////////////////////////////////////
            // 📝 CONTENIDO
            //////////////////////////////////////////////////////
            text = {

                Column {

                    //////////////////////////////////////////////////
                    // 📛 NOMBRE
                    //////////////////////////////////////////////////
                    OutlinedTextField(

                        value = nombre,

                        onValueChange = {
                            nombre = it
                        },

                        label = {
                            Text("Nombre")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        //////////////////////////////////////////////////
                        // 🎨 COLORES
                        //////////////////////////////////////////////////
                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFFD4C15F),

                            unfocusedBorderColor = Color(0xFFB0A79F),

                            focusedTextColor = Color(0xFF4E342E),

                            unfocusedTextColor = Color(0xFF4E342E),

                            cursorColor = Color(0xFF4E342E),

                            focusedLabelColor = Color(0xFFD4C15F),

                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    //////////////////////////////////////////////////
                    // 🔢 CONTROL DE CANTIDAD PRO
                    // ////////////////////////////////////////////////

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.Center,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        //////////////////////////////////////////////////
                        // ➖ RESTAR
                        //////////////////////////////////////////////////
                        HoldButton(

                            text = "-",

                            color = Color.Gray,

                            onClick = {

                                if (cantidad > 1) {

                                    cantidad--
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.width(22.dp)
                        )

                        //////////////////////////////////////////////////
                        // 🔢 CANTIDAD
                        //////////////////////////////////////////////////
                        Text(

                            text = cantidad.toString(),

                            fontSize = 28.sp,

                            fontWeight = FontWeight.Bold,

                            color = Color(0xFF4E342E)
                        )

                        Spacer(
                            modifier = Modifier.width(22.dp)
                        )

                        //////////////////////////////////////////////////
                        // ➕ SUMAR
                        //////////////////////////////////////////////////
                        HoldButton(

                            text = "+",

                            color = Color(0xFF6D4C41),

                            onClick = {

                                cantidad++
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    //////////////////////////////////////////////////
                    // 🔽 DROPDOWN
                    //////////////////////////////////////////////////
                    ExposedDropdownMenuBox(

                        expanded = expanded,

                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        OutlinedTextField(

                            value = unidad,

                            onValueChange = {},

                            readOnly = true,

                            label = {
                                Text("Unidad")
                            },

                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(expanded)
                            },

                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),

                            //////////////////////////////////////////////////
                            // 🎨 COLORES
                            //////////////////////////////////////////////////
                            colors = OutlinedTextFieldDefaults.colors(

                                focusedBorderColor = Color(0xFFD4C15F),

                                unfocusedBorderColor = Color(0xFFB0A79F),

                                focusedTextColor = Color(0xFF4E342E),

                                unfocusedTextColor = Color(0xFF4E342E),

                                focusedTrailingIconColor = Color.Gray,

                                unfocusedTrailingIconColor = Color.Gray,

                                focusedLabelColor = Color(0xFFD4C15F),

                                unfocusedLabelColor = Color.Gray
                            )
                        )

                        //////////////////////////////////////////////////
                        // 🔽 MENÚ
                        //////////////////////////////////////////////////
                        ExposedDropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            unidades.forEach {

                                DropdownMenuItem(

                                    text = {
                                        Text(it)
                                    },

                                    onClick = {

                                        unidad = it

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },

            //////////////////////////////////////////////////////
            // ✅ GUARDAR
            //////////////////////////////////////////////////////
            confirmButton = {

                TextButton(

                    onClick = {

                        //////////////////////////////////////////////////
                        // 🔥 NUEVO INSUMO
                        //////////////////////////////////////////////////
                        val nuevo = hashMapOf(

                            "nombre" to nombre,

                            "cantidad" to cantidad,

                            "unidad" to unidad
                        )

                        //////////////////////////////////////////////////
                        // 🔥 FIREBASE
                        //////////////////////////////////////////////////
                        db.collection("insumos")
                            .add(nuevo)

                        //////////////////////////////////////////////////
                        // 🔄 LIMPIAR
                        //////////////////////////////////////////////////
                        nombre = ""

                        cantidad = 1

                        unidad = "Kg"

                        showAddDialog = false

                        //////////////////////////////////////////////////
                        // 🔔 MENSAJE
                        //////////////////////////////////////////////////
                        coroutineScope.launch {

                            snackbarHostState.showSnackbar(
                                "✅ Insumo agregado correctamente"
                            )
                        }
                    }
                ) {

                    Text("Guardar")
                }
            },

            //////////////////////////////////////////////////////
            // ❌ CANCELAR
            //////////////////////////////////////////////////////
            dismissButton = {

                TextButton(

                    onClick = {
                        showAddDialog = false
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }

    //////////////////////////////////////////////////////////
    // 🗑️ DIALOG ELIMINAR
    //////////////////////////////////////////////////////////
    EliminarInsumoDialog(

        visible = showDeleteDialog,

        onDismiss = {
            showDeleteDialog = false
        },

        onConfirm = {

            selectedInsumo?.let {

                //////////////////////////////////////////////////////
                // 🔥 FIREBASE
                //////////////////////////////////////////////////////
                db.collection("insumos")
                    .document(it.id)
                    .delete()

                //////////////////////////////////////////////////////
                // 🔔 MENSAJE
                //////////////////////////////////////////////////////
                coroutineScope.launch {

                    snackbarHostState.showSnackbar(
                        "🗑️ Producto eliminado"
                    )
                }
            }

            showDeleteDialog = false
        }
    )

    //////////////////////////////////////////////////////////
    // ✏️ DIALOG ACTUALIZAR
    //////////////////////////////////////////////////////////
    ActualizarDialog(

        visible = showUpdateDialog,

        onDismiss = {
            showUpdateDialog = false
        },

        onConfirm = {

            selectedInsumo?.let {

                //////////////////////////////////////////////////////
                // 🔥 FIREBASE
                //////////////////////////////////////////////////////
                db.collection("insumos")
                    .document(it.id)
                    .update(
                        "cantidad",
                        it.cantidad
                    )

                //////////////////////////////////////////////////////
                // 🔔 MENSAJE
                //////////////////////////////////////////////////////
                coroutineScope.launch {

                    snackbarHostState.showSnackbar(
                        "✅ Stock actualizado"
                    )
                }
            }

            showUpdateDialog = false
        }
    )
}
