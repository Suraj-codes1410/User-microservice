package com.ecommerce.user.dto;

import com.ecommerce.user.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;
    private AddressDTO address;


}
