package com.procyon.resume.extract.controller;

import com.procyon.resume.extract.service.ResumeParserService;
import com.procyon.resume.extract.service.TikaService;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * Author: balachandran
 * Date: 20/11/24
 */
@RestController
@RequestMapping("/v1/api/extract")
public class TikaController {

    @Autowired
    private TikaService tikaService;

    @Autowired
    private ResumeParserService resumeParserService;

    @PostMapping("/text")
    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String text = tikaService.extractText(inputStream);
            return ResponseEntity.ok(text);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error extracting text: " + e.getMessage());
        }
    }

    @PostMapping("/metadata")
    public ResponseEntity<Metadata> extractMetadata(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Metadata metadata = tikaService.extractMetadata(inputStream);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/resume")
    public ResponseEntity<Map<String, Object>> parseResume(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // Extract text from the uploaded file
            String content = resumeParserService.extractTextFromFile(inputStream);

            // Extract fields using regex and NLP
            Map<String, Object> extractedFields = resumeParserService.extractFields(content);
            return ResponseEntity.ok(extractedFields);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
