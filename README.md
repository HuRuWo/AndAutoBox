## FastTest

## 是什么

一个轻量级【简洁的免ROOT自动化Andorid端脱机测试框架】 
基于无障碍服务 方便测试二次开发导入即可

### 写在前面

#### 为什么要重新造轮子?

并不是 是一种补充。原本就是个人日常使用的工具。我不太喜欢那些现成的工具和框架。
明明我只想写一个轻轻的点击事件却要下载一堆文件和依赖。
又要连接电脑又要下载PC客户端，还要学会写另外一种脚本语言(js/lua/python)来契合
我是使用纯java实现一个版本 只关注点击

##### 和其他框架比有什么优势吗?

更轻量级 只专注与自动化点击测试 不考虑更多多谢

### 特点

1.相对于Appium不需要链接电脑

2.相对于Auto.js更加轻量级

3.核心类只有5个，但是功能齐全 方便定制

4.纯java实现 依赖于无障碍服务


### 支持的Auto操作

1.单击

```java
public static boolean actionClickNode(String viewId,String text,int index,ClickAction clickAction,long sleep)
```

2.双击

```java
public static boolean performAutoClick(AccessibilityNodeInfo nodeInfo)
```

3.长按

```java
public static boolean performAutoDoubleClick(AccessibilityNodeInfo nodeInfo)
```

4.滑动

不建议使用这个滑动 很怪 还是基于GestureDescription的path滑动好用

```java
public static boolean performAutoSlide(AccessibilityNodeInfo nodeInfo, boolean direction) 
```

5.输入文本

```java
public static boolean inputAutoText(AccessibilityNodeInfo nodeInfo, String text) 
```

### 一些全局操作 

1.回到桌面

```java
public static void goAutoHome(long sleep) 
```

2.按下返回按钮

```java
public static void performAutoBack(long sleep) 
```


### 基于 GestureDescription支持的手势( 系统必须7.0以上)

GestureDescription支持path和持续时间

在这个基础上可以做出很多动作

比如path只是一个点 就是单击 来两次就是双击 持续时间比较长是长按

比如path是一段段长距离的线 加上时间就是滑动

比如path是三条滑动 就可以实现类似小米那种三指截屏操作

#### 1.点击任意位置point


```
public static boolean useGestureClickPoint(Point point)
```


#### 2.点击控件的中心

基于点击任意位置操作 可以点击任意控件，我们先找到控件的中心就行

```java
Rect rect = new Rect();
info.getBoundsInScreen(rect);
useGestureClickPoint(new Point(rect.centerX(), rect.centerY()));
```

#### 3.进阶演示基于path的操作

##### 1.miui三指截屏



##### 2.画布上绘制五边形


### 针对无id控件的优化

对于 webview 和 flutter 等框架做起来的view界面，会由于缺少对应的ViewId导致难以定位。

为此我们只能通过几种方式来确定:

#### 1.文本文字确定

#### 2.控件层次确定






