package com.company;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public abstract class RecipientsReader {


    public static ArrayList<ArrayList<String>> readRecipients(String path) {
        String string = path.substring(path.lastIndexOf('.') + 1).toLowerCase();
        return switch (string) {
            case "ods" -> readRecipientsODS(path);
            case "xlsx" -> readRecipientsXLSX(path);
            default -> new ArrayList();
        };
    }


    public static ArrayList<ArrayList<String>> readRecipientsODS(String path) {
        ArrayList<ArrayList<String>> recipientsAndParameters = new ArrayList<>();
        try {
            SpreadSheet spread = new SpreadSheet(new File(path));
            Sheet sheet = spread.getSheet(0);
            Range range = sheet.getRange(0, 0, sheet.getLastRow(), sheet.getLastColumn());

            for (int j = 0; j < sheet.getLastColumn(); j++) {
                ArrayList<String> column = new ArrayList<>();
                for (int i = 0; i < sheet.getLastRow(); i++) {
                    if (range.getCell(i, j).getValue() == null) break;
                    column.add(range.getCell(i, j).getValue().toString());
                }
                if (!column.isEmpty() && column.get(0) != null) {
                    recipientsAndParameters.add(column);
                }
            }
            setAddressesFirst(recipientsAndParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipientsAndParameters;
    }


    public static ArrayList<ArrayList<String>> readRecipientsXLSX(String path) {
        ArrayList<ArrayList<String>> recipientsAndParameters = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);   //creating Workbook instance that refers to .xlsx file
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            XSSFRow row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                recipientsAndParameters.add(new ArrayList<>());
            }
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                for (int j = 0; j <= row.getLastCellNum(); j++) {
                    if (row.getCell(j) != null)
                        recipientsAndParameters.get(j).add(row.getCell(j).getStringCellValue());
                }
            }
            setAddressesFirst(recipientsAndParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipientsAndParameters;
    }

    private static void setAddressesFirst(ArrayList<ArrayList<String>> recipientsAndParameters) {
        int i = 0;
        while (i < recipientsAndParameters.size()) {
            if (recipientsAndParameters.get(i).isEmpty()) {
                recipientsAndParameters.remove(i);
                i = 0;
            }
            i++;
        }
        if (!recipientsAndParameters.get(0).get(0).equalsIgnoreCase("почта")) {
            for (i = 1; i < recipientsAndParameters.size(); i++) {
                if (recipientsAndParameters.get(i).get(0).equalsIgnoreCase("почта")) {
                    ArrayList<String> buf = recipientsAndParameters.get(i);
                    recipientsAndParameters.set(i, recipientsAndParameters.get(0));
                    recipientsAndParameters.set(0, buf);
                }
            }
        }
    }

}

