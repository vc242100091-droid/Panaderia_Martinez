@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.statusBarsPadding

import kotlinx.coroutines.launch

import com.google.firebase.firestore.FirebaseFirestore

//////////////////////////////////////////////////////////////
// 🍞 MODELO FORMULARIO
//////////////////////////////////////////////////////////////
class ProductoItem {

    var nombre by mutableStateOf("")

    var latas by mutableStateOf(1)
}

//////////////////////////////////////////////////////////////
// 🍞 MODELO STOCK
//////////////////////////////////////////////////////////////
data class ProductoStock(

    val nombre: String = "",

    val latas: Int = 0
)

//////////////////////////////////////////////////////////////
// 🍞 PRODUCTOS SCREEN PRO
//////////////////////////////////////////////////////////////
@Composable
fun ProductosScreen(

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
    // 🔥 FIREBASE
    //////////////////////////////////////////////////////////
    val db = FirebaseFirestore.getInstance()

    //////////////////////////////////////////////////////////
    // 📦 STOCK
    //////////////////////////////////////////////////////////
    val stockProductos =
        remember {
            mutableStateListOf<ProductoStock>()
        }

    //////////////////////////////////////////////////////////////
    // 🔍 BUSCADOR
    //////////////////////////////////////////////////////////////
    var searchText by remember {

        mutableStateOf("")
    }

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
    // ➕ DIALOG AGREGAR
    //////////////////////////////////////////////////////////
    var showAddDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // ❌ DIALOG ELIMINAR
    //////////////////////////////////////////////////////////
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 📦 FORMULARIOS
    //////////////////////////////////////////////////////////
    val productos =
        remember {
            mutableStateListOf(
                ProductoItem()
            )
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
    // 🔄 FIREBASE REALTIME
    //////////////////////////////////////////////////////////
    LaunchedEffect(Unit) {

        db.collection("productos")
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {

                    stockProductos.clear()

                    snapshot.documents.forEach {

                        stockProductos.add(

                            ProductoStock(

                                nombre = it.id,

                                latas = (
                                        it.getLong("latas")
                                            ?: 0
                                        ).toInt()
                            )
                        )
                    }
                }
            }
    }

    //////////////////////////////////////////////////////////////
    // 🔍 PRODUCTOS FILTRADOS
    //////////////////////////////////////////////////////////////
    val filteredProductos = stockProductos.filter {

        it.nombre.contains(
            searchText,
            ignoreCase = true
        )
    }

    //////////////////////////////////////////////////////////
    // 🎨 UI
    //////////////////////////////////////////////////////////
    Scaffold(

        //////////////////////////////////////////////////////
        // 🔻 BOTTOM BAR
        //////////////////////////////////////////////////////
        bottomBar = {

            BottomBarPro(

                selected = "productos",

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

            if (rol == "Administrador") {

                FloatingActionButton(

                    onClick = {

                        showAddDialog = true
                    },

                    containerColor =
                        Color(0xFF6D4C41)
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Add,

                        contentDescription = null,

                        tint = Color.White
                    )
                }
            }
        },

        //////////////////////////////////////////////////////
        // 🔔 SNACKBAR
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
// 🥖 HEADER PRO
//////////////////////////////////////////////////////////////
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                //////////////////////////////////////////////////////////
                // 🥖 ICONO
                //////////////////////////////////////////////////////////
                Text(
                    text = "🥖",
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.width(10.dp))

                //////////////////////////////////////////////////////////
                // 📝 TEXTOS
                //////////////////////////////////////////////////////////
                Column {

                    Text(
                        text = "Productos",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4E342E)
                    )

                    Text(
                        text = "Gestión de panes y producción diaria",
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

                    Text("Buscar producto...")
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

            Spacer(modifier = Modifier.height(20.dp))

            //////////////////////////////////////////////////
            // 📊 TÍTULO
            //////////////////////////////////////////////////
            Text(

                text = "Producción actual",

                fontWeight = FontWeight.Bold,

                fontSize = 20.sp,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(12.dp))

            //////////////////////////////////////////////////
            // 🗑️ ELIMINAR PRODUCCIÓN
            //////////////////////////////////////////////////
            if (rol == "Administrador") {

                Button(

                    onClick = {

                        showDeleteDialog = true
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD84315)
                        )
                ) {

                    Text(

                        text =
                            "Eliminar producción del día",

                        color = Color.White
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            //////////////////////////////////////////////////
            // 📦 LISTA
            //////////////////////////////////////////////////
            LazyColumn {

                items(filteredProductos) { producto ->

                    ProductoCardPro(
                        producto = producto
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
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
    // ➕ DIALOG AGREGAR
    //////////////////////////////////////////////////////////
    if (showAddDialog) {

        AlertDialog(

            onDismissRequest = {

                showAddDialog = false
            },

            containerColor =
                Color(0xFFF8F3EF),

            //////////////////////////////////////////////////////
            // 📛 TÍTULO
            //////////////////////////////////////////////////////
            title = {

                Text(

                    text = "Nueva producción",

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )
            },

            //////////////////////////////////////////////////////
            // 📝 CONTENIDO
            //////////////////////////////////////////////////////
            text = {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {

                    //////////////////////////////////////////////////
                    // 🔁 FORMULARIOS
                    //////////////////////////////////////////////////
                    productos.forEachIndexed { index, item ->

                        ProductoItemCardPro(

                            item = item,

                            catalogo = catalogo,

                            onRemove = {

                                if (
                                    productos.size > 1
                                ) {

                                    productos.removeAt(index)
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )
                    }

                    //////////////////////////////////////////////////
                    // ➕ AGREGAR MÁS
                    //////////////////////////////////////////////////
                    Button(

                        onClick = {

                            productos.add(
                                ProductoItem()
                            )
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF6D4C41)
                            )
                    ) {

                        Text(
                            "Agregar otro producto"
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            },

            //////////////////////////////////////////////////////
            // 💾 GUARDAR
            //////////////////////////////////////////////////////
            confirmButton = {

                TextButton(

                    onClick = {

                        //////////////////////////////////////////////////
                        // 🔥 GUARDAR
                        //////////////////////////////////////////////////
                        productos.forEach { item ->

                            if (
                                item.nombre.isNotEmpty()
                            ) {

                                val ref =
                                    db.collection("productos")
                                        .document(item.nombre)

                                db.runTransaction {

                                        transaction ->

                                    val snap =
                                        transaction.get(ref)

                                    val actual =
                                        snap.getLong("latas")
                                            ?: 0

                                    transaction.set(

                                        ref,

                                        mapOf(
                                            "latas" to
                                                    actual + item.latas
                                        )
                                    )
                                }
                            }
                        }

                        //////////////////////////////////////////////////
                        // 🔄 RESET
                        //////////////////////////////////////////////////
                        productos.clear()

                        productos.add(
                            ProductoItem()
                        )

                        showAddDialog = false

                        //////////////////////////////////////////////////
                        // 🔔 MENSAJE
                        //////////////////////////////////////////////////
                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "✅ Producción guardada"
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
    // ❌ DIALOG ELIMINAR TODO
    //////////////////////////////////////////////////////////
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {

                showDeleteDialog = false
            },

            title = {

                Text("Confirmación")
            },

            text = {

                Text(
                    "¿Eliminar toda la producción?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        db.collection("productos")
                            .get()
                            .addOnSuccessListener {

                                    snapshot ->

                                snapshot.documents.forEach {

                                    db.collection("productos")
                                        .document(it.id)
                                        .delete()
                                }
                            }

                        showDeleteDialog = false

                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "🗑️ Producción eliminada"
                            )
                        }
                    }
                ) {

                    Text("Aceptar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showDeleteDialog = false
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}

//////////////////////////////////////////////////////////////
// 🍞 CARD STOCK
//////////////////////////////////////////////////////////////
@Composable
fun ProductoCardPro(

    producto: ProductoStock
) {

    //////////////////////////////////////////////////////////
    // 🎨 COLOR
    //////////////////////////////////////////////////////////
    val color = when {

        producto.latas > 50 ->
            Color(0xFF4CAF50)

        producto.latas > 20 ->
            Color(0xFFFFA000)

        else ->
            Color(0xFFD32F2F)
    }

    //////////////////////////////////////////////////////////
    // 🪪 CARD
    //////////////////////////////////////////////////////////
    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F3EF)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(

            modifier = Modifier.padding(18.dp)
        ) {

            //////////////////////////////////////////////////
            // 📛 NOMBRE
            //////////////////////////////////////////////////
            Text(

                text = producto.nombre,

                fontWeight = FontWeight.Bold,

                fontSize = 20.sp,

                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            //////////////////////////////////////////////////
            // 🔢 LATAS
            //////////////////////////////////////////////////
            Text(

                text = "${producto.latas} latas",

                color = color,

                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            //////////////////////////////////////////////////
            // 📊 PROGRESS
            //////////////////////////////////////////////////
            LinearProgressIndicator(

                progress = {
                    (producto.latas / 100f)
                        .coerceIn(0f, 1f)
                },

                color = color,

                trackColor = Color.LightGray,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
            )
        }
    }
}

//////////////////////////////////////////////////////////////
// 🍞 FORM CARD
//////////////////////////////////////////////////////////////
@Composable
fun ProductoItemCardPro(

    item: ProductoItem,

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
    // 🪪 CARD
    //////////////////////////////////////////////////////////
    Card(

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1ECE8)
        )
    ) {

        Column(

            modifier = Modifier.padding(16.dp)
        ) {

//////////////////////////////////////////////////
// 🔽 PRODUCTO
//////////////////////////////////////////////////
            ExposedDropdownMenuBox(

                expanded = expanded,

                onExpandedChange = {

                    expanded = !expanded
                }
            ) {

                OutlinedTextField(

                    value = item.nombre,

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

                //////////////////////////////////////////////////
                // 🔽 MENÚ
                //////////////////////////////////////////////////
                ExposedDropdownMenu(

                    expanded = expanded,

                    onDismissRequest = {

                        expanded = false
                    }
                ) {

                    catalogo.forEach {

                        DropdownMenuItem(

                            text = {
                                Text(it)
                            },

                            onClick = {

                                item.nombre = it

                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //////////////////////////////////////////////////
            // 🔢 CONTROLES
            //////////////////////////////////////////////////
            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.Center,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                //////////////////////////////////////////////////
                // ➖
                //////////////////////////////////////////////////
                HoldButton(

                    text = "-",

                    color = Color.Gray,

                    onClick = {

                        if (item.latas > 1) {

                            item.latas--
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.width(20.dp)
                )

                //////////////////////////////////////////////////
                // 🔢 CANTIDAD
                //////////////////////////////////////////////////
                Text(

                    text = item.latas.toString(),

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4E342E)
                )

                Spacer(
                    modifier = Modifier.width(20.dp)
                )

                //////////////////////////////////////////////////
                // ➕
                //////////////////////////////////////////////////
                HoldButton(

                    text = "+",

                    color = Color(0xFF6D4C41),

                    onClick = {

                        item.latas++
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //////////////////////////////////////////////////
            // ❌ ELIMINAR
            //////////////////////////////////////////////////
            TextButton(

                onClick = onRemove
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Delete,

                    contentDescription = null,

                    tint = Color.Red
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(

                    text = "Eliminar",

                    color = Color.Red
                )
            }
        }
    }
}
