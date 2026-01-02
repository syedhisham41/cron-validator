package com.syedhisham41.cron_validator.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

@Component
public class WorkerSchemaLoader {

    public String schemaLoader(String path) throws IOException {

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("Schema not found :" + path);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
