package com.example.panaderiamartinez

//////////////////////////////////////////////////////////////
// 🔥 IMPORTS
//////////////////////////////////////////////////////////////

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Inventory2

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.launch

//////////////////////////////////////////////////////////////
// 🟣 MODELO PEDIDO
//////////////////////////////////////////////////////////////

data class Pedido(

    //////////////////////////////////////////////////////////
    // 🆔 ID
    //////////////////////////////////////////////////////////
    val id: String = "",

    //////////////////////////////////////////////////////////
    // 👤 CLIENTE
    //////////////////////////////////////////////////////////
    val cliente: String = "",

    //////////////////////////////////////////////////////////
    // 📦 ESTADO
    //////////////////////////////////////////////////////////
    val estado: String = "",

    //////////////////////////////////////////////////////////
    // 📅 FECHA
    //////////////////////////////////////////////////////////
    val fechaEntrega: String = ""
)

//////////////////////////////////////////////////////////////
// 🟣 MODELO ITEM
//////////////////////////////////////////////////////////////

data class PedidoItemData(

    //////////////////////////////////////////////////////////
    // 🍞 PRODUCTO
    //////////////////////////////////////////////////////////
    val producto: String = "",

    //////////////////////////////////////////////////////////
    // 🔢 CANTIDAD
    //////////////////////////////////////////////////////////
    val cantidad: Int = 0,

    //////////////////////////////////////////////////////////
    // 💲 PRECIO
    //////////////////////////////////////////////////////////
    val precio: String = ""
)

//////////////////////////////////////////////////////////////
// 🟣 VER PEDIDOS SCREEN PRO
//////////////////////////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerPedidosScreen(

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
    // 📦 PEDIDOS
    //////////////////////////////////////////////////////////
    var pedidos by remember {
        mutableStateOf(listOf<Pedido>())
    }

    //////////////////////////////////////////////////////////
    // 📦 ITEMS MAP
    //////////////////////////////////////////////////////////
    val itemsMap =
        remember {
            mutableStateMapOf<String, List<PedidoItemData>>()
        }

    //////////////////////////////////////////////////////////
    // 🔍 BUSCADOR
    //////////////////////////////////////////////////////////
    var searchText by remember {
        mutableStateOf("")
    }

    //////////////////////////////////////////////////////////////
// ⚠️ CONFIRMAR ENTREGA
//////////////////////////////////////////////////////////////
    var pedidoSeleccionado by remember {
        mutableStateOf<Pedido?>(null)
    }

