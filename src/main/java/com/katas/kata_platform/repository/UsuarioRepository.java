package com.katas.kata_platform.repository;

import com.katas.kata_platform.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
