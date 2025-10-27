package com.vivitasol.carcasamvvm.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Option2ViewModel : ViewModel() {
    // Lista de mangas actualizados según las imágenes disponibles
    private val _items = MutableStateFlow(listOf(
        "Chainsaw Man",
        "Heaven Officials Blessing",
        "JoJo's Bizarre Adventure: Phantom Blood",
        "Kaguya-sama: Love is War",
        "Made in Abyss",
        "Tokyo Revengers",
        "Yona of the Dawn"
    ))
    val items: StateFlow<List<String>> = _items
}
