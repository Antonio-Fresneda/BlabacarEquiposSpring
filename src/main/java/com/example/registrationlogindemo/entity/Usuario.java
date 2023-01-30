package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String avatar;
    private String password;
    private String email;
    private String descripcion;
    @OneToMany(mappedBy="emisor")
    List<Mensaje> enviados;
    @OneToMany(mappedBy = "destinatario")
    List<Mensaje> recibidos;

    public Usuario(){
    }
    public Usuario(String username, String email){
        this.username=username;
        this.email=email;
    }

    public Usuario(String username, String email, String avatar){
        this.username=username;
        this.email=email;
        this.avatar=avatar;
    }
}
