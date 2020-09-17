package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Call detail record validator.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Component
public class CallDetailRecordValidator {

    /**
     * Validate record for 4 criteria:
     * - Account and destination is not same
     * - Start date is before end date
     * - Cost per minute is not negative
     * - All fields are filled and not null
     *
     * @param cdr the call detail record
     */
    public void validateRecord(CallDetailRecord cdr) {
        if (cdr.getStartDatetime().toInstant().isAfter(cdr.getEndDatetime().toInstant())) {
            throw new CdrApiException(String.format("Error with record %s - start date is after end date!", cdr.getId().toString()));
        }
        if (cdr.getAccount().equals(cdr.getDestination())) {
            throw new CdrApiException(String.format("Error with record %s - account is same as destination!", cdr.getId().toString()));
        }
        if (cdr.getCostPerMinute().doubleValue() < 0) {
            throw new CdrApiException(String.format("Error with record %s - cost per minute cannot be negative!", cdr.getId().toString()));
        }
        if (cdr.getCostPerMinute() == null || cdr.getId() == null || isBlank(cdr.getAccount())
                || isBlank(cdr.getDestination()) || cdr.getStartDatetime() == null || cdr.getEndDatetime() == null) {
            throw new CdrApiException(String.format("Error with record %s - some fields are null or empty!", cdr.getId()));
        }
        // Here we might need to add other checks for proper account and destination formats using patterns
    }

}
