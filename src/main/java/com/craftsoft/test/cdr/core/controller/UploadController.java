package com.craftsoft.test.cdr.core.controller;

import com.craftsoft.test.cdr.core.service.CallDetailRecordService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@RestController
@Slf4j
public class UploadController {

    private final CallDetailRecordService callDetailRecordService;

    public UploadController(CallDetailRecordService callDetailRecordService) {
        this.callDetailRecordService = callDetailRecordService;
    }

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        callDetailRecordService.storeNewRecords(file);
        return ResponseEntity.accepted()
                .build();
    }

}