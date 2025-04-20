package com.sijan.movie.service;

import java.util.List;

import com.sijan.movie.model.Customer;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer updatedCustomer);
    void deleteCustomer(Long id);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);

    List<Customer> getCustomersWithMostRentals(); 
    List<Customer> getCustomerByEmail(String email);
}

