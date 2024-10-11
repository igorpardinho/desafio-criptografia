package com.example.hexa.repository;


import com.example.hexa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer save(Customer customer);

    @Override
    List<Customer> findAll();

    @Override
    Optional<Customer> findById(Long id);


    @Override
    void deleteById(Long id);
}
