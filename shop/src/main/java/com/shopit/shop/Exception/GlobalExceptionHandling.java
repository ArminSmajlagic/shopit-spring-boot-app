package com.shopit.shop.Exception;

import com.shopit.shop.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(request.getDescription(false), ex.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleAllRuntimeExceptions(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(request.getDescription(false), ex.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDTO> handleNullPtrExceptions(NullPointerException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(request.getDescription(false), ex.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(request.getDescription(false), ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotValidExceptions(NotValidException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(request.getDescription(false), ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
