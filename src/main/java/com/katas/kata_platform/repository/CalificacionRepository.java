package com.katas.kata_platform.repository;

import com.katas.kata_platform.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByParticipanteId(Long usuarioId);
}