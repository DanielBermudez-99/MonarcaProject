package com.monarca.backendmonarca.domain.cart;

import com.monarca.backendmonarca.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    List<CartItem> findByUserId(Long userId);

    List<CartItem> findByUser(User user);

    // Nuevo método para obtener solo los cartItems activos de un usuario
    List<CartItem> findByUserIdAndIsActiveTrue(Long userId);
}