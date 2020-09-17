package com.craftsoft.test.cdr.core.dto;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO for keeping call details records.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CallsDetailsDTO {

    private Long page;

    private Long resultsPerPage;

    private Long totalPages;

    private Long totalRecords;

    private List<CallDetailRecord> callDetailRecords;

}
