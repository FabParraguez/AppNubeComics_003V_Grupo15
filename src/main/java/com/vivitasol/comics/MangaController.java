package com.vivitasol.comics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para endpoints de Mangas
 */
@RestController
@RequestMapping("/api/mangas")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (tu app móvil)
public class MangaController {
    
    @Autowired
    private MangaService mangaService;
    
    // GET /api/mangas - Obtener todos los mangas
    @GetMapping
    public ResponseEntity<List<Manga>> getAllMangas() {
        List<Manga> mangas = mangaService.getAllMangas();
        return ResponseEntity.ok(mangas);
    }
    
    // GET /api/mangas/{id} - Obtener un manga por ID
    @GetMapping("/{id}")
    public ResponseEntity<Manga> getMangaById(@PathVariable Long id) {
        return mangaService.getMangaById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/mangas - Crear un nuevo manga
    @PostMapping
    public ResponseEntity<Manga> createManga(@RequestBody Manga manga) {
        Manga nuevoManga = mangaService.createManga(manga);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoManga);
    }
    
    // PUT /api/mangas/{id} - Actualizar un manga existente
    @PutMapping("/{id}")
    public ResponseEntity<Manga> updateManga(@PathVariable Long id, @RequestBody Manga manga) {
        try {
            Manga mangaActualizado = mangaService.updateManga(id, manga);
            return ResponseEntity.ok(mangaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/mangas/{id} - Eliminar un manga
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id) {
        mangaService.deleteManga(id);
        return ResponseEntity.noContent().build();
    }
    
    // GET /api/mangas/search/autor?nombre={nombre} - Buscar por autor
    @GetMapping("/search/autor")
    public ResponseEntity<List<Manga>> searchByAutor(@RequestParam String nombre) {
        List<Manga> mangas = mangaService.searchByAutor(nombre);
        return ResponseEntity.ok(mangas);
    }
    
    // GET /api/mangas/search/genero?tipo={tipo} - Buscar por género
    @GetMapping("/search/genero")
    public ResponseEntity<List<Manga>> searchByGenero(@RequestParam String tipo) {
        List<Manga> mangas = mangaService.searchByGenero(tipo);
        return ResponseEntity.ok(mangas);
    }
    
    // GET /api/mangas/search/titulo?texto={texto} - Buscar por título
    @GetMapping("/search/titulo")
    public ResponseEntity<List<Manga>> searchByTitulo(@RequestParam String texto) {
        List<Manga> mangas = mangaService.searchByTitulo(texto);
        return ResponseEntity.ok(mangas);
    }
}
