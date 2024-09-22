package com.services.scores.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Receta
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @ElementCollection
    @NotEmpty(message = "La lista de ingredientes no puede estar vacía")
    private List<String> ingredientes;

    @NotBlank(message = "Las instrucciones son obligatorias")
    private String instrucciones;

    @Min(value = 1, message = "El tiempo de preparación debe ser mayor a 1 minuto")
    private Integer tiempoPreparacion;

    @NotBlank(message = "La dificultad es obligatoria")
    private String dificultad;

    @NotBlank(message = "El nombre del participante es obligatorio")
    private String participante;

    private Integer votos = 0;

    public Receta() {

    }
}