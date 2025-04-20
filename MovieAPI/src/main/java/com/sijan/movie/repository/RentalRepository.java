package com.sijan.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sijan.movie.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>{

	List<Rental> getRentalsByCustomerId(Long customerId);

	@Query("SELECT r FROM Rental r WHERE r.rentalDate < CURRENT_DATE AND r.returnDate IS NULL")
	List<Rental> getOverdueRentals();

	@Query("SELECT r FROM Rental r WHERE r.returned = FALSE")
	List<Rental> getUnreturnedRentals();

}
