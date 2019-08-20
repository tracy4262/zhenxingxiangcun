<template>
</template>
<script>
// layui-layim-status
    var websocket;
    import $ from 'jquery'
    import axios from 'axios';
    export default {
        props: {
        },
        data() {
            return {
                user: '',
                // url: `//192.168.13.54:30000/relationship/im/getInitList/`,
                // socketUrl: '//www.ns51.cn:30000',
                // url: '//192.168.100.205:8081',
                socketUrl: this.$url.socketUrl,
                layim: null,
                // websocket: null,
            };
        },
        created () {
            var user = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')));
            this.user = user.loginAccount;
            // this.user = 'kang'
        },
        methods: {
            showUnreadMsgCount() {
                // 显示未读取的用户的消息数
                // 发送Ajax请求，查询该用户未读取的消息数
                axios.get(`${this.$url.api}/im/groupfriend/getUnreadMsgCount/${this.user}`).then((res,status) => {
                    layim.msgbox(res);
                });
            },
            layIm() {
                var that = this;
                layui.config({
                    base: '../../../static/js/contextMenu/'
                }).extend({
                    //rightClick: 'rightClick',
                    customRightClick: 'customRightClick',
                })
                layui.use(['layim', 'layer', 'customRightClick', 'jquery'], (layim, layer, customRightClick, $) => {
                    // 右键事件
                    var customRightClick = layui.customRightClick;
                    customRightClick.config({
                        layim: layim,
                    });
                    // var host=`ws:${this.url}/websocket/${this.user}`
                    // 右键事件
                    /*layim.on('ready', options => {
                        layui.rightClick.init(options)
                    })*/

                    // var webSocketUrl = `ws:${this.url}${this.user}`;
                    var webSocketUrl = `ws:${this.socketUrl}`;
                    //进行websocket校验
                    if (!window.WebSocket) {
                        layer.alert("您的浏览器不支持webscoket协议,建议使用新版谷歌、火狐等浏览器，请勿使用IE10以下浏览器，360浏览器请使用极速模式，不要使用兼容模式！");
                        return;
                    }
                    //支持websocket初始化websocket监听
                    websocket = new WebSocket(webSocketUrl);
                    websocket.onopen = function(event) {
                        // websocket.send(JSON.stringify({"emit": "online", "data": {"account": that.user}}));
                        that.dowebSocketSend(JSON.stringify({"emit": "online", "data": {"account": that.user}}))
                        console.log('ws连接成功！');
                    };
                    websocket.onclose = function(event) {
                        console.log('ws断开连接！');
                    };
                    websocket.onerror = function(event) {
                        console.log("发生websocket未知错误");
                        console.log(event);
                    };
                    //监听窗口关闭事件
                    window.onbeforeunload = function () {
                        websocket.close();
                    }
                    //websocket 消息监听
                    websocket.onmessage = (event) => {
                        var res = eval("("+ event.data +")");
                        console.log("收到服务器消息==>" + res);

                        var resEmit = res.emit;
                        var resData = res.data;
                        //  var resData = eval("("+ res.data +")");
                        if (resEmit == "onlineRes") {
                            console.log("initdata:" + JSON.stringify(resData));
                            //上线响应（即初始化主面板数据，带有好友在线状态）
                            layim.config({
                                title: '我的好友',//自定义主面板最小化时的标题
                                isgroup: false,//是否开启群组
                                copyright: true,
                                //
                                //init 获取主面板列表信息 可获得：我的信息、好友列表(好友分组、分组下具体好友)、群组列表
                                init:{
                                    mine: resData.mine
                                    ,friend: resData.friend
                                    ,group: resData.group
                                    // url: `${this.url}/relationship/groupfriend/getInitList/${this.user}`,
                                    //url: `${this.url}/im/getInitList/${this.user} `,
                                    //type: 'get',
                                    // dataType: 'jsonp',
                                    // crossDomain: true,
                                    //data: {}
                                },
                                uploadImage: {
                                    url: `${this.$url.api}/im/im/upload/img`
                                    //+ layim.cache().mine.id
                                },
                                uploadFile: {
                                    url: `${this.$url.api}/im/im/upload/file`
                                },
                            //members 获取群员列表
                                /*members: {
                                    url: `${this.url}/groupfriend/getMembers/${this.user}`,
                                    type: 'get',
                                    data: {}
                                },*/
                                //tool 扩展工具栏，可同时配置多个
                                /*tool: [{
                                    alias: 'code',//工具别名
                                    title: '代码',//工具名称
                                    icon: '&#xe64e;'//工具图标，参考图标文档
                                }],*/
                            //    msgbox: '../../static/js/layui/css/modules/layim/html/msgbox.html',
                                // find: '//192.168.7.61:8080/dist/static/js/layui/css/modules/layim/html/find.html',
                            //    find: '../../static/js/layui/css/modules/layim/html/find.html',
                                //chatLog: '../../static/js/layui/css/modules/layim/html/chatlog.html'
                                chatLog: '../../static/js/layui/css/modules/layim/html/chatlog.html'
                            })

                            $('.layim-list-friend >li > h5 > span').each((index, item) => {
                              $(item).attr('title', item.innerText)
                            })
                        } else if (resEmit == "online") {
                            console.log("online-data:" + JSON.stringify(resData));
                            //上线通知（即通知该用户其好友上线通知）
                            var friendId = resData.userId;
                            layim.setFriendStatus(friendId, 'online');
                        } else if (resEmit == "offline") {
                            console.log("offline-data:" + JSON.stringify(resData));
                            //下线通知（即通知该用户其好友下线通知）
                            var friendId = resData.userId;
                            layim.setFriendStatus(friendId, 'offline');
                        } else if (resEmit == "friendMsg" || resEmit == "groupMsg") {
                            console.log("friend||group-data:" + JSON.stringify(resData));
                            layim.getMessage(resData);
                        } else if (resEmit == "statusChange") {
                            console.log("statusChange-data:" + JSON.stringify(resData));
                            var friendId = resData.userId;
                            var status = resData.status;//online或者hide
                            if (status == "online") {
                                layim.setFriendStatus(friendId, 'online');
                            } else {
                                layim.setFriendStatus(friendId, 'offline');
                            }
                        }
                    }

                    // 监听发送的消息
                    layim.on('sendMessage', data => {
                        console.log("layim发送消息" + JSON.stringify(data));
                        var emit = data.to.type;
                        if ("friend" == emit) {
                            //发送好友消息
                            that.dowebSocketSend(JSON.stringify({emit: "friendMsg",data: data}));
                        } else if ("group" == emit) {
                            //发送群消息
                            that.dowebSocketSend(JSON.stringify({emit: "groupMsg",data: data}));
                        }
                    })
                    //监听在线状态切换
                    layim.on('online', function(status){
                        console.log("监听在线状态切换" + status + "userId:" + layim.cache().mine.id); //获得online或者hide
                        that.dowebSocketSend(JSON.stringify({emit: "statusChange",data: {"userId" : layim.cache().mine.id, "status" : status}}));
                    });
                })

            },
            dowebSocketSend(message) {
                console.log("websocket send msg:" + message);
                if (websocket.readyState == WebSocket.OPEN) {
                    websocket.send(message);
                } else {
                    console.log("未与服务器成功建连，Socket.readyState：" + Socket.readyState);
                }
            }
        },
        mounted () {
            this.layIm()
            let that = this
            setTimeout(() => {
                let html = $('.layui-layim-user').html()
                $('.layui-layim-user').attr('title', `${html}`)
                $('.layui-layim-remark').blur(function(){
                    let signature = $('.layui-layim-remark').val()
                     if(signature && $('.layui-layim-remark')!= " "){
                        that.$api.get('/member/login/editSignature?signature='+signature+ '&account=' + that.$user.loginAccount)
                        .then(res => {
                            if(res.code === 200){
                                that.$Message.success('修改成功！')
                            }else{
                                that.$Message.error('修改失败！')
                            }
                        })
                    }
                });
            }, 3000)
        }
    }
</script>
