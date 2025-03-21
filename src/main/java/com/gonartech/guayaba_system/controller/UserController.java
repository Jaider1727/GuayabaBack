package com.gonartech.guayaba_system.controller;

import com.gonartech.guayaba_system.model.Estado;
import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //  Registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Lista de usuarios
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    //  Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //  Obtener usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    //  Obtener usuarios por rol
    @GetMapping("/role/{rol}")
    public ResponseEntity<List<User>> getUsersByRol(@PathVariable Rol rol) {
        return ResponseEntity.ok(userService.getUsersByRol(rol));
    }

    //  Obtener usuarios por estado
    @GetMapping("/status/{estado}")
    public ResponseEntity<List<User>> getUsersByEstado(@PathVariable Estado estado) {
        return ResponseEntity.ok(userService.getUsersByEstado(estado));
    }

    //  Actualizar datos de usuario
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody @Valid User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    //  Cambiar contraseña
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Integer id, @RequestParam String newPassword) {
        userService.changePassword(id, newPassword);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    //  Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
