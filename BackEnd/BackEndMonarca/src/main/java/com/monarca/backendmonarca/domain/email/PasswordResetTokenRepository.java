package com.monarca.backendmonarca.domain.email;

import com.monarca.backendmonarca.domain.email.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
