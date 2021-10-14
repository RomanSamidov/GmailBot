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

public class RecipientsReader {

    public static ArrayList<String> readRecipients(String path) {
        String string = path.substring(path.lastIndexOf('.')+1).toLowerCase();
        return switch (string) {
            case "ods" -> readRecipientsODS(path);
            case "txt" -> readRecipientsTXT(path);
            case "xlsx" -> readRecipientsXLSX(path);
            default -> new ArrayList<>();
        };
    }


    public static ArrayList<String> readRecipientsODS(String path) {
        ArrayList<String> recipients = new ArrayList<>();
        try {
            SpreadSheet spread = new SpreadSheet(new File(path));
            Sheet sheet = spread.getSheet(0);
            Range range = sheet.getRange(0,0, sheet.getLastRow(),1);

            for(int i = 0; i < sheet.getLastRow(); i++) {
                if(range.getCell(i,0).getValue() == null) break;
                recipients.add((String) range.getCell(i,0).getValue());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return recipients;
    }

    public static ArrayList<String> readRecipientsTXT(String path) {
        ArrayList<String> recipients = new ArrayList<>();
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
        return recipients;
    }


    public static ArrayList<String> readRecipientsXLSX(String path) {
        ArrayList<String> recipients = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(path);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);   //creating Workbook instance that refers to .xlsx file
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                recipients.add(cell.getStringCellValue());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return recipients;
    }
}

