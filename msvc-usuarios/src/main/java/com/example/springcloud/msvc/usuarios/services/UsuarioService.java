package com.example.springcloud.msvc.usuarios.services;

import com.example.springcloud.msvc.usuarios.models.enity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();
    Optional<Usuario>  porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    Optional<Usuario> getPorEmail(String email);
}
