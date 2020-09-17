package com.craftsoft.test.cdr.core.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Andrew Ruban
 * @since 18.09.2020
 */
@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RatingFrequencyRecord {

    private String phone;

    private Long amountOfCalls;

}
