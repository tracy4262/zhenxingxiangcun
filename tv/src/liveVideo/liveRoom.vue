<template>
<div>
    <!-- <top :address="false" /> -->
  <div class="chatRoom">
      <div class="w">
          <Row>
               <i-col span="16">
                    <div class="title">
                        <img :src="data.liveImage" alt="" height="80px" width="80px">
                        <div class="describe">
                        <h4 class="text">{{data.userName}}直播间</h4>
                        <i-button type="text" class="name">{{data.userName}}直播间</i-button> 
                        <img src="../../static/img/p.png" alt="" height="18px" width="18px" class="img">
                        <img src="../../static/img/v.png" alt="" height="18px" width="18px">
                    </div>                    
                    </div>
                    <div class="tv-on-line" id="video">
                        <div id="agora_local"></div>
                    </div>
                </i-col>
                 <i-col span="8">
                     <div class="chatRoom-right">
                         <div class="message-title">聊天室</div>         
                        <div ref="box" class="message-content">
                            <messageLineLeft :data="messageData"></messageLineLeft>
                        </div>
                        <div class="message-edit">                            
                            <Row>
                                <i-col span="19">
                                    <!-- <div class="textarea"> -->
                                        <textarea  placeholder="快点跟TA互动吧" class="ivu-input" v-model="enterText" v-on:keyup.enter="enter"/>
                                    <!-- </div> -->
                                </i-col>
                                <i-col span="5">
                                    <span><i-button type="primary" @click="enter">发送</i-button></span>
                                </i-col>
                            </Row>                         
                            
                            
                        </div>
                     </div>
                    
                </i-col>
          </Row>
      </div>
     
  </div>
  </div>
