package com.example.registrationlogindemo.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "pasajeros")
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "usuario_email", referencedColumnName = "email")
    private User usuario;
}
