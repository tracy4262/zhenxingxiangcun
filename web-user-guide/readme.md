# 农事无忧项目首页及会员中心前端

农事无忧项目为湖北省农科院联合光谷信息，共同开发的一款针对农业大数据的智慧农业项目


## 部署注意事项

1.运行git clone http://192.168.7.90/nswy/web-member.git 将代码clone到本地，运行之前需要手动复制src/api/config.js 文件到本机

2.本机安装node运行环境，并将node加入到系统PATH变量

3.运行以下命令安装npm淘宝镜像源

    npm install -g cnpm --registry=https://registry.npm.taobao.org


4.运行以下命令，安装项目运行所需依赖

    cnpm install

5.运行以下命令开启调试

    cnpm run dev

6.平时每次pull完代码，记得运行

    cnpm update

进行包依赖的更新


## 代码结构
* src               --- 目录包含了应用的核心代码;
* -- api            --- 目录包含了所有的Artisan命令。
* -- componments    --- 
* -- config         --- 
* -- css            --- 
* -- font           ---
* -- img            ---
* -- js             ---
* -- libs           ---
* -- page           ---
* -- scss           ---
* -- template       ---
* -- app.vue        ---
* -- college.js     ---
* -- demo.vue       ---
* static            ---
* -- datas          --- 系统假数据
* theme             --- 



## 第三方组件

1. iview             -- 一套基于 Vue.js 的高质量 UI 组件库 (https://www.iviewui.com/)


## License

此系统由武汉光谷信息技术股份有限公司开发，全部解释权归光谷信息所有