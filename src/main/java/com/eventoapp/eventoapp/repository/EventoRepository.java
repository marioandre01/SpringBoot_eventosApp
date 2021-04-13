package com.eventoapp.eventoapp.repository;

import com.eventoapp.eventoapp.model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {
    Evento findByCodigo(long codigo);
}

