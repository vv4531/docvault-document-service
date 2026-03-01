package com.docvault.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Extracts plain text from uploaded documents using Apache Tika.
 * Extracted text is forwarded to Azure AI Search for indexing.
 *
 * Supports: PDF, DOCX, XLSX, PPTX, TXT, CSV, and 1000+ other formats.
 */
@Service
public class TextExtractionService {

    private static final Logger log    = LoggerFactory.getLogger(TextExtractionService.class);
    private static final int    MAX_LEN = 500_000; // Max chars to index
    private final Tika           tika   = new Tika();

    /**
     * Extract plain text from a multipart file.
     *
     * @param file uploaded file
     * @return extracted text (capped at MAX_LEN chars), or empty string on failure
     */
    public String extract(MultipartFile file) {
        try {
            String text = tika.parseToString(file.getInputStream(), MAX_LEN);
            log.debug("[Tika] Extracted {} chars from {}", text.length(), file.getOriginalFilename());
            return text;
        } catch (IOException | TikaException e) {
            log.warn("[Tika] Extraction failed for {}: {}", file.getOriginalFilename(), e.getMessage());
            return "";
        }
    }
}
