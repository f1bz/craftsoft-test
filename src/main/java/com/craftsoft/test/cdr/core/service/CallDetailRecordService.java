package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.repository.CallDetailRecordRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service that performs insert operation with call details records.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordService {

    private final CallDetailRecordCSVParser callDetailRecordCSVParser;
    private final CallDetailRecordRepository callDetailRecordRepository;

    public CallDetailRecordService(CallDetailRecordCSVParser callDetailRecordCSVParser, CallDetailRecordRepository callDetailRecordRepository, CallDetailRecordStatisticsCalculator callDetailRecordStatisticsCalculator) {
        this.callDetailRecordCSVParser = callDetailRecordCSVParser;
        this.callDetailRecordRepository = callDetailRecordRepository;
        this.callDetailRecordStatisticsCalculator = callDetailRecordStatisticsCalculator;
    }

    /**
     * Insert new call details records.
     *
     * @param uploadedFile the uploaded file from HTTP request
     */
    @SneakyThrows
    @Transactional
    public void insertNewRecords(MultipartFile uploadedFile) {
        if (uploadedFile == null) {
            throw new CdrApiException("Missing required request parameter: \"file\"");
        }
        final List<CallDetailRecord> callDetailRecords = callDetailRecordCSVParser.parseRecords(uploadedFile.getBytes());
        if (!callDetailRecords.isEmpty()) {
            checkPresence(callDetailRecords);
            storeRecords(callDetailRecords);
        } else {
            throw new CdrApiException("File contains no rows");
        }
    }

    private void checkPresence(List<CallDetailRecord> callDetailRecords) {
        final List<UUID> uuidsToSave = callDetailRecords.stream().
                map(CallDetailRecord::getId)
                .collect(Collectors.toList());
        final List<String> allUUIDThatAlreadyExist = callDetailRecordRepository.getAllThatExists(uuidsToSave)
                .stream()
                .map(uuid -> uuid.getId().toString())
                .collect(Collectors.toList());
        if (!allUUIDThatAlreadyExist.isEmpty()) {
            throw new CdrApiException("Following uuids already exist: " + allUUIDThatAlreadyExist);
        }
    }

    private void storeRecords(List<CallDetailRecord> callDetailRecords) {
        callDetailRecordRepository.saveAll(callDetailRecords);
    }

    private final CallDetailRecordStatisticsCalculator callDetailRecordStatisticsCalculator;

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
        return new CallsDetailsDTO(page, callsDetailsRequest.getResultsPerPage(), totalPages, totalRecords, records);
    }

    @Transactional
    public Optional<CallDetailRecord> findByUUID(UUID uuid) {
        return callDetailRecordRepository.findById(uuid);
    }


}
