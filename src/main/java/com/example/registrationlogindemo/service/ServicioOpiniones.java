package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Opiniones;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RepositorioOpiniones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ServicioOpiniones {
    @Autowired
    RepositorioOpiniones repo;

    public ArrayList<Opiniones> findAll(){
        return repo.findAll();
    }

    public Opiniones findById(long id){
        return repo.findById(id);
    }

    public ArrayList<Opiniones> findByUser(User user){
        return repo.findByUser(user);
    }

    public Opiniones save(Opiniones opiniones){
        return repo.save(opiniones);
    }

    public void delete(Opiniones opiniones){
        repo.delete(opiniones);
    }

    public ArrayList<Opiniones> find3(){
        return repo.find3();
    }
}
