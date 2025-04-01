package com.gonartech.guayaba_system.service;

import com.gonartech.guayaba_system.dto.UserDto;
import com.gonartech.guayaba_system.jwt.JwtUtil;
import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.repository.RolRepository;
import com.gonartech.guayaba_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private final UserService userService;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public AuthService(UserService userService, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public String authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerUser(UserDto userDto) {
        if (userService.existsByUserName(userDto.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Rol rolUser = rolRepository.findByNombre("trabajador").orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), rolUser);

        userService.save(user);
    }
}
