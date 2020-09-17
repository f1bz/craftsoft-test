package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.entity.RatingFrequencyRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.FrequencyRatingCallsDetailsRequest;
import com.craftsoft.test.cdr.core.repository.CallDetailRecordRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public CallDetailRecordService(CallDetailRecordCSVParser callDetailRecordCSVParser, CallDetailRecordRepository callDetailRecordRepository, CallDetailRecordAverageCalculator callDetailRecordAverageCalculator) {
        this.callDetailRecordCSVParser = callDetailRecordCSVParser;
        this.callDetailRecordRepository = callDetailRecordRepository;
        this.callDetailRecordAverageCalculator = callDetailRecordAverageCalculator;
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

    private final CallDetailRecordAverageCalculator callDetailRecordAverageCalculator;

    /**
     * Gets average info.
     *
     * @param averageCallsDetailsRequest the average calls details request
     * @return the average
     */
    @Transactional
    public AverageCallsDetailsInfo getAverage(AverageCallsDetailsRequest averageCallsDetailsRequest) {
        final List<CallDetailRecord> allForAverage = callDetailRecordRepository.findAllWithParams(
                averageCallsDetailsRequest.getAccounts(),
                averageCallsDetailsRequest.getDestinations(),
                averageCallsDetailsRequest.getStatuses(),
                averageCallsDetailsRequest.getStartDatetime(),
                averageCallsDetailsRequest.getEndDatetime(),
                averageCallsDetailsRequest.getMinCallDurationInSeconds(),
                averageCallsDetailsRequest.getMaxCallDurationInSeconds(),
                Pageable.unpaged()
        );

        return callDetailRecordAverageCalculator.calculateAverage(allForAverage);
    }

    /**
     * Gets all call detail records.
     *
     * @param callsDetailsRequest the calls details request
     * @return the all call detail records
     */
    @Transactional
    public CallsDetailsDTO getAllCallDetailRecords(CallsDetailsRequest callsDetailsRequest) {
        final Long totalRecords = callDetailRecordRepository.findCountAllForAverage(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getMinCallDurationInSeconds(),
                callsDetailsRequest.getMaxCallDurationInSeconds()
        );
        final Long page = callsDetailsRequest.getPage();
        PageRequest pageRequest = PageRequest.of(
                page.intValue() - 1,
                callsDetailsRequest.getResultsPerPage().intValue(),
                Sort.by(callsDetailsRequest.getSortDirection(), callsDetailsRequest.getSortBy()));
        final long totalPages = totalRecords / callsDetailsRequest.getResultsPerPage() + (totalRecords % callsDetailsRequest.getResultsPerPage() == 0 ? 0 : 1);
        final List<CallDetailRecord> records = callDetailRecordRepository.findAllWithParams(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getMinCallDurationInSeconds(),
                callsDetailsRequest.getMaxCallDurationInSeconds(),
                pageRequest
        );
        return new CallsDetailsDTO(page, callsDetailsRequest.getResultsPerPage(), totalPages, totalRecords, records);
    }

    /**
     * Find by uuid.
     *
     * @param uuid the uuid
     * @return the optional record
     */
    @Transactional
    public Optional<CallDetailRecord> findByUUID(UUID uuid) {
        return callDetailRecordRepository.findById(uuid);
    }

    /**
     * Gets top account frequency rating.
     *
     * @param callsDetailsRequest the calls details request
     * @return the top account frequency rating
     */
    @Transactional
    public List<RatingFrequencyRecord> getTopAccountFrequencyRating(FrequencyRatingCallsDetailsRequest callsDetailsRequest) {
        return callDetailRecordRepository.findAccountRatingFrequency(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getMinCallDurationInSeconds(),
                callsDetailsRequest.getMaxCallDurationInSeconds());
    }

    /**
     * Gets top destination frequency rating.
     *
     * @param callsDetailsRequest the calls details request
     * @return the top destination frequency rating
     */
    @Transactional
    public List<RatingFrequencyRecord> getTopDestinationFrequencyRating(FrequencyRatingCallsDetailsRequest callsDetailsRequest) {
        return callDetailRecordRepository.findDestinationRatingFrequency(
                callsDetailsRequest.getAccounts(),
                callsDetailsRequest.getDestinations(),
                callsDetailsRequest.getStatuses(),
                callsDetailsRequest.getStartDatetime(),
                callsDetailsRequest.getEndDatetime(),
                callsDetailsRequest.getMinCallDurationInSeconds(),
                callsDetailsRequest.getMaxCallDurationInSeconds());
    }
}
