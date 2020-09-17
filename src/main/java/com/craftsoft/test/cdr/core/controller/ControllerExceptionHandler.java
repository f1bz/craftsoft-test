package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.dto.CdrApiExceptionDTO;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
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
     * Handle CdrApiException.
     *
     * @param e the CdrApiException
     * @return the response entity
     */
    @ExceptionHandler(CdrApiException.class)
    public ResponseEntity<CdrApiExceptionDTO> handleApiException(CdrApiException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CdrApiExceptionDTO(e.getMessage()));
    }

    /**
     * Handle other exceptions.
     *
     * @param e the other Exception
     * @return the response entity with default API exception dto
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CdrApiExceptionDTO> handleOtherException(Exception e) {
        log.error("{}:{}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CdrApiExceptionDTO.DEFAULT_CDR_API_EXCEPTION_DTO);
    }

}