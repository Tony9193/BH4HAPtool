# BH4HAPtools 主程序开发文档

## 1. 文档定位

这份文档面向主程序（app 壳层）开发，不讨论具体子程序业务实现细节。

主程序的目标是：
- 提供统一入口和稳定导航骨架
- 管理模块注册与全局 UI 框架
- 承担全局配置、依赖注入入口和发布打包
- 保持对子程序低耦合、可持续扩展

配套文档：
- docs/subprogram-development-guide.md（子程序开发手册）

---

## 2. 主程序边界与职责

### 2.1 主程序负责

- Application 启动入口和全局初始化
- Activity 壳层、主题和根导航
- 首页入口展示（卡片网格）
- 子程序注册中心聚合
- 全局字符串资源和应用级清单声明
- 构建配置、签名、打包与发布流程

### 2.2 主程序不负责

- 子程序具体业务逻辑（应位于 feature 模块）
- 子程序内部状态细节
- 子程序私有数据模型

### 2.3 依赖方向

- app 可以依赖：core + feature
- feature 只能依赖：core（不能互相依赖）
- core 不依赖 app 和 feature

---

## 3. 当前主程序结构（基于现状）

```text
app/
  src/main/
    AndroidManifest.xml
    java/com/example/bh4haptool/
      BH4HAPtoolsApplication.kt
      MainActivity.kt
      navigation/
        AppDestination.kt
        AppNavHost.kt
      tool/
        ToolEntry.kt
        ToolRegistry.kt
      ui/
        home/
          HomeScreen.kt
        theme/
          Color.kt
          Theme.kt
          Type.kt
    res/values/
      strings.xml
  build.gradle.kts
```

---

## 4. 启动与页面链路

当前启动链路：

1. Android 启动 app，进入 `BH4HAPtoolsApplication`
2. `MainActivity` 执行 `enableEdgeToEdge()`
3. `MainActivity` 装载 `BH4HAPtoolTheme`
4. `BH4HAPtoolApp()` 启动 `AppNavHost`
5. `AppNavHost` 以 `home` 作为 startDestination
6. 首页 `HomeRoute` 读取 `ToolRegistry.entries` 渲染入口卡片
7. 点击卡片根据 `tool.route` 导航到具体子程序页面

主程序里最核心的 4 个点：
- 启动：BH4HAPtoolsApplication.kt
- 壳层：MainActivity.kt
- 导航：AppNavHost.kt
- 注册：ToolRegistry.kt

---

## 5. 主程序核心组件说明

### 5.1 Application 层

文件：`app/src/main/java/com/example/bh4haptool/BH4HAPtoolsApplication.kt`

- 使用 `@HiltAndroidApp` 标记
- 是 Hilt 依赖注入的根入口
- 后续全局初始化（日志、崩溃上报、全局配置）优先放这里

开发约定：
- 保持轻量初始化，耗时工作不要阻塞主线程
- 仅放全局职责，不要掺入页面逻辑

### 5.2 Activity 壳层

文件：`app/src/main/java/com/example/bh4haptool/MainActivity.kt`

- 使用 `@AndroidEntryPoint`
- 负责主题装载与根 Compose 容器
- 不应写具体业务逻辑

开发约定：
- Activity 只承载壳层职责
- 业务路由和页面逻辑下放到导航/feature

### 5.3 根导航

文件：`app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt`

- 维护全局 NavHost
- 聚合首页与各 feature route
- 控制 startDestination

开发约定：
- AppNavHost 只做路由编排，不写业务判断
- 每新增一个子程序，必须在此增加 composable 入口

### 5.4 工具注册中心

文件：`app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt`

- 统一维护首页可见入口
- 每个入口包含 id/title/description/route

文件：`app/src/main/java/com/example/bh4haptool/tool/ToolEntry.kt`

- 入口数据结构定义
- 标题与描述使用 `@StringRes`

开发约定：
- `id` 必须稳定，不要随意改（便于埋点和排序配置）
- 文案必须走资源，不要硬编码
- `route` 必须与 feature destination 对齐

### 5.5 首页

文件：`app/src/main/java/com/example/bh4haptool/ui/home/HomeScreen.kt`

- 使用 Material 3 Scaffold + TopAppBar
- 使用 LazyVerticalGrid 展示入口卡片
- 点击后回调 `onToolSelected`

开发约定：
- 首页只做导航，不写业务逻辑
- 首页布局保持可扩展（新增入口时不应改结构）

### 5.6 主题

文件：
- `app/src/main/java/com/example/bh4haptool/ui/theme/Theme.kt`
- `app/src/main/java/com/example/bh4haptool/ui/theme/Color.kt`
- `app/src/main/java/com/example/bh4haptool/ui/theme/Type.kt`

- 已启用 Material 3
- 支持动态色（Android 12+）

开发约定：
- 全局主题变更通过 theme 包统一维护
- feature 不要自行创建割裂主题体系

---

## 6. 主程序开发常见任务

## 6.1 新增一个子程序入口（主程序侧）

主程序侧最小改动清单：

1. app/build.gradle.kts 增加模块依赖
2. AppNavHost.kt 增加 composable(route)
3. ToolRegistry.kt 增加 ToolEntry
4. app/res/values/strings.xml 增加入口标题与描述

> 说明：子程序内部代码创建请看子程序手册。

### 6.2 新增一个主程序页面（比如设置页）

建议步骤：

