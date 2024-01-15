package com.monarca.backendmonarca.domain.payment;

import java.time.LocalDate;

public record DataUpdatePayment(Long id, String cardNumber, String cardHolderName, LocalDate expiryDate, String cvv, Double balance) {
}
