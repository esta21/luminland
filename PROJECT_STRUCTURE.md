# 项目结构说明

## 项目定位

**照片处理工具** - 一个综合性的照片和视频处理工具集

本项目已从简单的照片分类工具重构为完整的照片处理工具集，所有工具统一集成在一个项目中。

## 包结构

```
com.organizer/
├── core/                    # 核心工具类
│   ├── MediaTypeDetector    # 媒体类型检测器（识别照片/视频格式）
│   └── FileUtils            # 文件操作工具类
│
├── model/                   # 数据模型
│   ├── MediaFileInfo        # 媒体文件信息模型
│   ├── ScanResult           # 扫描结果模型
│   └── OrganizeResult       # 整理结果模型
│
├── service/                 # 业务逻辑服务
│   ├── MediaScanService     # 媒体文件扫描服务
│   └── MediaOrganizeService # 媒体文件整理服务
│
├── controller/              # REST API 控制器
│   └── PhotoOrganizerController  # 照片处理工具 REST API
│
├── PhotoOrganizerApplication.java  # Spring Boot 应用入口
└── PhotoOrganizer.java             # 命令行工具入口
```

## 模块说明

### Core 模块（核心工具）
- **MediaTypeDetector**: 负责识别文件是否为照片或视频，支持多种格式
- **FileUtils**: 提供文件移动、大小格式化等通用工具方法

### Model 模块（数据模型）
- **MediaFileInfo**: 封装媒体文件的基本信息（名称、大小、格式等）
- **ScanResult**: 封装扫描结果（文件统计、格式分布等）
- **OrganizeResult**: 封装整理结果（移动的文件列表、统计信息等）

### Service 模块（业务服务）
- **MediaScanService**: 负责扫描目录中的媒体文件并生成统计信息
- **MediaOrganizeService**: 负责按格式整理媒体文件到对应文件夹

### Controller 模块（API 接口）
- **PhotoOrganizerController**: 提供 REST API 接口，支持 Web 界面调用

## 功能特性

1. **模块化设计**: 代码按功能模块清晰分类，易于维护和扩展
2. **统一工具集**: 所有照片处理工具集成在一个项目中
3. **多种使用方式**: 支持 Web 界面和命令行两种使用方式
4. **易于扩展**: 清晰的包结构便于添加新的处理工具

## 扩展指南

如需添加新的照片处理功能：

1. 在 `core` 包中添加核心工具类
2. 在 `service` 包中添加业务逻辑服务
3. 在 `model` 包中添加必要的数据模型
4. 在 `controller` 包中添加 REST API 接口（如需要）
5. 更新文档说明新功能

