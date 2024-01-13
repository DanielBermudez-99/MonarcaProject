package com.monarca.backendmonarca.controller.cart;

import com.monarca.backendmonarca.domain.cart.CartItem;
import com.monarca.backendmonarca.domain.cart.CartItemRepository;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.product.ProductRepository;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public static class QuantityRequest {
        private Integer quantity;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartItem> addToCart(@PathVariable Long userId, @PathVariable Long productId, @RequestBody QuantityRequest quantityRequest) {
        Optional<User> user = userRepository.findById(String.valueOf(userId));
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user.get());
            cartItem.setProduct(product.get());
            cartItem.setQuantity(quantityRequest.getQuantity());
            cartItemRepository.save(cartItem);
            return ResponseEntity.ok(cartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        Optional<CartItem> cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem.isPresent()) {
            cartItemRepository.delete(cartItem.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //El controlador anásliza si los items estan activos o no por medio del repositorio.
    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getCart(@PathVariable Long userId) {
        // Cambia esta línea para usar el nuevo método del repositorio
        List<CartItem> cartItems = cartItemRepository.findByUserIdAndIsActiveTrue(userId);

        List<Map<String, Object>> cartInfo = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Long productId = cartItem.getProduct().getId();

            // Obtén la información del producto directamente desde el repositorio de productos
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                // Agrega la información del producto al resultado
                Map<String, Object> cartItemInfo = new HashMap<>();
                cartItemInfo.put("productInfo", product);
                cartItemInfo.put("quantity", cartItem.getQuantity());
                cartItemInfo.put("total", cartItem.getTotal());
                cartItemInfo.put("productId", productId);
                // Puedes agregar más campos según sea necesario

                cartInfo.add(cartItemInfo);
            }
        }

        return ResponseEntity.ok(cartInfo);
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getTotal());
        }

        return ResponseEntity.ok(total);
    }

    @PutMapping("/{userId}/update/{productId}")
    public ResponseEntity<CartItem> updateQuantityInCart(@PathVariable Long userId, @PathVariable Long productId, @RequestBody QuantityRequest quantityRequest) {
        Optional<CartItem> cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem.isPresent()) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(quantityRequest.getQuantity());
            cartItemRepository.save(existingCartItem);
            return ResponseEntity.ok(existingCartItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
