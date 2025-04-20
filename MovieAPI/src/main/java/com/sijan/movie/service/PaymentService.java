package com.sijan.movie.service;

import java.util.List;

import com.sijan.movie.model.Payment;

public interface PaymentService {
    Payment makePayment(Long RentalId, double Amount);
    Payment getPaymentById(Long id);
    List<Payment> getPaymentsByCustomerId(Long customerId);
    List<Payment> getAllPayments();

    // Custom features
    Double getTotalRevenue();
}

