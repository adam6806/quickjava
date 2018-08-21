package com.github.adam6806.quickjava.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

/**
 * Adam on 6/17/2017.
 */
public class FileChooser {

    private JFileChooser openFileChooser;

    public FileChooser() {
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Excel File",  "xlsx", "xls");
        openFileChooser = new JFileChooser();
        openFileChooser.setFileFilter(extensionFilter);
        openFileChooser.setAcceptAllFileFilterUsed(false);
    }

    public File showOpenFileChooser(String dialogName) {
        openFileChooser.setDialogTitle(dialogName);
        openFileChooser.showOpenDialog(null);
        return openFileChooser.getSelectedFile();
    }

    public void showInstructionDialog(String message) {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(null, message);
    }

    public int showContinueDialog(String message) {
        return JOptionPane.showConfirmDialog(
                null,
                message,
                "Continue?",
                JOptionPane.YES_NO_OPTION);
    }
}