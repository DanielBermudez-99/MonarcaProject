package com.monarca.backendmonarca.infra.services.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}