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

    private final String description;
}
