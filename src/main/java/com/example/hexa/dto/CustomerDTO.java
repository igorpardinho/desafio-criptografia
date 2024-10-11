package com.example.hexa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private Long id;
    @NotBlank
    private String userDocument;

    @NotBlank
    private String creditCardToken;

    @NotNull
    private Long value;







}
