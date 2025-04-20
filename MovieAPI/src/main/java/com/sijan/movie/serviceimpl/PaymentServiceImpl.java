package com.sijan.movie.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijan.movie.model.Payment;
import com.sijan.movie.model.Rental;
import com.sijan.movie.repository.PaymentRepository;
import com.sijan.movie.service.PaymentService;
import com.sijan.movie.service.RentalService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private RentalService rentalService;

    @Override
    public Payment makePayment(Long rentalId, double amount) {
    	if (rentalId == null || rentalId < 0) {
            throw new IllegalArgumentException("Invalid Rental ID.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }

        Rental rental = rentalService.getRentalById(rentalId);
        if (rental == null) {
            throw new RuntimeException("Rental not found for ID: " + rentalId);
        }

        boolean alreadyPaid = paymentRepo.existsById(rentalId);
        if (alreadyPaid) {
            throw new RuntimeException("Payment has already been made for this rental.");
        }

        if (amount < rental.getTotalAmount()) {
            throw new RuntimeException("Insufficient payment amount.");
        }

        Payment payment = new Payment();
        payment.setRental(rental);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());

        return paymentRepo.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Payment ID.");
        }
        return paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Id not found"));
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(Long customerId) {
        if (customerId == null || customerId < 0) {
            throw new IllegalArgumentException("Invalid customer ID.");
        }

        List<Payment> payments = paymentRepo.getPaymentsByCustomerId(customerId);
        if (payments.isEmpty()) {
            throw new RuntimeException("No payments found for customer ID: " + customerId);
        }

        return payments;
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> allPayments = paymentRepo.findAll();
        
        if (allPayments.isEmpty()) {
            throw new RuntimeException("No payments found in the system.");
        }

        return allPayments;
    }


    @Override
    public Double getTotalRevenue() {
    	Double revenue = paymentRepo.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
}
