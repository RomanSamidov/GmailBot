package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class RecipientsReader {


    public static RecipientsSelector readRecipients(String path) {
        String string = path.substring(path.lastIndexOf('.')+1).toLowerCase();
        new RecipientsSelector();
        return switch (string) {
            case "ods" -> readRecipientsODS(path);
            case "txt" -> readRecipientsTXT(path);
            case "xlsx" -> readRecipientsXLSX(path);
            default -> new RecipientsSelector();
        };
    }


    public static RecipientsSelector readRecipientsODS(String path) {
        RecipientsSelector rs = new RecipientsSelector();
        try {
            SpreadSheet spread = new SpreadSheet(new File(path));
            Sheet sheet = spread.getSheet(0);
            Range range = sheet.getRange(0,0, sheet.getLastRow(),3);
            if(range.getCell(0, 0).getValue().toString().contentEquals("Почта")) {
                ArrayList<ArrayList<String>> recipientsAdvanced = new ArrayList<>();
                recipientsAdvanced.add(new ArrayList<>());
                recipientsAdvanced.add(new ArrayList<>());
                recipientsAdvanced.add(new ArrayList<>());
                for (int i = 1; i < sheet.getLastRow(); i++) {
                    if (range.getCell(i, 0).getValue() == null) break;
                    recipientsAdvanced.get(0).add((String) range.getCell(i, 0).getValue());
                    recipientsAdvanced.get(1).add((String) range.getCell(i, 1).getValue());
                    recipientsAdvanced.get(2).add((String) range.getCell(i, 2).getValue());
                }
                rs.setRecipientsAdvanced(recipientsAdvanced);
            } else {
                ArrayList<String> recipients = new ArrayList<>();
                for (int i = 0; i < sheet.getLastRow(); i++) {
                    if (range.getCell(i, 0).getValue() == null) break;
                    recipients.add((String) range.getCell(i, 0).getValue());
                }
               rs.setRecipients(recipients);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return rs;
    }


    public static RecipientsSelector readRecipientsTXT(String path) {
        ArrayList<String> recipients = new ArrayList<>();
        RecipientsSelector rs = new RecipientsSelector();
        try(FileReader reader = new FileReader(path))
        {
            while (true) {
                StringBuilder username = new StringBuilder();
                int c = reader.read();
                if( c == '[') {
                    c = reader.read();
                    while (c != ']') {
                        if(c != ' ') username.append((char) c);
                        c = reader.read();
                    }
                    recipients.add(username.toString());
                }
                if(c == -1) {
                    break;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        rs.setRecipients(recipients);
        return rs;
    }


    public static RecipientsSelector readRecipientsXLSX(String path) {
        RecipientsSelector rs = new RecipientsSelector();
        try
        {
            FileInputStream fis = new FileInputStream(path);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);   //creating Workbook instance that refers to .xlsx file
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            if(sheet.getRow(0).getCell(0).getStringCellValue().contentEquals("Почта"))
            {
                ArrayList<ArrayList<String>> recipientsAdvanced = new ArrayList<>();
                recipientsAdvanced.add(new ArrayList<>());
                recipientsAdvanced.add(new ArrayList<>());
                recipientsAdvanced.add(new ArrayList<>());
                for (Row row : sheet) {
                    if(row.getCell(0).getStringCellValue().contentEquals("Почта")) continue;
                    recipientsAdvanced.get(0).add(row.getCell(0).getStringCellValue());
                    recipientsAdvanced.get(1).add(row.getCell(1).getStringCellValue());
                    recipientsAdvanced.get(2).add(row.getCell(2).getStringCellValue());
                }
                rs.setRecipientsAdvanced(recipientsAdvanced);
            } else {
                ArrayList<String> recipients = new ArrayList<>();
                for (Row row : sheet) {
                    Cell cell = row.getCell(0);
                    recipients.add(cell.getStringCellValue());
                }
                rs.setRecipients(recipients);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }
}