</template>
<script>
import messageLineLeft from '../components/messageLineLeft.vue'
// import top from "../top";
import api from "../api";
import $ from 'jquery'


    // var webSocketUrl = "wss://192.168.7.226:20000/websocket";
    // var webSocketUrl = "ws://192.168.7.226:20000/";
    var imType = {"online" : 1, "offline" : 2, "sendMsg" : 3, "recvMsg" : 4};
    var websocket;
    var roomId;
    var userId;
    var userName;
    var isAnchor = false;//false 游客 true 主播
    var onlineMap = {};
    var client, localStream, camera, microphone,clientUid;

    export default{
        components:{
            messageLineLeft,
            // top
        },
        data () {
            return {
                loginuserinfo:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))), 
                // loginuserinfo:{loginAccount:'luona'},        
                textarea:'',
                formRight: {
                    input1: '',
                    input2: '',
                    input3: ''
                },
                audios:[],
                cameras:[],
                formItem:{
                    camera:'',
                    audio:''
                },
                enterText:'',
                messageData:[],
                roomId:'',
                data:{
                    liveDescribe:'',
                    liveImage:'',
                    liveName:'',
                    userName:''
                },
                appidKey:'41d1c54e03994b68ae2d603391ea6334',
            }
        },
        created () {
            this.roomId = this.$route.query.id.toString()
            roomId = this.$route.query.id.toString()
            api.get('/relationship/live/getLive/' + this.roomId).then(res=>{
                if(res.code == 200){
                    this.data = res.data
                }
            })
            userId = this.loginuserinfo.loginAccount.toString()
            userName = this.loginuserinfo.loginAccount.toString()               
            //初始化1、检测浏览器是否支持，2、发送加入直播的请求
            this.getInit()
        },
        methods:{
            //初始化校验
            getInit(){
                var that = this
                 //进行websocket校验
                if (!window.WebSocket) {
                    alert("浏览器不支持websocket");
                    return;
                }
                //支持websocket初始化websocket监听
                websocket = new WebSocket(this.$url.webSocketUrl);
                websocket.onopen = function(event) {
                    that.onOpen(event)
                };
                websocket.onclose = function(event) {
                    that.onClose(event)
                };
                websocket.onmessage = function(event) {
                    that.onMessage(event)
                };
                websocket.onerror = function(event) {
                    that.onError(event)
                };               
            },
            //下线方法
            offline(){
                this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.offline}))
                // this.unpublish() 这个只要主播猜执行
                this.leave()
            },
             /*
            Socket.readyState 只读属性 readyState 表示连接状态，可以是以下值：
            0 - 表示连接尚未建立。
            1 - 表示连接已建立，可以进行通信。
            2 - 表示连接正在进行关闭。
            3 - 表示连接已经关闭或者连接不能打开。
            */
            dowebSocketSend(message) {
                if (websocket.readyState == WebSocket.OPEN) {
                    websocket.send(message);
                    if(JSON.parse(message).type == 1){//上线
                        this.join()                      
                    }  
                    if(JSON.parse(message).type == 3){//自己发送消息
                        this.enterText = ''
                    }
                } else {
                    alert("未与服务器成功建连，Socket.readyState：" + websocket.readyState);
                }
            },
            //连接，关闭，发送消息，以及错误信息的事件
            onOpen(event) {
                this.dowebSocketSend(JSON.stringify({"roomId":this.roomId, "userId": userId, "userName": userName, "type": imType.online}))
            },
            onClose(event) {
            },
            onMessage(event) {
                // $('div#video').html('<img src="../../live/static/img/huiyuan-logo.png" alt="" width="150px">')
                //判断收到的服务端数据为服务端的imResponse还是imRequest
                //response包含isSucc
                if (event.data.indexOf("succ") != -1) {
                    var imResponse = eval("("+ event.data +")");
                    if (imResponse) {
                        //行为类型（如：上线、下线）
                        var type = imResponse.type;
                        if (type == imType.online) {
                            //上线请求的响应
                            if (imResponse.succ) {
                                //上线成功
                                //初始化在线人员列表
                                var onlines = imResponse.onlines;
                                //在线人员列表
                                for (var onlineUserId in onlines) {
                                    //自身上线已经在点击上线按钮时触发了（实际业务应为点击进入直播室即视为自动上线）故排除自身上线再次赋值
                                    if (onlineUserId == userId) {
                                        continue;
                                    }
                                    // this.info(onlines[onlineUserId],true)
                                    //onlineMap
                                }
                            } else {
                                // alert("上线失败!")
                            }
                        } else if (type == imType.sendMsg) {
                            //发送消息的响应

                        } else if (type == imType.offline) {
                            //下线消息响应
                            //默认直播室业务 下线为离开直播室，在离开直播室时发起下线请求
                        }
                    }
                } else {
                    //服务发送的imRequest数据
                    var imRequest = eval("("+ event.data +")");
                    if (imRequest) {
                        //行为类型（如：上线、下线）
                        var type = imRequest.type;
                        var msgData = []
                        if (type == imType.online) {
                           //上线通知 （只会收到除自己之外其他人的上线通知）
                             var list = {name:imRequest.userName,text:imRequest.userId}   
                            if(imRequest.userName == userName) {
                                //自己的上线提醒
                                this.success(imRequest.userName)
                            }else{
                                //别人的上线提醒
                                this.info(imRequest.userName,true)
                            } 
                        } else if (type == imType.recvMsg) {
                            //消息通知
                             //消息通知
                            if(imRequest.userName == userName){ //自己的消息
                                var list = {name:'我',text:imRequest.message,type:1}
                            }else{//别人的消息
                                var list = {name:imRequest.userName,text:imRequest.message,type:2}
                            }                            
                            this.messageData.push(list)
                        } else if (type == imType.offline) {
                            //下线通知（只会收到除自己之外其他人的上线通知）
                            this.info(imRequest.userId,false)
                        }
                    }
                }
            },
             //通信发生错误时触发
            onError(event) {
            },
             //自己上线成功的提示
            success (name) {
                this.$Notice.success({
                    title: name+'上线了',
                    desc:''
                });
            },
            //上下线的消息通知
            info (name,type) {
                this.$Notice.info({
                    title: type?name+'上线了':name+'下线了',
                    desc:''
                });
            },
             join(){                                 
                var that = this
                var appidKey = this.appidKey;//获取appid (暂时固定值)
                var tvChannel = this.roomId;//通道（即房间号）
                client = AgoraRTC.createClient({mode: 'interop'});//使用VP8编码、H264解码，浏览器与 Native 互通（Safari 除外）

                client.init(appidKey, function() {
                            //加入频道
                            /*
                            join(channelKey, channel, uid, onSuccess, onFailure)
                            channelKey： 密钥
                            channel：通话频道（即房间号）
                            uid：指定用户的ID。32位无符号整数。建议设置范围: 1 到 (2^32-1)，并保证唯一性。
                            如果不指定（即设为 null），服务器会自动分配一个，并在 onSuccess 回调方法中返回
                            */
                            client.join(null, tvChannel, null, function(uid) {
                                clientUid = uid;
                                
                            }, function(err) {
                            });
                        }, function(err) {
                        });
                        //监听流事件并订阅远端流
                        //回调事件
                        client.on('error', function(err) {
                            if (err.reason === 'DYNAMIC_KEY_TIMEOUT') {
                                client.renewChannelKey(channelKey, function(){
                                }, function(err){
                                });
                            }
                        });
                        //远程音视频流已添加回调事件
                        //该回调通知应用程序远程音视频流已添加
                        client.on('stream-added', function (evt) {
                            var stream = evt.stream;
                            //订阅远程音视频流 (subscribe)
                            client.subscribe(stream, function (err) {
                            });
                        });
                        //回调通知应用程序已接收远程音视频流
                        client.on('stream-subscribed', function (evt) {
                            var stream = evt.stream;
                                $('div#video').html('')
                                $('div#video').html('<div id="agora_remote'+stream.getId()+'" style="float:left; width:100%;height:100%;display:inline-block;"></div>');
                            
                            stream.play('agora_remote' + stream.getId());
                        });
                        //回调通知应用程序已删除远程音视频流，即对方调用了 unpublish stream
                        client.on('stream-removed', function (evt) {
                            var stream = evt.stream;
                            stream.stop();
                            $('#agora_remote' + stream.getId()).remove();                           
                            $('div#video').html('<img src="../../live/static/img/huiyuan-logo.png" alt="" width="150px">')
                        });
                        //回调通知应用程序对方用户已离开会议室，即对方调用了 client.leave()
                        client.on('peer-leave', function (evt) {
                            var stream = evt.stream;
                            if (stream) {
                                stream.stop();
                                $('#agora_remote' + stream.getId()).remove();
                            }
                        });
            },
            //离开频道
            leave() {
                client.leave(function () {
                }, function (err) {
                });
            },
            //上传本地音视频流
            publish() {
                client.publish(localStream, function (err) {
                });
            },
            enter(){
                // var list ={name:'我',my:1,text:this.enterText}
                // this.messageData.push(list)
                // this.enterText=''
                this.$refs.box.scrollTop = this.$refs.box.scrollHeight
                setTimeout(() => {
                    this.$refs.box.scrollTop = this.$refs.box.scrollHeight
                }, 50);
                var str = $.trim(this.enterText)
                if(str != ''){
                    this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.sendMsg, "message": this.enterText}))  
                }
            }
        },
        mounted(){        
        }
    }
