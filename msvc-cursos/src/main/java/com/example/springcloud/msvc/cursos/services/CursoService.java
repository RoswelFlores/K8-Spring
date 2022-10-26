package com.example.springcloud.msvc.cursos.services;

import com.example.springcloud.msvc.cursos.models.Usuario;
import com.example.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    Optional<Curso> porIdConUsuarios(Long id);
    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);


    Optional<Usuario> asignarUsuario(Usuario usuario,Long cursoId);

    Optional <Usuario> crearUsuario(Usuario usuario,Long cursoId);

    Optional <Usuario> eliminarUsuario(Usuario usuario,Long cursoId);



}
