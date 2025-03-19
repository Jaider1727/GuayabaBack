package com.gonartech.guayaba_system.dto;

import com.gonartech.guayaba_system.model.Rol;
import com.gonartech.guayaba_system.model.userState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Rol rol;
    private userState estado;

}
