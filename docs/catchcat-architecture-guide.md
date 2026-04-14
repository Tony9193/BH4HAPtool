# 捉小猫架构迁移说明（Phaser -> BH4HAPtools）

## 1. 目标

本说明对应你提出的需求：
- 借鉴 Web 版 phaser-catch-the-cat 的主要架构
- 在当前 Android 多模块工程中重构为可维护子程序

当前落地模块：
- feature/catchcat

---

## 2. 架构映射

Phaser 版核心层次：
- Scene（主循环与事件）
- Sprite（猫、格子、按钮）
- Solver（猫移动策略）
- Data（资源与动画）

Android 版对应层次：
- ViewModel + Engine：替代 Scene，负责状态推进与回合规则
- Compose Screen + Canvas：替代 Sprite 层，负责棋盘绘制和点击输入
- CatchCatSolver：保留 solver 抽象，可替换策略
- strings + Theme：替代 data 中与表现相关的配置

---

## 3. 代码结构

```text
feature/catchcat/
  src/main/java/com/example/bh4haptool/feature/catchcat/
    navigation/
      CatchCatDestination.kt
    domain/
      CatchCatEngine.kt
    ui/
      CatchCatUiState.kt
      CatchCatViewModel.kt
      CatchCatScreen.kt
  src/main/res/values/strings.xml
```

职责拆分：
- CatchCatEngine
  - 棋盘状态（猫坐标、墙集合、历史记录）
  - 回合推进（玩家落子 -> 猫移动）
  - 胜负判定、撤销、重开
- NearestPathSolver（在 CatchCatEngine.kt 中）
  - 计算猫到边缘的 BFS 距离
  - 在最短路方向里选择“后续路径数更多”的方向
- CatchCatViewModel
  - 管理 UI State
  - 响应点击/撤销/重置事件
- CatchCatScreen
  - 负责 Material3 页面结构和棋盘渲染

---

## 4. 规则对齐（与 Web 版）

已保留的关键规则：
- 六方向邻接（奇偶行偏移）
- 玩家每次点击新增一堵墙
- 玩家点击一次后猫移动一步
- 猫走到边缘：玩家失败
- 猫无路可走或 solver 返回无解：玩家胜利
- 支持撤销与重开

默认参数：
- 棋盘 11x11
- 初始随机墙数量 8
- 猫初始位置为中心

---

## 5. Solver 可扩展点

Solver 抽象：
- CatchCatSolver.nextDirection(width, height, walls, cat): Int

方向编码与 Web 版一致：
- 0 左
- 1 左上
- 2 右上
- 3 右
- 4 右下
- 5 左下
- -1 无解/认输

你可以在 domain 中新增策略，例如：
- RandomSolver：随机可走方向
- GreedySolver：只看最近边缘
- HybridSolver：前期保守、后期激进

然后在 CatchCatEngine 构造时注入对应 solver。

---

## 6. 主程序接入点

已接入文件：
- settings.gradle.kts
- app/build.gradle.kts
- app/src/main/java/com/example/bh4haptool/navigation/AppNavHost.kt
- app/src/main/java/com/example/bh4haptool/tool/ToolRegistry.kt
- app/src/main/res/values/strings.xml

结果：
- 首页新增“捉小猫”入口
- 点击可进入二级功能页

---

## 7. 后续可选增强

1. 难度档位
- 简单：初始墙更多
- 困难：初始墙更少，或猫策略更强

2. 更丰富动画
- 猫移动方向动画
- 结束态反馈动画

3. 对局复盘
- 保存每一步落子与猫移动轨迹
- 支持回放

4. 自动演示模式
- 玩家由策略自动落子
- 用于测试 solver 强弱
