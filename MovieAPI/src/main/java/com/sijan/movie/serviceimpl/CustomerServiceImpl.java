package com.sijan.movie.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijan.movie.model.Customer;
import com.sijan.movie.repository.CustomerRepository;
import com.sijan.movie.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public Customer getCustomerById(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Customer ID.");
        }
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer with ID " + id + " not found"));
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        validateCustomerFields(customer);

        if (!customerRepo.getCustomerByEmail(customer.getEmail()).isEmpty()) {
            throw new RuntimeException("A customer with this email already exists.");
        }

        customer.setName(capitalizeFirstLetter(customer.getName()));
        customer.setEmail(customer.getEmail().toLowerCase().trim());

        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Customer ID.");
        }
        Customer existingCustomer = getCustomerById(id);

        if (updatedCustomer.getName() != null && !updatedCustomer.getName().trim().isEmpty()) {
            existingCustomer.setName(capitalizeFirstLetter(updatedCustomer.getName()));
        }

        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().trim().isEmpty()) {
            String normalizedEmail = updatedCustomer.getEmail().toLowerCase().trim();
            if (!normalizedEmail.equalsIgnoreCase(existingCustomer.getEmail())
                    && !customerRepo.getCustomerByEmail(normalizedEmail).isEmpty()) {
                throw new RuntimeException("Another customer with this email already exists.");
            }
            existingCustomer.setEmail(normalizedEmail);
        }

        if (updatedCustomer.getPhone() != null && !updatedCustomer.getPhone().trim().isEmpty()) {
            existingCustomer.setPhone(updatedCustomer.getPhone().trim());
        }

        if (updatedCustomer.getRentals() != null) {
            Set<Long> rentalIds = new HashSet<>();
            updatedCustomer.getRentals().forEach(rental -> {
                if (!rentalIds.add(rental.getId())) {
                    throw new RuntimeException("Duplicate rental entry found for customer.");
                }
            });
            existingCustomer.setRentals(updatedCustomer.getRentals());
        }

        return customerRepo.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Customer ID.");
        }
        if (!customerRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete. Customer with ID " + id + " does not exist.");
        }
        customerRepo.deleteById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new RuntimeException("No customers found.");
        }
        return customers;
    }

    @Override
    public List<Customer> getCustomersWithMostRentals() {
        List<Customer> topCustomers = customerRepo.getCustomersWithMostRentals();
        if (topCustomers.isEmpty()) {
            throw new RuntimeException("No top customers found.");
        }
        return topCustomers;
    }

    @Override
    public List<Customer> getCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        List<Customer> customers = customerRepo.getCustomerByEmail(email.toLowerCase().trim());
        if (customers.isEmpty()) {
            throw new RuntimeException("No customer found with email: " + email);
        }

        return customers;
    }

    private void validateCustomerFields(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }

        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer phone number cannot be empty.");
        }
    }

    private String capitalizeFirstLetter(String input) {
        input = input.trim();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }
}
