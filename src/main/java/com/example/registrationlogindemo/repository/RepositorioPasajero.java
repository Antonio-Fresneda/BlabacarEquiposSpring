package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Oferta;
import com.example.registrationlogindemo.entity.Opiniones;
import com.example.registrationlogindemo.entity.Pasajero;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RepositorioPasajero extends JpaRepository<Pasajero,Long> {

    public ArrayList<Pasajero> findAll();

    public Pasajero save(Pasajero pasajero);

    List<Pasajero> findByOfertaId(Long ofertaId);




}
