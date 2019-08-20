$(document).ready(function() {
    var webSocketUrl = "wss://192.168.7.226:20000/websocket";
    //var webSocketUrl = "ws://192.168.7.226:20000/";
    var imType = {"online" : 1, "offline" : 2, "sendMsg" : 3, "recvMsg" : 4};
    var websocket;
    var roomId;
    var userId;
    var userName;
    var isAnchor = false;//false 游客 true 主播
    var onlineMap = {};
    $("#userInfo").click(function(){
        //初始化必要信息
        roomId = $('#roomId').val();
        //tv
        $('#channel').val(roomId);
        userId = $('#userId').val();
        userName = $('#userName').val();
        if (roomId == "" || userId == "" || userName == "") {
            alert("这个同志，不是说了请填入必要信息吗，都不能为空的！");
            return;
        }
        $('#createUser').css("display","none");
        $('#on-offline').css("display","block");
        $('#showIm').css("display","block");
        //进行websocket校验
        if (!window.WebSocket) {
            alert("浏览器不支持websocket");
            return;
        }
        //支持websocket初始化websocket监听
        websocket = new WebSocket(webSocketUrl);
        websocket.onopen = function(event) {
            onOpen(event)
        };
        websocket.onclose = function(event) {
            onClose(event)
        };
        websocket.onmessage = function(event) {
            onMessage(event)
        };
        websocket.onerror = function(event) {
            onError(event)
        };
        //上线
        $('#onlineBtn').bind("click", function(){
            dowebSocketSend(JSON.stringify({"roomId": roomId, "userId": userId, "userName": userName, "type": imType.online}))
            $('#onlineBtn').attr("disabled","disabled");
            $("#offlineBtn").removeAttr("disabled");
            //tv
            $('#tvonlineBtn').attr("disabled","disabled");
            $('#onlineMember').append("<tr id='tr_" + userId + "'><td>" + userName + "</td><input type='hidden' id="+ userId + " value=" + userName + "></tr>");
            //tv join
            join();
        });

        //主播模式上线
        $('#tvonlineBtn').bind("click", function(){
            dowebSocketSend(JSON.stringify({"roomId": roomId, "userId": userId, "userName": userName, "type": imType.online}))
            $('#onlineBtn').attr("disabled","disabled");
            $("#offlineBtn").removeAttr("disabled");
            //tv
            $('#tvonlineBtn').attr("disabled","disabled");
            $('#onlineMember').append("<tr id='tr_" + userId + "'><td>" + userName + "</td><input type='hidden' id="+ userId + " value=" + userName + "></tr>");
            //tv join
            isAnchor = true;//主播模式
            join();
        });


        //发送消息
        $('#sendMsgBtn').bind("click", function(){
            var message = $('#messageInput').val();
            if (message == "") {
                alert("有毒？发空消息？不允许！")
            }
            dowebSocketSend(JSON.stringify({"roomId": roomId, "userId": userId, "userName": userName, "type": imType.sendMsg, "message": message}))
            $('#messageInput').val("");
        })
        //下线
        $('#offlineBtn').bind("click", function(){
            dowebSocketSend(JSON.stringify({"roomId": roomId, "userId": userId, "userName": userName, "type": imType.offline}))
            $('#offlineBtn').attr("disabled","disabled");
            $("#onlineBtn").attr("disabled","disabled");
            //tv
            $('#tvonlineBtn').attr("disabled","disabled");
            $('#tr_' + userId).remove();

            //tv
            //下线
            if (isAnchor) {
                //若为主播下线 取消上传视频
                unpublish();
            }
            //假设离开了直播间 调用leave
            leave();
            alert("下线默认离开直播间，请刷新页面！");

        })
    });

    //连接建立时触发
    function onOpen(event) {
        console.log("恭喜你，与后台服务器成功建连");
    }

    //连接关闭时触发
    function onClose(event) {
        console.log("与后台服务器断连");
    }

    //客户端接收服务端数据时触发
    function onMessage(event) {
        console.log("收到服务器消息:" + event.data);
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
                            //onlineMap
                            $('#onlineMember').append("<tr id='tr_" + onlineUserId + "'><td>" + onlines[onlineUserId] + "</td><input type='hidden' id="+ onlineUserId + " value=" + onlines[onlineUserId] + "></tr>");
                        }
                    } else {
                        //上线失败
                        //do nothing
                        alert("上线失败!")
                    }
                } else if (type == imType.sendMsg) {
                    //发送消息的响应
                    console.log(imResponse.message);

                } else if (type == imType.offline) {
                    //下线消息响应
                    console.log(imResponse.message);
                    //默认直播室业务 下线为离开直播室，在离开直播室时发起下线请求
                }
            }
        } else {
            //服务发送的imRequest数据
            var imRequest = eval("("+ event.data +")");
            if (imRequest) {
                //行为类型（如：上线、下线）
                var type = imRequest.type;
                if (type == imType.online) {
                    //上线通知 （只会收到除自己之外其他人的上线通知）
                    $('#onlineMember').append("<tr id='tr_" + imRequest.userId + "'><td>" + imRequest.userName + "</td><input type='hidden' id="+ imRequest.userId + " value=" + imRequest.username + "></tr>");
                } else if (type == imType.recvMsg) {
                    //消息通知
                    $('#showTalk').append("<tr><td>" + imRequest.userName + ": " + imRequest.message + "</td></tr>")
                } else if (type == imType.offline) {
                    //下线通知（只会收到除自己之外其他人的上线通知）
                    $('#tr_' + imRequest.userId).remove();
                }
            }
        }
    }

    //通信发生错误时触发
    function onError(event) {
        console.log("发生未知错误");
        console.log(event);
    }

    /*
     Socket.readyState 只读属性 readyState 表示连接状态，可以是以下值：
     0 - 表示连接尚未建立。
     1 - 表示连接已建立，可以进行通信。
     2 - 表示连接正在进行关闭。
     3 - 表示连接已经关闭或者连接不能打开。
     */
    function dowebSocketSend(message) {
        console.log("websocket send msg:" + message);
        if (websocket.readyState == WebSocket.OPEN) {
            websocket.send(message);
        } else {
            alert("未与服务器成功建连，Socket.readyState：" + Socket.readyState);
        }
    }



    //直播TV start

    //1.校验浏览器是否支持
    if(!AgoraRTC.checkSystemRequirements()) {
        alert("browser is no support webRTC");
    }

    //2.设置日志级别
    AgoraRTC.Logger.setLogLevel(AgoraRTC.Logger.DEBUG);
    //AgoraRTC.Logger.debug('this is debug');

    //3.枚举系统设备 枚举系统中的摄像头/麦克风设备
    var audioSelect = document.querySelector('select#audioSource');
    var videoSelect = document.querySelector('select#videoSource');
    if (isAnchor) {
        //主播模式才开启音视频设备获取
        getDevices();
    }


    function getDevices() {
        AgoraRTC.getDevices(function (devices) {
            for (var i = 0; i !== devices.length; ++i) {
                var device = devices[i];
                var option = document.createElement('option');
                option.value = device.deviceId;//deviceId: 设备号码 label: 设备名称(字符串)
                if (device.kind === 'audioinput') {
                    option.text = device.label || 'microphone ' + (audioSelect.length + 1);
                    audioSelect.appendChild(option);
                } else if (device.kind === 'videoinput') {
                    option.text = device.label || 'camera ' + (videoSelect.length + 1);
                    videoSelect.appendChild(option);
                } else {
                    console.log('Some other kind of source/device: ', device);
                }
            }
        });
    }

    //4.初始化客户端
    var client, localStream, camera, microphone,clientUid;
    function join() {
        var appidKey = $('#appidKey').val();//获取appid (暂时固定值)
        var tvChannel = roomId;//通道（即房间号）
        console.log("init web sdk client,appId: ", appidKey,", channel:", tvChannel);
        client = AgoraRTC.createClient({mode: 'interop'});//使用VP8编码、H264解码，浏览器与 Native 互通（Safari 除外）
        client.init(appidKey, function() {
            console.log("client initialized");
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
                console.log("client" + uid + "joined channel");

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
                    camera = $('#videoSource').find("option:selected").val();
                    microphone = $('#audioSource').find("option:selected").val();
                    localStream = AgoraRTC.createStream({streamID: uid, audio: isAnchor, cameraId: camera, microphoneId: microphone, video: isAnchor, screen: false});
                    //设置视频属性 默认值为 480p_1(640x480) 720p_3(1280x720)
                    localStream.setVideoProfile('720p_3');
                    //回调通知应用程序已获取本地摄像头／麦克风使用权限
                    // The user has granted access to the camera and mic.
                    localStream.on("accessAllowed", function(){
                        console.log("accessAllowed");
                    })
                    // The user has denied access to the camera and mic.
                    localStream.on("accessDenied", function(){
                        console.log("accessDenied");
                    })
                    //初始化音视频流对象
                    localStream.init(function() {
                        console.log("getUserMedia successfully");
                        //播放音视频流
                        localStream.play('agora_local');
                        $('#player_' + clientUid).css("overflow",'');
                        $('#video' + clientUid).css("position",'');
                        //上传本地音视频流
                        client.publish(localStream, function (err) {
                            console.log("Publish local stream error: " + err);
                        });
                        //本地音视频已上传回调事件
                        client.on('stream-published', function (evt) {
                            console.log("Publish local stream successfully");
                        });
                    }, function (err) {
                        console.log("getUserMedia failed", err);
                    });
                }

            }, function(err) {
                console.error("client join failed ", err);
            });
        }, function(err) {
            console.log("client init failed ", err);
        });
        //监听流事件并订阅远端流
        //回调事件
        client.on('error', function(err) {
            console.log("Got error msg:", err.reason);
            if (err.reason === 'DYNAMIC_KEY_TIMEOUT') {
                client.renewChannelKey(channelKey, function(){
                    console.log("Renew channel key successfully");
                }, function(err){
                    console.log("Renew channel key failed: ", err);
                });
            }
        });
        //远程音视频流已添加回调事件
        //该回调通知应用程序远程音视频流已添加
        client.on('stream-added', function (evt) {
            var stream = evt.stream;
            console.log("New stream added: " + stream.getId());
            console.log("Subscribe ", stream);
            //订阅远程音视频流 (subscribe)
            client.subscribe(stream, function (err) {
                console.log("Subscribe stream failed", err);
            });
        });
        //回调通知应用程序已接收远程音视频流
        client.on('stream-subscribed', function (evt) {
            var stream = evt.stream;
            console.log("Subscribe remote stream successfully: " + stream.getId());
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
            console.log("Remote stream is removed " + stream.getId());
        });
        //回调通知应用程序对方用户已离开会议室，即对方调用了 client.leave()
        client.on('peer-leave', function (evt) {
            var stream = evt.stream;
            if (stream) {
                stream.stop();
                $('#agora_remote' + stream.getId()).remove();
                console.log(evt.uid + " leaved from this channel");
            }
        });
    }

    //离开频道
    function leave() {
        client.leave(function () {
            console.log("Leavel channel successfully");
        }, function (err) {
            console.log("Leave channel failed");
        });
    }

    //上传本地音视频流
    function publish() {
        client.publish(localStream, function (err) {
            console.log("Publish local stream error: " + err);
        });
    }

    //取消上传本地音视频流
    function unpublish() {
        client.unpublish(localStream, function (err) {
            console.log("Unpublish local stream failed" + err);
        });
    }

});