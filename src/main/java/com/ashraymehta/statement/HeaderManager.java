package com.ashraymehta.statement;

import com.ashraymehta.statement.model.Header;
import com.ashraymehta.statement.model.HeaderType;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.List;

class HeaderManager {
    private final List<Header> headers;

    HeaderManager() {
        headers = new ArrayList<>();
    }

    void createHeader(HeaderType headerType, Cell cell) {
        headers.add(new Header(cell.getAddress().getRow(), cell.getAddress().getColumn(), headerType));
    }

    Header getHeader(Cell cell) {
        final int cellColumn = cell.getAddress().getColumn();
        final int cellRow = cell.getAddress().getRow();
        for (Header header : headers) {
            if (header.getColumn() == cellColumn && header.getRow() == cellRow) {
                return null;
            } else if (header.getColumn() == cellColumn) {
                return header;
            }
        }
        return null;
    }

    boolean areHeadersCreated() {
        return headers.size() == HeaderType.values().length;
    }
}