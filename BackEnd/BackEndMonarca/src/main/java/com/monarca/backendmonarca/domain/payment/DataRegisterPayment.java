package com.monarca.backendmonarca.domain.payment;

import java.time.LocalDate;

public record DataRegisterPayment( Long id, String cardNumber, String cardHolderName, LocalDate expiryDate, String cvv, Double balance) {
}
