# 快速开始指南

## 🚀 5 分钟上手

### 步骤 1：启动服务

```bash
cd photo-organizer
mvn spring-boot:run
```

等待看到以下信息：
```
Started PhotoOrganizerApplication in X.XXX seconds
```

### 步骤 2：打开浏览器

访问：`http://localhost:8080`

你会看到一个漂亮的紫色渐变界面 ✨

### 步骤 3：整理照片

1. **输入路径**：在输入框中输入你的照片目录，如 `/Users/devin/Pictures`
2. **点击"扫描照片"**：查看该目录下的照片统计
3. **点击"开始整理"**：照片会按格式自动分类

## 📁 项目文件说明

```
photo-organizer/
├── src/main/java/com/organizer/
│   ├── PhotoOrganizer.java          # 命令行版本（原有）
│   ├── PhotoOrganizerApplication.java    # Web 应用入口
│   └── PhotoOrganizerController.java    # REST API
├── src/main/resources/
│   ├── application.properties        # 配置文件
│   └── static/                      # Web 前端
│       ├── index.html              # 主页面
│       ├── style.css               # 样式
│       └── app.js                  # 前端逻辑
├── pom.xml                         # Maven 配置
├── README.md                       # 详细文档
├── WEB_GUIDE.md                    # Web 界面指南
└── QUICK_START.md                  # 本文件
```

## 🎨 界面预览

- 紫色渐变背景
- 卡片式布局
- 实时扫描统计
- 平滑动画效果

## 🛠 技术栈

- **Spring Boot 2.7** - Web 框架
- **RESTful API** - 后端接口
- **原生 JavaScript** - 前端逻辑
- **现代 CSS** - 渐变设计

## ❓ 常见问题

**Q: 无法启动服务？**  
A: 确保已安装 Java 8+ 和 Maven

**Q: 端口被占用？**  
A: 修改 `src/main/resources/application.properties` 中的端口

**Q: 找不到照片？**  
A: 检查路径是否正确，使用绝对路径

## 📚 更多文档

- [README.md](README.md) - 完整项目文档

---

**Enjoy!** 🎉

