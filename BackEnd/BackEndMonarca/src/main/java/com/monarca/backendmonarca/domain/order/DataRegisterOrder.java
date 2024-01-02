package com.monarca.backendmonarca.domain.order;

import java.time.LocalDate;

public record DataRegisterOrder(LocalDate date_purchase, LocalDate date_payment, LocalDate date_delivery, Double total_price, Status status, PaymentMethod payment_method) {
}
