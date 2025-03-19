package com.gonartech.guayaba_system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Rol {
    TRABAJADOR, ADMINISTRADOR;

    @JsonCreator
    public static Rol fromString(String value) {
        return Rol.valueOf(value.toUpperCase());
    }
    @JsonValue
    public String tovalue() {
        return name();
    }
}
