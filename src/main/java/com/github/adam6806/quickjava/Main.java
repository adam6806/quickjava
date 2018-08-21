package com.github.adam6806.quickjava;

import com.github.adam6806.quickjava.comparison.DifferenceCalculator;
import com.github.adam6806.quickjava.export.ExcelExport;
import com.github.adam6806.quickjava.gui.FileChooser;
import com.github.adam6806.quickjava.parse.ExcelParser;
import com.github.adam6806.quickjava.parse.LineItem;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Adam on 6/17/2017.
 */
public class Main {

    public static void main(String[] args) {
        FileChooser fileChooser = new FileChooser();
        File previousFile = getFile(fileChooser, "Choose previous excel file", "Previous File");
        File currentFile = getFile(fileChooser, "Choose current excel file", "Current File");
        List<LineItem> previousLineItems = callParser(previousFile, fileChooser);
        List<LineItem> currentLineItems = callParser(currentFile, fileChooser);
        List<LineItem> newLineItems = DifferenceCalculator.calculateDifference(previousLineItems, currentLineItems);
        String newFullPath = "";
        try {
            newFullPath = ExcelExport.createOutputFile(newLineItems, currentFile);
        } catch (IOException | InvalidFormatException e) {
            handleException(e, fileChooser);
        }
        fileChooser.showInstructionDialog("Operation success! New file created: " + newFullPath);
    }

    private static File getFile(FileChooser fileChooser, String instruction, String dialogName) {
        File file = null;
        do {
            fileChooser.showInstructionDialog(instruction);
            file = fileChooser.showOpenFileChooser(dialogName);
        } while (isNotValidFile(file, fileChooser));
        return file;
    }

    private static List<LineItem> callParser(File file, FileChooser fileChooser) {
        List<LineItem> lineItemList = null;
        try {
            lineItemList = ExcelParser.parseExcelFile(file);
        } catch (IOException | InvalidFormatException e) {
            handleException(e, fileChooser);
        }
        return lineItemList;
    }

    private static void handleException(Exception e, FileChooser fileChooser) {
        e.printStackTrace();
        fileChooser.showInstructionDialog("An error occurred. Please seek assistance from your son. Program will exit.");
        System.exit(0);
    }

    private static boolean isNotValidFile(File file, FileChooser fileChooser) {
        if (file == null) {
            int choice = fileChooser.showContinueDialog("File was invalid. Do you want to continue?");
            if (choice == JOptionPane.OK_OPTION) {
                return true;
            }
            System.exit(0);
        }
        return false;
    }
}
