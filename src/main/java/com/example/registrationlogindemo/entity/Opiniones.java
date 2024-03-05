package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Opiniones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    @Column(columnDefinition = "INTEGER")
    private int valoracion;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/YYYY")
    private LocalDate fecha;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "opinador_email", referencedColumnName = "email")
    private User opinador;
}


