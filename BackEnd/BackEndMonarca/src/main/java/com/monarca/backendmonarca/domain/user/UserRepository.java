package com.monarca.backendmonarca.domain.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String rol_user);


    User findByEmail(String userEmail);
}

