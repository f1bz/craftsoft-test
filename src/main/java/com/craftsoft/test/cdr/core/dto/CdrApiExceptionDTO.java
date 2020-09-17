package com.craftsoft.test.cdr.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@AllArgsConstructor
@Data
public class CdrApiExceptionDTO {

    public static final CdrApiExceptionDTO DEFAULT_CDR_API_EXCEPTION_DTO = new CdrApiExceptionDTO("Oops, something went wrong! Try again later.");

    private final String description;
}
