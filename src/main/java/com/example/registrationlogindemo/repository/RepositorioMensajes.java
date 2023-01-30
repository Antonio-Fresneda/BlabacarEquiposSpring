package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Mensaje;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RepositorioMensajes extends JpaRepository<Mensaje, Long> {
    public Mensaje findById(long id);
    public List<Mensaje> findByEmisorAndDestinatario(User emisor, User destinatario);

    @Query(
            value = "SELECT * FROM USERS u WHERE u.status = 1",
            nativeQuery = true)
    Collection<User> findAllActiveUsersNative();
}
