### web-im
![](https://img.shields.io/badge/language-java-orange.svg) ![](https://img.shields.io/badge/qq-923260818-brightgreen.svg) ![](https://img.shields.io/badge/build-%20passing-brightgreen.svg) ![](https://img.shields.io/badge/created-Jan-brightgreen.svg)

#### 概述
农事无忧web-im

#### 功能说明

1. 登录初始化好友分组及分组对应好友，初始化时在线好友默认排序在前

2. 实时更新好友在线、离线状态

3. 好友在线、离线消息

4. 消息类型支持图片、文字、文件

5. 查看聊天记录

#### 注意事项

1. 用户信息通过用户account远程调用relation-ship-service获取
2. 消息推送均采用异步线程池推送
3. 目前仅支持单机，暂未修改为集群模式
4. im主面板示例数据详见resources/im-init/layim示例数据
5. 需要在数据库建立聊天记录表，建表sql详见resources/im-init/init.sql
6. 运行脚本见shell

#### 备注

如果你有好的意见或建议，欢迎给我们提issue或pull request。