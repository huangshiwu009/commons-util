package com.perfecton.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: Huang ShiWu
 * @Date: 2020/5/10 14:36
 */
public class ZIPUtil {

    /**
     * 压缩文件(注意：压缩源路径与压缩目的路径不能为同一个目录，因为压缩目的文件会处于打开状态，不能进行读取)
     *
     * @param srcFilePath  压缩源路径
     * @param destFilePath 压缩目的路径
     */
    public static void compress(String srcFilePath, String destFilePath) {
        File src = new File(srcFilePath);
        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destFilePath);
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            compressByType(src, zos);
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩
     *
     * @param src
     * @param zos
     */
    private static void compressByType(File src, ZipOutputStream zos) {
        System.out.println("压缩路径" + src.getName());
        // 判断文件是否是文件，如果是文件调用compressFile方法,如果是路径，则调用compressDir方法；
        if (src.isFile()) {
            compressFile(src, zos);
        } else if (src.isDirectory()) {
            compressDir(src, zos);
        }
    }

    /**
     * 压缩文件
     */
    private static void compressFile(File file, ZipOutputStream zos) {
        if (!file.exists() || !file.isFile()) {
            return;
        }
        try {
            ZipEntry entry = new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            Files.copy(file.toPath(), zos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件夹
     */
    private static void compressDir(File dir, ZipOutputStream zos) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files.length == 0) {
            try {
                zos.putNextEntry(new ZipEntry(dir.getName() + File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
            compressByType(file, zos);
        }
    }

    public static void main(String[] args) {
        // 第一个参数是需要压缩的源路径；第二个参数是压缩文件的目的路径，这边需要将压缩的文件名字加上去
        compress("test", "oo.zip");
    }
}
