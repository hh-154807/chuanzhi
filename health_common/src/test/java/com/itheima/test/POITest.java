package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ author He
 * @ create 2022-03-10 16:35
 */
public class POITest {
    //使用poi读取excel中的数据
    @Test
    public void test() {
        try {
            //加载指定文件，创建excel对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\He\\Desktop\\abc.xlsx")));
            //读取excel第一个sheet
            XSSFSheet sheet1 = excel.getSheetAt(0);
            //遍历sheet标签页，获得每一行
            for (Row row : sheet1) {
                //遍历行，获得单元格
                for (Cell cell : row) {
                    System.out.println(cell.getStringCellValue());
                }
            }
            //关闭资源
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //使用poi读取excel中的数据
    @Test
    public void test2() {
        try {
            //加载指定文件，创建excel对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\He\\Desktop\\abc.xlsx")));
            //读取excel第一个sheet
            XSSFSheet sheet1 = excel.getSheetAt(0);
            //获取当前工作表最后一行的行号，行号从0开始
            int lastRowNum = sheet1.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                //根据行号获取行对象
                XSSFRow row = sheet1.getRow(i);
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    //获得列，列从1开始
                    XSSFCell cell = row.getCell(j);
                    System.out.println(cell.getStringCellValue());
                }
            }
            //关闭资源
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入数据
    @Test
    public void test3() {
        try {
            //在内存中创建一个Excel文件
            XSSFWorkbook excel = new XSSFWorkbook();
            //创建工作表，指定工作表名称
            XSSFSheet sheet = excel.createSheet("传智播客");
            //创建行，0表示第一行
            XSSFRow title = sheet.createRow(0);
            //在行中创建单元个对象
            title.createCell(0).setCellValue("姓名");
            title.createCell(1).setCellValue("地址");
            title.createCell(2).setCellValue("年龄");

            XSSFRow dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue("小明");
            dataRow.createCell(1).setCellValue("北京");
            dataRow.createCell(2).setCellValue("20");

            //创建一个输出流，将内存中的文件写入磁盘
            FileOutputStream outputStream = new FileOutputStream(new File("e:\\hello.xlsx"));
            excel.write(outputStream);
            outputStream.flush();
            //关闭资源
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
