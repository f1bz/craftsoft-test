package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.dto.AverageCallsDetailsInfo;
import com.craftsoft.test.cdr.core.dto.CallsDetailsDTO;
import com.craftsoft.test.cdr.core.entity.CallDetailRecord;
import com.craftsoft.test.cdr.core.entity.RatingFrequencyRecord;
import com.craftsoft.test.cdr.core.payload.AverageCallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.CallsDetailsRequest;
import com.craftsoft.test.cdr.core.payload.FrequencyRatingCallsDetailsRequest;
import com.craftsoft.test.cdr.core.service.CallDetailRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Operation(summary = "Get average statistics for filtered params", description = "Upload files containing call details records in csv format")
    @PostMapping("/calls/average")
    public ResponseEntity<AverageCallsDetailsInfo> getAverage(@Validated @RequestBody AverageCallsDetailsRequest averageCallsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getAverage(averageCallsDetailsRequest));
    }

    @Operation(summary = "Get average statistics for filtered params", description = "Upload files containing call details records in csv format")
    @PostMapping("/calls")
    public ResponseEntity<CallsDetailsDTO> filterCalls(@Validated @RequestBody CallsDetailsRequest callsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getAllCallDetailRecords(callsDetailsRequest));
    }

    @Operation(summary = "Get call details record", description = "Get all call details record")
    @GetMapping("/call/{uuid}")
    public ResponseEntity<CallDetailRecord> getCall(@PathVariable(value = "uuid") String uuid) {
        return ResponseEntity.of(callDetailRecordService.findByUUID(UUID.fromString(uuid)));
    }

    @Operation(summary = "Get account top list", description = "Get outcoming account top list with amount of class with filtered params")
    @PostMapping("/calls/account-rating")
    public ResponseEntity<List<RatingFrequencyRecord>> getTopAccountFrequencyRating(@Validated @RequestBody FrequencyRatingCallsDetailsRequest callsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getTopAccountFrequencyRating(callsDetailsRequest));
    }

    @Operation(summary = "Get destination top list", description = "Get incoming destination top list with amount of class with filtered params")
    @PostMapping("/calls/destination-rating")
    public ResponseEntity<List<RatingFrequencyRecord>> getTopDestinationFrequencyRating(@Validated @RequestBody FrequencyRatingCallsDetailsRequest callsDetailsRequest) {
        return ResponseEntity.ok(callDetailRecordService.getTopDestinationFrequencyRating(callsDetailsRequest));
    }

}