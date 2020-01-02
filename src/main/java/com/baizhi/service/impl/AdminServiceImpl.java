package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin login(String username, String password) {
        //查询当前输入用户存不存在
        Admin admin = adminDao.selectByUsername(username);
        //判断密码是否正确
        if (admin != null){
            if(admin.getPassword().equals(password)){
                return admin;
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void outPoi(HttpServletResponse response) throws IOException {
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
        //设置下载方式
        response.setHeader("content-disposition","attachment;filename=admin.xls");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }
}
