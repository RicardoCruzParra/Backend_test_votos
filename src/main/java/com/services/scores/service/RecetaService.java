package com.services.scores.service;

import com.services.scores.entity.Receta;
import com.services.scores.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService
{
    @Autowired
    private RecetaRepository recetaRepository;

    public List<Receta> obtenerTodasRecetas()
    {
        return recetaRepository.findAll();
    }

    public Optional<Receta> obtenerRecetaPorId(Long id)
    {
        return recetaRepository.findById(id);
    }

    public Receta crearReceta(Receta receta)
    {
        return recetaRepository.save(receta);
    }

    public Receta actualizarReceta(Long id, Receta recetaActualizada)
    {
        return recetaRepository.findById(id)
                .map(receta -> {
                    receta.setNombre(recetaActualizada.getNombre());
                    receta.setDescripcion(recetaActualizada.getDescripcion());
                    receta.setIngredientes(recetaActualizada.getIngredientes());
                    receta.setInstrucciones(recetaActualizada.getInstrucciones());
                    receta.setTiempoPreparacion(recetaActualizada.getTiempoPreparacion());
                    receta.setDificultad(recetaActualizada.getDificultad());
                    receta.setParticipante(recetaActualizada.getParticipante());
                    return recetaRepository.save(receta);
                }).orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
    }

    public void eliminarReceta(Long id)
    {
        recetaRepository.deleteById(id);
    }

    public Receta votarReceta(Long id, String tipoVoto)
    {
        return recetaRepository.findById(id)
                .map(receta -> {
                    if (tipoVoto.equals("positivo")) {
                        receta.setVotos(receta.getVotos() + 1);
                    } else if (tipoVoto.equals("negativo")) {
                        receta.setVotos(receta.getVotos() - 2);
                    } else {
                        throw new IllegalArgumentException("Tipo de voto inválido: " + tipoVoto);
                    }
                    return recetaRepository.save(receta);
                }).orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
    }
}