package com.craftsoft.test.cdr.core.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CallsDetailsRequest {

    private List<String> accounts;

    private List<String> destinations;

    private Date startDatetime;

    private Date endDatetime;

    private List<String> statuses;

    private String sortBy = "uuid";

    @Min(0)
    private Integer callDurationInSeconds;

    @NotNull
    @Min(1)
    private Long page;

    @NotNull
    @Max(100)
    @Min(1)
    private Long resultsPerPage;
}
