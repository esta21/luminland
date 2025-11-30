#!/bin/bash

# 照片分类工具编译脚本

echo "========================================="
echo "    Photo Organizer - 照片分类工具"
echo "========================================="
echo ""

# 检查 Java 是否安装
if ! command -v javac &> /dev/null; then
    echo "错误: 未找到 Java 编译器"
    echo "请先安装 Java 8 或更高版本"
    exit 1
fi

# 编译 Java 文件
echo "正在编译..."
cd src/main/java
javac com/organizer/PhotoOrganizer.java

if [ $? -eq 0 ]; then
    echo "✓ 编译成功"
    echo ""
    echo "使用方法:"
    echo "  cd ../../.."
    echo "  java com.organizer.PhotoOrganizer <照片目录路径>"
    echo ""
    echo "示例:"
    echo "  java com.organizer.PhotoOrganizer /Users/devin/Pictures"
else
    echo "✗ 编译失败"
    exit 1
fi

