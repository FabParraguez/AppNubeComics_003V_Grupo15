package com.vivitasol.carcasamvvm.viewmodels

import com.vivitasol.carcasamvvm.model.Manga
import com.vivitasol.carcasamvvm.repository.MangaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para MangaViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MangaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Repositorio fake para éxito
    class FakeSuccessMangaRepository : MangaRepository() {
        override suspend fun getMangas(): List<Manga> = listOf(
            Manga(1L, "Chainsaw Man", "Tatsuki Fujimoto", 2018, precio = 10990.0),
            Manga(2L, "Tokyo Revengers", "Ken Wakui", 2017, precio = 10990.0)
        )

        override suspend fun getManga(id: Long): Manga =
            Manga(1L, "Chainsaw Man", "Tatsuki Fujimoto", 2018, precio = 10990.0)

        override suspend fun createManga(manga: Manga): Manga =
            manga.copy(id = 3L)

        override suspend fun updateManga(id: Long, manga: Manga): Manga =
            manga.copy(id = id)

        override suspend fun deleteManga(id: Long) {}
    }

    // Repositorio fake para error
    class FakeErrorMangaRepository : MangaRepository() {
        override suspend fun getMangas(): List<Manga> {
            throw Exception("Error de conexión")
        }

        override suspend fun getManga(id: Long): Manga {
            throw Exception("Manga no encontrado")
        }

        override suspend fun createManga(manga: Manga): Manga {
            throw Exception("Error al crear")
        }

        override suspend fun updateManga(id: Long, manga: Manga): Manga {
            throw Exception("Error al actualizar")
        }

        override suspend fun deleteManga(id: Long) {
            throw Exception("Error al eliminar")
        }
    }

    @Test
    fun `fetchMangas carga lista correctamente`() = runTest {
        val viewModel = MangaViewModel(FakeSuccessMangaRepository())
        
        // Avanzar tiempo para que se complete la coroutine
        advanceUntilIdle()

        val mangas = viewModel.mangas.value
        assertEquals(2, mangas.size)
        assertEquals("Chainsaw Man", mangas[0].titulo)
        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `fetchMangas maneja error correctamente`() = runTest {
        val viewModel = MangaViewModel(FakeErrorMangaRepository())
        
        advanceUntilIdle()

        assertTrue(viewModel.mangas.value.isEmpty())
        assertEquals(false, viewModel.isLoading.value)
        assertNotNull(viewModel.error.value)
        assertTrue(viewModel.error.value!!.contains("Error al cargar mangas"))
    }

    @Test
    fun `createManga ejecuta correctamente y recarga lista`() = runTest {
        val viewModel = MangaViewModel(FakeSuccessMangaRepository())
        
        advanceUntilIdle() // Esperar carga inicial
        
        val newManga = Manga(
            titulo = "Jujutsu Kaisen",
            autor = "Gege Akutami",
            precio = 11990.0
        )
        
        viewModel.createManga(newManga)
        advanceUntilIdle()

        assertEquals("Manga creado exitosamente", viewModel.operationSuccess.value)
        assertEquals(2, viewModel.mangas.value.size) // Lista recargada
    }

    @Test
    fun `createManga maneja error`() = runTest {
        val viewModel = MangaViewModel(FakeErrorMangaRepository())
        
        advanceUntilIdle()
        
        val newManga = Manga(titulo = "Test", autor = "Test")
        viewModel.createManga(newManga)
        advanceUntilIdle()

        assertNotNull(viewModel.error.value)
        assertTrue(viewModel.error.value!!.contains("Error al crear manga"))
    }

    @Test
    fun `updateManga ejecuta correctamente`() = runTest {
        val viewModel = MangaViewModel(FakeSuccessMangaRepository())
        
        advanceUntilIdle()
        
        val updatedManga = Manga(titulo = "Updated", autor = "Test")
        viewModel.updateManga(1L, updatedManga)
        advanceUntilIdle()

        assertEquals("Manga actualizado exitosamente", viewModel.operationSuccess.value)
    }

    @Test
    fun `updateManga maneja error`() = runTest {
        val viewModel = MangaViewModel(FakeErrorMangaRepository())
        
        advanceUntilIdle()
        
        val updatedManga = Manga(titulo = "Updated", autor = "Test")
        viewModel.updateManga(1L, updatedManga)
        advanceUntilIdle()

        assertTrue(viewModel.error.value!!.contains("Error al actualizar manga"))
    }

    @Test
    fun `deleteManga ejecuta correctamente`() = runTest {
        val viewModel = MangaViewModel(FakeSuccessMangaRepository())
        
        advanceUntilIdle()
        
        viewModel.deleteManga(1L)
        advanceUntilIdle()

        assertEquals("Manga eliminado exitosamente", viewModel.operationSuccess.value)
    }

    @Test
    fun `deleteManga maneja error`() = runTest {
        val viewModel = MangaViewModel(FakeErrorMangaRepository())
        
        advanceUntilIdle()
        
        viewModel.deleteManga(1L)
        advanceUntilIdle()

        assertTrue(viewModel.error.value!!.contains("Error al eliminar manga"))
    }

    @Test
    fun `clearMessages limpia errores y mensajes de éxito`() = runTest {
        val viewModel = MangaViewModel(FakeErrorMangaRepository())
        
        advanceUntilIdle()
        
        assertNotNull(viewModel.error.value)
        
        viewModel.clearMessages()
        
        assertNull(viewModel.error.value)
        assertNull(viewModel.operationSuccess.value)
    }
}
