package com.vivitasol.carcasamvvm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vivitasol.carcasamvvm.model.Manga
import com.vivitasol.carcasamvvm.viewmodels.MangaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    vm: MangaViewModel = viewModel()
) {
    val mangas by vm.mangas.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.error.collectAsState()
    val operationSuccess by vm.operationSuccess.collectAsState()
    
    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedManga by remember { mutableStateOf<Manga?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Mangas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, "Crear manga")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mensajes de error/éxito
            error?.let {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            operationSuccess?.let {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)
                    vm.clearMessages()
                }
            }

            // Contenido principal
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Cargando mangas...")
                        }
                    }
                }
                mangas.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay mangas disponibles")
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(mangas) { manga ->
                            MangaCard(
                                manga = manga,
                                onEdit = {
                                    selectedManga = manga
                                    showEditDialog = true
                                },
                                onDelete = { manga.id?.let { vm.deleteManga(it) } }
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo para crear manga
    if (showCreateDialog) {
        MangaFormDialog(
            title = "Crear Manga",
            onDismiss = { showCreateDialog = false },
            onConfirm = { manga ->
                vm.createManga(manga)
                showCreateDialog = false
            }
        )
    }

    // Diálogo para editar manga
    if (showEditDialog && selectedManga != null) {
        MangaFormDialog(
            title = "Editar Manga",
            initialManga = selectedManga,
            onDismiss = {
                showEditDialog = false
                selectedManga = null
            },
            onConfirm = { manga ->
                selectedManga?.id?.let { id ->
                    vm.updateManga(id, manga)
                }
                showEditDialog = false
                selectedManga = null
            }
        )
    }
}

@Composable
fun MangaCard(
    manga: Manga,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = manga.titulo,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Autor: ${manga.autor}",
                style = MaterialTheme.typography.bodyMedium
            )
            manga.genero?.let {
                Text(
                    text = "Género: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            manga.precio?.let {
                Text(
                    text = "Precio: $$it",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            manga.descripcion?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaFormDialog(
    title: String,
    initialManga: Manga? = null,
    onDismiss: () -> Unit,
    onConfirm: (Manga) -> Unit
) {
    var titulo by remember { mutableStateOf(initialManga?.titulo ?: "") }
    var autor by remember { mutableStateOf(initialManga?.autor ?: "") }
    var anio by remember { mutableStateOf(initialManga?.anioPublicacion?.toString() ?: "") }
    var editorial by remember { mutableStateOf(initialManga?.editorial ?: "") }
    var genero by remember { mutableStateOf(initialManga?.genero ?: "") }
    var descripcion by remember { mutableStateOf(initialManga?.descripcion ?: "") }
    var precio by remember { mutableStateOf(initialManga?.precio?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Autor *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = anio,
                    onValueChange = { anio = it },
                    label = { Text("Año de publicación") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = editorial,
                    onValueChange = { editorial = it },
                    label = { Text("Editorial") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.isNotBlank() && autor.isNotBlank()) {
                        val manga = Manga(
                            id = initialManga?.id,
                            titulo = titulo,
                            autor = autor,
                            anioPublicacion = anio.toIntOrNull(),
                            editorial = editorial.ifBlank { null },
                            genero = genero.ifBlank { null },
                            descripcion = descripcion.ifBlank { null },
                            precio = precio.toDoubleOrNull()
                        )
                        onConfirm(manga)
                    }
                },
                enabled = titulo.isNotBlank() && autor.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
