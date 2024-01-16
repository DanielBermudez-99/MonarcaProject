package com.monarca.backendmonarca.controller.orders;

import com.monarca.backendmonarca.domain.cart.CartItem;
import com.monarca.backendmonarca.domain.cart.CartItemRepository;
import com.monarca.backendmonarca.domain.category.Category;
import com.monarca.backendmonarca.domain.order.*;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.product.ProductRepository;
import com.monarca.backendmonarca.domain.user.User;
import com.monarca.backendmonarca.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/list")
    public ResponseEntity<?> obtenerOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }


    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<Orders>> getPendingOrdersByUserId(@PathVariable Long userId) {
        List<Orders> allOrders = orderRepository.findByUserId(userId);
        List<Orders> pendingOrders = new ArrayList<>();

        for (Orders order : allOrders) {
            if (order.getStatus().equals(Status.valueOf("PENDING"))) {
                pendingOrders.add(order);
            }
        }

        return ResponseEntity.ok(pendingOrders);
    }

    @GetMapping("/user/{userId}/paid")
    public ResponseEntity<List<Orders>> getPaidOrdersByUserId(@PathVariable Long userId) {
        List<Orders> allOrders = orderRepository.findByUserId(userId);
        List<Orders> paidOrders = new ArrayList<>();

        for (Orders order : allOrders) {
            if (order.getStatus().equals(Status.valueOf("PAID"))) {
                paidOrders.add(order);
            }
        }

        return ResponseEntity.ok(paidOrders);
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

        // Obtener los elementos del carrito del usuario
        List<CartItem> cartItems = cartItemRepository.findByUser(userOptional.get());

        // Calcular el total sin tener que crear un nuevo campo
        double total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // Crear la orden con el total calculado
        Orders order = new Orders(dataRegisterOrder);
        order.setUser(userOptional.get());
        order.setTotalPrice(total);
        order.setStatus(Status.valueOf("PENDING"));
        order.setPayment_method(PaymentMethod.valueOf("CREDIT_CARD"));
        order.setDate_payment(null);
        order.setDate_delivery(null);
        order.setDate_purchase(null);

        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    // Agregar productos a una orden
//    @PostMapping("/addProducts/{orderId}")
//    public ResponseEntity<?> addProductsToOrder(@PathVariable Long orderId, @RequestBody List<Long> productIds) {
//        try {
//            Optional<Orders> orderOptional = orderRepository.findById(orderId);
//            if (!orderOptional.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            Orders order = orderOptional.get();
//            for (Long productId : productIds) {
//                Optional<Product> productOptional = productRepository.findById(productId);
//                if (!productOptional.isPresent()) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found for id: " + productId);
//                }
//
//                Product product = productOptional.get();
//                order.getProducts().add(product);
//            }
//
//            orderRepository.save(order);
//            return ResponseEntity.ok(order);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding products to order: " + e.getMessage());
//        }
//    }

    @PostMapping("/addCartItems/{orderId}")
    public ResponseEntity<?> addCartItemsToOrder(@PathVariable Long orderId) {
        try {
            Optional<Orders> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Orders order = orderOptional.get();

            // Obtener los elementos del carrito del usuario
            List<CartItem> cartItems = cartItemRepository.findByUser(order.getUser());

            for (CartItem cartItem : cartItems) {
                order.getCartItems().add(cartItem);
                cartItem.setIsActive(false);
                cartItemRepository.save(cartItem);
            }

            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding cartItems to order: " + e.getMessage());
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