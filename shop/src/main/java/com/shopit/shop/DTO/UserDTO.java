package com.shopit.shop.DTO;

import com.shopit.shop.Entity.Types.UserRole;
import com.shopit.shop.Entity.User;

public record UserDTO(String username, String email, String role, String zipCode, String address, String phone) {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),   // UserRole enum → String
                user.getZipCode(),
                user.getAddress(),
                user.getPhone()
        );
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setRole(UserRole.valueOf(dto.role()));  // String → UserRole enum
        user.setZipCode(dto.zipCode());
        user.setAddress(dto.address());
        user.setPhone(dto.phone());
        // password, firstName, lastName are NOT in UserDTO — set separately
        return user;
    }
}
