package com.craftsoft.test.cdr.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to handle API exception.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@AllArgsConstructor
@Data
public class CdrApiExceptionDTO {

    /**
     * Default API exception dto
     */
    public static final CdrApiExceptionDTO DEFAULT_CDR_API_EXCEPTION_DTO = new CdrApiExceptionDTO("Oops, something went wrong! Try again later.");

    private final String description;
}
