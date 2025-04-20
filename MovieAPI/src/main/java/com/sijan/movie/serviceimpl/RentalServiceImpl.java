package com.sijan.movie.serviceimpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijan.movie.model.Customer;
import com.sijan.movie.model.Movie;
import com.sijan.movie.model.Rental;
import com.sijan.movie.repository.RentalRepository;
import com.sijan.movie.service.CustomerService;
import com.sijan.movie.service.MovieService;
import com.sijan.movie.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MovieService movieService;

    @Override
    public Rental rentMovie(Rental rentRequest) {
        if (rentRequest == null || rentRequest.getCustomer() == null || rentRequest.getMovie() == null || rentRequest.getReturnDate() == null) {
            throw new IllegalArgumentException("Rental request, customer, movie, and return date must not be null.");
        }

        Long customerId = rentRequest.getCustomer().getId();
        Long movieId = rentRequest.getMovie().getId();

        if (customerId == null || movieId == null) {
            throw new IllegalArgumentException("Customer ID and Movie ID must be provided.");
        }

        Customer customer = customerService.getCustomerById(customerId);
        Movie movie = movieService.getMovieById(movieId);

        LocalDate rentalDate = LocalDate.now();
        LocalDate returnDate = rentRequest.getReturnDate();

        long days = ChronoUnit.DAYS.between(rentalDate, returnDate);
        if (days <= 0) {
            throw new RuntimeException("Return date must be after rental date.");
        }

        double dailyRate = movie.getRentalPrice();
        if (dailyRate <= 0) {
            throw new RuntimeException("Invalid rental price for movie.");
        }

        double totalAmount = dailyRate * days;

        rentRequest.setCustomer(customer);
        rentRequest.setMovie(movie);
        rentRequest.setRentalDate(rentalDate);
        rentRequest.setTotalAmount(totalAmount);

        return rentalRepo.save(rentRequest);
    }

    @Override
    public Rental getRentalById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid rental ID.");
        }

        return rentalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental with ID " + id + " not found."));
    }

    @Override
    public List<Rental> getAllRentals() {
        List<Rental> rentals = rentalRepo.findAll();
        if (rentals.isEmpty()) {
            throw new RuntimeException("No rentals found.");
        }
        return rentals;
    }

    @Override
    public List<Rental> getRentalsByCustomerId(Long customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID.");
        }

        List<Rental> rentals = rentalRepo.getRentalsByCustomerId(customerId);
        if (rentals.isEmpty()) {
            throw new RuntimeException("No rentals found for customer ID: " + customerId);
        }
        return rentals;
    }

    @Override
    public List<Rental> getOverdueRentals() {
        List<Rental> overdueRentals = rentalRepo.getOverdueRentals();
        if (overdueRentals.isEmpty()) {
            throw new RuntimeException("No overdue rentals found.");
        }
        return overdueRentals;
    }

    @Override
    public List<Rental> getUnreturnedRentals() {
        List<Rental> unreturned = rentalRepo.getUnreturnedRentals();
        if (unreturned.isEmpty()) {
            throw new RuntimeException("All rentals have been returned.");
        }
        return unreturned;
    }
}
