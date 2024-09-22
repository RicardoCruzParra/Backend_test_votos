package com.services.scores.service;

import com.services.scores.entity.Receta;
import com.services.scores.repository.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private RecetaService recetaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllRecetas() {
        // Simula la respuesta del repositorio
        when(recetaRepository.findAll()).thenReturn(Arrays.asList(
                new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5),
                new Receta(2L, "Pizza", "Pizza Margarita", Arrays.asList("Harina", "Tomate", "Queso"), "Hornear la pizza", 30, "media", "Chef Pedro", 3)
        ));

        // Llama al servicio
        List<Receta> recetas = recetaService.obtenerTodasRecetas();
        assertEquals(2, recetas.size());
        assertEquals("Tacos", recetas.get(0).getNombre());

        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    void shouldGetRecetaById() {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5);
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        Optional<Receta> foundReceta = recetaService.obtenerRecetaPorId(1L);
        assertTrue(foundReceta.isPresent());
        assertEquals("Tacos", foundReceta.get().getNombre());

        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateNewReceta() {
        Receta receta = new Receta(null, "Pizza", "Pizza Margarita", Arrays.asList("Harina", "Tomate"), "Hornear la pizza", 40, "media", "Chef Pedro", 0);
        when(recetaRepository.save(any(Receta.class))).thenReturn(new Receta(1L, receta.getNombre(), receta.getDescripcion(), receta.getIngredientes(), receta.getInstrucciones(), receta.getTiempoPreparacion(), receta.getDificultad(), receta.getParticipante(), receta.getVotos()));

        Receta createdReceta = recetaService.crearReceta(receta);
        assertNotNull(createdReceta.getId());
        assertEquals("Pizza", createdReceta.getNombre());

        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    void shouldUpdateReceta() {
        Receta receta = new Receta(1L, "Pizza Margarita", "Pizza Margarita clásica", Arrays.asList("Harina", "Tomate", "Queso"), "Hornear", 40, "media", "Chef Pedro", 0);
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        Optional<Receta> updatedReceta = Optional.ofNullable(recetaService.actualizarReceta(1L, receta));
        assertTrue(updatedReceta.isPresent());
        assertEquals("Pizza Margarita", updatedReceta.get().getNombre());

        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    void shouldDeleteReceta() {
        doNothing().when(recetaRepository).deleteById(1L);

        recetaService.eliminarReceta(1L);
        verify(recetaRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldVoteForReceta() {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5);
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        Optional<Receta> votedReceta = Optional.ofNullable((recetaService.votarReceta(1L, "positivo")));  // Valor positivo (+1)
        assertTrue(votedReceta.isPresent());
        assertEquals(6, votedReceta.get().getVotos());  // 5 + 1 = 6

        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    void shouldVoteAgainstReceta() {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5);
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        Optional<Receta> votedReceta = Optional.ofNullable(recetaService.votarReceta(1L, "negativo"));  // Valor negativo (-1)
        assertTrue(votedReceta.isPresent());
        assertEquals(3, votedReceta.get().getVotos());  // 5 - 2 = 3 (porque se resta 2 puntos por voto negativo)

        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(any(Receta.class));
    }
}
