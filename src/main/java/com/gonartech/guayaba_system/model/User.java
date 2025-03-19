package com.gonartech.guayaba_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ingresar un email válido")
    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña tiene que ser mínimo de 6 caracteres")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.TRABAJADOR;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private userState estado = userState.ACTIVO;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
