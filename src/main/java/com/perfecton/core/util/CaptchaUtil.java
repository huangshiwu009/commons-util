package com.perfecton.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Description:
 * Author: HuangShiwu
 * Date:   2019/8/20 10:11
 */
public class CaptchaUtil {

    private final static Logger log = LoggerFactory.getLogger(CaptchaUtil.class);

    // pc: 100 30, mobile: 55 20

    private final static int PC_WIDTH = 100;
    private final static int PC_HEIGHT = 30;

    public static final String CAPTCHA = "captcha";

    private CaptchaUtil() {
    }


    public static void draw(HttpServletRequest request, HttpServletResponse response) {
        // 生成一张图片
        BufferedImage image = new BufferedImage(PC_WIDTH, PC_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        // 为图片构建一只画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 设置背景色
        setBackColor(g);
        // 设置边框
        setBorder(g);
        // 添加干扰线
        addRandomLine(g);
        // 添加随机常用中文字
        String checkCode = addRandomWord(g);

        // 将验证码存入session中
        request.getSession().setAttribute(CAPTCHA, checkCode);
        log.info("图形验证码为:" + CAPTCHA + "|" + request.getSession().getAttribute(CAPTCHA));
        // 将图片写到浏览器上
        response.setContentType("image/jpeg");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            log.error("图片验证码生成失败", e);
        }
    }

    public static String draw(HttpServletResponse response) {
        // 生成一张图片
        BufferedImage image = new BufferedImage(PC_WIDTH, PC_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        // 为图片构建一只画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 设置背景色
        setBackColor(g);
        // 设置边框
        setBorder(g);
        // 添加干扰线
        addRandomLine(g);
        // 添加随机常用中文字
        String checkCode = addRandomWord(g);
        // 将图片写到浏览器上
        response.setContentType("image/jpeg");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            log.error("图片验证码生成失败", e);
        }
        return checkCode;
    }

    /**
     * 设置图片背景色
     *
     * @param g
     */
    private static void setBackColor(Graphics2D g) {
        // 设置画笔颜色
        g.setColor(new Color(252, 252, 252));
        // 填充指定大小的矩形
        g.fillRect(0, 0, PC_WIDTH, PC_HEIGHT);
    }

    /**
     * 设置边框颜色
     *
     * @param g
     */
    private static void setBorder(Graphics2D g) {
        g.setColor(new Color(0, 0, 80));
    }

    /**
     * 添加干扰线
     *
     * @param g
     */
    private static void addRandomLine(Graphics2D g) {
        g.setColor(new Color(124, 252, 0));
        // 创建随机对象
        Random random = new Random();
        // 随机两个坐标,添加5条干扰线
        for (int i = 1; i <= 3; i++) {
            int x1 = random.nextInt(PC_WIDTH);
            int y1 = random.nextInt(PC_HEIGHT);
            int x2 = random.nextInt(PC_WIDTH);
            int y2 = random.nextInt(PC_HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private static String addRandomWord(Graphics2D g) {
        StringBuilder sb = new StringBuilder();
        g.setColor(new Color(20, 20, 20));
        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD, PC_HEIGHT - 9));
        // 创建随机对象
        Random random = new Random();
        // 随机中常用中文字
        String words = "ABCDEFGHJKMNPQRTUVWXYZ2346789abcdefghjkmnpqrtuvwxyz";
        // 定义一个下标
        int index;
        // 定义X轴变量
        int x = PC_WIDTH / 10;
        for (int i = 1; i <= 4; i++) {
            // 随机的下标小于words的长度
            index = random.nextInt(words.length());
            // 获取index索引处的字符
            char c = words.charAt(index);
            // 将每个字添加到sb中
            sb = sb.append(c);
            // 画上字符
            g.drawString(c + "", x, PC_HEIGHT / 2 + 10);
            // 转回来
            x += PC_WIDTH / 5;
        }
        return sb.toString();
    }

}
