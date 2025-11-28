package com.vivitasol.carcasamvvm.remote

import com.vivitasol.carcasamvvm.model.Manga
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz Retrofit para el microservicio de Mangas
 * Endpoints: http://localhost:8080/api/mangas
 */
interface MangaApiService {
    @GET("mangas")
    suspend fun getMangas(): List<Manga>

    @GET("mangas/{id}")
    suspend fun getManga(@Path("id") id: Long): Manga

    @POST("mangas")
    suspend fun createManga(@Body manga: Manga): Manga

    @PUT("mangas/{id}")
    suspend fun updateManga(@Path("id") id: Long, @Body manga: Manga): Manga

    @DELETE("mangas/{id}")
    suspend fun deleteManga(@Path("id") id: Long)
}
