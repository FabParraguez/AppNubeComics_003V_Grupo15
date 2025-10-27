package com.vivitasol.carcasamvvm.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FormState(
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val editorial: String = "",
    val autorizar: Boolean = false,
    val anunciar: Boolean = false
)

data class FormErrors(
    val nombre: String? = null,
    val descripcion: String? = null,
    val precio: String? = null,
    val editorial: String? = null,
    val autorizar: String? = null
)

class Option3ViewModel : ViewModel() {
    private val _state = MutableStateFlow(FormState())
    val state: StateFlow<FormState> = _state

    private val _errors = MutableStateFlow(FormErrors())
    val errors: StateFlow<FormErrors> = _errors

    private val editorialesValidas = listOf("Norma", "Ivrea", "Planeta@")
    val editoriales: List<String> = editorialesValidas

    fun onNombreChange(v: String) { _state.value = _state.value.copy(nombre = v) }
    fun onDescripcionChange(v: String) { _state.value = _state.value.copy(descripcion = v) }
    fun onPrecioChange(v: String) { _state.value = _state.value.copy(precio = v) }
    fun onEditorialChange(v: String) { _state.value = _state.value.copy(editorial = v) }
    fun onTerminosChange(v: Boolean) { _state.value = _state.value.copy(autorizar = v) }
    fun onSuscripcionChange(v: Boolean) { _state.value = _state.value.copy(anunciar = v) }

    fun validate(): Boolean {
        val s = _state.value
        var ok = true

        val nombreErr = if (s.nombre.isBlank()) "Obligatorio" else null
        val descripcionErr = if (s.descripcion.isBlank()) "Obligatorio" else null
        val precioErr = when {
            s.precio.isBlank() -> "Obligatorio"
            s.precio.toIntOrNull() == null -> "Debe ser número"
            s.precio.toInt() !in 1..50000 -> "Rango 1-50000"
            else -> null
        }
        val editorialErr = if (s.editorial.isBlank() || s.editorial !in editorialesValidas) "Selecciona una editorial" else null
        val autorizarErr = if (!s.autorizar) "Debes autorizar la subida del producto" else null

        _errors.value = FormErrors(nombreErr, descripcionErr, precioErr, editorialErr, autorizarErr)
        ok = listOf(nombreErr, descripcionErr, precioErr, editorialErr, autorizarErr).all { it == null }
        return ok
    }

    fun reset() {
        _state.value = FormState()
        _errors.value = FormErrors()
    }
}
