package com.sijan.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sijan.movie.model.Rental;
import com.sijan.movie.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rentmovie")
    public Rental rentMovie(@RequestBody Rental rentRequest) {
        return rentalService.rentMovie(rentRequest);
    }
    
    @GetMapping("/{rentalId}")
    public Rental getRentalById(@PathVariable Long rentalId) {
        return rentalService.getRentalById(rentalId);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/customer/{customerId}")
    public List<Rental> getRentalsByCustomerId(@PathVariable Long customerId) {
        return rentalService.getRentalsByCustomerId(customerId);
    }

    @GetMapping("/overdue")
    public List<Rental> getOverdueRentals() {
        return rentalService.getOverdueRentals();
    }

    @GetMapping("/unreturned")
    public List<Rental> getUnreturnedRentals() {
        return rentalService.getUnreturnedRentals();
    }
}
