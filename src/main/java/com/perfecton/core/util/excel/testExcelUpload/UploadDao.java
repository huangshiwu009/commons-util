package com.perfecton.core.util.excel.testExcelUpload;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Huang ShiWu
 * @data 2020/02/25 16:50
 * @email 673724206@qq.com
 */
@Repository
public class UploadDao {

    public void save(List<UploadData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        System.out.println("已经进入dao");
    }
}
