package com.ashraymehta.statement.util;

import com.ashraymehta.statement.model.ConfiguredHeader;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Optional;

public class CellUtil {
    public static Optional<String> getValue(Cell cell, ConfiguredHeader header) {
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
            final String stringValue = String.valueOf(value);
            if (header.isAmount()) {
                return Optional.of(StringUtil.parseAsAmount(stringValue));
            }
            return Optional.of(stringValue);
        }
    }
}