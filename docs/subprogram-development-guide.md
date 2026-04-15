# BH4HAPtools 子程序开发文档

## 1. 文档目标

本手册用于指导你在当前工程中快速开发和接入新的子程序（功能模块）。

适用范围：
- 单 APK 内部模块化架构
- 离线可用
- Android Compose + Material 3
- Hilt + KSP 注入

本手册覆盖：
- 当前架构说明
- 新增子程序的标准流程
- 代码模板
- 测试与验收标准
- 常见问题与排错

---

## 2. 当前架构快照

### 2.1 模块结构

- app
  - 应用壳层
  - 首页入口网格
  - 导航路由聚合
  - 子程序注册中心接入
- core/toolkit
  - 跨子程序复用能力
  - 抽签核心逻辑
  - DataStore 偏好持久化
  - 通用 DI 绑定
- feature/shakedraw
  - 摇一摇抽签子程序（已合并普通抽签功能）

### 2.2 路由与页面层级

- 一级页面：工具箱首页
- 二级页面：子程序页面
- 路由来源：每个子程序自行暴露 destination route，app 壳层聚合

### 2.3 核心设计原则（已落地）

- 主框架稳定，子程序独立扩展
- 首页只做导航，不承载复杂业务
- 业务逻辑尽量进入 feature ViewModel 或 core 用例
- 公共逻辑下沉到 core/toolkit，避免跨 feature 相互依赖

---

## 3. 关键文件职责

### 3.1 壳层

- app/src/main/java/com/example/bh4haptool/MainActivity.kt
  - App 入口
  - 加载主题
  - 承载 AppNavHost

- app/src/main/java/com/example/bh4haptool/BH4HAPtoolsApplication.kt
  - Hilt Application 声明

- app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt
  - 根导航图
  - 聚合首页与所有子程序路由

- app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt
  - 子程序入口注册中心
  - 首页入口数据源

- app/src/main/java/com/example/bh4haptool/ui/home/HomeScreen.kt
  - 首页卡片网格
  - 点击入口跳转子程序

### 3.2 Core 公共层

- core/toolkit/src/main/java/com/example/bh4haptool/core/toolkit/draw
  - 抽签算法与名单解析

- core/toolkit/src/main/java/com/example/bh4haptool/core/toolkit/data
  - DataStore 偏好仓库
  - 共享配置模型

- core/toolkit/src/main/java/com/example/bh4haptool/core/toolkit/di/ToolkitModule.kt
  - 通用依赖绑定

### 3.3 Feature 子程序层

每个子程序至少包含：
- navigation: route 定义
- ui: Screen / UiState / ViewModel
- res/values/strings.xml: 子程序文案

---

## 4. 环境与构建约定

### 4.1 当前技术栈

- AGP: 9.1.0
- Kotlin: 2.2.10
- Compose BOM: 2026.02.01
- Navigation Compose: 2.9.0
- Hilt: 2.57.1
- KSP: 2.2.10-2.0.2

### 4.2 构建兼容开关（当前仓库必须保留）

位于 gradle.properties：
- android.newDsl=false
- android.disallowKotlinSourceSets=false

说明：
- 这是当前 AGP 9 + Hilt/KSP 组合下的兼容策略。
- 如果你升级 AGP / Hilt 并验证通过，可再评估移除。

### 4.3 常用命令

```powershell
.\gradlew.bat --no-daemon :app:assembleDebug --console=plain
.\gradlew.bat --no-daemon :core:toolkit:testDebugUnitTest --console=plain
```

---

## 5. 新增子程序标准流程（强烈建议按顺序执行）

以下示例以新增子程序 luckywheel（幸运转盘）为例。

### 步骤 1：创建 feature 模块

1) 目录创建
- feature/luckywheel
- feature/luckywheel/src/main/java/com/example/bh4haptool/feature/luckywheel
- feature/luckywheel/src/main/res/values

2) 在 settings.gradle.kts 增加 include

```kotlin
include(":feature:luckywheel")
```

3) 新建 feature/luckywheel/build.gradle.kts，参考现有 feature 模块

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.bh4haptool.feature.luckywheel"
    compileSdk = 36

    defaultConfig {
        minSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:toolkit"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
}
```

4) 新建空清单与混淆文件
- feature/luckywheel/src/main/AndroidManifest.xml
- feature/luckywheel/consumer-rules.pro
- feature/luckywheel/proguard-rules.pro

AndroidManifest 示例：

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest />
```

### 步骤 2：接入 app 依赖

编辑 app/build.gradle.kts：

```kotlin
dependencies {
    implementation(project(":feature:luckywheel"))
}
```

### 步骤 3：定义路由

新增 feature/luckywheel/.../navigation/LuckyWheelDestination.kt：

```kotlin
object LuckyWheelDestination {
    const val route = "tool/lucky-wheel"
}
```

