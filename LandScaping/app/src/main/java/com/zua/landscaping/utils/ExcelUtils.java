package com.zua.landscaping.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zua.landscaping.bean.Project;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by roy on 16/5/20.
 */
public class ExcelUtils {


    public static void writeExcel(Context context, String fileName, List<Project> list) {

        Log.e("roy", "ExcelUtils" + list.toString());

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(fileName + ".xls");

            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("绿化项目进度表");

            createTag(workbook, sheet, list);

            workbook.write(fos);
            fos.close();
            Toast.makeText(context, "已完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createTag(Workbook workbook, Sheet sheet, List<Project> list) {

        String title[] = {"项目编号", "项目名称", "项目总用时", "项目开始时间", "项目结束时间", "项目已用时间"};

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);


        Row row1 = sheet.createRow(1);
        for (int j = 0; j < title.length; j++) {
            Cell cell = row1.createCell(j);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title[j]);
        }

        for (int i = 0; i < list.size(); i++) {
            Row row2 = sheet.createRow(i + 2);
            Cell cell0 = row2.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue(list.get(i).getpId());

            Cell cell1 = row2.createCell(1);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(list.get(i).getpName());

            Cell cell2 = row2.createCell(2);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(list.get(i).getpTotalTime());

            Cell cell3 = row2.createCell(3);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(list.get(i).getpStartTime());

            Cell cell4 = row2.createCell(4);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(list.get(i).getPEndTime());

            Cell cell5 = row2.createCell(5);
            cell5.setCellStyle(cellStyle);
            cell5.setCellValue(list.get(i).getpUseTime());
        }

    }
}
