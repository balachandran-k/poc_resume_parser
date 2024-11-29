package com.procyon.resume.extract.service;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Author: balachandran
 * Date: 20/11/24
 */
@Service
public class TikaService {

    private final Tika tika;

    public TikaService() {
        this.tika = new Tika();
    }

    // Extract plain text content
    public String extractText(InputStream inputStream) throws Exception {
        return tika.parseToString(inputStream);
    }

    // Extract metadata
    public Metadata extractMetadata(InputStream inputStream) throws Exception {
        Metadata metadata = new Metadata();
        AutoDetectParser parser = new AutoDetectParser();
        parser.parse(inputStream, new BodyContentHandler(), metadata);
        return metadata;
    }
}

