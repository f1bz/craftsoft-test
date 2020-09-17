package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Service that provides different statistics and information.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordStatisticsCalculator {

    public AverageCallsDetailsInfo calculateAverage(List<CallDetailRecord> callDetailRecords) {
        BigDecimal averageCostPerMinute = BigDecimal.ZERO;
        BigDecimal averageDuration = BigDecimal.ZERO;
        BigDecimal totalCalls = BigDecimal.valueOf(callDetailRecords.size());
        BigDecimal averageTotalCallCost = BigDecimal.ZERO;
        for (CallDetailRecord callDetailRecord : callDetailRecords) {
            averageCostPerMinute = averageCostPerMinute.add(callDetailRecord.getCostPerMinute());
            averageDuration = averageDuration.add(callDetailRecord.getTotalCallDuration());
            averageTotalCallCost = averageTotalCallCost.add(callDetailRecord.getTotalCallCost());
        }
        if (!totalCalls.equals(BigDecimal.ZERO)) {
            averageCostPerMinute = averageCostPerMinute.divide(totalCalls, 10, RoundingMode.HALF_UP);
            averageDuration = averageDuration.divide(totalCalls, 10, RoundingMode.UP);
            averageTotalCallCost = averageTotalCallCost.divide(totalCalls, 10, RoundingMode.UP);
        }
        return new AverageCallsDetailsInfo(averageTotalCallCost, averageCostPerMinute, averageDuration, totalCalls);
    }
}
