package com.shopit.shop.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "Username can not be blank.")
        @Size(min=5, max = 25, message = "Username must not be less then 5 characters and longer then 25 characters.")
        String username,
        @NotBlank(message = "Password can not be blank.")
        @Size(min=8, max = 25, message = "Password must not be less then 8 characters and longer then 25 characters.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "" +
                "At least one lowercase letter\n" +
                "At least one uppercase letter\n" +
                "At least one digit\n" +
                "At least one special character (@$!%*?&)\n" +
                "Minimum 8 characters")
        String password,
        @Email(message = "Email is not valid.")
        String email,
        @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN.")
        String role,
        String zipCode,
        String address,
        String phone) {
}
