package com.example.registrationlogindemo.repository;




import com.example.registrationlogindemo.entity.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface RepositorioEquipo extends JpaRepository<Equipos,Long>{

        public ArrayList<Equipos> findAll();
        public Equipos findById(long id);
        public Equipos save(Equipos equipos);

        //public ArrayList<Oferta> findByEquipo(String equipo);

    }

