package com.gonartech.guayaba_system.service;

import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.model.userState;
import com.gonartech.guayaba_system.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> obtenerTodos(){
        return userRepository.findAll();
    }
    public Optional<User> obtenerPorId(Long id){
        return userRepository.findById(id);
    }

    public User guardar( User newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User actualizar(Long id, User updatedUser){
        return userRepository.findById(id).map(User ->{
            User.setUsername(updatedUser.getUsername());
            User.setEmail(updatedUser.getEmail());
            User.setRol(updatedUser.getRol());
            User.setEstado(updatedUser.getEstado());
            return userRepository.save(User);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public void desactivarUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setEstado(userState.INACTIVO);
        userRepository.save(user);
    }
}
