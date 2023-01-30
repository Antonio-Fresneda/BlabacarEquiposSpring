package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {
    public Usuario findById(long id);
    public Usuario findByUsername(String username);
}
