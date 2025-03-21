package com.gonartech.guayaba_system.repository;

import com.gonartech.guayaba_system.model.Estado;
import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Lista de usuarios
    List<User> findAll();

    // Buscar usuario por email
    Optional<User> findByEmail(String email);

    // Buscar usuarios por estado
    List<User> findByEstado(Estado estado);

    // Buscar usuarios por rol
    List<User> findByRol(Rol rol);

    // Verificar si un email ya existe
    boolean existsByEmail(String email);
}