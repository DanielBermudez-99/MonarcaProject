package com.monarca.backendmonarca.domain.user;

import javax.management.relation.Role;

public record DataRegisterUser(String name, String lastname, String username, String password, String email, String phone, String location, String complement, String address, String career, String street, String number) {
}
