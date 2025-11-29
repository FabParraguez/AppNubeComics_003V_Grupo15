package com.vivitasol.comics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio con lógica de negocio para Mangas
 */
@Service
public class MangaService {
    
    @Autowired
    private MangaRepository mangaRepository;
    
    // Obtener todos los mangas
    public List<Manga> getAllMangas() {
        return mangaRepository.findAll();
    }
    
    // Obtener manga por ID
    public Optional<Manga> getMangaById(Long id) {
        return mangaRepository.findById(id);
    }
    
    // Crear un nuevo manga
    public Manga createManga(Manga manga) {
        return mangaRepository.save(manga);
    }
    
    // Actualizar un manga existente
    public Manga updateManga(Long id, Manga mangaActualizado) {
        return mangaRepository.findById(id)
            .map(manga -> {
                manga.setTitulo(mangaActualizado.getTitulo());
                manga.setAutor(mangaActualizado.getAutor());
                manga.setAnioPublicacion(mangaActualizado.getAnioPublicacion());
                manga.setEditorial(mangaActualizado.getEditorial());
                manga.setGenero(mangaActualizado.getGenero());
                manga.setDescripcion(mangaActualizado.getDescripcion());
                manga.setPortadaUrl(mangaActualizado.getPortadaUrl());
                manga.setPrecio(mangaActualizado.getPrecio());
                return mangaRepository.save(manga);
            })
            .orElseThrow(() -> new RuntimeException("Manga no encontrado con id: " + id));
    }
    
    // Eliminar un manga
    public void deleteManga(Long id) {
        mangaRepository.deleteById(id);
    }
    
    // Búsqueda por autor
    public List<Manga> searchByAutor(String autor) {
        return mangaRepository.findByAutorContainingIgnoreCase(autor);
    }
    
    // Búsqueda por género
    public List<Manga> searchByGenero(String genero) {
        return mangaRepository.findByGeneroIgnoreCase(genero);
    }
    
    // Búsqueda por título
    public List<Manga> searchByTitulo(String titulo) {
        return mangaRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
