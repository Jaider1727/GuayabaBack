package com.gonartech.guayaba_system.service;

import com.gonartech.guayaba_system.model.Estado;
import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Lista de usuarios
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    //  Registrar usuario (con encriptación de contraseña)
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //  Buscar usuario por ID
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    //  Buscar usuario por email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    //  Buscar usuarios por rol
    public List<User> getUsersByRol(Rol rol) {
        return userRepository.findByRol(rol);
    }

    //  Buscar usuarios por estado
    public List<User> getUsersByEstado(Estado estado) {
        return userRepository.findByEstado(estado);
    }

    // 🛠 Actualizar datos de usuario
    public User updateUser(Integer id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRol(updatedUser.getRol());
        existingUser.setEstado(updatedUser.getEstado());
        return userRepository.save(existingUser);
    }

    //  Cambiar contraseña
    public void changePassword(Integer id, String newPassword) {
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    //  Eliminar usuario
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}

