package com.vivitasol.carcasamvvm.model

/**
 * Modelo de datos para un Post compatible con
 * https://jsonplaceholder.typicode.com/posts
 */
data class Post(
    val userId: Int? = null,
    val id: Int? = null,
    val title: String = "",
    val body: String = ""
)
