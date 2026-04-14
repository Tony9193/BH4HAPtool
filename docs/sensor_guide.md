# Android 传感器使用开发指南

在 Jetpack Compose 和 Android 开发中，直接操作硬件传感器有时存在坑点。本文档总结了项目中关于传感器（加速度计、陀螺仪、地磁/罗盘）的正确用法和避坑经验。

## 1. 权限要求与声明（极其重要 ⚠️）

**运动传感器和环境传感器通常不需要任何运行时权限！**

新手常犯的错误是给调用加速度计、指南针等运动方向相关的传感器加上了 `android.permission.BODY_SENSORS` 权限。
*   `BODY_SENSORS` 是专门针对**健康类传感器**（如心率计、血氧计）设立的权限，属于非常敏感的隐私权限。
*   **加速度计(Accelerometer)**、**陀螺仪(Gyroscope)**、**磁力计(Magnetic Field)**、**旋转矢量(Rotation Vector)** 在 Android 中被视为基础输入外设，**默认直接开放**，无需任何动态授权或许可证声明即可调用。
*   只需要在 `AndroidManifest.xml` 中以 `<uses-feature>` 的形式声明，以便在应用商店进行过滤（可选）：

```xml
<uses-feature android:name="android.hardware.sensor.accelerometer" android:required="false" />
<uses-feature android:name="android.hardware.sensor.gyroscope" android:required="false" />
<uses-feature android:name="android.hardware.sensor.compass" android:required="false" />
```

## 2. 摇一摇功能 (Shake Detection) 的选型

开发“摇一摇”功能时，首选的传感器应当是**加速度计(Accelerometer)**，而不是陀螺仪。

### 为什么不用陀螺仪？
陀螺仪测量的是角速度（手机围绕某个轴旋转的速度）。用户如果在平面上平移地、像摇沙锤一样前后水平摇动手机，**角速度几乎为零**。此时陀螺仪将完全无法检测到动作。

### 正确的加速度计摇一摇逻辑：
加速度计的 3 轴向量模长在手机静止时等于地球重力加速度（约 `9.8 m/s²` 或者 `SensorManager.GRAVITY_EARTH`）。
当摇晃时，模长会发生剧烈变化。我们只要测量其模长变化超出了一个预定阈值即可：

```kotlin
val magnitude = sqrt(x*x + y*y + z*z)
val isShaking = abs(magnitude - SensorManager.GRAVITY_EARTH) >= SHAKE_THRESHOLD
// SHAKE_THRESHOLD 建议设置为 10.0f ~ 15.0f 左右
```

## 3. 指南针与罗盘开发 (Compass)

要实现在屏幕上绘制精确稳定的指南针：

### 推荐的传感器：
优先使用 **旋转矢量传感器 (`TYPE_ROTATION_VECTOR`)**。
*   它是 Android 系统融合了加速度计、陀螺仪和磁力计数据的虚拟传感器，非常平滑抗干扰。
*   使用 `SensorManager.getRotationMatrixFromVector` 可以直接拿到旋转矩阵，再通过 `SensorManager.getOrientation` 算出精确的偏航角（Yaw / Azimuth）。

### UI 绘制逻辑要点 (Compose)：
在绘制传统罗盘时（保持真实世界的方向锚定）：
1.  **指南盘应跟随手机旋转**：当手机左转时，指南盘应向右以相同的角度旋转，以保证“南方”颜色块一直固守现实世界的中轴线。
2.  **绘制实现**：如果设备当前指向的角度是 `heading` （范围 0~360），则整个颜色罗盘需要在 Canvas 中旋转 `-heading` 角度。（或者通过 `startAngle = baseline + (-heading)`）。
3.  **指针固定**：指针（代表手机头）不需要依据传感器转动，它应该永久锁定在 UI 坐标系的 `顶部 / 前方`（比如标准的 `-90度` 位置）。

## 4. Jetpack Compose 中的生命周期管理

传感器非常耗电，请务必保证传感器监听器的注册与注销跟随 UI 的出现与消失挂钩。常用的模式是利用 `DisposableEffect` 包裹传感器的生命周期：

```kotlin
val sensorController = remember(sensorManager) { MySensorController(sensorManager) }

DisposableEffect(uiState.isActive) {
    if (uiState.isActive) {
        sensorController.start()
    }
    onDispose {
        sensorController.stop()
    }
}
```

注意：不要将不稳定的参数传递给 Controller，以免 `remember` 因为传参变化而被反复触发重组，导致传感器被微观上频繁启停而漏掉真实的事件。
