package com.shopit.shop.DTO;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String path, String message, LocalDateTime timestamp, HttpStatus status) {
}
