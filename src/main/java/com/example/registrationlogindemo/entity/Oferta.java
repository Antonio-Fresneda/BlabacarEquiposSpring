package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ofertas")
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/YYYY")
    private LocalDate fechaOferta;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaViaje;

    private String puntoSalida;

    @OneToOne
    @JoinColumn(name = "equipo_id")
    private Equipos equipo;

    private String vehiculo;

    @Transient
    private String escudo;

    private int plazas;

    private String destino;

    @ManyToOne
    @JoinColumn(name = "propietario_email", referencedColumnName = "email")
    private User propietario;


    public void setPropietario(User user) {
        this.propietario = user;
    }


}
