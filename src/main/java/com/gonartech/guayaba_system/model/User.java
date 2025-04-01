package com.gonartech.guayaba_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String username;

    @Email(message = "Debe ingresar un email válido")
    @Column(unique = true,length = 100)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña tiene que ser mínimo de 6 caracteres")
    @Column(nullable = false)
    private String password;

    @ManyToOne()
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne()
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    public User(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;

    }
}
