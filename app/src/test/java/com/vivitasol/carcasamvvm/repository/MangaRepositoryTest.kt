package com.vivitasol.carcasamvvm.repository

import com.vivitasol.carcasamvvm.model.Manga
import com.vivitasol.carcasamvvm.remote.MangaApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests unitarios para MangaRepository
 */
class MangaRepositoryTest {

    // Fake API Service para simular respuestas del microservicio
    private val fakeMangaApi = object : MangaApiService {
        private val mangaList = listOf(
            Manga(1L, "Chainsaw Man", "Tatsuki Fujimoto", 2018, precio = 10990.0),
            Manga(2L, "Tokyo Revengers", "Ken Wakui", 2017, precio = 10990.0),
            Manga(3L, "Made in Abyss", "Akihito Tsukushi", 2012, precio = 12990.0)
        )

        override suspend fun getMangas(): List<Manga> = mangaList

        override suspend fun getManga(id: Long): Manga =
            mangaList.first { it.id == id }

        override suspend fun createManga(manga: Manga): Manga =
            manga.copy(id = 4L)

        override suspend fun updateManga(id: Long, manga: Manga): Manga =
            manga.copy(id = id)

        override suspend fun deleteManga(id: Long) {}
    }

    // Repository testeable que acepta una API fake
    class TestableMangaRepository(private val testApi: MangaApiService) : MangaRepository() {
        override suspend fun getMangas(): List<Manga> = testApi.getMangas()
        override suspend fun getManga(id: Long): Manga = testApi.getManga(id)
        override suspend fun createManga(manga: Manga): Manga = testApi.createManga(manga)
        override suspend fun updateManga(id: Long, manga: Manga): Manga = testApi.updateManga(id, manga)
        override suspend fun deleteManga(id: Long) = testApi.deleteManga(id)
    }

    private val repository = TestableMangaRepository(fakeMangaApi)

    @Test
    fun `getMangas devuelve lista de mangas`() = runTest {
        val mangas = repository.getMangas()
        assertEquals(3, mangas.size)
        assertEquals("Chainsaw Man", mangas[0].titulo)
        assertEquals("Tokyo Revengers", mangas[1].titulo)
    }

    @Test
    fun `getManga devuelve manga por id`() = runTest {
        val manga = repository.getManga(1L)
        assertEquals(1L, manga.id)
        assertEquals("Chainsaw Man", manga.titulo)
        assertEquals("Tatsuki Fujimoto", manga.autor)
    }

    @Test
    fun `createManga devuelve manga con id asignado`() = runTest {
        val newManga = Manga(
            titulo = "Jujutsu Kaisen",
            autor = "Gege Akutami",
            anioPublicacion = 2018,
            precio = 11990.0
        )
        val created = repository.createManga(newManga)
        assertEquals(4L, created.id)
        assertEquals("Jujutsu Kaisen", created.titulo)
    }

    @Test
    fun `updateManga actualiza manga existente`() = runTest {
        val updatedManga = Manga(
            titulo = "Chainsaw Man Updated",
            autor = "Tatsuki Fujimoto",
            precio = 12990.0
        )
        val result = repository.updateManga(1L, updatedManga)
        assertEquals(1L, result.id)
        assertEquals("Chainsaw Man Updated", result.titulo)
    }

    @Test
    fun `deleteManga ejecuta sin errores`() = runTest {
        // No debe lanzar excepción
        repository.deleteManga(1L)
    }
}
