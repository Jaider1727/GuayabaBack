package com.gonartech.guayaba_system.service;

import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticar(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña Incorrecta");
        }
        return "JWT-TOKEN";
    }
}
