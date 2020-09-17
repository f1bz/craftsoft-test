package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.service.CallDetailRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller to upload file and insert new call detail records
 *
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Slf4j
@RestController
public class UploadController {

    private final CallDetailRecordService callDetailRecordService;

    public UploadController(CallDetailRecordService callDetailRecordService) {
        this.callDetailRecordService = callDetailRecordService;
    }

    @Operation(summary = "Upload file in csv format", description = "Upload files containing call details records in csv format")
    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        callDetailRecordService.insertNewRecords(file);
        return ResponseEntity.accepted()
                .build();
    }

}