package com.github.adam6806.quickjava.parse;

import java.text.DecimalFormat;
import java.util.Formatter;

/**
 * Adam on 6/17/2017.
 */
public class LineItem {

    private String description;
    private Double credit;
    private Double debit;
    private static DecimalFormat decimalFormat = new DecimalFormat("###,###.##");

    public LineItem() {
        description = "";
        credit = 0.0;
        debit = 0.0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCredit() {
        return credit;
    }

    public String getCreditString() {
        return decimalFormat.format(getCredit());
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public String getDebitString() {
        return decimalFormat.format(getDebit());
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public boolean equals(LineItem lineItem) {
        return getDescription().equalsIgnoreCase(lineItem.getDescription());
    }

    public LineItem minus(LineItem prevLineItem) {
        LineItem result = new LineItem();
        result.setDescription(getDescription());
        result.setCredit(getCredit() - prevLineItem.getCredit());
        result.setDebit(getDebit() - prevLineItem.getDebit());
        return result;
    }
}