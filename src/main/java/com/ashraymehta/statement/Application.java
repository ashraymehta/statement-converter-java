package com.ashraymehta.statement;

import com.ashraymehta.statement.model.ConfiguredHeader;
import com.ashraymehta.statement.util.CellUtil;
import com.beust.jcommander.JCommander;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Application {
    public static void main(String... args) throws IOException {
        final Arguments arguments = new Arguments();
        new JCommander(arguments, args);

        final HeaderManager headerManager = new HeaderManager();

        final FileInputStream inputStream = new FileInputStream(arguments.file);
        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet firstSheet = workbook.getSheetAt(0);

        headerManager.initialize();
        createHeaders(headerManager, firstSheet);

        final List<HashMap<ConfiguredHeader, String>> rows = parseRows(firstSheet, headerManager);

        System.out.println("Found [" + rows.size() + "] transactions!");
        workbook.close();
        inputStream.close();

        final Path outputPath = Paths.get(arguments.file.getParentFile().getCanonicalPath(), arguments.file.getName() + ".csv");
        System.out.println("Creating file [" + outputPath + "]");
        FileWriter writer = new FileWriter(outputPath.toFile());
        final CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.write(headerManager.getConfiguredHeaders());

        csvWriter.write(headerManager.getConfiguredHeaders(), rows);

        csvWriter.close();
    }

    private static ArrayList<HashMap<ConfiguredHeader, String>> parseRows(Sheet firstSheet, HeaderManager headerManager) {
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

    private static void createHeaders(HeaderManager headerManager, Sheet firstSheet) {
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