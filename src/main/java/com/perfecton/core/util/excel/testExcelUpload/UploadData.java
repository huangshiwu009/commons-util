package com.perfecton.core.util.excel.testExcelUpload;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Huang ShiWu
 * @data 2020/02/25 16:46
 * @email 673724206@qq.com
 */
@Data
public class UploadData {

    /** 支付手机号/呢称 */
    private String payAccount;
    /** 支付金额 */
    private BigDecimal money;
    /** 支付时间 */
    private Date payTime;
    /** 用户等级 */
    private String userLevel;
    /** 上级手机号/昵称 */
    private String upAccount;
    /** 上级等级 */
    private String upLevel;
    /** 手机号 */
    private String mobile;
}
