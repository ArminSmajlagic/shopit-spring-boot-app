package com.shopit.shop.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Username can not be blank.")
        @Size(min=5, max = 25, message = "Username must not be less then 5 characters and longer then 25 characters.")
        String username,
        @NotBlank(message = "Password can not be blank.")
        @Size(min=8, max = 25, message = "Password must not be less then 8 characters and longer then 25 characters.")
        String password) {
}
