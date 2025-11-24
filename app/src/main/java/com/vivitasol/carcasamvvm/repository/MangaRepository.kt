package com.vivitasol.carcasamvvm.repository

import com.vivitasol.carcasamvvm.model.Manga
import com.vivitasol.carcasamvvm.remote.MangaApiService
import com.vivitasol.carcasamvvm.remote.MangaRetrofitInstance

/**
 * Repositorio para operaciones CRUD sobre mangas usando el microservicio.
 */
open class MangaRepository(
    private val api: MangaApiService = MangaRetrofitInstance.api
) {
    open suspend fun getMangas(): List<Manga> = api.getMangas()

    open suspend fun getManga(id: Long): Manga = api.getManga(id)

    open suspend fun createManga(manga: Manga): Manga = api.createManga(manga)

    open suspend fun updateManga(id: Long, manga: Manga): Manga = api.updateManga(id, manga)

    open suspend fun deleteManga(id: Long) = api.deleteManga(id)
}
