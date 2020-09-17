package com.craftsoft.test.cdr.core.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AverageCallsDetailsRequest {

    private List<String> accounts;

    private List<String> destinations;

    private Date startDatetime;

    private Date endDatetime;

    private List<String> statuses;

}
