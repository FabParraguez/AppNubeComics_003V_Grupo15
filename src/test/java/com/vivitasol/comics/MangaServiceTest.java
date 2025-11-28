package com.vivitasol.comics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MangaServiceTest {

    @Mock
    private MangaRepository mangaRepository;

    @InjectMocks
    private MangaService mangaService;

    private Manga testManga;

    @BeforeEach
    void setUp() {
        testManga = new Manga();
        testManga.setId(1L);
        testManga.setTitulo("Chainsaw Man");
        testManga.setAutor("Tatsuki Fujimoto");
        testManga.setAnioPublicacion(2018);
        testManga.setGenero("Shonen");
        testManga.setPrecio(10990.0);
    }

    @Test
    void getAllMangas_DeberiaRetornarListaDeMangas() {
        // Arrange
        Manga manga2 = new Manga();
        manga2.setId(2L);
        manga2.setTitulo("Tokyo Revengers");
        manga2.setAutor("Ken Wakui");
        
        List<Manga> expectedMangas = Arrays.asList(testManga, manga2);
        when(mangaRepository.findAll()).thenReturn(expectedMangas);

        // Act
        List<Manga> result = mangaService.getAllMangas();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Chainsaw Man", result.get(0).getTitulo());
        verify(mangaRepository, times(1)).findAll();
    }

    @Test
    void getMangaById_CuandoExiste_DeberiaRetornarManga() {
        // Arrange
        when(mangaRepository.findById(1L)).thenReturn(Optional.of(testManga));

        // Act
        Optional<Manga> result = mangaService.getMangaById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Chainsaw Man", result.get().getTitulo());
        verify(mangaRepository, times(1)).findById(1L);
    }

    @Test
    void getMangaById_CuandoNoExiste_DeberiaRetornarEmpty() {
        // Arrange
        when(mangaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Manga> result = mangaService.getMangaById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(mangaRepository, times(1)).findById(999L);
    }

    @Test
    void createManga_DeberiaGuardarYRetornarManga() {
        // Arrange
        when(mangaRepository.save(any(Manga.class))).thenReturn(testManga);

        // Act
        Manga result = mangaService.createManga(testManga);

        // Assert
        assertNotNull(result);
        assertEquals("Chainsaw Man", result.getTitulo());
        verify(mangaRepository, times(1)).save(testManga);
    }

    @Test
    void updateManga_CuandoExiste_DeberiaActualizarYRetornar() {
        // Arrange
        Manga mangaActualizado = new Manga();
        mangaActualizado.setTitulo("Chainsaw Man Updated");
        mangaActualizado.setAutor("Tatsuki Fujimoto");
        mangaActualizado.setPrecio(12990.0);

        when(mangaRepository.findById(1L)).thenReturn(Optional.of(testManga));
        when(mangaRepository.save(any(Manga.class))).thenReturn(testManga);

        // Act
        Manga result = mangaService.updateManga(1L, mangaActualizado);

        // Assert
        assertNotNull(result);
        verify(mangaRepository, times(1)).findById(1L);
        verify(mangaRepository, times(1)).save(any(Manga.class));
    }

    @Test
    void updateManga_CuandoNoExiste_DeberiaLanzarExcepcion() {
        // Arrange
        Manga mangaActualizado = new Manga();
        when(mangaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            mangaService.updateManga(999L, mangaActualizado);
        });
        verify(mangaRepository, times(1)).findById(999L);
        verify(mangaRepository, never()).save(any(Manga.class));
    }

    @Test
    void deleteManga_DeberiaEliminarManga() {
        // Arrange
        doNothing().when(mangaRepository).deleteById(1L);

        // Act
        mangaService.deleteManga(1L);

        // Assert
        verify(mangaRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchByAutor_DeberiaRetornarMangasPorAutor() {
        // Arrange
        List<Manga> expectedMangas = Arrays.asList(testManga);
        when(mangaRepository.findByAutorContainingIgnoreCase("Fujimoto")).thenReturn(expectedMangas);

        // Act
        List<Manga> result = mangaService.searchByAutor("Fujimoto");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Tatsuki Fujimoto", result.get(0).getAutor());
        verify(mangaRepository, times(1)).findByAutorContainingIgnoreCase("Fujimoto");
    }

    @Test
    void searchByGenero_DeberiaRetornarMangasPorGenero() {
        // Arrange
        List<Manga> expectedMangas = Arrays.asList(testManga);
        when(mangaRepository.findByGeneroIgnoreCase("Shonen")).thenReturn(expectedMangas);

        // Act
        List<Manga> result = mangaService.searchByGenero("Shonen");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Shonen", result.get(0).getGenero());
        verify(mangaRepository, times(1)).findByGeneroIgnoreCase("Shonen");
    }

    @Test
    void searchByTitulo_DeberiaRetornarMangasPorTitulo() {
        // Arrange
        List<Manga> expectedMangas = Arrays.asList(testManga);
        when(mangaRepository.findByTituloContainingIgnoreCase("Chainsaw")).thenReturn(expectedMangas);

        // Act
        List<Manga> result = mangaService.searchByTitulo("Chainsaw");

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTitulo().contains("Chainsaw"));
        verify(mangaRepository, times(1)).findByTituloContainingIgnoreCase("Chainsaw");
    }
}