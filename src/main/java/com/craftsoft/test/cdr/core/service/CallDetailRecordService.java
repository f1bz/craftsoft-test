package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service that performs different operations with call details records .
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordService {

    private final CallDetailRecordCSVParser callDetailRecordCSVParser;

    public CallDetailRecordService(CallDetailRecordCSVParser callDetailRecordCSVParser) {
        this.callDetailRecordCSVParser = callDetailRecordCSVParser;
    }

    /**
     * Insert new call details records.
     *
     * @param uploadedFile the uploaded file from HTTP request
     */
    @SneakyThrows
    public void insertNewRecords(MultipartFile uploadedFile) {
        if (uploadedFile == null) {
            throw new CdrApiException("Missing required request parameter: \"file\"");
        }
        final List<CallDetailRecord> callDetailRecords = callDetailRecordCSVParser.parseRecords(uploadedFile.getBytes());

    }

}
