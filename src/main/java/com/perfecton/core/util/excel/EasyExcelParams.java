package com.perfecton.core.util.excel;

import com.alibaba.excel.metadata.BaseRowModel;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:
 * Author: HuangShiwu
 * Date:   2019/8/19 16:33
 */
public class EasyExcelParams {

    /**
     * excel文件名（不带拓展名)
     */
    private String excelNameWithoutExt;
    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 是否需要表头
     */
    private boolean needHead = true;
    /**
     * 数据
     */
    private List<? extends BaseRowModel> data;

    /**
     * 数据模型类型
     */
    private Class<? extends BaseRowModel> dataModelClazz;

    /**
     * 响应
     */
    private HttpServletResponse response;


    public EasyExcelParams() {
    }

    public String getExcelNameWithoutExt() {
        return excelNameWithoutExt;
    }

    public void setExcelNameWithoutExt(String excelNameWithoutExt) {
        this.excelNameWithoutExt = excelNameWithoutExt;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public boolean isNeedHead() {
        return needHead;
    }

    public void setNeedHead(boolean needHead) {
        this.needHead = needHead;
    }

    public List<? extends BaseRowModel> getData() {
        return data;
    }

    public void setData(List<? extends BaseRowModel> data) {
        this.data = data;
    }

    public Class<? extends BaseRowModel> getDataModelClazz() {
        return dataModelClazz;
    }

    public void setDataModelClazz(Class<? extends BaseRowModel> dataModelClazz) {
        this.dataModelClazz = dataModelClazz;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 检查不允许为空的属性
     */
    public boolean isValid() {
        return ObjectUtils.allNotNull(excelNameWithoutExt, data, dataModelClazz, response);
    }
}
