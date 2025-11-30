package com.jc.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 照片处理工具 Web 应用
 * 
 * 一个综合性的照片和视频处理工具集，提供以下功能：
 * - 媒体文件扫描和统计
 * - 按格式自动分类整理
 * - 支持多种照片和视频格式
 * - Web 界面和命令行两种使用方式
 */
@SpringBootApplication
public class PhotoOrganizerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PhotoOrganizerApplication.class, args);
    }
}

