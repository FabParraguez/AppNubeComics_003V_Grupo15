package com.vivitasol.comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para operaciones CRUD de Manga
 */
@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
    
    // Métodos de búsqueda personalizados
    List<Manga> findByAutorContainingIgnoreCase(String autor);
    
    List<Manga> findByGeneroIgnoreCase(String genero);
    
    List<Manga> findByTituloContainingIgnoreCase(String titulo);
}
