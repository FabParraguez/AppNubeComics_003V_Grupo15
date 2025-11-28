package com.vivitasol.carcasamvvm.model

/**
 * Modelo de datos para un Manga compatible con el microservicio
 * http://localhost:8080/api/mangas
 */
data class Manga(
    val id: Long? = null,
    val titulo: String = "",
    val autor: String = "",
    val anioPublicacion: Int? = null,
    val editorial: String? = null,
    val genero: String? = null,
    val descripcion: String? = null,
    val portadaUrl: String? = null,
    val precio: Double? = null,
    val fechaCreacion: String? = null,
    val fechaActualizacion: String? = null
)
