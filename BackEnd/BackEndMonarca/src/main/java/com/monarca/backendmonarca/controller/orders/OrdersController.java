package com.monarca.backendmonarca.controller.orders;

import com.monarca.backendmonarca.domain.order.Orders;
import com.monarca.backendmonarca.domain.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping ("/register")
    public Orders createOrder(@RequestBody Orders order) {
        return orderRepository.save(order);
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