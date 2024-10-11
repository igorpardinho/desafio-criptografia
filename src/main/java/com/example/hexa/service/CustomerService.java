package com.example.hexa.service;

import com.example.hexa.dto.CustomerDTO;
import com.example.hexa.entity.Customer;

import java.util.List;


public interface CustomerService {

    Customer save(CustomerDTO customerDTO);

    List<Customer> findAll();

    Customer findById(Long id);

    void delete(Long id);

}
