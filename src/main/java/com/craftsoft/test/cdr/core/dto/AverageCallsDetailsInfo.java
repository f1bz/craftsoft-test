package com.craftsoft.test.cdr.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for keeping different statistics values.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AverageCallsDetailsInfo {

    private BigDecimal averageCallCost;

    private BigDecimal averageCallsNumber;

    private BigDecimal averageCostPerMinute;

    private BigDecimal averageDuration;

    private BigDecimal totalCalls;

}