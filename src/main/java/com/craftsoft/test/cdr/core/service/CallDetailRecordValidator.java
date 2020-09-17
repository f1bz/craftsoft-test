package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import org.springframework.stereotype.Component;

/**
 * Call detail record validator.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Component
public class CallDetailRecordValidator {

    /**
     * Validate record for 3 criteria:
     *  - Account and destination is not same
     *  - Start date is before end date
     *  - Cost per minute is not negative
     *
     * @param callDetailRecord the call detail record
     */
    public void validateRecord(CallDetailRecord callDetailRecord) {
        if (callDetailRecord.getStartDatetime().isAfter(callDetailRecord.getEndDatetime())) {
            throw new CdrApiException(String.format("Error with record %s - start date is after end date!", callDetailRecord.getId().toString()));
        }
        if (callDetailRecord.getAccount().equals(callDetailRecord.getDestination())) {
            throw new CdrApiException(String.format("Error with record %s - account is same as destination!", callDetailRecord.getId().toString()));
        }
        if (callDetailRecord.getCostPerminute().doubleValue() < 0) {
            throw new CdrApiException(String.format("Error with record %s - cost per minute cannot be negative!", callDetailRecord.getId().toString()));
        }
    }

}
