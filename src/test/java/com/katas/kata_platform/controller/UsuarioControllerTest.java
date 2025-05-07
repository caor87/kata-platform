package com.katas.kata_platform.controller;


import com.katas.kata_platform.model.Usuario;
import com.katas.kata_platform.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioController = new UsuarioController(usuarioRepository);
    }

    @Test
    void testRegistrarUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Pedro");
        usuario.setTipo("jurado");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.registrar(usuario);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pedro", response.getBody().getNombre());
        assertEquals("jurado", response.getBody().getTipo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testObtenerUsuarios() {
        // Arrange
        Usuario u1 = new Usuario();
        u1.setNombre("Ana");
        u1.setTipo("participante");

        Usuario u2 = new Usuario();
        u2.setNombre("Luis");
        u2.setTipo("jurado");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        // Act
        ResponseEntity<List<Usuario>> response = usuarioController.obtenerUsuarios();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Ana", response.getBody().get(0).getNombre());
        assertEquals("Luis", response.getBody().get(1).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }
}
