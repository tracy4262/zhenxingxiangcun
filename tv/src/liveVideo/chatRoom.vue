<template>
<div>
    <!-- <top :address="false" /> -->
     <div class="ivu-chatRoom">
          <div class="w">
            <Row>
            <i-col span="16">
                <div class="chatRoom-left">
                    <div class="message-title">聊天室</div>         
                        <div ref="box" class="message-content">
                            <messageLineLeft :data="messageData" :isShow="false"></messageLineLeft>
                        </div>
                        <div class="message-edit">
                            <Row>
                                <i-col span="21">
                                        <!-- <div class="textarea"> -->
                                    <textarea  placeholder="快点跟TA互动吧" class="ivu-input" v-model="enterText" v-on:keyup.enter="enter"/>
                                        <!-- </div> -->
                                </i-col>
                                <i-col span="3">
                                    <span><i-button type="primary" @click="enter">发送</i-button></span>
                                </i-col>
                            </Row>            
                        </div>
                </div>
            </i-col>
            <i-col span="8">
                <div class="chatRoom-right">
                    <div class="video">
                        <div id="agora_local">
                        </div>
                        <i-button v-if="!isPlay"  size="large" icon="play" @click="play">播放</i-button>
                        <i-button v-if="isPlay"  size="large" icon="pause" @click="pause">暂停</i-button>
                        <i-button size="large" icon="android-settings" @click="setting">设置</i-button>
                    </div>
                    <div class="describe">
                        <div class="describe-top">
                            <img :src="data.liveImage" alt="" height="110" width="110">
                            <h4 class="text">{{data.userName}}直播间</h4>
                            <i-button type="text" class="name">{{data.userName}}直播间</i-button>
                            
                            <img src="../../static/img/p.png" alt="" height="18px" width="18px" class="img">
                            <img src="../../static/img/v.png" alt="" height="18px" width="18px">
                        </div>
                        <div class="lines"></div>
                        <p>{{data.liveDescribe}}</p>
                    </div>              

                </div>
            </i-col>
        </Row>
        
        <Modal
            v-model="isSeting"
            title="设置"
            @on-ok="ok">
            <div>
                <i-form :model="formItem" :label-width="150">
                    <Form-item label="摄像头">
                        <i-select v-model="formItem.camera" style="width:250px" ref="camera">
                            <i-option v-for="(item,index) in cameras" :value="item.value">
                                <span v-if="item.label">{{ item.label }}</span>
                                <span v-else>摄像头{{index+1}}</span>
                            </i-option>
                        </i-select>
                    </Form-item>
                    <Form-item label="音频">
                        <i-select v-model="formItem.audio" style="width:250px"  ref="audio">
                            <i-option v-for="(item,index) in audios" :value="item.value">
                                <span v-if="item.label">{{ item.label }}</span>
                                <span v-else>音频{{index+1}}</span>
                            </i-option>
                        </i-select>                   
                    </Form-item>
                </i-form>      
            </div>
        </Modal>
    </div>
  </div>
</div>
 
