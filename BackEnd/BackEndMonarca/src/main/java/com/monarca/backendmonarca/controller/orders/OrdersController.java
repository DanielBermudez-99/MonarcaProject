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

import java.util.*;

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
    public ResponseEntity<List<Map<String, Object>>> getPendingOrdersWithCartItemsByUserId(@PathVariable Long userId) {
        List<Orders> allOrders = orderRepository.findByUserId(userId);
        List<Orders> pendingOrders = new ArrayList<>();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Orders order : allOrders) {
            if (order.getStatus().equals(Status.valueOf("PENDING"))) {
                pendingOrders.add(order);
            }
        }

        for (Orders order : pendingOrders) {
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("order", order);
            List<Map<String, Object>> cartItemsDetails = new ArrayList<>();
            for (CartItem cartItem : order.getCartItems()) {
                Map<String, Object> cartItemDetails = new HashMap<>();
                cartItemDetails.put("productName", cartItem.getProduct().getName());
                cartItemDetails.put("quantity", cartItem.getQuantity());
                cartItemDetails.put("total", cartItem.getTotal());
                cartItemsDetails.add(cartItemDetails);
            }
            orderDetails.put("cartItems", cartItemsDetails);
            response.add(orderDetails);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/paid")
    public ResponseEntity<List<Map<String, Object>>> getPaidOrdersWithCartItemsByUserId(@PathVariable Long userId) {
        List<Orders> allOrders = orderRepository.findByUserId(userId);
        List<Orders> pendingOrders = new ArrayList<>();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Orders order : allOrders) {
            if (order.getStatus().equals(Status.valueOf("PAID"))) {
                pendingOrders.add(order);
            }
        }

        for (Orders order : pendingOrders) {
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("order", order);
            List<Map<String, Object>> cartItemsDetails = new ArrayList<>();
            for (CartItem cartItem : order.getCartItems()) {
                Map<String, Object> cartItemDetails = new HashMap<>();
                cartItemDetails.put("productName", cartItem.getProduct().getName());
                cartItemDetails.put("quantity", cartItem.getQuantity());
                cartItemDetails.put("total", cartItem.getTotal());
                cartItemsDetails.add(cartItemDetails);
            }
            orderDetails.put("cartItems", cartItemsDetails);
            response.add(orderDetails);
        }

        return ResponseEntity.ok(response);
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
        List<CartItem> cartItems = cartItemRepository.findByUserIdAndIsActiveTrue(userOptional.get().getId());

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

        // Agregar los elementos del carrito a la orden
        for (CartItem cartItem : cartItems) {
            order.getCartItems().add(cartItem);
        }

        // Marcar los elementos del carrito como inactivos
        for (CartItem cartItem : cartItems) {
            cartItem.setIsActive(false);
            cartItemRepository.save(cartItem);
        }

        // Guardar la orden después de que los CartItem se hayan marcado como inactivos
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }


    @PostMapping("/addCartItems/{orderId}")
    public ResponseEntity<?> addCartItemsToOrder(@PathVariable Long orderId) {
        try {
            Optional<Orders> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Orders order = orderOptional.get();

            // Obtener solo los elementos del carrito del usuario que están activos
            List<CartItem> cartItems = cartItemRepository.findByUserIdAndIsActiveTrue(order.getUser().getId());

            for (CartItem cartItem : cartItems) {
                order.getCartItems().add(cartItem);
                cartItem.setIsActive(false);
                cartItemRepository.save(cartItem);  // Guardar el cambio de estado en la base de datos
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