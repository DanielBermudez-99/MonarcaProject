package com.monarca.backendmonarca.controller.orders;

import com.monarca.backendmonarca.domain.cart.CartItem;
import com.monarca.backendmonarca.domain.cart.CartItemRepository;
import com.monarca.backendmonarca.domain.category.Category;
import com.monarca.backendmonarca.domain.order.DataRegisterOrder;
import com.monarca.backendmonarca.domain.order.Orders;
import com.monarca.backendmonarca.domain.order.OrderRepository;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.product.ProductRepository;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener todas las órdenes
//    @GetMapping("/list")
//    public List<Orders> getAllOrders() {
//        return (List<Orders>) orderRepository.findAll();
//    }

    @GetMapping("/list")
    public ResponseEntity<?> obtenerOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    // Obtener una orden por su ID
    @GetMapping("list/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Crear una nueva orden
    @PostMapping("/create/{userId}")
    public ResponseEntity<Orders> createOrder(@PathVariable Long userId, @RequestBody DataRegisterOrder dataRegisterOrder) {
        Optional<User> userOptional = userRepository.findById(String.valueOf(userId));
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Orders order = new Orders(dataRegisterOrder);
        order.setUser(userOptional.get());
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/addProducts/{orderId}")
    @Transactional
    public ResponseEntity<?> addProductsToOrder(@PathVariable Long orderId, @RequestBody List<Long> productIds) {
        try {
            Optional<Orders> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Orders order = orderOptional.get();
            for (Long productId : productIds) {
                Optional<Product> productOptional = productRepository.findById(productId);
                if (!productOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found for id: " + productId);
                }

                Product product = productOptional.get();
                product.getOrders().add(order); // Agrega la orden a la lista de órdenes del producto
                productRepository.save(product); // Guarda el producto con la nueva orden
            }

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding products to order: " + e.getMessage());
        }
    }


    // Actualizar una orden existente
    @PutMapping("update/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders orderDetails) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Orders updatedOrder = order.get();
            // Aquí puedes actualizar los campos de la orden que necesites
            // Por ejemplo: updatedOrder.setDatePurchase(orderDetails.getDatePurchase());
            return ResponseEntity.ok(orderRepository.save(updatedOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una orden
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}