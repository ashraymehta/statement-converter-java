package com.ashraymehta.statement;

import com.ashraymehta.statement.model.ConfiguredHeader;
import com.ashraymehta.statement.model.ConfiguredHeaders;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

class CSVWriter {

    private static final char SEPARATOR = ',';
    private final FileWriter writer;

    CSVWriter(FileWriter writer) {
        this.writer = writer;
    }

    void write(ConfiguredHeaders headers) throws IOException {
        writer.append(headers.getOutputValue(SEPARATOR));
        writer.append(System.lineSeparator());
    }

    void write(ConfiguredHeaders configuredHeaders, List<HashMap<ConfiguredHeader, String>> rows) throws IOException {
        for (HashMap<ConfiguredHeader, String> row : rows) {
            final StringBuilder stringBuilder = new StringBuilder();
            configuredHeaders.forEach(configuredHeader -> {
                if (row.containsKey(configuredHeader)) {
                    stringBuilder.append(row.get(configuredHeader));
                }
                stringBuilder.append(SEPARATOR);
            });
            writer.write(stringBuilder.toString());
            writer.append(System.lineSeparator());
        }
    }

    void close() throws IOException {
        writer.flush();
        writer.close();
    }
}