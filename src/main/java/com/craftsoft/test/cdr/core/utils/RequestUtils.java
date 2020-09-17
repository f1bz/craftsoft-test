package com.craftsoft.test.cdr.core.utils;

import com.craftsoft.test.cdr.core.exception.CdrApiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Andrew Ruban
 * @since 17.09.2020
 */
public class RequestUtils {

    private RequestUtils() {
    }

    public static String readFileContent(MultipartFile uploadedFile) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(uploadedFile.getBytes())) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CdrApiException("Cannot read content from uploaded file");
        }
    }
}
