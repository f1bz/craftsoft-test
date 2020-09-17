package com.craftsoft.test.cdr.core.service;

import com.craftsoft.test.cdr.core.utils.RequestUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
@Service
public class CallDetailRecordService {

    @SneakyThrows
    public void storeNewRecords(MultipartFile uploadedFile) {
        String fileContent = RequestUtils.readFileContent(uploadedFile);
    }

}
