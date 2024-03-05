package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Opiniones;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Repository
public interface RepositorioOpiniones extends JpaRepository<Opiniones,Long> {

    public ArrayList<Opiniones> findAll();

    public Opiniones findById(long id);

    public ArrayList<Opiniones> findByUser(User user);
    public Opiniones save(Opiniones opiniones);
    @Query("select c from Opiniones c order by c.id DESC limit 3")
    public ArrayList<Opiniones> find3();




}

