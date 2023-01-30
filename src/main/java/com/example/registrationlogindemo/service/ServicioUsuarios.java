package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Usuario;
import com.example.registrationlogindemo.repository.RepositorioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioUsuarios {
    @Autowired
    RepositorioUsuarios repositorio;

    public List<Usuario> findAll(){
        return repositorio.findAll();
    }

    public Usuario findById(long id){
        return repositorio.findById(id);
    }

    public Usuario findByUsername(String username){return repositorio.findByUsername(username);}

    public Usuario save(Usuario usuario){
        repositorio.save(usuario);
        return usuario;
    }

    public void delete(Usuario usuario){
        repositorio.delete(usuario);
    }

}
