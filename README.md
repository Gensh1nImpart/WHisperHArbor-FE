<div align="center">
<img src="app/src/main/res/drawable/logo.png" width="50%" />

# WHisperHArbor
_✨ 一个让大家自由倾诉的APP
</div>

### 简介
#### 程序介绍
- 是移动应用开发课程的期末大作业
- 前端是基于Android的简单的页面
- 前端本项目基于Android

#### 程序理想
- 用户注册登录，每个用户都自己的uid
- 发布帖子的时候可以选择是否公开
    - 公开的话直接明文存储到`mysql`数据库中
    - 非公开则使用对称加密进行密文存储，保证了用户的隐私。
- 首页可以浏览大家的帖子，可以进行收藏、点赞等操作
    - 因为是树洞，本人的理念是不可评论！
- 用户可以管理自己发的帖子包括修改、删除
- 用户可以查看自己的收藏等。

#### 程序进度
1. 实现了程序加载首先验证`MMKV`数据库中是否有`Token`字段并且验证是否过期
   1. 如果过期，则重新登陆
   2. 如果没过期，则直接进入首页
   3. 踩了不少坑（
2. 注册登录，但是没限制注册账号密码的长度和验证码功能，加到TODO里面

### 部分实现介绍
#### `MMKV`
这是[腾讯开源](https://github.com/Tencent/MMKV)的基于 mmap 内存映射的 key-value 组件。
需要导入依赖
```xml
implementation 'com.tencent:mmkv:1.2.10'
```
#### `Android4+`联网操作
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```