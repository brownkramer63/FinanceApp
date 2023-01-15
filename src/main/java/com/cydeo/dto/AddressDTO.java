package com.cydeo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {


  private Long id;

  @NotBlank(message = "Address is required field")
  @Size(max = 100, min = 2)
  private String addressLine1;

  @NotBlank(message = "Address id required field")
  @Size(max = 100, min = 2, message = "Address should have maximum of 100 characters long")
  private String addressLine2;

  @NotBlank(message = "City is a required field")
  @Size(max = 50, min = 2)
  private String city;

  @NotBlank(message = "State is a required field")
  @Size(max = 50, min = 2)
  private String state;

  @NotBlank(message = "Country is a required field")
  @Size(max = 50, min = 2)
  private String country;

  @NotBlank(message = "Zipcode is a required field.")
  @Pattern(regexp = "^\\d{5}([-]|\\s*)?(\\d{4})?$", message = "Zipcode should have a valid form.")
  private String zipCode;
}
