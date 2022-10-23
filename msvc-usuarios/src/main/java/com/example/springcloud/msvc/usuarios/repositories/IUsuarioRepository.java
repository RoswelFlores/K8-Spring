package com.example.springcloud.msvc.usuarios.repositories;

import com.example.springcloud.msvc.usuarios.models.enity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario,Long> {
    //Validar si existe el email
    Optional<Usuario> findByEmail(String email);
}
