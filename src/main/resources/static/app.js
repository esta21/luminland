// API 基础地址
const API_BASE = '/api';

// 全局变量存储扫描结果
let scanData = null;

/**
 * 浏览文件夹（使用文件系统访问 API）
 */
async function browseFolder() {
    try {
        // 检查浏览器是否支持文件系统访问 API
        if ('showDirectoryPicker' in window) {
            const dirHandle = await window.showDirectoryPicker();
            const pathInput = document.getElementById('path-input');
            
            // 获取目录名称
            const dirName = dirHandle.name;
            
            // 清空输入框并设置占位符提示用户输入完整路径
            pathInput.value = '';
            pathInput.placeholder = `已选择目录: ${dirName} (请在文件管理器中复制完整路径并粘贴到这里)`;
        } else {
            alert('您的浏览器不支持文件系统访问 API，请手动输入目录路径。\n\n' +
                  '提示：您可以打开文件管理器，按住 Option 键并拖拽文件夹到文本编辑器或终端中，即可获取完整路径。');
        }
    } catch (error) {
        if (error.name !== 'AbortError') {
            console.error('选择目录时出错:', error);
            alert('选择目录时出错: ' + error.message);
        }
    }
}

/**
 * 显示帮助对话框
 */
function showHelpDialog() {
    const helpText = `
如何获取目录的完整路径：

方法一：使用文件管理器（推荐）
1. 打开 Finder 或文件管理器
2. 导航到目标文件夹
3. 按住 Option 键并拖拽文件夹到文本编辑器或终端中
4. 复制显示的路径并粘贴到输入框中

方法二：使用文件管理器菜单
1. 右键点击目标文件夹
2. 按住 Option 键时选择"拷贝 '文件夹名称' 为路径名"
3. 粘贴到输入框中

方法三：使用终端
1. 打开终端应用
2. 将文件夹拖拽到终端窗口中
3. 复制生成的路径并粘贴到输入框中
    `.trim();
    
    alert(helpText);
}

/**
 * 扫描照片和视频
 */
async function scanPhotos() {
    const pathInput = document.getElementById('path-input');
    const path = pathInput.value.trim();
    
    if (!path) {
        showError('请输入照片和视频目录路径');
        return;
    }
    
    hideElement('error');
    hideElement('success');
    hideElement('result-card');
    showElement('loading');
    hideElement('scan-result-card');
    
    try {
        const response = await fetch(API_BASE + '/scan', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ path: path })
        });
        
        const data = await response.json();
        hideElement('loading');
        
        if (data.success) {
            scanData = data.data;
            showElement('scan-result-card');
            displayScanResults(data.data);
            document.getElementById('organize-btn').disabled = false;
            showSuccess('文件扫描完成！');
        } else {
            showError(data.message || '扫描失败');
        }
        
    } catch (error) {
        hideElement('loading');
        showError('连接服务器失败: ' + error.message);
        console.error(error);
    }
}

/**
 * 整理照片和视频
 */
async function organizePhotos() {
    const pathInput = document.getElementById('path-input');
    const path = pathInput.value.trim();
    
    if (!path) {
        showError('请输入照片和视频目录路径');
        return;
    }
    
    if (!scanData) {
        showError('请先扫描文件后再整理');
        return;
    }
    
    // 确认操作
    const fileCount = scanData.totalFiles;
    const formatCount = Object.keys(scanData.formats).length;
    
    let confirmMsg = `确定要整理照片和视频吗？\n\n`;
    confirmMsg += `• 文件总数：${fileCount} 个\n`;
    confirmMsg += `• 日期种类：${formatCount} 种\n\n`;
    confirmMsg += `文件将被移动到按"年月/格式"的层级文件夹中。`;
    
    if (!confirm(confirmMsg)) {
        return;
    }
    
    hideElement('error');
    hideElement('success');
    showElement('loading');
    
    try {
        const response = await fetch(API_BASE + '/organize', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ path: path })
        });
        
        const data = await response.json();
        hideElement('loading');
        
        if (data.success) {
            showElement('result-card');
            displayResults(data.data);
            showSuccess('照片和视频整理完成！');
            document.getElementById('organize-btn').disabled = true;
        } else {
            showError(data.message || '整理失败');
        }
        
    } catch (error) {
        hideElement('loading');
        showError('连接服务器失败: ' + error.message);
        console.error(error);
    }
}

/**
 * 显示扫描结果
 */
