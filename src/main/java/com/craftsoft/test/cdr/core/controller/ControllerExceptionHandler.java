package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.dto.CdrApiExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Main API exception handler.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * Handle occurred exception.
     *
     * @param e the e
     * @return the response entity with error description
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CdrApiExceptionDTO> handleException(Exception e) {
        log.error("{}:{}\n", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CdrApiExceptionDTO(e.getMessage()));
    }

}