1. 在 app 内新增 route 常量（AppDestination）
2. 在 app 内新增页面文件（例如 ui/settings/SettingsScreen.kt）
3. 在 AppNavHost 注册 composable
4. 从首页或 TopAppBar 增加跳转入口
5. 补充 strings 文案与测试

建议：
- 主程序页面应放全局功能（设置、关于、反馈入口）
- 不要把子程序业务回塞到 app 页面

### 6.3 调整首页卡片表现

常见改动点：
- 栅格列宽（GridCells.Adaptive）
- 卡片排版层级（标题、描述、图标）
- 分组/排序策略（由 ToolRegistry 支持）

建议扩展：
- 在 ToolEntry 增加 group、order、enabled 字段
- 在 HomeScreen 按组渲染 Section

### 6.4 新增全局权限或硬件能力

改动文件：`app/src/main/AndroidManifest.xml`

规则：
- hardware 建议 `required=false`，提供降级路径
- 权限遵循最小化原则
- 功能入口需要对不可用场景给出可理解文案

---

## 7. 构建与发布（主程序关注）

### 7.1 app 模块构建配置

文件：`app/build.gradle.kts`

关键点：
- compileSdk/minSdk/targetSdk 全局版本控制
- app 依赖所有 feature 模块
- hilt + ksp 注解处理
- Compose 构建开关

### 7.2 当前构建兼容策略（必须知晓）

文件：`gradle.properties`

当前存在两项兼容开关：
- `android.newDsl=false`
- `android.disallowKotlinSourceSets=false`

用途：
- 兼容当前 AGP9 + Hilt/KSP 组合构建行为

注意：
- 这是过渡兼容配置
- 升级 AGP/Hilt 后应优先评估回收

### 7.3 常用命令

```powershell
.\gradlew.bat --no-daemon :app:assembleDebug --console=plain
.\gradlew.bat --no-daemon :app:testDebugUnitTest --console=plain
.\gradlew.bat --no-daemon :core:toolkit:testDebugUnitTest --console=plain
```

---

## 8. 主程序质量规范

### 8.1 代码层面

- Activity 无业务逻辑
- AppNavHost 只做路由聚合
- 首页只做入口展示
- app 不直接操作 feature 内部状态

### 8.2 UI 层面

- Material 3 组件统一
- 文案全部资源化
- 返回行为一致（popBackStack）

### 8.3 稳定性层面

- 新增入口不影响已有入口可用性
- 无硬件能力时可降级
- 异常有明确反馈文案

---

## 9. 测试策略（主程序维度）

## 9.1 必做

- 导航主链路测试：首页 -> 子程序 -> 返回
- 首页入口完整性检查：ToolRegistry 与 AppNavHost 一致

### 9.2 建议

- 首页渲染快照测试（标题、描述、入口数量）
- 入口点击行为测试（路由正确性）

### 9.3 回归场景

每次主程序改动后至少手测：
- 首次启动
- 首页渲染
- 两个已有子程序可进入
- 旋转屏幕后的状态
- 无网络下启动与基本操作

---

## 10. 主程序变更 DoD（完成定义）

一次主程序改动合入前，应满足：

1. app 构建通过
2. 核心单测通过
3. 新入口可进可退
4. 原有入口不回归
5. 关键文案已补齐资源
6. 无无意义警告和明显坏味道
7. 文档同步（必要时更新本文件）

---

## 11. 常见问题排查

### 11.1 首页有入口但点击崩溃/无响应

检查：
1. ToolRegistry route 是否正确
2. AppNavHost 是否注册对应 composable
3. app/build.gradle.kts 是否依赖该 feature

### 11.2 子程序可编译但首页看不到

检查：
1. ToolRegistry 是否新增 ToolEntry
2. app strings 是否新增标题/描述
3. HomeScreen 是否使用 ToolRegistry.entries（当前是）

### 11.3 Hilt 注入相关编译异常

检查：
1. Application 是否 `@HiltAndroidApp`
2. MainActivity 是否 `@AndroidEntryPoint`
3. 模块是否使用 `ksp(libs.hilt.compiler)`
4. gradle.properties 兼容开关是否存在

### 11.4 主题表现异常

检查：
1. MainActivity 是否包裹 BH4HAPtoolTheme
2. Theme.kt 动态色逻辑是否被误改
3. feature 页面是否滥用局部主题覆盖

---

## 12. 推荐主程序迭代路线

### 阶段 A（当前）

- 入口网格 + 路由聚合 + 基础主题

### 阶段 B（建议近期）

- 首页分组（小游戏/随机/记录）
- 搜索入口
- 入口排序与开关能力（通过 ToolRegistry 字段扩展）

### 阶段 C（中期）

- 主程序设置页（全局偏好、阈值统一调节）
- 主程序级 UI 自动化测试
- 版本更新说明页（离线 changelog）

---

## 13. 主程序改动速查清单

当你改动主程序时，通常涉及以下文件：

- app/src/main/java/com/example/bh4haptool/BH4HAPtoolsApplication.kt
- app/src/main/java/com/example/bh4haptool/MainActivity.kt
- app/src/main/java/com/example/bh4haptool/navigation/AppDestination.kt
- app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt
- app/src/main/java/com/example/bh4haptool/tool/ToolEntry.kt
- app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt
- app/src/main/java/com/example/bh4haptool/ui/home/HomeScreen.kt
- app/src/main/java/com/example/bh4haptool/ui/theme/Theme.kt
- app/src/main/res/values/strings.xml
- app/src/main/AndroidManifest.xml
- app/build.gradle.kts
- gradle.properties

这份清单可作为主程序开发时的变更范围参考。