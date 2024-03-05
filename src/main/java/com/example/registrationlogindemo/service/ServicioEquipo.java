package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Equipos;
import com.example.registrationlogindemo.entity.Oferta;
import com.example.registrationlogindemo.repository.RepositorioEquipo;
import com.example.registrationlogindemo.repository.RepositorioOferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServicioEquipo {
    @Autowired
    RepositorioEquipo repo;

    public ArrayList<Equipos> findAll(){
        return repo.findAll();
    }

    public Equipos findById(long id){
        return repo.findById(id);
    }

    public Equipos save(Equipos equipo){
        return repo.save(equipo);
    }


    public void deleteById(long id){
        repo.deleteById(id);
    }
}

