package com.vivitasol.carcasamvvm.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vivitasol.carcasamvvm.viewmodels.Option3ViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Option3View(
    vm: Option3ViewModel = viewModel()
) {
    val state = vm.state.collectAsState().value
    val errors = vm.errors.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var editorialMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Ingresar Producto", style = MaterialTheme.typography.titleLarge)

            // NOMBRE
            OutlinedTextField(
                value = state.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre completo *") },
                isError = errors.nombre != null,
                supportingText = { if (errors.nombre != null) Text(errors.nombre!!) },
                modifier = Modifier.fillMaxWidth()
            )

            // DESCRIPCION
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = vm::onDescripcionChange,
                label = { Text("Descripcion *") },
                isError = errors.descripcion != null,
                supportingText = { if (errors.descripcion != null) Text(errors.descripcion!!) },
                modifier = Modifier.fillMaxWidth()
            )

            // PRECIO
            OutlinedTextField(
                value = state.precio,
                onValueChange = vm::onPrecioChange,
                label = { Text("Precio *") },
                isError = errors.precio != null,
                supportingText = { if (errors.precio != null) Text(errors.precio!!) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // EDITORIAL - ExposedDropdown (Material3)
            ExposedDropdownMenuBox(
                expanded = editorialMenuExpanded,
                onExpandedChange = { editorialMenuExpanded = !editorialMenuExpanded }
            ) {
                OutlinedTextField(
                    value = state.editorial,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Editorial *") },
                    isError = errors.editorial != null,
                    supportingText = { if (errors.editorial != null) Text(errors.editorial!!) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = editorialMenuExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = editorialMenuExpanded,
                    onDismissRequest = { editorialMenuExpanded = false }
                ) {
                    vm.editoriales.forEach { editorial ->
                        DropdownMenuItem(
                            text = { Text(editorial) },
                            onClick = {
                                vm.onEditorialChange(editorial)
                                editorialMenuExpanded = false
                            }
                        )
                    }
                }
            }

            // AUTORIZAR SUBIDA DEL PRODUCTO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(
                        checked = state.autorizar,
                        onCheckedChange = vm::onTerminosChange
                    )
                    Column {
                        Text("Autorizar Publicación *")
                        if (errors.autorizar != null) {
                            Text(errors.autorizar!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
                Row {
                    Text("Anunciar")
                    Spacer(Modifier.width(8.dp))
                    Switch(checked = state.anunciar, onCheckedChange = vm::onSuscripcionChange)
                }
            }

            Spacer(Modifier.height(8.dp))

            // BOTONES
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        val ok = vm.validate()
                        if (ok) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Formulario válido. Enviando…")
                            }
                            // Simular envío y limpiar
                            vm.reset()
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Revisa los campos resaltados.")
                            }
                        }
                    }
                ) {
                    Text("Subir")
                }
                OutlinedButton(onClick = { vm.reset() }) {
                    Text("Limpiar")
                }
            }
        }
    }
}

