package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Oferta;

import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Repository
public interface RepositorioOferta extends JpaRepository<Oferta,Long> {

    public ArrayList<Oferta> findAll();
    public Oferta findById(long id);
    public Oferta save(Oferta oferta);
    public ArrayList<Oferta> findByEquipo(String equipo);
    ArrayList<Oferta> findByPropietarioEmail(String email);

    @Modifying
    @Query("UPDATE Oferta o SET o.plazas = o.plazas - 1 WHERE o.id = :ofertaId AND o.plazas > 0")
    void restarPlaza(@Param("ofertaId") Long ofertaId);
}








