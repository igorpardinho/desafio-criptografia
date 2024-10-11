package com.example.hexa.controller;

import com.example.hexa.dto.CustomerDTO;
import com.example.hexa.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerServiceImpl customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id){

        try {
            customerService.findById(id).setUserDocument(customerService
                    .encrypt(customerService.findById(id).getUserDocument()));

            customerService.findById(id).setCreditCardToken(customerService
                    .encrypt(customerService.findById(id).getCreditCardToken()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(customerService.findById(id),CustomerDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll(){

        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll()
                .stream()
                .map(v -> {
                    try {
                        v.setUserDocument(customerService.encrypt(v.getUserDocument()));
                        v.setCreditCardToken(customerService.encrypt(v.getCreditCardToken()));
                         return v;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).map(v -> modelMapper.map(v,CustomerDTO.class)).toList());

    }
   @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.save(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}