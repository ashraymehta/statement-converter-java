package com.ashraymehta.statement;

import com.ashraymehta.statement.model.HeaderType;
import com.ashraymehta.statement.model.Transaction;
import org.apache.poi.ss.usermodel.Cell;

public interface HeaderAction {
    void execute(Transaction transaction, Cell cell);

    boolean supports(HeaderType headerType);

    class TransactionDateAction implements HeaderAction {
        @Override
        public void execute(Transaction transaction, Cell cell) {
            transaction.setDate(cell.getStringCellValue());
        }

        @Override
        public boolean supports(HeaderType headerType) {
            return headerType == HeaderType.TRANSACTION_DATE;
        }
    }

    class TransactionRemarksAction implements HeaderAction {
        @Override
        public void execute(Transaction transaction, Cell cell) {
            transaction.setMemo(cell.getStringCellValue());
        }

        @Override
        public boolean supports(HeaderType headerType) {
            return headerType == HeaderType.TRANSACTION_REMARKS;
        }
    }

    class WithdrawalAmountAction implements HeaderAction {
        @Override
        public void execute(Transaction transaction, Cell cell) {
            transaction.setWithdrawalAmount(cell.getNumericCellValue());
        }

        @Override
        public boolean supports(HeaderType headerType) {
            return headerType == HeaderType.WITHDRAWAL_AMOUNT;
        }
    }

    class DepositAmountAction implements HeaderAction {
        @Override
        public void execute(Transaction transaction, Cell cell) {
            transaction.setDepositAmount(cell.getNumericCellValue());
        }

        @Override
        public boolean supports(HeaderType headerType) {
            return headerType == HeaderType.DEPOSIT_AMOUNT;
        }
    }
}