function displayScanResults(data) {
    const resultsDiv = document.getElementById('scan-results');
    
    let html = `
        <div class="result-item">
            <h3>统计信息</h3>
            <div class="stat-grid">
                <div class="stat-card">
                    <h3>${data.totalFiles}</h3>
                    <p>文件总数</p>
                </div>
                <div class="stat-card">
                    <h3>${Object.keys(data.formats).length}</h3>
                    <p>日期种类</p>
                </div>
                <div class="stat-card">
                    <h3>${data.totalSizeFormatted}</h3>
                    <p>总大小</p>
                </div>
            </div>
        </div>
        
        <div class="result-item">
            <h3>日期统计</h3>
            <div class="format-list">
    `;
    
    const formatEntries = Object.entries(data.formats).sort((a, b) => b[1] - a[1]);
    for (const [format, count] of formatEntries) {
        html += `<div class="format-tag">${format}: ${count} 个</div>`;
    }
    
    html += `
            </div>
        </div>
    `;
    
    // 显示部分文件列表
    if (data.photosByFormat) {
        html += `
            <div class="result-item">
                <h3>文件预览（按日期分组）</h3>
        `;
        
        let fileCount = 0;
        for (const [format, files] of Object.entries(data.photosByFormat)) {
            if (files && files.length > 0) {
                html += `<h4>${format} (${files.length} 个文件)</h4>`;
                
                for (let i = 0; i < Math.min(5, files.length); i++) {
                    const file = files[i];
                    html += `
                        <div class="file-item">
                            <span class="file-name">${file.name}</span>
                            <span class="file-size">${file.sizeFormatted}</span>
                        </div>
                    `;
                    fileCount++;
                }
                
                if (files.length > 5) {
                    html += `<p style="text-align: center; margin: 5px 0; color: #666;">
                        ... 还有 ${files.length - 5} 个文件
                    </p>`;
                }
            }
        }
        
        if (fileCount === 0) {
            html += `<p>未找到文件</p>`;
        }
        
        html += `</div>`;
    }
    
    resultsDiv.innerHTML = html;
}

/**
 * 显示整理结果
 */
function displayResults(data) {
    const resultsDiv = document.getElementById('results');
    
    let html = `
        <div class="result-item">
            <h3>统计信息</h3>
            <div class="stat-grid">
                <div class="stat-card">
                    <h3>${data.totalFiles}</h3>
                    <p>整理文件数</p>
                </div>
            </div>
            <p style="margin-top: 15px; text-align: center; color: #666;">
                ${data.message}
            </p>
        </div>
        
        <div class="result-item">
            <h3>日期统计</h3>
            <div class="format-list">
    `;
    
    const formatEntries = Object.entries(data.formats).sort((a, b) => b[1] - a[1]);
    for (const [format, count] of formatEntries) {
        html += `<div class="format-tag">${format}: ${count} 个</div>`;
    }
    
    html += `
            </div>
        </div>
    `;
    
    if (data.movedFiles && data.movedFiles.length > 0) {
        html += `
            <div class="result-item">
                <h3>已移动文件（前10个）</h3>
        `;
        
        for (let i = 0; i < Math.min(10, data.movedFiles.length); i++) {
            const file = data.movedFiles[i];
            html += `
                <div class="file-item">
                    <span class="file-name">${file.originalName}</span>
                    <span class="file-size">→ ${file.format}</span>
                </div>
            `;
        }
        
        if (data.movedFiles.length > 10) {
            html += `<p style="text-align: center; margin-top: 10px; color: #666;">
                还有 ${data.movedFiles.length - 10} 个文件...
            </p>`;
        }
        
        html += `</div>`;
    }
    
    resultsDiv.innerHTML = html;
}

/**
 * 显示成功消息
 */
function showSuccess(message) {
    const successDiv = document.getElementById('success');
    document.getElementById('success-message').textContent = message;
    showElement('success');
    
    setTimeout(() => {
        hideElement('success');
    }, 5000);
}

/**
 * 显示错误消息
 */
function showError(message) {
    const errorDiv = document.getElementById('error');
    document.getElementById('error-message').textContent = message;
    showElement('error');
}

/**
 * 显示元素
 */
function showElement(id) {
    document.getElementById(id).style.display = 'block';
}

/**
 * 隐藏元素
 */
function hideElement(id) {
    document.getElementById(id).style.display = 'none';
}

/**
 * 格式化文件大小
 */
function formatFileSize(bytes) {
    if (bytes < 1024) {
        return bytes + ' B';
    } else if (bytes < 1024 * 1024) {
        return (bytes / 1024).toFixed(2) + ' KB';
    } else if (bytes < 1024 * 1024 * 1024) {
        return (bytes / (1024 * 1024)).toFixed(2) + ' MB';
    } else {
        return (bytes / (1024 * 1024 * 1024)).toFixed(2) + ' GB';
    }
}

// 按 Enter 键扫描
document.addEventListener('DOMContentLoaded', function() {
    const pathInput = document.getElementById('path-input');
    pathInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            scanPhotos();
        }
    });
});