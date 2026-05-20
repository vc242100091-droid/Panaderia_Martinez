package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import android.app.DatePickerDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inventory2

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.launch

import java.util.Calendar

//////////////////////////////////////////////////////////////
// 🟣 MODELO ITEM PEDIDO
//////////////////////////////////////////////////////////////

class PedidoItem {

    //////////////////////////////////////////////////////////
    // 🍞 PRODUCTO
    //////////////////////////////////////////////////////////
    var producto by mutableStateOf("")

    //////////////////////////////////////////////////////////
    // 🔢 CANTIDAD
    //////////////////////////////////////////////////////////
    var cantidad by mutableStateOf(0)

    //////////////////////////////////////////////////////////
    // 💲 PRECIO
    //////////////////////////////////////////////////////////
    var precio by mutableStateOf("")
}

//////////////////////////////////////////////////////////////
// 🟣 PEDIDOS SCREEN PRO V2
//////////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(

    //////////////////////////////////////////////////////////
    // 🔐 ROL
    //////////////////////////////////////////////////////////
    rol: String,

    //////////////////////////////////////////////////////////
    // 🧭 NAVEGACIÓN
    //////////////////////////////////////////////////////////
    onInicio: () -> Unit = {},

    onInsumos: () -> Unit = {},

    onProductos: () -> Unit = {},

    onPedidos: () -> Unit = {},

    onVerPedidos: () -> Unit = {}
) {

    //////////////////////////////////////////////////////////
    // 🔒 SOLO ADMIN
    //////////////////////////////////////////////////////////
    if (rol != "Administrador") {

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5E0C3)),

            contentAlignment = Alignment.Center
        ) {

            Text(

                text = "No tienes acceso a esta pantalla",

                color = Color.Red,

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold
            )
        }

        return
    }

    //////////////////////////////////////////////////////////
    // 🔥 FIREBASE
    //////////////////////////////////////////////////////////
    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 🔔 SNACKBAR
    //////////////////////////////////////////////////////////
    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    //////////////////////////////////////////////////////////
    // 🚀 COROUTINES
    //////////////////////////////////////////////////////////
    val scope =
        rememberCoroutineScope()

    //////////////////////////////////////////////////////////
    // ➕ DIALOG
    //////////////////////////////////////////////////////////
    var showDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 👤 CLIENTE
    //////////////////////////////////////////////////////////
    var cliente by remember {
        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 📅 FECHA
    //////////////////////////////////////////////////////////
    var fechaEntrega by remember {
        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////
    // 📦 ITEMS
    //////////////////////////////////////////////////////////
    val items =
        remember {
            mutableStateListOf(
                PedidoItem()
            )
        }

    //////////////////////////////////////////////////////////
    // 📊 CONTADORES
    //////////////////////////////////////////////////////////
    var pedidosPendientes by remember {
        mutableStateOf(0)
    }

    var pedidosEntregados by remember {
        mutableStateOf(0)
    }

    //////////////////////////////////////////////////////////
    // 🔄 REALTIME CONTADORES
    //////////////////////////////////////////////////////////
    LaunchedEffect(Unit) {

        db.collection("pedidos")

            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {

                    pedidosPendientes =
                        snapshot.documents.count { document ->

                            document.getString("estado") == "Pendiente"
                        }

                    pedidosEntregados =
                        snapshot.documents.count { document ->

                            document.getString("estado") == "Entregado"
                        }
                }
            }
    }

    //////////////////////////////////////////////////////////
    // 🍞 CATÁLOGO
    //////////////////////////////////////////////////////////
    val catalogo = listOf(

        "Pan francés de 16",

        "Pan francés de 12",

        "Pan francés de 9",

        "Pan francés de 24",

        "Pan francés de torta",

        "Pan francés de redondo",

        "Pan de yema",

        "Pan francés especial",

        "Quesadilla"
    )

    //////////////////////////////////////////////////////////
    // 📅 CONTEXTO
    //////////////////////////////////////////////////////////
    val context = LocalContext.current

    //////////////////////////////////////////////////////////
    // 📅 CALENDARIO
    //////////////////////////////////////////////////////////
    val calendar = Calendar.getInstance()

    //////////////////////////////////////////////////////////
    // 📅 DATE PICKER
    //////////////////////////////////////////////////////////
    val datePickerDialog = DatePickerDialog(

        context,

        { _, year, month, dayOfMonth ->

            fechaEntrega =
                "$dayOfMonth/${month + 1}/$year"
        },

        calendar.get(Calendar.YEAR),

        calendar.get(Calendar.MONTH),

        calendar.get(Calendar.DAY_OF_MONTH)
    )

    //////////////////////////////////////////////////////////
    // 🎨 UI
    //////////////////////////////////////////////////////////
    Scaffold(

        //////////////////////////////////////////////////////
        // 🔔 SNACKBAR
        //////////////////////////////////////////////////////
        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            )
        },

        //////////////////////////////////////////////////////
        // 🔻 BOTTOM BAR
        //////////////////////////////////////////////////////
        bottomBar = {

            BottomBarPro(

                selected = "pedidos",

                onInicio = onInicio,

                onInsumos = onInsumos,

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

            FloatingActionButton(

                onClick = {

                    showDialog = true
                },

                containerColor =
                    Color(0xFF795548)
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Add,

                    contentDescription = null,

                    tint = Color.White
                )
            }
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
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            //////////////////////////////////////////////////
            // 🍞 HEADER MODERNO
            //////////////////////////////////////////////////
            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Inventory2,

                    contentDescription = null,

                    tint = Color(0xFFB26A00),

                    modifier = Modifier
                        .size(34.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Column {

                    Text(

                        text = "Pedidos",

                        fontSize = 32.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Text(

                        text =
                            "Gestión inteligente de pedidos",

                        color = Color.Gray,

                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            //////////////////////////////////////////////////
            // 📊 CONTADORES
            //////////////////////////////////////////////////
            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                //////////////////////////////////////////////////
                // 🟠 PENDIENTES
                //////////////////////////////////////////////////
                Card(

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(22.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFF1ECE8)
                        )
                ) {

                    Column(

                        modifier =
                            Modifier.padding(18.dp)
                    ) {

                        Text(

                            text =
                                pedidosPendientes.toString(),

                            fontSize = 28.sp,

                            fontWeight = FontWeight.Bold,

                            color = Color(0xFFFF9800)
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(

                            text = "Pendientes",

                            color = Color.Gray
                        )
                    }
                }

                //////////////////////////////////////////////////
                // 🟢 ENTREGADOS
                //////////////////////////////////////////////////
                Card(

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(22.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFF1ECE8)
                        )
                ) {

                    Column(

                        modifier =
                            Modifier.padding(18.dp)
                    ) {

                        Text(

                            text =
                                pedidosEntregados.toString(),

                            fontSize = 28.sp,

                            fontWeight = FontWeight.Bold,

                            color = Color(0xFF4CAF50)
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(

                            text = "Entregados",

                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
            Spacer(
                modifier = Modifier.height(120.dp)
            )

        }
    }

    //////////////////////////////////////////////////////////
    // 🟤 DIALOG NUEVO PEDIDO
    //////////////////////////////////////////////////////////
    if (showDialog) {

        AlertDialog(

            onDismissRequest = {

                showDialog = false
            },

            //////////////////////////////////////////////////////
            // 💾 GUARDAR
            //////////////////////////////////////////////////////
            confirmButton = {

                TextButton(

                    onClick = {

                        //////////////////////////////////////////////////
                        // 🔒 VALIDACIONES
                        //////////////////////////////////////////////////
                        if (
                            cliente.isBlank() ||
                            fechaEntrega.isBlank()
                        ) {
                            return@TextButton
                        }

                        //////////////////////////////////////////////////
                        // 🔥 FIREBASE
                        //////////////////////////////////////////////////
                        val pedidoRef =
                            db.collection("pedidos")
                                .document()

                        //////////////////////////////////////////////////
                        // 📦 PEDIDO
                        //////////////////////////////////////////////////
                        val pedido = hashMapOf(

                            "cliente" to cliente,

                            "fechaEntrega" to fechaEntrega,

                            "estado" to "Pendiente",

                            "fechaCreacion" to
                                    System.currentTimeMillis()
                        )

                        //////////////////////////////////////////////////
                        // 🔥 GUARDAR
                        //////////////////////////////////////////////////
                        pedidoRef.set(pedido)

                            .addOnSuccessListener {

                                items.forEach {

                                    val itemMap = hashMapOf(

                                        "producto" to it.producto,

                                        "cantidad" to it.cantidad,

                                        "precio" to it.precio
                                    )

                                    pedidoRef
                                        .collection("items")
                                        .add(itemMap)
                                }

                                //////////////////////////////////////////////////
                                // 🔔 MENSAJE
                                //////////////////////////////////////////////////
                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "✅ Pedido registrado correctamente"
                                        )
                                }

                                //////////////////////////////////////////////////
                                // 🔄 LIMPIAR
                                //////////////////////////////////////////////////
                                cliente = ""

                                fechaEntrega = ""

                                items.clear()

                                items.add(
                                    PedidoItem()
                                )

                                showDialog = false
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

                        showDialog = false
                    }
                ) {

                    Text("Cancelar")
                }
            },

            //////////////////////////////////////////////////////
            // 📝 CONTENIDO
            //////////////////////////////////////////////////////
            text = {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp)
                ) {

                    //////////////////////////////////////////////////
                    // 🧾 TÍTULO
                    //////////////////////////////////////////////////
                    Text(

                        text = "Nuevo pedido",

                        fontWeight = FontWeight.Bold,

                        fontSize = 24.sp,

                        color = Color(0xFF5D4037)
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    //////////////////////////////////////////////////
                    // 👤 CLIENTE
                    //////////////////////////////////////////////////
                    OutlinedTextField(

                        value = cliente,

                        onValueChange = {

                            cliente = it
                        },

                        label = {

                            Text("Cliente")
                        },

                        modifier =
                            Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    //////////////////////////////////////////////////
                    // 📅 FECHA
                    //////////////////////////////////////////////////
                    OutlinedTextField(

                        value = fechaEntrega,

                        onValueChange = {},

                        readOnly = true,

                        label = {

                            Text("Fecha entrega")
                        },

                        trailingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Default.DateRange,

                                contentDescription = null,

                                modifier = Modifier
                                    .clickable {

                                        datePickerDialog.show()
                                    }
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                datePickerDialog.show()
                            }
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    //////////////////////////////////////////////////
                    // 📦 ITEMS
                    //////////////////////////////////////////////////
                    LazyColumn(

                        modifier = Modifier
                            .height(280.dp)
                    ) {

                        itemsIndexed(items) {

                                index, item ->

                            PedidoItemCardPro(

                                item = item,

                                catalogo = catalogo,

                                onRemove = {

                                    if (
                                        items.size > 1
                                    ) {

                                        items.removeAt(index)
                                    }
                                }
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    //////////////////////////////////////////////////
                    // ➕ AGREGAR
                    //////////////////////////////////////////////////
                    Button(

                        onClick = {

                            items.add(
                                PedidoItem()
                            )
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF795548)
                            )
                    ) {

                        Text("Agregar producto")
                    }
                }
            }
        )
    }
}


//////////////////////////////////////////////////////////////
// 🧾 CARD ITEM PEDIDO
//////////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoItemCardPro(

    item: PedidoItem,

    catalogo: List<String>,

    onRemove: () -> Unit
) {

    //////////////////////////////////////////////////////////
    // 🔽 DROPDOWN
    //////////////////////////////////////////////////////////
    var expanded by remember {

        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🎨 CARD
    //////////////////////////////////////////////////////////
    Card(

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F4F0)
        )
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {

            //////////////////////////////////////////////////
            // 🍞 PRODUCTO
            //////////////////////////////////////////////////
            ExposedDropdownMenuBox(

                expanded = expanded,

                onExpandedChange = {

                    expanded = !expanded
                }
            ) {

                OutlinedTextField(

                    value = item.producto,

                    onValueChange = {},

                    readOnly = true,

                    label = {

                        Text("Producto")
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

                ExposedDropdownMenu(

                    expanded = expanded,

                    onDismissRequest = {

                        expanded = false
                    }
                ) {

                    catalogo.forEach { producto ->

                        DropdownMenuItem(

                            text = {

                                Text(producto)
                            },

                            onClick = {

                                item.producto =
                                    producto

                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            //////////////////////////////////////////////////
            // 🔢 CANTIDAD
            //////////////////////////////////////////////////
            OutlinedTextField(

                value =
                    if (item.cantidad == 0)
                        ""
                    else
                        item.cantidad.toString(),

                onValueChange = {

                    item.cantidad =
                        it.toIntOrNull() ?: 0
                },

                label = {

                    Text("Cantidad")
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

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            //////////////////////////////////////////////////
            // 💲 PRECIO
            //////////////////////////////////////////////////
            OutlinedTextField(

                value = item.precio,

                onValueChange = {

                    item.precio = it
                },

                label = {

                    Text("Precio")
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

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            //////////////////////////////////////////////////
            // 🗑️ ELIMINAR
            //////////////////////////////////////////////////
            Button(

                onClick = onRemove,

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Delete,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text("Eliminar")
            }
        }
    }
}