路由规范：
- 前缀统一使用 tool/
- kebab-case 或稳定短词
- 一旦发布尽量不变

### 步骤 4：建立子程序 UI 三件套

建议最小集合：
- LuckyWheelUiState.kt
- LuckyWheelViewModel.kt
- LuckyWheelScreen.kt

UiState 示例：

```kotlin
data class LuckyWheelUiState(
    val input: String = "",
    val result: String? = null,
    val message: String? = null
)
```

ViewModel 模板建议：
- 仅暴露只读 uiState
- 通过事件函数驱动状态变化
- 需要持久化时注入 ToolboxPreferencesRepository

Screen 模板建议：
- 采用 Route 组合函数 + hiltViewModel
- 顶部 TopAppBar + 返回
- 业务操作按钮统一使用 Material 3

### 步骤 5：补充字符串资源

新增 feature/luckywheel/src/main/res/values/strings.xml：

```xml
<resources>
    <string name="lucky_wheel_title">幸运转盘</string>
    <string name="lucky_wheel_back">返回</string>
    <string name="lucky_wheel_start">开始</string>
</resources>
```

命名规范：
- 使用模块前缀，如 lucky_wheel_
- 不要复用其他模块前缀

### 步骤 6：注册首页入口

编辑 app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt：

```kotlin
ToolEntry(
    id = "lucky_wheel",
    titleRes = R.string.tool_lucky_wheel_title,
    descriptionRes = R.string.tool_lucky_wheel_description,
    route = LuckyWheelDestination.route
)
```

并在 app 的 strings.xml 增加首页卡片文案：

```xml
<string name="tool_lucky_wheel_title">幸运转盘</string>
<string name="tool_lucky_wheel_description">随机轮盘选择结果</string>
```

### 步骤 7：加入导航图

编辑 app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt：

```kotlin
composable(route = LuckyWheelDestination.route) {
    LuckyWheelRoute(onBack = { navController.popBackStack() })
}
```

### 步骤 8：必要时扩展权限或硬件声明

如果子程序依赖硬件（如传感器、蓝牙等），在 app/src/main/AndroidManifest.xml 增加：
- uses-feature（建议 required=false，提升兼容）
- uses-permission（按最小权限原则）

### 步骤 9：必要时扩展 DataStore

若需要保存该子程序配置：
1) 在 ToolboxSettings 增加字段
2) 在 ToolboxPreferencesRepository 增加 key + read + update 方法
3) 在子程序 ViewModel 中读写

推荐约定：
- key 使用模块前缀
- 保持默认值合理，避免首次启动崩溃

### 步骤 10：最小测试补齐

至少补两类测试：
- 纯逻辑单元测试（优先）
- 页面关键路径测试（可后续补）

你可以参考：
- core/toolkit/src/test/java/com/example/bh4haptool/core/toolkit/draw/CandidateParserTest.kt
- core/toolkit/src/test/java/com/example/bh4haptool/core/toolkit/draw/RandomDrawEngineTest.kt

### 步骤 11：验收命令

```powershell
.\gradlew.bat --no-daemon :app:assembleDebug --console=plain
.\gradlew.bat --no-daemon :core:toolkit:testDebugUnitTest --console=plain
```

如果子程序有独立测试，可补跑：

```powershell
.\gradlew.bat --no-daemon :feature:luckywheel:testDebugUnitTest --console=plain
```

---

## 6. 子程序开发规范

### 6.1 命名规范

- 模块名：feature:xxx
- 包名：com.example.bh4haptool.feature.xxx
- 路由：tool/xxx
- 首页入口 id：snake_case
- 资源前缀：xxx_

### 6.2 代码组织规范

每个子程序目录建议：

- navigation/
  - XxxDestination.kt
- ui/
  - XxxScreen.kt
  - XxxUiState.kt
  - XxxViewModel.kt
- domain/（可选）
  - UseCase、规则引擎
- data/（可选）
  - 模块私有仓库

### 6.3 依赖边界

- 允许依赖：core/toolkit
- 不允许：feature 之间直接依赖
- 跨子程序共享通过 core 扩展提供

### 6.4 UI 约定

- Material 3 组件优先
- 统一 TopAppBar + 返回
- 首页不写业务逻辑
- 重操作使用清晰反馈文案

### 6.5 状态管理约定

- Screen 只负责渲染与事件转发
- ViewModel 负责状态流和业务协调
- UiState 保持可序列化思维（便于后续状态恢复）

---

## 7. 发布前 DoD（Definition of Done）

一个新子程序在合入前，应满足：

1. 能从首页卡片进入并返回。
2. 核心功能在无网络环境可用。
3. 不依赖其他 feature 模块内部实现。
4. 关键异常路径有用户提示（空输入、硬件不支持等）。
5. 构建通过：app assembleDebug。
6. 至少有一组逻辑单测。
7. 资源命名、路由命名符合规范。

---

## 8. 常见问题与排错