</template>
<script>
import '../../static/js/AgoraRTCSDK-2.0.0.js';
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
    var isAnchor = true;//false 游客 true 主播
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
                // loginuserinfo:{loginAccount:'kang'},
                data:{
                    liveDescribe:'',
                    liveImage:'',
                    liveName:'',
                    userName:''
                },
                textarea:'',
                isSeting:false,
                isPlay:true,
                formItem: {
                    audio: '',
                    camera: ''
                },
                enterText:'',
                messageData:[],
                cameras:[],
                audios:[],
                appidKey:'41d1c54e03994b68ae2d603391ea6334',
                roomId:'',
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
            //获取电脑设备的音频视频设置
            this.getDevices()
            //初始化1、检测浏览器是否支持，2、发送加入直播的请求
            this.getInit()
        },
        methods:{
            //获取电脑设备的音频视频设置
            getDevices(set) {  
                var that = this
                AgoraRTC.getDevices(function (devices) {
                    for (var i = 0; i < devices.length; i++) {
                        var device = devices[i];
                        var option
                        var k = i+1
                        if (device.kind === 'audioinput') {
                            //音频
                            option = device.label;
                            if(option){
                                that.audios.push({label:'音频'+k,value:device.deviceId})
                            }else{
                                that.audios.push({label:option,value:device.deviceId})
                            }
                        } else if (device.kind === 'videoinput') {
                            //视频
                            option = device.label;
                            if(option){
                                that.cameras.push({label:'音频'+k,value:device.deviceId}) 
                            }else{
                               that.cameras.push({label:option,value:device.deviceId}) 
                            }
                            
                        } else {
                        }                       
                    }
                        //设置默认值
                        that.formItem.camera = that.cameras[0].value
                        that.formItem.audio = that.audios[0].value
                });
            },
            //初始化校验
            getInit(){
                var that = this
                 //进行websocket校验
                if (!window.WebSocket) {
                    // alert("浏览器不支持websocket");
                    that.$Message.error('浏览器不支持websocket')
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
                this.unpublish()
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
                        this.isPlay = true                   
                    }       
                    
                    if(JSON.parse(message).type == 3){//自己发送消息
                        this.enterText = ''
                    }
                    if(JSON.parse(message).type == 2){//下线
                        this.isPlay = false                   
                    }  
                } else {
                    alert("未与服务器成功建连，Socket.readyState：" + websocket.readyState);
                }
            },
            //连接，关闭，发送消息，以及错误信息的事件
            onOpen(event) {
                this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.online}))
                // this.join()
            },
            onClose(event) {
            },
            onMessage(event) {
                // $('#agora_local').html('<img src="../../live/static/img/huiyuan-logo.png" alt="" width="120px">')
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
                                //上线失败
                                alert("上线失败!")
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
            //别人上下线的消息通知
            info (name,type) {
                this.$Notice.info({
                    title: type?name+'上线了':name+'下线了',
                    desc:''
                });
            },
            //自己上线成功的提示
            success (name) {
                this.$Notice.success({
                    title: name+'上线了',
                    desc:''
                });
            },
             //通信发生错误时触发
            onError(event) {
            },
            //点击设置
            setting(){
                this.isSeting = true
            },
            //设置完成点击确认
            ok(){
                this.offline() //下线
                this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.online}))
            },
            join(){
                var that = this
                var appidKey = this.appidKey;//获取appid (暂时固定值)
                var tvChannel = this.roomId.toString();//通道（即房间号）,string类型
                client = AgoraRTC.createClient({mode: 'interop'});//使用VP8编码、H264解码，浏览器与 Native 互通（Safari 除外）
                client.init(appidKey, function() {                    
                            $('#agora_local').html('')
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

                                //创建音视频流对象
                                /*
                                streamID:音视频流 ID，通常设置为 uid，可通过 client.join 方法获取
                                audio:(flag) True/False，指定该音视频流是否包含音频资源
                                cameraId:(可选) 通过 getDevices 方法获取的摄像头设备 ID
                                microphoneId:(可选) 通过 getDevices 方法获取的麦克风设备 ID
                                video:(flag) True/False，指定该音视频流是否包含视频资源 (isAnchor 主播模式 true 游客模式false)
                                screen:(flag) True/False，指定该音视频流是否包含屏幕共享功能
                                */
                                //label: 设备名称(字符串)
                                if (isAnchor) {
                                    camera = that.formItem.camera;
                                    microphone = that.formItem.audios;
                                    localStream = AgoraRTC.createStream({streamID: uid, audio: isAnchor, cameraId: camera, microphoneId: microphone, video: isAnchor, screen: false});
                                    //设置视频属性 默认值为 480p_1(640x480) 720p_3(1280x720)
                                    localStream.setVideoProfile('720p_3');
                                    //回调通知应用程序已获取本地摄像头／麦克风使用权限
                                    // The user has granted access to the camera and mic.
                                    localStream.on("accessAllowed", function(){
                                    })
                                    // The user has denied access to the camera and mic.
                                    localStream.on("accessDenied", function(){
                                    })
                                    //初始化音视频流对象
                                    localStream.init(function() {
                                        //播放音视频流
                                        localStream.play('agora_local');
                                        //上传本地音视频流
                                        client.publish(localStream, function (err) {
                                        });
                                        //本地音视频已上传回调事件
                                        client.on('stream-published', function (evt) {
                                        });
                                    }, function (err) {
                                    });
                                }

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
                            if ($('div#video #agora_remote'+stream.getId()).length === 0) {
                                $('div#video').append('<div id="agora_remote'+stream.getId()+'" style="float:left; width:810px;height:607px;display:inline-block;"></div>');
                            }
                            stream.play('agora_remote' + stream.getId());
                        });
                        //回调通知应用程序已删除远程音视频流，即对方调用了 unpublish stream
                        client.on('stream-removed', function (evt) {
                            var stream = evt.stream;
                            stream.stop();
                            $('#agora_remote' + stream.getId()).remove();
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
            //取消上传本地音视频流
            unpublish() {
                client.unpublish(localStream, function (err) {
                });
            },
            enter(){ //发送消息
                this.$refs.box.scrollTop = this.$refs.box.scrollHeight
                setTimeout(() => {
                    this.$refs.box.scrollTop = this.$refs.box.scrollHeight
                }, 50);
                var str = $.trim(this.enterText)
                if(str != ''){
                    this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.sendMsg, "message": this.enterText}))                    
                }
            },
            pause(){
                //暂停
                $('#agora_local').html('<img src="../../live/static/img/huiyuan-logo.png" alt="" width="120px">')
                this.offline() //下线
            },
            play(){
                //播放  
                // $('#agora_local').html('')              
                this.dowebSocketSend(JSON.stringify({"roomId": this.roomId, "userId": userId, "userName": userName, "type": imType.online}))
            }
        },
        
    }
