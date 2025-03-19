package com.gonartech.guayaba_system.controller;

import com.gonartech.guayaba_system.dto.UserDto;
import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.User;
import com.gonartech.guayaba_system.model.userState;
import com.gonartech.guayaba_system.service.userService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping
    public List<UserDto> obtenerTodos(){
        return userService.obtenerTodos().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRol(), user.getEstado()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<User> obtenerPorId(@PathVariable Long id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        user.setPassword(user.getPassword());
        return ResponseEntity.ok(userService.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<User> usuarioOptional = userService.obtenerPorId(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User usuario = usuarioOptional.get();

        // Verificar si el campo "rol" está presente en la solicitud y convertirlo a tipo Rol
        if (updates.containsKey("rol")) {
            try {
                Rol nuevoRol = Rol.valueOf(updates.get("rol").toString().toUpperCase());
                usuario.setRol(nuevoRol);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: Rol inválido");
            }
        }

        // Verificar si el campo "state" está presente en la solicitud y convertirlo a tipo userState
        if (updates.containsKey("estado")) {
            try {
                userState nuevoEstado = userState.valueOf(updates.get("estado").toString().toUpperCase());
                usuario.setEstado(nuevoEstado);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: Estado inválido");
            }
        }

        // Guardar los cambios en la base de datos
        userService.guardar(usuario);

        return ResponseEntity.ok(usuario);
    }



    @DeleteMapping("/{id}")
    public void desactivarUser(@PathVariable Long id){
        userService.desactivarUser(id);
    }

}