### 8.1 Hilt 插件报错 Android BaseExtension not found

现象：编译期 Hilt 插件无法应用。

处理：
- 确认 gradle.properties 中 android.newDsl=false
- 确认模块使用 hilt + ksp 而不是 kapt

### 8.2 KSP 生成目录相关错误

现象：提示不允许 kotlin.sourceSets 追加。

处理：
- 确认 gradle.properties 中 android.disallowKotlinSourceSets=false

### 8.3 Compose 页面出现 collectAsState 未解析

处理：
- 增加 import androidx.compose.runtime.collectAsState

### 8.4 TopAppBar 报 experimental API

处理：
- 在对应函数或文件添加 @OptIn(ExperimentalMaterial3Api::class)

### 8.5 子程序已实现但首页看不到入口

检查顺序：
1) ToolRegistry 是否新增条目
2) app strings 是否新增标题和描述
3) AppNavHost 是否新增 composable
4) app/build.gradle.kts 是否添加模块依赖

---

## 9. 推荐开发流程（每个子程序）

1. 建模块与基础文件（10 分钟）
2. 先打通路由跳转（15 分钟）
3. 实现最小功能闭环（60 分钟）
4. 接入持久化（可选，30 分钟）
5. 补单测与异常路径（30 分钟）
6. 构建验收（10 分钟）

---

## 10. 后续可演进方向

- 为 ToolRegistry 增加分类、排序和开关字段
- 引入统一的 feature contract，减少重复模板代码
- 将公共输入组件（名单编辑、结果卡片）下沉到 core-ui（如未来需要）
- 增加子程序脚手架脚本，一键生成模块骨架

---

## 11. 速查清单（新增子程序必须改的文件）

- settings.gradle.kts
- app/build.gradle.kts
- app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt
- app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt
- app/src/main/res/values/strings.xml
- feature/新模块/build.gradle.kts
- feature/新模块/src/main/AndroidManifest.xml
- feature/新模块/src/main/java/.../navigation/XxxDestination.kt
- feature/新模块/src/main/java/.../ui/XxxScreen.kt
- feature/新模块/src/main/java/.../ui/XxxViewModel.kt
- feature/新模块/src/main/java/.../ui/XxxUiState.kt
- feature/新模块/src/main/res/values/strings.xml

以上清单可作为每次开发新子程序的最小改动集合。

---

## 12. 架构借鉴示例（俄罗斯方块 + 推箱子）

为了避免在每个小游戏里重复造轮子，建议沿用当前仓库已经验证过的三层模式：

- domain 层只做规则与状态快照，不依赖 Compose。
- ui 层中的 ViewModel 负责调度、状态流、持久化读写。
- Screen 只负责渲染和事件转发。

### 12.1 俄罗斯方块（feature:tetris）

建议目录：

- navigation/TetrisDestination.kt
- domain/TetrisEngine.kt
- ui/TetrisUiState.kt
- ui/TetrisViewModel.kt
- ui/TetrisScreen.kt

关键借鉴点：

1. 借鉴 Minesweeper 的计时与状态同步：
  - 用 ViewModel 内 Job 驱动 tick。
  - 每次引擎更新后只通过 snapshot 回灌 UiState。
2. 借鉴 Minesweeper 的设置草稿模式：
  - 先改 draft，再统一 apply 写入 DataStore。
3. 借鉴 CatchCat 的“棋盘在上、按钮在中、状态在下”布局，保证小屏可操作。

推荐最小持久化字段：

- tetris_start_level
- tetris_vibration_enabled
- tetris_high_score
- tetris_best_level

### 12.2 推箱子（feature:sokoban）

建议目录：

- navigation/SokobanDestination.kt
- domain/SokobanLevel.kt
- domain/SokobanEngine.kt
- ui/SokobanUiState.kt
- ui/SokobanViewModel.kt
- ui/SokobanScreen.kt

关键借鉴点：

1. 借鉴 CatchCat 的棋盘点击输入映射（坐标 -> 操作方向）。
2. 借鉴 Minesweeper 的状态卡结构，统一展示关卡、步数、通关和文案。
3. 借鉴现有 DataStore 约定，把统计存储为简单 key/value：
  - 累计通关数
  - 每关最佳步数（编码为字符串）
  - 上次关卡索引

推荐最小持久化字段：

- sokoban_vibration_enabled
- sokoban_completed_levels
- sokoban_best_moves
- sokoban_last_level_index

### 12.3 本次实践的复用结论

1. Domain 引擎 + Snapshot 的模式适用于回合制（推箱子）和实时类（俄罗斯方块）两种玩法。
2. 对于实时游戏，ViewModel 持有 tick Job 能够稳定控制生命周期，避免 Compose 重组导致的时序问题。
3. 对于关卡类游戏，DataStore 以“轻编码字符串 + 小型映射”持久化足够实用，后续再按需演进为 JSON。