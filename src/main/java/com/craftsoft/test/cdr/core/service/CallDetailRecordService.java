package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.entity.CallDetailRecordView;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import com.craftsoft.test.cdr.core.repository.CallDetailRecordRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service that performs different operations with call details records .
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordService {

    private final CallDetailRecordCSVParser callDetailRecordCSVParser;
    private final CallDetailRecordRepository callDetailRecordRepository;

    public CallDetailRecordService(CallDetailRecordCSVParser callDetailRecordCSVParser, CallDetailRecordRepository callDetailRecordRepository) {
        this.callDetailRecordCSVParser = callDetailRecordCSVParser;
        this.callDetailRecordRepository = callDetailRecordRepository;
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

}
