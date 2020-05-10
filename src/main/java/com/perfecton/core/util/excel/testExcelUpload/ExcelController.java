package com.perfecton.core.util.excel.testExcelUpload;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Huang ShiWu
 * @data 2020/02/25 16:45
 * @email 673724206@qq.com
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("upload")
    @ResponseBody
    public Object upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "文件为空";
        }
        // 存储的动作都在UploadDataListener
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadService)).sheet().doRead();
        return "存储成功";
    }

    /**
     * 针对：linux下获取IP的方式有时候会得到127.0.0.1
     * 对网络接口进行筛选,非回送接口 且 非虚拟网卡 且 正在使用中
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIp")
    public Object getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "IP地址获取失败";
    }
}
