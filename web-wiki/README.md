农事无忧-物种百科
=============
> [项目地址](http://192.168.7.90/nswy/web-wiki.git)

## 项目运行说明
- 克隆项目
```
git clone http://192.168.7.90/nswy/web-wiki.git
```
- 安装依赖包
```
npm i or cnpm i  or yarn add
```
- 启动项目
```
npm run dev
```
- 项目打包
```
npm run build
```

## 框架
- [UI框架 iview](https://www.iviewui.com)
- [JS框架 vue](https://cn.vuejs.org)

## 项目目录说明
```
build       --- 脚手架相关配置目录
config      --- 开发和构建两种环境下的配置
src         --- 项目核心代码文件夹
|api        --- 接口相关配置
|assets     --- 静态资源将会被打包
 |img       --- 图片
 |font      --- 字体相关
|components --- 公共组件
|view       --- 相关页面目录
|router     --- 路由配置相关
|store      --- 状态管理部分
|utils      --- 公共方法类
static      --- 静态资源将不会被打包
|img        --- 不需要打包的图片
|font       --- 字体相关
|data       --- 测试数据文件夹
 |json      --- 测试数据
 |img       --- 测试图片
test        --- 测试相关配置
```
