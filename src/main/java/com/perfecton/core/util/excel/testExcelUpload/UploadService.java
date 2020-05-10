package com.perfecton.core.util.excel.testExcelUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Huang ShiWu
 * @data 2020/02/25 18:09
 * @email 673724206@qq.com
 */
@Service
public class UploadService {

    @Autowired
    private UploadDao uploadDAO;

    public void excel2Db(List<UploadData> list) {
        System.out.println("已经进入service");
        // 批量存储
        uploadDAO.save(list);
    }
}
