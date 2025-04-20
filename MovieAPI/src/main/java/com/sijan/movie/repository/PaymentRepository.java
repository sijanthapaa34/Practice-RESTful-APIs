package com.sijan.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sijan.movie.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	@Query("SELECT p FROM Payment p WHERE p.rental.customer.id = :customerId")
	List<Payment> getPaymentsByCustomerId(Long customerId);

	@Query("SELECT SUM(p.amount) FROM Payment p")
	Double getTotalRevenue();

}
