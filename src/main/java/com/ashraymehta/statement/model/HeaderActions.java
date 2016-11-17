package com.ashraymehta.statement.model;

import com.ashraymehta.statement.HeaderAction;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;

public class HeaderActions extends ArrayList<HeaderAction> {

    public static HeaderActions create() {
        return new HeaderActions() {{
            add(new HeaderAction.DepositAmountAction());
            add(new HeaderAction.TransactionDateAction());
            add(new HeaderAction.WithdrawalAmountAction());
            add(new HeaderAction.TransactionRemarksAction());
        }};
    }

    public void executeFor(Header header, Transaction transaction, Cell cell) {
        forEach(headerAction -> {
            if (headerAction.supports(header.getType())) {
                headerAction.execute(transaction, cell);
            }
        });
    }
}