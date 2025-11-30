package com.jc.photo.core;

import java.io.File;
import java.nio.file.*;

/**
 * 文件工具类
 * 提供文件操作相关的工具方法
 */
public class FileUtils {
    
    /**
     * 移动文件到目标目录
     * 如果目标文件已存在，自动添加数字后缀
     */
    public static Path moveFile(File sourceFile, File targetDir) throws Exception {
        Path source = sourceFile.toPath();
        Path target = new File(targetDir, sourceFile.getName()).toPath();
        
        // 如果目标文件已存在，添加数字后缀
        int counter = 1;
        String baseName = sourceFile.getName();
        String extension = MediaTypeDetector.getFileExtension(sourceFile);
        
        if (extension != null) {
            String nameWithoutExt = baseName.substring(0, baseName.lastIndexOf('.'));
            
            while (target.toFile().exists()) {
                String newName = nameWithoutExt + "_" + counter + "." + extension;
                target = new File(targetDir, newName).toPath();
                counter++;
            }
        }
        
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        return target;
    }
    
    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * 确保目录存在，如果不存在则创建
     */
    public static void ensureDirectoryExists(File directory) throws Exception {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (!directory.isDirectory()) {
            throw new Exception("路径不是目录: " + directory.getAbsolutePath());
        }
    }
}

