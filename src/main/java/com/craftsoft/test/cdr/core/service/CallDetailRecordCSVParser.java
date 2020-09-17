package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.exception.CdrApiException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/**
 * Call detail record csv parser.
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Component
public class CallDetailRecordCSVParser {

    private final CallDetailRecordValidator callDetailRecordValidator;

    public CallDetailRecordCSVParser(CallDetailRecordValidator callDetailRecordValidator) {
        this.callDetailRecordValidator = callDetailRecordValidator;
    }

    private enum CSV_HEADERS {
        ID,
        ACCOUNT,
        DESTINATION,
        STARTDATE,
        ENDDATE,
        STATUS,
        COSTPERMINUTE
    }

    /**
     * Parse records list using bytes from file content.
     *
     * @param fileContent the input file content
     * @return the list of parsed call record details
     */
    public List<CallDetailRecord> parseRecords(byte[] fileContent) {
        try {
            Reader in = new InputStreamReader(new ByteArrayInputStream(fileContent));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(CSV_HEADERS.class)
                    .withFirstRecordAsHeader()
                    .parse(in);

            List<CallDetailRecord> parsedRecords = new LinkedList<>();
            for (CSVRecord record : records) {
                CallDetailRecord callDetailRecord = parseRecord(record);
                callDetailRecordValidator.validateRecord(callDetailRecord);
                parsedRecords.add(callDetailRecord);
            }
            return parsedRecords;
        } catch (CdrApiException e) {
            throw e;
        } catch (IOException e) {
            throw new CdrApiException("Cannot parse file - " + e.getMessage());
        } catch (Exception e) {
            throw new CdrApiException("Something went wrong - " + e.getMessage());
        }
    }

    private CallDetailRecord parseRecord(CSVRecord record) {
        String id = record.get(CSV_HEADERS.ID).trim();
        String account = record.get(CSV_HEADERS.ACCOUNT).trim();
        String destination = record.get(CSV_HEADERS.DESTINATION).trim();
        Date startDatetime = new Date(Long.parseLong(record.get(CSV_HEADERS.STARTDATE)));
        Date endDatetime = new Date(Long.parseLong(record.get(CSV_HEADERS.ENDDATE)));
        String status = record.get(CSV_HEADERS.STATUS);
        BigDecimal costPerMinute = new BigDecimal(record.get(CSV_HEADERS.COSTPERMINUTE));
        CallDetailRecord callDetailRecord = new CallDetailRecord();
        callDetailRecord.setId(UUID.fromString(id));
        callDetailRecord.setAccount(account);
        callDetailRecord.setDestination(destination);
        callDetailRecord.setStartDatetime(startDatetime);
        callDetailRecord.setEndDatetime(endDatetime);
        callDetailRecord.setStatus(status);
        callDetailRecord.setCostPerMinute(costPerMinute);
        return callDetailRecord;
    }

}
