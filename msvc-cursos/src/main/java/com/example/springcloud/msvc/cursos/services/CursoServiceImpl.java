package com.example.springcloud.msvc.cursos.services;

import com.example.springcloud.msvc.cursos.clients.UsuarioClientRest;
import com.example.springcloud.msvc.cursos.models.Usuario;
import com.example.springcloud.msvc.cursos.models.entity.Curso;
import com.example.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.example.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl  implements CursoService{

    @Autowired
    private UsuarioClientRest client;

    @Autowired
    private CursoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    //Metodos para asignar los usuarios a los cursos
    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        //Pasar curso
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            //Buscamos en el microServicio el usuario
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            //Obtener curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return  Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        //Pasar curso
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            //Pasamos el usuario que obtenemos y lo creamos
            Usuario usuarioNuevoMsvc = client.crear(usuario);
            //Obtener curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return  Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        //Pasar curso
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isPresent()){
            //Buscamos en el microServicio el usuario
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            //Obtener curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return  Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
