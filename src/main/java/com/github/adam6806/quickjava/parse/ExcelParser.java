package com.github.adam6806.quickjava.parse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adam on 6/17/2017.
 */
public class ExcelParser {

    public static List<LineItem> parseExcelFile(File excelFile) throws IOException, InvalidFormatException {

        List<LineItem> lineItemList = new ArrayList();

        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row == null) continue;
            LineItem lineItem = new LineItem();
            boolean isLineItem = false;
            for (Cell cell : row) {
                if (cell.getAddress().getRow() == 0 && cell.getAddress().getColumn() == 2) {
                    lineItem.setDescription(cell.getStringCellValue());
                } else if (cell.getAddress().getColumn() == 1 && !cell.getStringCellValue().isEmpty()) {
                    isLineItem = true;
                    lineItem.setDescription(cell.getStringCellValue());
                } else if (cell.getAddress().getColumn() == 2 && isLineItem) {
                    lineItem.setDebit(cell.getNumericCellValue());
                } else if (cell.getAddress().getColumn() == 4 && isLineItem) {
                    lineItem.setCredit(cell.getNumericCellValue());
                }
            }
            String description = lineItem.getDescription().trim();
            if (!description.isEmpty()) {
                lineItemList.add(lineItem);
            }
        }
        return lineItemList;
    }
}
