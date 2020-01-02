package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Article;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PoiTest {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private ArticleDao articleDao;

    @Test
    public void test(){
        //创建文件
        HSSFWorkbook workbook = new HSSFWorkbook();
            //设置样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建sheet
        HSSFSheet sheet = workbook.createSheet("sheet");
        //创建行
        HSSFRow row = sheet.createRow(0);

        //自定义标题行
        String[] s = {"编号","用户名","密码"};
        for (int i = 0; i < s.length; i++) {
            String title = s[i];
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }

        //查询数据
        List<Admin> admins = adminDao.selectAll();
        for (int i = 0; i < admins.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(admins.get(i).getId());
            row1.createCell(1).setCellValue(admins.get(i).getUsername());
            row1.createCell(2).setCellValue(admins.get(i).getPassword());
            row.setRowStyle(cellStyle);
        }
        try {
            workbook.write(new File("E:/Baizhi/180班的目录/后期项目/test.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    @Test
    public void tset2(){

    }
}
