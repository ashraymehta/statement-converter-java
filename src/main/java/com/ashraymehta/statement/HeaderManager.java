package com.ashraymehta.statement;

import com.ashraymehta.statement.model.Config;
import com.ashraymehta.statement.model.ConfiguredHeader;
import com.ashraymehta.statement.model.ConfiguredHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

class HeaderManager {
    private static final String HEADERS_FILE_NAME = "headers.json";

    private ConfiguredHeaders configuredHeaders;

    void initialize() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(HEADERS_FILE_NAME);
        final Config config = objectMapper.readValue(inputStream, Config.class);

        configuredHeaders = config.getHeaders();
    }

    void createHeaderIfNeeded(Cell cell) {
        final CellAddress cellAddress = cell.getAddress();
        try {
            final String stringCellValue = cell.getStringCellValue();
            final Optional<ConfiguredHeader> headerConfigOptional = configuredHeaders.get(stringCellValue);
            headerConfigOptional.ifPresent(configuredHeader -> {
                System.out.println("Found header " + stringCellValue + " [" + cellAddress + "] of type : " + stringCellValue);
                configuredHeader.setAddress(cell.getAddress());
            });
        } catch (Exception ex) {
            System.err.println("Could not parse header at [" + cellAddress + "]");
        }
    }

    Optional<ConfiguredHeader> getHeader(Cell cell) {
        final int cellColumn = cell.getAddress().getColumn();
        final int cellRow = cell.getAddress().getRow();
        for (ConfiguredHeader header : configuredHeaders) {
            if (header.wasFound()) {
                if (header.getRow() > cellRow) {
                    continue;
                } else if (header.getColumn() == cellColumn && header.getRow() == cellRow) {
                    return Optional.empty();
                } else if (header.getColumn() == cellColumn) {
                    return Optional.of(header);
                }
            }
        }
        return Optional.empty();
    }

    ConfiguredHeaders getConfiguredHeaders() {
        return configuredHeaders;
    }
}