package com.ashraymehta.statement.util;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Optional;

public class CellUtil {
    public static Optional<String> getStringValue(Cell cell) {
        Object value = null;
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case BLANK:
                value = null;
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case ERROR:
                value = cell.getErrorCellValue();
                break;
        }

        if (value == null) {
            return Optional.empty();
        } else {
            return Optional.of(String.valueOf(value));
        }
    }
}