//////////////////////////////////////////////////////////////
// 🚫 CANCELAR PEDIDO
//////////////////////////////////////////////////////////////
    var pedidoCancelar by remember {

        mutableStateOf<Pedido?>(null)
    }

    //////////////////////////////////////////////////////////
    // ⚠️ ELIMINAR DÍA
    //////////////////////////////////////////////////////////
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    //////////////////////////////////////////////////////////
    // 🔄 REALTIME
    //////////////////////////////////////////////////////////
    LaunchedEffect(Unit) {

        db.collection("pedidos")

            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {

                    val lista =
                        mutableListOf<Pedido>()

                    snapshot.documents.forEach { doc ->

                        //////////////////////////////////////////////////
                        // 📦 PEDIDO
                        //////////////////////////////////////////////////
                        val pedido = Pedido(

                            id = doc.id,

                            cliente =
                                doc.getString("cliente")
                                    ?: "",

                            estado =
                                doc.getString("estado")
                                    ?: "",

                            fechaEntrega =
                                doc.getString("fechaEntrega")
                                    ?: ""
                        )

                        lista.add(pedido)

                        //////////////////////////////////////////////////
                        // 📦 ITEMS
                        //////////////////////////////////////////////////
                        doc.reference
                            .collection("items")
                            .get()

                            .addOnSuccessListener { itemsSnap ->

                                val listaItems =
                                    itemsSnap.documents.map {

                                        PedidoItemData(

                                            producto =
                                                it.getString("producto")
                                                    ?: "",

                                            cantidad =
                                                it.getLong("cantidad")
                                                    ?.toInt()
                                                    ?: 0,

                                            precio =
                                                it.getString("precio")
                                                    ?: ""
                                        )
                                    }

                                itemsMap[doc.id] =
                                    listaItems
                            }
                    }

                    //////////////////////////////////////////////////////
                    // 🔥 ORDENAR
                    //////////////////////////////////////////////////////
                    pedidos =
                        lista.sortedBy {

                            it.estado == "Entregado"
                        }
                }
            }
    }

    //////////////////////////////////////////////////////////
    // 🔍 FILTRAR
    //////////////////////////////////////////////////////////
    val pedidosFiltrados = pedidos.filter {

        it.cliente.contains(
            searchText,
            ignoreCase = true
        )
    }

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

                selected = "verPedidos",

                onInicio = onInicio,

                onInsumos = onInsumos,

                onProductos = onProductos,

                onPedidos = onPedidos,

                onVerPedidos = onVerPedidos,

                rol = rol
            )
        },

        //////////////////////////////////////////////////////
        // 🗑 FAB
        //////////////////////////////////////////////////////
        floatingActionButton = {

            if (rol == "Administrador") {

                FloatingActionButton(

                    onClick = {

                        showDeleteDialog = true
                    },

                    containerColor = Color.Red
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription = null,

                        tint = Color.White
                    )
                }
            }
        },

        //////////////////////////////////////////////////////
        // 🎨 COLOR
        //////////////////////////////////////////////////////
        containerColor = Color(0xFFF5E0C3)

    ) { paddingValues ->

        //////////////////////////////////////////////////////
        // 🎨 CONTENIDO
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

                        text = "Ver pedidos",

                        fontSize = 32.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4E342E)
                    )

                    Text(

                        text =
                            "Pedidos pendientes y entregados",

                        color = Color.Gray,

                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

//////////////////////////////////////////////////
// 🔍 BUSCADOR
//////////////////////////////////////////////////
            if (rol == "Administrador") {

                OutlinedTextField(

                    value = searchText,

                    onValueChange = {

                        searchText = it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    leadingIcon = {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    },

                    placeholder = {

                        Text("Buscar cliente...")
                    },

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

                Spacer(
                    modifier = Modifier.height(18.dp)
                )
            }

            //////////////////////////////////////////////////
            // 👨‍💼 ADMIN
            //////////////////////////////////////////////////
            if (rol == "Administrador") {

                LazyColumn {

                    items(pedidosFiltrados) { pedido ->

                        //////////////////////////////////////////////////
                        // 📦 ITEMS PEDIDO
                        //////////////////////////////////////////////////
                        val itemsPedido =
                            itemsMap[pedido.id]
                                ?: emptyList()

                        //////////////////////////////////////////////////
                        // 🎨 CARD
                        //////////////////////////////////////////////////
                        Card(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp),

                            shape =
                                RoundedCornerShape(24.dp),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White
                                )
                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(18.dp)
                            ) {

                                //////////////////////////////////////////////////
                                // 👤 CLIENTE
                                //////////////////////////////////////////////////
                                Text(

                                    text = pedido.cliente,

                                    fontSize = 24.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = Color(0xFF4E342E)
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                //////////////////////////////////////////////////
                                // 📅 FECHA
                                //////////////////////////////////////////////////
                                Text(
                                    "Entrega: ${pedido.fechaEntrega}"
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                //////////////////////////////////////////////////
                                // 📦 ESTADO
                                //////////////////////////////////////////////////
                                val estadoColor =

                                    if (
                                        pedido.estado == "Entregado"
                                    )
                                        Color(0xFF4CAF50)

                                    else
                                        Color(0xFFFF9800)

                                Text(

                                    text = pedido.estado,

                                    color = estadoColor,

                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                //////////////////////////////////////////////////
                                // 📦 ITEMS
                                //////////////////////////////////////////////////
                                itemsPedido.forEach { item ->

                                    Card(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp),

                                        shape =
                                            RoundedCornerShape(18.dp),

                                        colors =
                                            CardDefaults.cardColors(
                                                containerColor =
                                                    Color(0xFFF7F2EE)
                                            )
                                    ) {

                                        Column(

                                            modifier =
                                                Modifier.padding(14.dp)
                                        ) {

                                            Text(

                                                text = item.producto,

                                                fontSize = 19.sp,

                                                fontWeight = FontWeight.Bold
                                            )

                                            Spacer(
                                                modifier = Modifier.height(4.dp)
                                            )

                                            Text(
                                                "${item.cantidad} latas"
                                            )

                                            Text(
                                                "Precio: $${item.precio}"
                                            )
                                        }
                                    }
                                }

                                //////////////////////////////////////////////////////////////
// 🚚 ENTREGAR
//////////////////////////////////////////////////////////////
                                if (
                                    pedido.estado != "Entregado"
                                ) {

                                    Spacer(
                                        modifier = Modifier.height(12.dp)
                                    )

                                    //////////////////////////////////////////////////////////
                                    // ✅ ENTREGAR
                                    //////////////////////////////////////////////////////////
                                    Button(

                                        onClick = {

                                            pedidoSeleccionado =
                                                pedido
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
                                            "Marcar como entregado"
                                        )
                                    }

                                    //////////////////////////////////////////////////////////
                                    // 🚫 CANCELAR PEDIDO
                                    //////////////////////////////////////////////////////////
                                    Spacer(
                                        modifier = Modifier.height(10.dp)
                                    )

                                    OutlinedButton(

                                        onClick = {

                                            pedidoCancelar = pedido
                                        },

                                        modifier =
                                            Modifier.fillMaxWidth(),

                                        colors =
                                            ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color.Red
                                            )
                                    ) {

                                        Text(
                                            "Cancelar pedido"
                                        )
                                    }
                                }
                            }
                        }
                    }

                    //////////////////////////////////////////////////////
                    // 🔥 ESPACIO EXTRA
                    //////////////////////////////////////////////////////
                    item {

                        Spacer(
                            modifier = Modifier.height(120.dp)
                        )
                    }
                }
            }

            //////////////////////////////////////////////////
            // 👷 EMPLEADO
            //////////////////////////////////////////////////
            else {

                //////////////////////////////////////////////////////
                // 📦 TODOS ITEMS
                //////////////////////////////////////////////////////
                val todosItems =
                    itemsMap.values.flatten()

                //////////////////////////////////////////////////////
                // 📦 AGRUPAR
                //////////////////////////////////////////////////////
                val agrupados =

                    todosItems.groupBy {

                        it.producto

                    }.mapValues {

                        it.value.sumOf { item ->

                            item.cantidad
                        }
                    }

                LazyColumn {

                    items(
                        agrupados.toList()
                    ) { item ->

                        val producto =
                            item.first

                        val total =
                            item.second

                        //////////////////////////////////////////////////
                        // 🎨 CARD
                        //////////////////////////////////////////////////
                        Card(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp),

                            shape =
                                RoundedCornerShape(22.dp),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White
                                )
                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(18.dp)
                            ) {

                                Text(

                                    text = producto,

                                    fontSize = 22.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = Color(0xFF4E342E)
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(

                                    text = "$total latas",

                                    fontSize = 18.sp,

                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    //////////////////////////////////////////////////////
                    // 🔥 ESPACIO EXTRA
                    //////////////////////////////////////////////////////
                    item {

                        Spacer(
                            modifier = Modifier.height(120.dp)
                        )
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////
    // ⚠️ CONFIRMAR ENTREGA
    //////////////////////////////////////////////////////////
    if (pedidoSeleccionado != null) {

        AlertDialog(

            onDismissRequest = {

                pedidoSeleccionado = null
            },

            title = {

                Text("Confirmar entrega")
            },

            text = {

                Text(
                    "¿Seguro que deseas marcar este pedido como entregado?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        val pedido =
                            pedidoSeleccionado!!

                        //////////////////////////////////////////////////
                        // 🔥 ACTUALIZAR ESTADO
                        //////////////////////////////////////////////////
                        db.collection("pedidos")
                            .document(pedido.id)
                            .update(
                                "estado",
                                "Entregado"
                            )

                        //////////////////////////////////////////////////
                        // 🔥 DESCONTAR STOCK
                        //////////////////////////////////////////////////
                        db.collection("pedidos")
                            .document(pedido.id)
                            .collection("items")
                            .get()

                            .addOnSuccessListener { itemsSnap ->

                                itemsSnap.documents.forEach { item ->

                                    //////////////////////////////////////////////////
                                    // 🍞 PRODUCTO
                                    //////////////////////////////////////////////////
                                    val producto =
                                        item.getString("producto")
                                            ?: ""

                                    //////////////////////////////////////////////////
                                    // 🔢 CANTIDAD
                                    //////////////////////////////////////////////////
                                    val cantidad =
                                        item.getLong("cantidad")
                                            ?: 0

                                    //////////////////////////////////////////////////
                                    // 🔥 REFERENCIA
                                    //////////////////////////////////////////////////
                                    val ref =
                                        db.collection("productos")
                                            .document(producto)

                                    //////////////////////////////////////////////////
                                    // 🔥 TRANSACTION
                                    //////////////////////////////////////////////////
                                    db.runTransaction {

                                            transaction ->

                                        val snap =
                                            transaction.get(ref)

                                        val actual =
                                            snap.getLong("latas")
                                                ?: 0

                                        val nuevo =
                                            (actual - cantidad)
                                                .coerceAtLeast(0)

                                        transaction.update(

                                            ref,

                                            "latas",

                                            nuevo
                                        )
                                    }
                                }
                            }

                        //////////////////////////////////////////////////
                        // 🔔 MENSAJE
                        //////////////////////////////////////////////////
                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "✅ Pedido entregado correctamente"
                            )
                        }

                        //////////////////////////////////////////////////
                        // ❌ CERRAR
                        //////////////////////////////////////////////////
                        pedidoSeleccionado = null
                    }
                ) {

                    Text("Confirmar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        pedidoSeleccionado = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }

    //////////////////////////////////////////////////////////
    // 🗑 ELIMINAR ENTREGADOS
    //////////////////////////////////////////////////////////
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {

                showDeleteDialog = false
            },

            title = {

                Text("Eliminar pedidos")
            },

            text = {

                Text(
                    "¿Seguro que deseas eliminar los pedidos entregados?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        pedidos.forEach {

                            if (
                                it.estado == "Entregado"
                            ) {

                                db.collection("pedidos")
                                    .document(it.id)
                                    .delete()
                            }
                        }

                        //////////////////////////////////////////////////
                        // 🔔 MENSAJE
                        //////////////////////////////////////////////////
                        scope.launch {

                            snackbarHostState.showSnackbar(
                                "🗑 Pedidos eliminados"
                            )
                        }

                        showDeleteDialog = false
                    }
                ) {

                    Text("Eliminar")
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
    //////////////////////////////////////////////////////////////
// 🚫 CANCELAR PEDIDO
//////////////////////////////////////////////////////////////
    if (pedidoCancelar != null) {

        AlertDialog(

            onDismissRequest = {

                pedidoCancelar = null
            },

            //////////////////////////////////////////////////////
            // 📝 TÍTULO
            //////////////////////////////////////////////////////
            title = {

                Text("Cancelar pedido")
            },

            //////////////////////////////////////////////////////
            // 📄 MENSAJE
            //////////////////////////////////////////////////////
            text = {

                Text(
                    "¿Deseas cancelar el pedido?"
                )
            },

            //////////////////////////////////////////////////////
            // ✅ CONFIRMAR
            //////////////////////////////////////////////////////
            confirmButton = {

                TextButton(

                    onClick = {

                        //////////////////////////////////////////////////
                        // 📦 PEDIDO
                        //////////////////////////////////////////////////
                        val pedido =
                            pedidoCancelar!!

                        //////////////////////////////////////////////////
                        // 🔥 ELIMINAR ITEMS
                        //////////////////////////////////////////////////
                        db.collection("pedidos")
                            .document(pedido.id)
                            .collection("items")
                            .get()

                            .addOnSuccessListener { itemsSnap ->

                                itemsSnap.documents.forEach {

                                    //////////////////////////////////////////////////
                                    // 🗑 ELIMINAR ITEM
                                    //////////////////////////////////////////////////
                                    it.reference.delete()
                                }

                                //////////////////////////////////////////////////
                                // 🗑 ELIMINAR PEDIDO
                                //////////////////////////////////////////////////
                                db.collection("pedidos")
                                    .document(pedido.id)
                                    .delete()

                                //////////////////////////////////////////////////
                                // 🔔 MENSAJE
                                //////////////////////////////////////////////////
                                scope.launch {

                                    snackbarHostState.showSnackbar(
                                        "🚫 Pedido cancelado"
                                    )
                                }
                            }

                        //////////////////////////////////////////////////
                        // ❌ CERRAR
                        //////////////////////////////////////////////////
                        pedidoCancelar = null
                    }
                ) {

                    Text("Sí, cancelar")
                }
            },

            //////////////////////////////////////////////////////
            // ❌ CANCELAR
            //////////////////////////////////////////////////////
            dismissButton = {

                TextButton(

                    onClick = {

                        pedidoCancelar = null
                    }
                ) {

                    Text("No")
                }
            }
        )
    }
}
