package com.example.hexa.service.impl;

import com.example.hexa.dto.CustomerDTO;
import com.example.hexa.entity.Customer;
import com.example.hexa.repository.CustomerRepository;
import com.example.hexa.service.CustomerService;
import com.example.hexa.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer save(CustomerDTO customerDTO) {
        return customerRepository.save(modelMapper.map(customerDTO,Customer.class));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        return optionalCustomer.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

    }

    @Override
    public void delete(Long id) {
        findById(id);
        customerRepository.deleteById(id);
    }


    public String encrypt(String valueToHash) throws NoSuchAlgorithmException {
        String generatedPassword = null;
        String salt = getSalt();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(valueToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;

    }



    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }
}
