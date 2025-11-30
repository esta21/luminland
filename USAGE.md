# 照片分类工具 - 使用说明

## 项目结构

```
photo-organizer/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── organizer/
│                   └── PhotoOrganizer.java
├── pom.xml              # Maven 配置文件
├── build.sh             # 编译脚本
├── README.md            # 项目说明
└── USAGE.md            # 使用说明
```

## 编译方式

### 方式一：使用 Java 直接编译（推荐）

```bash
cd photo-organizer/src/main/java
javac com/organizer/PhotoOrganizer.java
```

### 方式二：使用 Maven 编译

```bash
cd photo-organizer
mvn clean compile
```

## 运行方式

### 方式一：直接运行

```bash
cd photo-organizer/src/main/java
java com.jc.photo.PhotoOrganizer /your/photo/path
```

### 方式二：使用 Maven 运行

```bash
cd photo-organizer
mvn exec:java -Dexec.mainClass="com.jc.photo.PhotoOrganizer" -Dexec.args="/your/photo/path"
```

### 方式三：打包成 JAR 运行

```bash
cd photo-organizer
mvn clean package
java -jar target/photo-organizer-1.0.0.jar /your/photo/path
```

## 使用示例

### 示例 1：整理桌面上的照片

```bash
java -jar photo-organizer.jar /Users/devin/Desktop
```

输出：
```
开始整理照片...
源目录: /Users/devin/Desktop
找到以下格式的照片:
  - jpg: 15 个文件
  - cr3: 8 个文件
  - png: 3 个文件

创建文件夹: JPG
创建文件夹: CR3
创建文件夹: PNG
  移动: IMG_001.jpg -> JPG/IMG_001.jpg
  移动: IMG_002.cr3 -> CR3/IMG_002.cr3
  ...
照片整理完成！
```

### 示例 2：整理相机照片目录

```bash
java -jar photo-organizer.jar /Users/devin/Pictures/Camera
```

## 功能说明

1. **自动识别格式**：支持常见图片格式和相机 RAW 格式
2. **创建文件夹**：按格式自动创建大写文件夹（如 `JPG/`、`CR3/`）
3. **移动文件**：将照片移动到对应格式的文件夹
4. **处理冲突**：同名文件自动添加序号（如 `file_1.jpg`）

## 注意事项

- 请在运行前备份重要照片
- 程序会修改文件位置
- 确保有足够的磁盘空间
- 如果文件名包含中文，请确保文件系统支持 UTF-8

## 支持的格式列表

- **通用格式**：JPG, JPEG, PNG, GIF, BMP, TIFF, HEIC
- **Canon**：CR3, CR2
- **Nikon**：NEF
- **Sony**：ARW
- **Olympus**：ORF
- **Adobe**：DNG
- **其他**：RAF, RW2, PEF, SRW, X3F, RAW

## 故障排除

### 问题：找不到 Java

**解决方法**：
```bash
# macOS 使用 Homebrew 安装
brew install openjdk@8

# 或者直接使用 JDK
export JAVA_HOME=$(/usr/libexec/java_home)
```

### 问题：编译错误

**解决方法**：
- 确保 Java 版本 ≥ 1.8
- 检查文件编码是否为 UTF-8

### 问题：文件无法移动

**解决方法**：
- 检查文件权限
- 确保有足够的磁盘空间
- 检查文件是否正在被其他程序使用

