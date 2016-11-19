package com.ashraymehta.statement.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Transaction {
    private String date;
    private String memo;
    private double withdrawalAmount;
    private double depositAmount;

    public void setDate(String date) {
        this.date = date;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    private boolean isEmpty() {
        return this.withdrawalAmount == 0 && this.depositAmount == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public String getCSVValue() {
        return String.join(",", date, "", "", memo, String.valueOf(withdrawalAmount), String.valueOf(depositAmount));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}