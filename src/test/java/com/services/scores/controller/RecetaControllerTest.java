package com.services.scores.controller;

import com.services.scores.entity.Receta;
import com.services.scores.repository.RecetaRepository;
import com.services.scores.service.RecetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecetaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecetaService recetaService;

    @InjectMocks
    private RecetaController recetaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recetaController).build();
    }

    @Test
    void shouldGetAllRecetas() throws Exception {
        when(recetaService.obtenerTodasRecetas()).thenReturn(Arrays.asList(
                new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 0),
                new Receta(2L, "Paella", "Paella de mariscos", Arrays.asList("Arroz", "Mariscos"), "Cocinar arroz y mariscos", 60, "media", "Chef María", 0)
        ));

        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tacos"))
                .andExpect(jsonPath("$[1].nombre").value("Paella"));

        verify(recetaService, times(1)).obtenerTodasRecetas();
    }

    @Test
    void shouldGetRecetaById() throws Exception {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 0);
        when(recetaService.obtenerRecetaPorId(1L)).thenReturn(Optional.of(receta));

        mockMvc.perform(get("/api/recetas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tacos"));

        verify(recetaService, times(1)).obtenerRecetaPorId(1L);
    }

    @Test
    void shouldCreateNewReceta() throws Exception {
        Receta receta = new Receta(null, "Pizza", "Pizza Margarita", Arrays.asList("Harina", "Tomate"), "Hornear la pizza", 40, "media", "Chef Pedro", 0);
        when(recetaService.crearReceta(any(Receta.class))).thenReturn(new Receta(1L, receta.getNombre(), receta.getDescripcion(), receta.getIngredientes(), receta.getInstrucciones(), receta.getTiempoPreparacion(), receta.getDificultad(), receta.getParticipante(), receta.getVotos()));

        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Pizza\",\"descripcion\":\"Pizza Margarita\",\"ingredientes\":[\"Harina\", \"Tomate\"],\"instrucciones\":\"Hornear la pizza\",\"tiempoPreparacion\":40,\"dificultad\":\"media\",\"participante\":\"Chef Pedro\"}"))
                .andExpect(status().isCreated())  // Cambiar de isOk() a isCreated()
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pizza"));

        verify(recetaService, times(1)).crearReceta(any(Receta.class));
    }

    @Test
    void shouldUpdateReceta() throws Exception {
        Receta updatedReceta = new Receta(1L, "Pizza Margarita", "Pizza Margarita clásica", Arrays.asList("Harina", "Tomate", "Queso"), "Hornear", 40, "media", "Chef Pedro", 0);
        when(recetaService.actualizarReceta(eq(1L), any(Receta.class))).thenReturn((updatedReceta));

        mockMvc.perform(put("/api/recetas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Pizza Margarita\",\"descripcion\":\"Pizza Margarita clásica\",\"ingredientes\":[\"Harina\", \"Tomate\", \"Queso\"],\"instrucciones\":\"Hornear\",\"tiempoPreparacion\":40,\"dificultad\":\"media\",\"participante\":\"Chef Pedro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pizza Margarita"));

        verify(recetaService, times(1)).actualizarReceta(eq(1L), any(Receta.class));
    }

    @Test
    void shouldDeleteReceta() throws Exception {
        doNothing().when(recetaService).eliminarReceta(1L);

        mockMvc.perform(delete("/api/recetas/1"))
                .andExpect(status().isNoContent());  // Cambiar de isOk() a isNoContent()

        verify(recetaService, times(1)).eliminarReceta(1L);
    }

    @Test
    void shouldVotePositivelyForReceta() throws Exception
    {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5);
        receta.setVotos(6);  // Después del voto negativo, el valor de los votos debería ser 6
        when(recetaService.votarReceta(1L, "positivo")).thenReturn(receta);

        mockMvc.perform(post("/api/recetas/1/votar/positivo"))  // Envía el voto como "positivo"
                .andExpect(status().isOk())  // Verifica que devuelve 200 OK
                .andExpect(jsonPath("$.votos").value(6));  // Verifica que los votos han aumentado en 1

        verify(recetaService, times(1)).votarReceta(1L, "positivo");
    }

    @Test
    void shouldVoteNegativelyForReceta() throws Exception
    {
        Receta receta = new Receta(1L, "Tacos", "Tacos de carne", Arrays.asList("Carne", "Tortillas"), "Asar la carne", 20, "baja", "Chef Juan", 5);
        receta.setVotos(3);  // Después del voto negativo, el valor de los votos debería ser 3
        when(recetaService.votarReceta(1L, "negativo")).thenReturn(receta);

        mockMvc.perform(post("/api/recetas/1/votar/negativo"))  // Envía el voto como "negativo"
                .andExpect(status().isOk())  // Verifica que devuelve 200 OK
                .andExpect(jsonPath("$.votos").value(3));  // Verifica que los votos han disminuido en 2

        verify(recetaService, times(1)).votarReceta(1L, "negativo");
    }
}
