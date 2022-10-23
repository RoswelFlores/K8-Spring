package com.example.springcloud.msvc.cursos.clients;

import com.example.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//nombre del microServicio que se va a consumir
@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    //Consumir la lista de usuarios
    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);
}
