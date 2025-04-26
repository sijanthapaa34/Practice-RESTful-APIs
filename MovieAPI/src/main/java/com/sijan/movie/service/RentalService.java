package com.sijan.movie.service;

import java.util.List;

import com.sijan.movie.model.Rental;

public interface RentalService {
    Rental rentMovie(Rental rentRequest);
    Rental getRentalById(Long rentalId);
    List<Rental> getAllRentals();
    List<Rental> getRentalsByCustomerId(Long customerId);

    List<Rental> getOverdueRentals(); 
    List<Rental> getUnreturnedRentals();
}

