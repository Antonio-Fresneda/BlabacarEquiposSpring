package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Oferta;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RepositorioOferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioOferta {

    @Autowired
    RepositorioOferta repo;

    public ArrayList<Oferta> findAll(){
        return repo.findAll();
    }

    public Oferta findById(long id){
        return repo.findById(id);
    }

    public Oferta save(Oferta oferta){
        return repo.save(oferta);
    }

    public ArrayList<Oferta> findByEquipo(String equipo){
        return repo.findByEquipo(equipo);
    }


    public ArrayList<Oferta> buscarOfertasPorEmailPropietario(String emailPropietario) {
        return repo.findByPropietarioEmail(emailPropietario);}

    public void deleteById(long id){
        repo.deleteById(id);
    }

    public List<Oferta> findOfertasByPropietarioEmail(String propietarioEmail) {
        return repo.findByPropietarioEmail(propietarioEmail);
    }

    @Modifying
    @Transactional
    @Query("UPDATE Oferta o SET o.plazas = o.plazas - 1 WHERE o.id = :ofertaId AND o.plazas > 0")
    public void restarPlaza(@Param("ofertaId") Long ofertaId) {
        repo.restarPlaza(ofertaId);
    }


}
