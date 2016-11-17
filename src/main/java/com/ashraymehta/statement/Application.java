package com.ashraymehta.statement;

import com.ashraymehta.statement.model.Header;
import com.ashraymehta.statement.model.HeaderActions;
import com.ashraymehta.statement.model.HeaderType;
import com.ashraymehta.statement.model.Transaction;
import com.beust.jcommander.JCommander;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Application {
    public static void main(String... args) throws IOException {
        final Arguments arguments = new Arguments();
        new JCommander(arguments, args);

        final HeaderManager headerManager = new HeaderManager();
        final HeaderActions headerActions = HeaderActions.create();
        final List<Transaction> transactions = new ArrayList<>();

        final FileInputStream inputStream = new FileInputStream(arguments.file);
        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet firstSheet = workbook.getSheetAt(0);

        for (Row nextRow : firstSheet) {
            final Iterator<Cell> cellIterator = nextRow.cellIterator();
            Transaction transaction = new Transaction();

            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                if (cell.getCellTypeEnum() == CellType.BLANK)
                    continue;

                createHeaderIfNeeded(headerManager, cell);
                final Header header = headerManager.getHeader(cell);
                if (header != null) {
                    headerActions.executeFor(header, transaction, cell);
                }
            }

            if (transaction.isNotEmpty()) {
                System.out.println("Transaction: [" + transaction.toString() + "]");
                transactions.add(transaction);
            }
        }

        System.out.println("Found [" + transactions.size() + "] transactions!");
        workbook.close();
        inputStream.close();
    }

    private static void createHeaderIfNeeded(HeaderManager headerManager, Cell cell) {
        if (headerManager.areHeadersCreated())
            return;

        final CellAddress cellAddress = cell.getAddress();
        try {
            final HeaderType headerType = HeaderType.parse(cell.getStringCellValue());
            if (headerType != null) {
                System.out.println("Found header " + cell.getStringCellValue() + " [" + cellAddress + "] of type : " + headerType);
                headerManager.createHeader(headerType, cell);
            }
        } catch (Exception ex) {
            System.err.println("Could not parse header at [" + cellAddress + "]");
        }
    }
}