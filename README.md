# NokeyBypass

Nokey（乘趣）Xposed 破解模块

## 功能

- 🔓 绕过启动签名校验
- 🛡️ 绕过安全环境检测（Root/Hook/调试/模拟器等6项）
- 🎨 解锁所有付费皮肤

## 使用方法

1. 在 Xposed 管理器中激活模块
2. 重启系统框架
3. 打开 Nokey 即可

## 自行构建

```bash
git clone https://github.com/maodaiyong/NokeyBypass.git
cd NokeyBypass
./gradlew assembleDebug
```

APK 生成在 app/build/outputs/apk/debug/

## 原理

| 模块 | 目标类 | 方法 | 修改方式 |
|------|--------|------|----------|
| 签名校验绕过 | AppConstants | compareNokeySignaturesSHA1 | 返回 true |
| 安全检测绕过 | BaseInspector | O00000o0 | 参数强制改为 1 |
| 皮肤解锁 | SkinItem | canUse | 返回 true |
| 皮肤解锁 | SkinItem | isSupportTry | 返回 true |
| 皮肤解锁 | SkinItem | isTrialExpired | 返回 false |
| 皮肤解锁 | SkinItem | isNeedShowPrice | 返回 false |
| 皮肤解锁 | SkinItem | getKeepStatus | 返回 3 |
| 皮肤解锁 | SkinItem | getUsingFlag | 返回 1 |

## 免责声明

本模块仅供学习和研究使用，请勿用于商业用途或侵犯他人权益。
