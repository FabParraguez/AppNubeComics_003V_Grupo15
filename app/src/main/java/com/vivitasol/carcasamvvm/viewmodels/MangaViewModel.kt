package com.vivitasol.carcasamvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivitasol.carcasamvvm.model.Manga
import com.vivitasol.carcasamvvm.repository.MangaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para operaciones CRUD sobre mangas.
 */
class MangaViewModel(
    private val repository: MangaRepository = MangaRepository()
) : ViewModel() {

    private val _mangas = MutableStateFlow<List<Manga>>(emptyList())
    val mangas: StateFlow<List<Manga>> = _mangas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _operationSuccess = MutableStateFlow<String?>(null)
    val operationSuccess: StateFlow<String?> = _operationSuccess

    init {
        fetchMangas()
    }

    fun fetchMangas() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getMangas()
                _mangas.value = result
            } catch (e: Exception) {
                _error.value = "Error al cargar mangas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createManga(manga: Manga) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _operationSuccess.value = null
            try {
                repository.createManga(manga)
                _operationSuccess.value = "Manga creado exitosamente"
                fetchMangas() // Recargar lista
            } catch (e: Exception) {
                _error.value = "Error al crear manga: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateManga(id: Long, manga: Manga) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _operationSuccess.value = null
            try {
                repository.updateManga(id, manga)
                _operationSuccess.value = "Manga actualizado exitosamente"
                fetchMangas() // Recargar lista
            } catch (e: Exception) {
                _error.value = "Error al actualizar manga: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteManga(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _operationSuccess.value = null
            try {
                repository.deleteManga(id)
                _operationSuccess.value = "Manga eliminado exitosamente"
                fetchMangas() // Recargar lista
            } catch (e: Exception) {
                _error.value = "Error al eliminar manga: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessages() {
        _error.value = null
        _operationSuccess.value = null
    }
}
