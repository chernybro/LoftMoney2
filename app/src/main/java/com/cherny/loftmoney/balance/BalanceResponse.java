package com.cherny.loftmoney.balance;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {

    private String status;

    @SerializedName("total_expenses")
    private int totalExpences;

    @SerializedName("total_income")
    private int totalIncome;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public int getTotalExpences() {
        return totalExpences;
    }

    public void setTotalExpences(final int totalExpences) {
        this.totalExpences = totalExpences;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(final int totalIncome) {
        this.totalIncome = totalIncome;
    }
}
