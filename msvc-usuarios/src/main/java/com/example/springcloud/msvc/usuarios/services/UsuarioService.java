package com.example.springcloud.msvc.usuarios.services;

import com.example.springcloud.msvc.usuarios.models.enity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();
    Optional<Usuario>  porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    
    //Metodo para recibir un arreglo con los ids
    List<Usuario> listarPorIds(Iterable<Long> ids);

    Optional<Usuario> getPorEmail(String email);
}
