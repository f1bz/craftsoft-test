package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that provides different statistics and information.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordAverageCalculator {

    private static final int SCALE_VALUE = 10;

    /**
     * Calculate average statistics values calls basing on list of call details records.
     *
     * @param callDetailRecords the call detail records
     * @return the average calls details info
     */
    public AverageCallsDetailsInfo calculateAverage(List<CallDetailRecord> callDetailRecords) {
        BigDecimal averageCostPerMinute = BigDecimal.ZERO;
        BigDecimal averageDuration = BigDecimal.ZERO;
        BigDecimal totalCalls = BigDecimal.valueOf(callDetailRecords.size());
        BigDecimal averageTotalCallCost = BigDecimal.ZERO;
        BigDecimal averageCallsNumber = BigDecimal.ZERO;
        Map<String, Integer> callsByAccount = new HashMap<>();

        for (CallDetailRecord callDetailRecord : callDetailRecords) {
            callsByAccount.merge(callDetailRecord.getAccount(), 1, Integer::sum);
            averageCostPerMinute = averageCostPerMinute.add(callDetailRecord.getCostPerMinute());
            averageDuration = averageDuration.add(callDetailRecord.getTotalCallDuration());
            averageTotalCallCost = averageTotalCallCost.add(callDetailRecord.getTotalCallCost());
        }

        if (!totalCalls.equals(BigDecimal.ZERO)) {
            averageCostPerMinute = averageCostPerMinute.divide(totalCalls, SCALE_VALUE, RoundingMode.HALF_UP);
            averageDuration = averageDuration.divide(totalCalls, SCALE_VALUE, RoundingMode.HALF_UP);
            averageTotalCallCost = averageTotalCallCost.divide(totalCalls, SCALE_VALUE, RoundingMode.HALF_UP);
            int totalCallsByAccounts = callsByAccount.values().stream().mapToInt(i -> i).sum();
            averageCallsNumber = BigDecimal.valueOf(totalCallsByAccounts).divide(BigDecimal.valueOf(callsByAccount.size()), SCALE_VALUE, RoundingMode.HALF_UP);
        }
        return new AverageCallsDetailsInfo(averageTotalCallCost, averageCallsNumber, averageCostPerMinute, averageDuration, totalCalls);
    }
}