</script>
<style>
.fr{
    float: right;
}
.fl{
    float: left;
}
.chatRoom-left{
    width:100%;
}
.chatRoom-left .message-title{
    height: 50px;
    /* width:460px; */
    padding:10px;
    background: #fff;
    font-size: 16px;
    line-height: 30px;
}
.chatRoom-left .message-content{
    height: 695px;
    overflow-y: auto;
    /* width:460px; */
    padding:15px;
    background: #F8F8F8;
}
.chatRoom-left .message-edit{
    /* width:460px; */
    background: #fff;
    font-size: 0px;
    padding: 25px 10px;
    height: 125px;
}
.chatRoom-left .message-edit textarea:hover{
    border-color: #33d19f;
}
.chatRoom-left .message-edit textarea{
    border-radius: 4px 0 0 4px;
    font-size: 16px;
    resize: none;
    background: #F8F8F8;
    padding: 15px;
    color: #707070;
    height: 75px;
}
.chatRoom-left .message-edit .textarea{
    /* width: 1179px; */
    display: inline-block;
}
.chatRoom-left .message-edit button{
    height: 75px;
    width: 100%;
    border-radius: 0px 4px 4px 0px;
    font-size: 18px;
    vertical-align: top;
}
</style>
<style>
.w {
    min-width: 1200px;
    max-width: 1960px;
    margin:0 auto;
}
.ivu-chatRoom{
    padding:20px;
}
.ivu-chatRoom .video{    
    background:#fff;
}
.ivu-chatRoom .video button{
    background:#fff;
    width:203px;
    height: 48px;
    font-size:18px;
    margin-left:10px;
    margin-top:15px;
    margin-bottom:15px;
}
.ivu-chatRoom .describe{
    background:#fff;
    margin-top:15px;
    padding:30px;
    height:532px;
}
.ivu-chatRoom .describe .describe-top{
    padding-top:10px;
    text-align:center;
}
.ivu-chatRoom .describe .describe-top h4{
    font-size:26px;
    color:#222;
    padding-top:30px;
    padding-bottom:10px;
}
.ivu-chatRoom .describe .describe-top img{
     margin:0px 3px;
    vertical-align: middle;
    border-radius: 50%;
}
.ivu-chatRoom .describe .lines{
    height: 2px;
    width:100%;
    background:#FBFBFB;
    margin-top:20px;
    margin-bottom:30px;
}
.ivu-chatRoom .describe  p{
    font-size:16px;
    color:#999;
    line-height:28px;
}
</style>

<style>
.ivu-chatRoom{
    /* width:1765px; */
    margin:0 auto;
    background: #F3F3F3;
    padding: 20px;
}
.ivu-chatRoom .chatRoom-right{
    /* width:460px; */
    padding-left: 20px;
    display: inline-block;
}
.chatRoom-right .video #agora_local{
    width: 440px;
    height: 245px;
    /* padding: 10px; */
    position: relative;
    background: rgb(0, 0, 0);
    text-align: center;
    line-height: 245px;
}
.ivu-chatRoom .chatRoom-left{    
    display: inline-block;
    /* width:1290px; */
    height:870px;
    margin-right:10px;
    vertical-align: top;
}
.ivu-chatRoom .title{
    padding:10px;
    background: #fff;
    height: 100px;
}
</style>
