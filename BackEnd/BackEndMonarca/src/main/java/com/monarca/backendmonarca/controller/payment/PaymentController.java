package com.monarca.backendmonarca.controller.payment;

import com.monarca.backendmonarca.domain.order.OrderRepository;
import com.monarca.backendmonarca.domain.order.Orders;
import com.monarca.backendmonarca.domain.order.Status;
import com.monarca.backendmonarca.domain.payment.DataRegisterPayment;
import com.monarca.backendmonarca.domain.payment.DataUpdatePayment;
import com.monarca.backendmonarca.domain.payment.Payment;
import com.monarca.backendmonarca.domain.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository ordersRepository;

    private LocalDateTime purchaseTime;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody DataRegisterPayment dataRegisterPayment) {
        Payment payment = new Payment(dataRegisterPayment);
        return ResponseEntity.ok(paymentRepository.save(payment));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.of(paymentRepository.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentRepository.findByUserId(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody DataUpdatePayment dataUpdatePayment) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment = new Payment(dataUpdatePayment);
                    return ResponseEntity.ok(paymentRepository.save(payment));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{paymentId}/order/{orderId}")
    public ResponseEntity<Payment> associatePaymentWithOrder(@PathVariable Long paymentId, @PathVariable Long orderId, @RequestBody DataRegisterPayment dataRegisterPayment) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        Optional<Orders> ordersOptional = ordersRepository.findById(orderId);

        if (paymentOptional.isPresent() && ordersOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            Orders order = ordersOptional.get();

            // Verifica si los datos de Payment son correctos
            if (payment.getCardNumber().equals(dataRegisterPayment.cardNumber()) &&
                    payment.getCardHolderName().equals(dataRegisterPayment.cardHolderName()) &&
                    payment.getExpiryDate().equals(dataRegisterPayment.expiryDate()) &&
                    payment.getCvv().equals(dataRegisterPayment.cvv())) {

                // Deduce el total de la orden del balance del pago
                payment.deductFromBalance(order.getTotal());

                // Asocia el pago con la orden
                order.setPayment(payment);

                // Cambia el estado de la orden a "pagado"
                order.setStatus(Status.valueOf("PAID"));
                order.setDate_payment(LocalDate.now());
                order.setDate_delivery(null);
                order.setDate_purchase(LocalDate.now());

                // Guarda la orden en la base de datos
                ordersRepository.save(order);

                return ResponseEntity.ok(payment);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}