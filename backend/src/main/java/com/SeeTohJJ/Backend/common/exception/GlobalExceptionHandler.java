package com.SeeTohJJ.Backend.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientEnergyException.class)
    public ResponseEntity<Map<String, Object>>
    handleInsufficientEnergy(InsufficientEnergyException ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("error", "INSUFFICIENT_ENERGY");
        body.put("message", "Not enough energy");
        body.put("currentEnergy", ex.getCurrentEnergy());
        body.put("requiredEnergy", ex.getRequiredEnergy());
        body.put(
                "secondsUntilNextEnergy",
                ex.getSecondsUntilNextEnergy()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}