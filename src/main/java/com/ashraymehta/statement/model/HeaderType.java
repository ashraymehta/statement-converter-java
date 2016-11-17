package com.ashraymehta.statement.model;

public enum HeaderType {

    TRANSACTION_DATE("Transaction Date"),
    TRANSACTION_REMARKS("Transaction Remarks"),
    WITHDRAWAL_AMOUNT("Withdrawal Amount (INR )"),
    DEPOSIT_AMOUNT("Deposit Amount (INR )");

    private final String stringRepresentation;

    HeaderType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static HeaderType parse(String string) {
        for (HeaderType headerType : values()) {
            if (headerType.stringRepresentation.equalsIgnoreCase(string))
                return headerType;
        }
        return null;
    }
}