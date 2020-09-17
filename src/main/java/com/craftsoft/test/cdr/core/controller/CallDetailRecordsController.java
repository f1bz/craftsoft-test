package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.service.CallDetailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller to upload file and insert new call detail records
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Slf4j
@RestController
public class CallDetailRecordsController {

    private final CallDetailRecordService callDetailRecordService;

    public CallDetailRecordsController(CallDetailRecordService callDetailRecordService) {
        this.callDetailRecordService = callDetailRecordService;
    }

    @PostMapping("/calls/average")
    public ResponseEntity<AverageCallsDetailsInfo> getAverage(@Validated @RequestBody AverageCallsDetailsRequest averageCallsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getAverage(averageCallsDetailsRequest));
    }

    @PostMapping("/calls")
    public ResponseEntity<CallsDetailsDTO> filterCalls(@Validated @RequestBody CallsDetailsRequest callsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getAllCallDetailRecords(callsDetailsRequest));
    }

    @GetMapping("/call/{uuid}")
    public ResponseEntity<CallDetailRecord> getCall(@PathVariable(value = "uuid") String uuid) {
        return ResponseEntity.of(callDetailRecordService.findByUUID(UUID.fromString(uuid)));
    }

}