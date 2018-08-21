package com.github.adam6806.quickjava.comparison;

import com.github.adam6806.quickjava.parse.LineItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Adam on 6/17/2017.
 */
public class DifferenceCalculator {

    public static List<LineItem> calculateDifference(List<LineItem> previousLineItems, List<LineItem> currentLineItems) {

        List<LineItem> newLineItems = new ArrayList();
        ListIterator<LineItem> currentIterator = currentLineItems.listIterator();
        LineItem newTotalLineItem = new LineItem();
        newTotalLineItem.setDescription("Total");
        Double totalDebit = 0.0;
        Double totalCredit = 0.0;
        while (currentIterator.hasNext()) {
            LineItem currentLineItem = currentIterator.next();
            ListIterator<LineItem> previousIterator = previousLineItems.listIterator();
            boolean lineItemNotFound = true;
            while (previousIterator.hasNext()) {
                LineItem previousLineItem = previousIterator.next();
                if (currentLineItem.equals(previousLineItem)) {
                    LineItem newLineItem = currentLineItem.minus(previousLineItem);
                    totalDebit += newLineItem.getDebit();
                    totalCredit += newLineItem.getCredit();
                    newLineItems.add(newLineItem);
                    previousIterator.remove();
                    lineItemNotFound = false;
                    break;
                }
            }
            if (lineItemNotFound) {
                newLineItems.add(currentLineItem);
                totalDebit += currentLineItem.getDebit();
                totalCredit += currentLineItem.getCredit();
            }
        }

        newTotalLineItem.setDebit(totalDebit);
        newTotalLineItem.setCredit(totalCredit);
        newLineItems.add(newTotalLineItem);

        for (LineItem previousLineItem : previousLineItems) {
            previousLineItem.setDescription(previousLineItem.getDescription() + " **WAS NOT IN CURRENT FILE**");
        }
        return newLineItems;
    }
}