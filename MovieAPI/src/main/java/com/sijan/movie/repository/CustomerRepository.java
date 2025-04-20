package com.sijan.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sijan.movie.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query("SELECT c FROM Customer c LEFT JOIN c.rentals r GROUP BY c.id ORDER BY COUNT(r.id) DESC")
	List<Customer> getCustomersWithMostRentals();

	@Query("SELECT c FROM Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
	List<Customer> getCustomerByEmail(@Param("email") String email);

	
}
