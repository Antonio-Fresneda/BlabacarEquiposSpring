package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Oferta;
import com.example.registrationlogindemo.entity.Pasajero;
import com.example.registrationlogindemo.repository.RepositorioOferta;
import com.example.registrationlogindemo.repository.RepositorioPasajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPasajero {
    @Autowired
    RepositorioPasajero repo;

    public ArrayList<Pasajero> findAll(){
        return repo.findAll();
    }


    public Pasajero save(Pasajero pasajero){
        return repo.save(pasajero);
    }

    public List<Pasajero> findPasajerosByOfertaId(Long ofertaId) {
        return repo.findByOfertaId(ofertaId);
    }
}