</script>
<style>
.w {
    min-width: 1200px;
    max-width: 1960px;
    margin:0 auto;
}
.fr{
    float: right;
}
.fl{
    float: left;
}
.chatRoom-right{
    padding-left:20px;
}
.chatRoom-right .message-title{
    height: 50px;
    /* width:460px; */
    padding:10px;
    background: #fff;
    font-size: 16px;
}
.chatRoom-right .message-content{
    height: 695px;
    overflow-y: auto;
    /* width:460px; */
    padding:15px;
    background: #F8F8F8;
}
.chatRoom-right .message-edit{
    /* width:460px; */
    background: #fff;
    font-size: 0px;
    padding: 25px 10px;
    height: 125px;
}
.chatRoom-right .message-edit textarea:hover{
    border-color: #33d19f;
}
.chatRoom-right .message-edit textarea{
    border-radius: 4px 0 0 4px;
    font-size: 16px;
    resize: none;
    background: #F8F8F8;
    padding: 15px;
    color: #707070;
    height: 75px;
}
.chatRoom-right .message-edit .textarea{
    width: 360px;
    display: inline-block;
}
.chatRoom-right .message-edit button{
    height: 75px;
    width: 100%;
    border-radius: 0px 4px 4px 0px;
    font-size: 18px;
    vertical-align: top;
}
</style>

<style>
.chatRoom{
    background: #F3F3F3;
    /* margin: 20px; */
        padding: 20px;
}
.chatRoom .title{
    padding:10px;
    background: #fff;
    height: 100px;
}
.chatRoom .title img{
    border-radius:50%;
}
.chatRoom .title .describe{
    display: inline-block;
    /* height: 100px; */
    vertical-align: top;
    padding-left: 15px;
}
.chatRoom .title .describe .name{
    color:#333;
    padding:0px;
    padding-right:7px;
    font-size: 16px;
}
.chatRoom .title .describe img{
    margin:0px 3px;
    vertical-align: middle;
}
.chatRoom .title .describe h4{
    font-size: 22px;
    padding: 5px 0px 15px;
    color: #222;    
}
.chatRoom .tv-on-line{
    height: 770px;
    width:100%;
    /* padding: 10px; */
    background: rgb(0, 0, 0);
    text-align: center;
    line-height: 770px;
}
</style>

