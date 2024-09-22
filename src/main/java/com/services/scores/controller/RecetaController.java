package com.services.scores.controller;

import com.services.scores.entity.Receta;
import com.services.scores.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController
{
    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public List<Receta> obtenerTodasRecetas()
    {
        return recetaService.obtenerTodasRecetas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Long id)
    {
        return recetaService.obtenerRecetaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receta> crearReceta(@Valid @RequestBody Receta receta)
    {
        Receta nuevaReceta = recetaService.crearReceta(receta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Long id, @Valid @RequestBody Receta recetaActualizada)
    {
        return ResponseEntity.ok(recetaService.actualizarReceta(id, recetaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id)
    {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();  // Devuelve 204 No Content
    }

    @PostMapping("/{id}/votar/{tipoVoto}")
    public ResponseEntity<Receta> votarReceta(@PathVariable Long id, @PathVariable String tipoVoto)
    {
        Receta recetaVotada = recetaService.votarReceta(id, tipoVoto);  // Invoca el servicio
        return ResponseEntity.ok(recetaVotada);  // Devuelve la receta actualizada con código 200 OK
    }
}

