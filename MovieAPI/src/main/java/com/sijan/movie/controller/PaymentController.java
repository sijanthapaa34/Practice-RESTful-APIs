package com.sijan.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sijan.movie.model.Payment;
import com.sijan.movie.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{rentalId}")
    public Payment makePayment(
            @PathVariable Long rentalId,
            @RequestParam double amount) {
        return paymentService.makePayment(rentalId, amount);
    }


    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Payment> getPaymentsByCustomerId(@PathVariable Long customerId) {
        return paymentService.getPaymentsByCustomerId(customerId);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/total-revenue")
    public Double getTotalRevenue() {
        return paymentService.getTotalRevenue();
    }
}
