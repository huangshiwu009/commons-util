package com.perfecton.core.util.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 * Author: HuangShiwu
 * Date:   2019/8/16 14:26
 */
public class EasyExcelUtil {

    /**
     * 下载EXCEL文件2007版本
     *
     * @throws IOException IO异常
     */
    public static void exportExcel2007Format(EasyExcelParams excelParams) throws IOException {
        exportExcel(excelParams, ExcelTypeEnum.XLSX);
    }

    /**
     * 下载EXCEL文件2003版本
     *
     * @throws IOException IO异常
     */
    public static void exportExcel2003Format(EasyExcelParams excelParams) throws IOException {
        exportExcel(excelParams, ExcelTypeEnum.XLS);
    }

    /**
     * 根据参数和版本枚举导出excel文件
     *
     * @param excelParams 参数实体
     * @param typeEnum    excel类型枚举
     * @throws IOException
     */
    private static void exportExcel(EasyExcelParams excelParams, ExcelTypeEnum typeEnum) throws IOException {
        Validate.isTrue(excelParams.isValid(), "easyExcel params is not valid");

        HttpServletResponse response = excelParams.getResponse();
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, typeEnum, excelParams.isNeedHead());
        prepareResponds(response, excelParams.getExcelNameWithoutExt(), typeEnum);
        Sheet sheet1 = new Sheet(1, 0, excelParams.getDataModelClazz());
        if (StringUtils.isNotBlank(excelParams.getSheetName())) {
            sheet1.setSheetName(excelParams.getSheetName());
        }
        writer.write(excelParams.getData(), sheet1);
        writer.finish();
        out.flush();
    }

    /**
     * 将文件输出到浏览器（导出文件）
     * @param response 响应
     * @param fileName 文件名（不含拓展名）
     * @param typeEnum excel类型
     */
    private static void prepareResponds(HttpServletResponse response, String fileName, ExcelTypeEnum typeEnum) {
        String fileName2Export = new String((fileName).getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2Export + typeEnum.getValue());
    }

    /**
     * 生成报表文件名：时间戳 + 5位随机码
     * @return
     */
    public static String createExcelFileName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + RandomStringUtils.randomNumeric(5);
    }

}
