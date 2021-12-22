package com.company;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


public abstract class ErrorsWriter {


    public static void writeErrorsODS(ArrayList<String> errors, ArrayList<String> recipients) {
        try {
            int rows = recipients.size();
            int columns = 2;
            Sheet sheet = new Sheet("A", rows, columns);

            for (int i = 0; i < errors.size(); i++) {
                sheet.getDataRange().getCell(i, 0).setValues(recipients.get(i));
                sheet.getDataRange().getCell(i, 0).setFontSize(14);
                sheet.getDataRange().getCell(i, 1).setValues(errors.get(i));
                sheet.getDataRange().getCell(i, 1).setFontSize(14);
            }

            SpreadSheet spread = new SpreadSheet();
            spread.appendSheet(sheet);
            String path = "";
            try {
                path = (ErrorsWriter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (path.contains(".jar"))
                path = path.substring(0, path.lastIndexOf('/') + 1);
            path += "Errors.ods";
            spread.save(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
