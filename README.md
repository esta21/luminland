# Photo Processing Tool - 照片处理工具

一个综合性的照片和视频处理工具集，提供媒体文件扫描、统计、分类整理等功能。

## 功能特点

### 核心功能
- **媒体文件扫描**：快速扫描目录中的照片和视频文件，提供详细的统计信息
- **自动分类整理**：按格式自动创建文件夹并整理文件
- **多格式支持**：支持多种照片格式和视频格式
- **文件冲突处理**：自动处理同名文件，避免覆盖

### 工具特性
- **Web 界面**：现代化的 Web 界面，操作简单直观
- **命令行工具**：支持命令行方式使用，适合批量处理
- **模块化设计**：代码结构清晰，易于扩展和维护
- **完整工具集**：所有照片处理工具统一集成

## 使用方法

### 方式一：Web 界面（推荐）⭐

启动 Web 服务并访问图形界面：

```bash
cd photo-organizer

# 编译并启动服务
mvn clean package
java -jar target/photo-organizer-1.0.0.jar

# 或者直接运行
mvn spring-boot:run
```

然后在浏览器中访问：`http://localhost:8080`

### 方式二：命令行工具

```bash
cd photo-organizer

# 编译
mvn clean compile

# 运行
mvn exec:java -Dexec.mainClass="com.jc.photo.PhotoOrganizer" -Dexec.args="/your/photo/path"

# 或者打包成可执行的 JAR
mvn clean package
java -jar target/photo-organizer-1.0.0.jar /your/photo/path
```

### 使用示例

```bash
# 整理桌面上的照片
java -jar photo-organizer.jar /Users/devin/Desktop

# 整理某文件夹下的照片
java -jar photo-organizer.jar /Users/devin/Pictures/2024
```

## 支持的格式

### 通用格式
- JPG / JPEG
- PNG
- GIF
- BMP
- TIFF / TIF
- HEIC / HEIF

### 相机 RAW 格式
- CR3 (Canon)
- CR2 (Canon)
- NEF (Nikon)
- ARW (Sony)
- ORF (Olympus)
- DNG (Adobe)
- RAW (通用)
- RAF (Fuji)
- RW2 (Panasonic)
- PEF (Pentax)
- SRW (Samsung)
- X3F (Sigma)

## 工作方式

1. 扫描指定目录下的所有文件
2. 识别照片文件的格式
3. 按格式创建对应的文件夹（如 `JPG/`、`CR3/` 等）
4. 将照片移动到对应的格式文件夹下
5. 如果存在同名文件，自动添加序号后缀避免覆盖

## 注意事项

- 请确保目标目录有写权限
- 建议先备份重要照片
- 程序会修改文件位置，请谨慎使用

## 系统要求

- Java 8 或更高版本
- Maven 3.x（如使用 Maven 编译）

