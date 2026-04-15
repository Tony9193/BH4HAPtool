# BH4HAPtools

一个基于 Android + Jetpack Compose 的轻量工具与小游戏合集，面向日常活动、小型聚会和碎片时间娱乐使用。项目采用多模块结构，当前已支持手机与平板场景，并持续优化大屏横屏体验。

## 项目特点

- 离线可用，打开即玩，适合活动现场快速使用
- 工具与小游戏放在同一入口，切换简单
- 使用 Jetpack Compose 构建，界面统一，便于后续扩展
- 已加入平板模式，横屏大屏下支持更舒适的双栏布局

## 当前功能

### 1. 摇一摇抽签

- 支持名单抽签和随机数字抽取
- 支持手动触发与摇一摇触发
- 可自定义候选名单与数字范围

### 2. 抓小猫

- 经典围堵玩法，阻止小猫逃到边缘

### 3. 飞盘分组

- 用于随机分队与活动分组
- 支持转盘与抽签两种随机方式

### 4. 扫雷

- 支持经典扫雷玩法
- 支持自定义棋盘大小、雷数与辅助设置
- 已优化大尺寸棋盘的屏幕适配

### 5. 俄罗斯方块

- 经典消行玩法
- 支持速度与震动相关设置

### 6. 推箱子

- 支持关卡游玩、撤销、重开与选关
- 记录最优步数
- 已优化高地图和宽地图的显示适配

## v1.2.0 更新亮点

- 新增平板模式与横屏双栏布局，游戏主画面优先展示
- 普通抽签已合并进摇一摇抽签，入口更统一
- 摇一摇抽签新增随机数字模式
- 修复扫雷与推箱子在部分地图下画面超出屏幕的问题
- 补充平板模式开发文档与版本更新说明

## 技术栈

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- KSP
- Gradle 多模块工程

## 项目结构

```text
BH4HAPtool/
|- app/                  主程序与首页导航
|- core/toolkit/         公共 UI、布局与工具基础能力
|- feature/shakedraw/    摇一摇抽签
|- feature/catchcat/     抓小猫
|- feature/frisbeegroup/ 飞盘分组
|- feature/minesweeper/  扫雷
|- feature/tetris/       俄罗斯方块
|- feature/sokoban/      推箱子
`- docs/                 项目文档
```

## 运行与构建

### 环境要求

- Android Studio
- JDK 11
- Android SDK 36
- Android 12 及以上设备或模拟器

### 本地编译

```powershell
.\gradlew.bat --no-daemon :app:assembleDebug --console=plain
```

## 文档

- [主程序开发说明](docs/main-program-development-guide.md)
- [子应用开发说明](docs/subprogram-development-guide.md)
- [平板模式说明](docs/tablet-mode-guide.md)
- [传感器说明](docs/sensor_guide.md)
- [v1.2.0 更新内容](docs/release-notes-v1.2.0.md)

## 后续方向

- 继续补齐更多子应用的平板模式细节
- 持续优化大屏、横屏与极端比例设备的显示表现
- 逐步完善文档与发布说明
