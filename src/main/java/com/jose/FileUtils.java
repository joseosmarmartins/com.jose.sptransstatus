package com.jose;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

class FileUtils {

    static String convertXlsToCsv(InputStream inputStream) throws Exception {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        return csvConverter(sheet);
    }

    static void mapToXls(Map<String, String> csv, String outputFileAddress) throws Exception {
        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet();
        int rowNum = 0;
        for (Map.Entry<String, String> line : csv.entrySet()) {
            rowNum++;
            Row currentRow = sheet.createRow(rowNum);
            currentRow.createCell(0).setCellValue(line.getKey());
            currentRow.createCell(1).setCellValue(line.getValue());
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outputFileAddress);
        workBook.write(fileOutputStream);
        fileOutputStream.close();
    }

    private static String csvConverter(Sheet sheet) {
        Row row;
        StringBuilder csv = new StringBuilder();
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i);
            StringBuilder rowString = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                if (row.getCell(j) == null) {
                    rowString.append(";");
                } else {
                    rowString.append(row.getCell(j)).append(";");
                }
            }
            csv.append(rowString.substring(0, rowString.length() - 1)).append("\n");
        }
        return csv.toString();
    }
}
