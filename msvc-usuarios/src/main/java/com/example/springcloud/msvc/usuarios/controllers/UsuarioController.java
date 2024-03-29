package com.example.springcloud.msvc.usuarios.controllers;

import com.example.springcloud.msvc.usuarios.models.enity.Usuario;
import com.example.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.logging.Logger;

@RestController
public class UsuarioController {


    @Autowired
    private UsuarioService service;

    @GetMapping("/")
    public Map<String,List<Usuario>>  listar(){

        return Collections.singletonMap("usuarios",service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.porId(id);
        if(usuarioOptional.isPresent()){
            System.out.println("El usuario es : " + usuarioOptional.get().getNombre());
            return ResponseEntity.ok().body(usuarioOptional.get());
        }
        System.out.println("El usuario es : " + usuarioOptional);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        if(!usuario.getEmail().isEmpty() && service.getPorEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("mensaje","Ya existe un usuario con ese email"));
        }
        if(result.hasErrors()){
            return validar(result);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario,BindingResult result,@PathVariable Long id) {

        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()){

            Usuario usuarioDb = o.get();

            if(!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.getPorEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("mensaje","Ya existe un usuario con ese email"));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-cursos")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "El campo " + err.getField()+ " "+ err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
