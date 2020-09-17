package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.repository.CallDetailRecordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service that provides different statistics and information.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordStatisticsService {

    private final CallDetailRecordRepository callDetailRecordRepository;
    private final CallDetailRecordStatisticsCalculator callDetailRecordStatisticsCalculator;

    public CallDetailRecordStatisticsService(CallDetailRecordRepository callDetailRecordRepository, CallDetailRecordStatisticsCalculator callDetailRecordStatisticsCalculator) {
        this.callDetailRecordRepository = callDetailRecordRepository;
        this.callDetailRecordStatisticsCalculator = callDetailRecordStatisticsCalculator;
    }

    @Transactional
    public AverageCallsDetailsInfo getAverage(AverageCallsDetailsRequest averageCallsDetailsRequest) {
        final List<CallDetailRecord> allForAverage = callDetailRecordRepository.findAllWithParams(
                averageCallsDetailsRequest.getAccounts(),
                averageCallsDetailsRequest.getDestinations(),
                averageCallsDetailsRequest.getStatuses(),
                averageCallsDetailsRequest.getStartDatetime(),
                averageCallsDetailsRequest.getEndDatetime(),
                null,
                Pageable.unpaged()
        );

        return callDetailRecordStatisticsCalculator.calculateAverage(allForAverage);
    }

    @Transactional
    public CallsDetailsDTO getAllCallDetailRecords(CallsDetailsRequest callsDetailsRequest) {
        final Long totalRecords = callDetailRecordRepository.findCountAllForAverage(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getCallDurationInSeconds()
        );
        final Long page = callsDetailsRequest.getPage();
        PageRequest pageRequest = PageRequest.of(page.intValue() - 1, callsDetailsRequest.getResultsPerPage().intValue());
        final long totalPages = totalRecords / callsDetailsRequest.getResultsPerPage() + (totalRecords % callsDetailsRequest.getResultsPerPage() == 0 ? 0 : 1);
        final List<CallDetailRecord> records = callDetailRecordRepository.findAllWithParams(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getCallDurationInSeconds(),
                pageRequest
        );
        return new CallsDetailsDTO(page, callsDetailsRequest.getResultsPerPage(), totalPages, totalRecords,records);
    }

}
