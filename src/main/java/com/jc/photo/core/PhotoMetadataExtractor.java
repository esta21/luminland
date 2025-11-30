package com.jc.photo.core;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 照片元数据提取器
 * 用于读取照片的拍摄日期等EXIF信息
 */
public class PhotoMetadataExtractor {
    
    /**
     * 获取照片的拍摄日期
     * @param photoFile 照片文件
     * @return 拍摄日期字符串 (yyyy-MM格式)，如果无法获取则返回null
     */
    public static String getPhotoDate(File photoFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(photoFile);
            
            // 获取EXIF子IFD目录
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (directory != null) {
                Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (date == null) {
                    date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME);
                }
                
                if (date != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    return sdf.format(date);
                }
            }
        } catch (ImageProcessingException | IOException e) {
            // 如果无法读取元数据，返回null
            System.err.println("无法读取照片元数据: " + photoFile.getName() + " - " + e.getMessage());
        }
        
        // 如果无法从EXIF获取日期，则使用文件最后修改时间
        return getDefaultDate(photoFile);
    }
    
    /**
     * 获取文件的最后修改时间作为默认日期
     * @param file 文件
     * @return 日期字符串 (yyyy-MM格式)
     */
    private static String getDefaultDate(File file) {
        long lastModified = file.lastModified();
        if (lastModified > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            return sdf.format(new Date(lastModified));
        }
        return null;
    }
}