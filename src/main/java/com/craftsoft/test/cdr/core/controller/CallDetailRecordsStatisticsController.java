package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.service.CallDetailRecordStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to upload file and insert new call detail records
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Slf4j
@RestController
public class CallDetailRecordsStatisticsController {

    private final CallDetailRecordStatisticsService callDetailRecordStatisticsService;

    public CallDetailRecordsStatisticsController(CallDetailRecordStatisticsService callDetailRecordStatisticsService) {
        this.callDetailRecordStatisticsService = callDetailRecordStatisticsService;
    }

    @PostMapping("/average-info")
    public ResponseEntity<AverageCallsDetailsInfo> getAverage(@Validated @RequestBody AverageCallsDetailsRequest averageCallsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordStatisticsService.getAverage(averageCallsDetailsRequest));
    }

    @PostMapping("/list-calls")
    public ResponseEntity<CallsDetailsDTO> getAverage(@Validated @RequestBody CallsDetailsRequest callsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordStatisticsService.getAllCallDetailRecords(callsDetailsRequest));
    }

}