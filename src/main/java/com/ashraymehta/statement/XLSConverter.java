package com.ashraymehta.statement;

import com.ashraymehta.statement.model.ConfiguredHeader;
import com.ashraymehta.statement.util.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class XLSConverter {

    private static final Logger logger = LoggerFactory.getLogger(XLSConverter.class);

    void convert(File xlsFile) throws IOException {
        final HeaderManager headerManager = new HeaderManager();

        final List<HashMap<ConfiguredHeader, String>> rows = readCSV(xlsFile, headerManager);

        final Path outputPath = Paths.get(xlsFile.getParentFile().getCanonicalPath(), xlsFile.getName() + ".csv");
        logger.info("Creating file {}", outputPath);
        FileWriter writer = new FileWriter(outputPath.toFile());
        final CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.write(headerManager.getConfiguredHeaders());

        csvWriter.write(headerManager.getConfiguredHeaders(), rows);

        csvWriter.close();
        logger.info("Created file at {}", outputPath.getParent());
    }

    private List<HashMap<ConfiguredHeader, String>> readCSV(File csvFile, HeaderManager headerManager) throws IOException {
        final FileInputStream inputStream = new FileInputStream(csvFile);
        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet firstSheet = workbook.getSheetAt(0);

        headerManager.initialize();
        createHeaders(headerManager, firstSheet);

        final List<HashMap<ConfiguredHeader, String>> transactions = parseRows(firstSheet, headerManager);

        logger.info("Found {} transactions!", transactions.size());
        workbook.close();
        inputStream.close();
        return transactions;
    }

    private ArrayList<HashMap<ConfiguredHeader, String>> parseRows(Sheet firstSheet, HeaderManager headerManager) {
        final ArrayList<HashMap<ConfiguredHeader, String>> rows = new ArrayList<>();
        firstSheet.forEach(row -> {
            HashMap<ConfiguredHeader, String> values = new HashMap<>();
            row.forEach(cell -> {
                headerManager.getHeader(cell).ifPresent(header -> {
                    final Optional<String> optionalValue = CellUtil.getValue(cell, header);
                    optionalValue.ifPresent(value -> values.put(header, value));
                });
            });
            if (!values.isEmpty()) {
                rows.add(values);
            }
        });
        return rows;
    }

    private void createHeaders(HeaderManager headerManager, Sheet firstSheet) {
        for (Row nextRow : firstSheet) {
            final Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                if (cell.getCellTypeEnum() == CellType.BLANK)
                    continue;

                headerManager.createHeaderIfNeeded(cell);
            }
        }
    }
}
