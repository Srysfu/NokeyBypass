<div align="center">

# NokeyBypass

**NokeyBypass** 是一个基于 Xposed Framework 的 Android Hook 模块，  
针对某数字钥匙应用进行签名校验绕过、运行环境检测绕过以及皮肤付费限制解除。

<p align="center">
  <img src="https://img.shields.io/badge/Android-7.0%2B-3DDC84?logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Xposed-API%2082-FF6600" />
  <img src="https://img.shields.io/badge/Language-Java%208-ED8B00?logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Gradle-8.x-02303A?logo=gradle&logoColor=white" />
  <img src="https://img.shields.io/badge/License-MIT-blue" />
</p>

<p align="center">
  <img src="https://img.shields.io/github/stars/Srysfu/NokeyBypass?style=flat&logo=github" />
  <img src="https://img.shields.io/github/last-commit/Srysfu/NokeyBypass?style=flat" />
  <img src="https://img.shields.io/github/repo-size/Srysfu/NokeyBypass?style=flat" />
  <img src="https://img.shields.io/github/actions/workflow/status/Srysfu/NokeyBypass/build.yml?style=flat&logo=githubactions" />
</p>

</div>

---

## 功能概览

| 功能模块 | 作用目标 | 方案 |
|----------|---------|------|
| 签名校验绕过 | AppConstants.compareNokeySignaturesSHA1 | Hook 返回 true，跳过完整性验证 |
| 安全检测绕过 | O00000Oo.O00000o0(int) | 截获检测参数，强制置为通过状态 |
| 皮肤解锁 | SkinItem 类（6 个方法） | 全面接管解锁、试用、过期、价格展示等状态 |

---

## Hook 原理详解

### 1. 签名校验绕过

目标应用的签名校验通常在 Application 或 Activity.onCreate 阶段执行。  
采用 Xposed 方法级 Hook 直接于 Java 层篡改校验结果：

- boolean 返回值类型：afterHookedMethod 中 param.setResult(true)
- 异常捕获类型：拦截校验方法，跳过异常抛出逻辑
- native / PackageManager 方案（备选）：Hook PackageManager.getPackageInfo 返回伪造签名

### 2. 安全检测绕过

目标会检测 Root / Xposed / 调试模式等运行环境。根据检测手段分类处理：

| 检测类型 | 检测方式 | 绕过策略 |
|----------|----------|----------|
| 文件检测 | File.exists，File.length | Hook 后对敏感路径返回不存在 |
| 进程检测 | Runtime.exec 读取 /proc | 过滤 magisk、su 等进程名 |
| 反射检测 | Class.forName 探测 XposedBridge | 抛出 ClassNotFoundException |
| Build 标签 | Build.TAGS.contains(test-keys) | Hook Build.TAGS getter 返回 release-keys |

### 3. 皮肤解锁

付费皮肤限制通过在线状态验证 + 本地状态缓存实现。  
全量 Hook SkinItem 的以下方法达成完全解锁：

```
canUse           -> true          # 可直接使用
isSupportTry     -> true          # 支持试用
isTrialExpired   -> false         # 试用未过期
isNeedShowPrice  -> false         # 不显示价格
getKeepStatus    -> 3             # 永久保留状态
getUsingFlag     -> 1             # 正在使用标记
```

---

## 编译

```bash
git clone https://github.com/Srysfu/NokeyBypass.git
cd NokeyBypass
./gradlew assembleRelease
```

编译产物路径：app/build/outputs/apk/release/

---

## 安装

1. 确保设备已安装 Xposed / LSPosed 框架
2. 在模块管理中找到 NokeyBypass 并勾选启用
3. 重启 SystemUI 或重启设备
4. 打开目标应用即可生效

---

## 技术栈

- 语言：Java 8
- 框架：Xposed Framework (API 82)
- 构建工具：Gradle 8.x + Kotlin DSL
- 最低支持：Android 7.0 (API 24)

---

> 免责声明：本项目仅供学习研究使用，请勿用于商业或非法用途。