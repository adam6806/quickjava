package com.github.adam6806.quickjava.export;

import com.github.adam6806.quickjava.parse.LineItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Adam on 6/17/2017.
 */
public class ExcelExport {

    public static String createOutputFile(List<LineItem> newLineItems, File currentFile) throws IOException, InvalidFormatException {

        LineItem dateLineItem = newLineItems.get(0);
        String date = dateLineItem.getDescription();
        newLineItems.remove(dateLineItem);
        String currentPath = currentFile.getCanonicalPath();
        String newPath = FilenameUtils.getFullPath(currentPath);
        String newFileName = FilenameUtils.getBaseName(currentPath) + " - (" + date + ")";
        String newExtension = FilenameUtils.getExtension(currentPath);
        String newFullPath = newPath + newFileName + "." + newExtension;
        FileOutputStream newFile = new FileOutputStream(new File(newFullPath));
        Workbook workbook;
        if (newExtension.equals("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < newLineItems.size(); i++) {
            Row row = sheet.createRow(i);
            if (i == 0) {
                row.createCell(0).setCellValue("Account");
                row.createCell(1).setCellValue("Debit");
                row.createCell(2).setCellValue("Credit");
            } else {
                LineItem lineItem = newLineItems.get(i);
                row.createCell(0).setCellValue(lineItem.getDescription());
                row.createCell(1, CellType.NUMERIC).setCellValue(lineItem.getDebit());
                row.createCell(2, CellType.NUMERIC).setCellValue(lineItem.getCredit());
                Cell debitCell = row.getCell(1);
                Cell creditCell = row.getCell(2);
                debitCell.setCellStyle(cellStyle);
                creditCell.setCellStyle(cellStyle);
            }
        }
        workbook.write(newFile);
        workbook.close();
        return newFullPath;
    }
}
