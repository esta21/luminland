# Web 界面使用指南

## 快速启动

### 方式一：使用 Maven 启动

```bash
cd photo-organizer
mvn spring-boot:run
```

### 方式二：打包后启动

```bash
cd photo-organizer
mvn clean package
java -jar target/photo-organizer-1.0.0.jar
```

## 访问界面

启动成功后，在浏览器中打开：

```
http://localhost:8080
```

## 使用步骤

### 1. 输入照片目录路径

在输入框中输入照片所在的目录路径，例如：
- macOS/Linux: `/Users/devin/Pictures`
- Windows: `C:\Users\devin\Pictures`

### 2. 扫描照片

点击"🔍 扫描照片"按钮，系统会分析该目录下的照片：
- 显示照片总数
- 显示总大小
- 按格式统计分布

### 3. 整理照片

扫描完成后，点击"📁 开始整理"按钮：
- 按格式创建子文件夹（如 `JPG/`、`CR3/`）
- 将照片移动到对应的格式文件夹
- 显示处理结果

## 功能特点

### 可视化界面
- 美观的渐变背景设计
- 响应式布局，支持移动设备
- 实时加载动画
- 友好的错误提示

### 统计信息
- 照片总数统计
- 按格式分类统计
- 文件大小统计
- 格式分布图表

### 操作记录
- 显示处理的文件列表
- 记录文件移动路径
- 处理结果汇总

## API 接口

### 扫描照片

```bash
curl -X POST http://localhost:8080/api/scan \
  -H "Content-Type: application/json" \
  -d '{"path": "/your/photo/path"}'
```

### 整理照片

```bash
curl -X POST http://localhost:8080/api/organize \
  -H "Content-Type: application/json" \
  -d '{"path": "/your/photo/path"}'
```

## 故障排除

### 问题：无法访问界面

**检查**：
1. 确认服务已启动（查看控制台输出）
2. 检查端口 8080 是否被占用
3. 确认防火墙设置

**解决**：
```bash
# 修改端口（在 application.properties 中）
server.port=8081
```

### 问题：扫描失败

**可能原因**：
- 路径不存在
- 路径不是目录
- 没有读取权限

**解决**：
- 检查路径是否正确
- 使用绝对路径
- 确保有足够的权限

### 问题：整理失败

**可能原因**：
- 文件被其他程序占用
- 磁盘空间不足
- 没有写入权限

**解决**：
- 关闭其他可能使用这些文件的程序
- 检查磁盘空间
- 修改文件夹权限

## 安全注意事项

1. **数据备份**：整理前请备份重要照片
2. **权限控制**：确保对目录有读写权限
3. **测试运行**：建议先在小范围测试

## 技术栈

- **前端**：原生 HTML/CSS/JavaScript
- **后端**：Spring Boot 2.7.0
- **API**：RESTful API
- **设计**：现代化渐变设计，响应式布局

## 浏览器支持

- Chrome/Edge（推荐）
- Firefox
- Safari
- 移动端浏览器

## 性能优化

- 扫描大目录可能需要几秒钟
- 整理大量文件可能需要更长时间
- 建议一次处理不超过 10,000 个文件

## 后续功能规划

- [ ] 文件预览功能
- [ ] 批量选择处理
- [ ] 自定义格式化规则
- [ ] 进度条显示
- [ ] 文件去重功能
- [ ] 照片元数据分析

