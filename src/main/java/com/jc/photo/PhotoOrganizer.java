package com.jc.photo;

import com.jc.photo.model.OrganizeResult;
import com.jc.photo.model.ScanResult;
import com.jc.photo.service.MediaOrganizeService;
import com.jc.photo.service.MediaScanService;

import java.io.File;

/**
 * 照片处理工具 - 命令行版本
 * 根据照片和视频格式自动创建文件夹并整理文件
 */
public class PhotoOrganizer {
    
    private static final MediaScanService scanService = new MediaScanService();
    private static final MediaOrganizeService organizeService = new MediaOrganizeService();
    
    /**
     * 主方法
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("用法: java PhotoOrganizer <照片目录路径>");
            System.out.println("示例: java PhotoOrganizer /Users/devin/Pictures");
            return;
        }
        
        String sourcePath = args[0];
        File sourceDir = new File(sourcePath);
        
        if (!sourceDir.exists()) {
            System.err.println("错误: 指定的路径不存在: " + sourcePath);
            return;
        }
        
        if (!sourceDir.isDirectory()) {
            System.err.println("错误: 指定的路径不是一个目录: " + sourcePath);
            return;
        }
        
        System.out.println("开始整理照片和视频...");
        System.out.println("源目录: " + sourcePath);
        
        // 先扫描
        System.out.println("\n正在扫描文件...");
        ScanResult scanResult = scanService.scanDirectory(sourceDir);
        
        if (scanResult.getTotalFiles() == 0) {
            System.out.println("未找到照片或视频文件");
            return;
        }
        
        System.out.println("找到以下格式的文件:");
        for (java.util.Map.Entry<String, Integer> entry : scanResult.getFormats().entrySet()) {
            System.out.println("  - " + entry.getKey() + ": " + entry.getValue() + " 个文件");
        }
        System.out.println();
        
        // 整理文件
        try {
            OrganizeResult organizeResult = organizeService.organizeDirectory(sourceDir);
            System.out.println(organizeResult.getMessage());
            System.out.println("照片和视频整理完成！");
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
        }
    }
}

