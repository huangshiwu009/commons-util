package java.com.perfecton.core.util.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 * Author: HuangShiwu
 * Date:   2019/8/16 14:34
 */
@Data
@AllArgsConstructor
public class StudentExportInfo extends BaseRowModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Byte age;

    @ExcelProperty(value = "邮箱", index = 2)
    private String email;

    @ExcelProperty(value = "出生日期", index = 3, format = "yyyy/MM/dd")
    private Date birthday;
}
