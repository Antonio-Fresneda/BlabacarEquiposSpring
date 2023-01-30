package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

    //Son los campos que añado para el chat a los de la gestión de usuarios
    private String avatar;
    private String descripcion;
    @OneToMany(mappedBy="emisor")
    List<Mensaje> enviados;
    @OneToMany(mappedBy = "destinatario")
    List<Mensaje> recibidos;

    public User(String name, String email){
        this.name=name;
        this.email=email;
    }
    public User(String name, String email, String avatar, String password){
        this.name=name;
        this.email=email;
        this.avatar=avatar;
        this.password=password;
    }

}
