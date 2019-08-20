/**
 * DESCRIPTION:
 * 自定义lay im右键事件及对应监听
 * @author zouyan
 * @create 2018/1/30-上午11:22
 * created by fuck~
 **/
layui.define(['jquery', 'layer','contextMenu','form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var cachedata =  layui.layim.cache();

    var conf = {
        layim: null
    };

    var rightClick = {
        config: function (options) {
            //浅拷贝合并 便于init时使用layim 外部调用传值layim(conf) 例：.config({layim: layim,});
            conf = $.extend(conf, options);
            this.init();//初始化右键菜单
        },
        init: function() {
            var layim = conf.layim;
            if (layim) {
                //在LayIM初始化就绪时 初始化自定义右键菜单
                layim.on('ready', function (options) {
                    //自定义右键操作
                    webIM.contextMenu(options);
                });
            }
        }
    };

    var webIM = {
        //自定义右键操作 初始化方法
        contextMenu : function(options){
            //所有好友分组下好友li
            var friendsLi = $('.layim-list-friend >li > ul > li');
            $(document).on('mousedown',friendsLi,function(e){
                //右键菜单传值详见contextMenu.js
                var data = {
                    //追加自定义class
                    contextItem: 'custom-friend',
                    //定义右键菜单属性值
                    target: function(ele) { // 当前元素
                        $('.custom-friend').attr('data-id', ele.attr('class').replace(/[^0-9]/ig, ""));//userId
                        $('.custom-friend').attr('data-name', ele.find('span').html());//用户名
                        $('.custom-friend').attr('data-img', ele.find('img').attr('src'));//用户头像地址
                        $('.custom-friend').attr('data-type', ele.attr('data-type'));//friend
                        $('.custom-friend').attr('data-account', options.friend[ele.parent().parent().index()].list[ele.index()].account);//account
                    },
                    menu:[]
                };
                //发送即时消息
                data.menu.push(webIM.menuChat());
                //查看空间
                data.menu.push(webIM.menuInfo(options));
                data.menu.push(webIM.menuChatLog());
                friendsLi.contextMenu(data);
            });
        },
        menuChat: function(){
            var menu = {
                text: '发送即时消息',
                icon: '&#xe63a;',
                callback: function(ele) {
                    var eleParent = ele.parent();
                    var userId = eleParent.data('id');
                    var type = eleParent.data('type');
                    var name = eleParent.data('name');
                    var avatar = eleParent.data('img');
                    conf.layim.chat({
                        id: userId,
                        name: name,
                        avatar: avatar,
                        type: type
                    });
                }
            };
            return menu;
        },
        menuInfo: (options) => {
            var menu = {
                text: '查看空间',
                icon: '&#xe64c',
                callback: (ele) => {
                    // 查询用户是否实名
                    let account = ele.parent().data('account')
                    let list = {
                        account: account
                    }
                    let url = `${window.location.origin.split(':')[1]}:11101`
                    $.ajax({
                        type: 'POST',
                        url: `${url}/member-reversion/realStep/findEnableStep`,
                        data: JSON.stringify(list),
                        dataType: 'json',
                        contentType: 'application/json; charset=UTF-8',
                        success: (response) => {
                            if (response.code === 200) {
                                if (response.data) {
                                    let step = Number(response.data.step)
                                    if (step >= 7) {
                                        $.ajax({
                                            contentType: 'application/json; charset=UTF-8',
                                            type: 'POST',
                                            url: `${url}/member-reversion/user/realCertification/findMemberClassByAccount`,
                                            data: JSON.stringify(list),
                                            dataType: 'json',
                                            success: (response) => {
                                                if (response.code === 200) {
                                                    let url = ''
                                                    if (response.data.member_class === '专家') {
                                                        // 专家门户
                                                        url = `/expertPortal/index?uid=${account}&id=0`
                                                    } else {
                                                      const member_class = response.data.member_class.join('/')
                                                      if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/6a9a0d73a3a546b59ee10ab7a2398a0e/c9ef371731d34c3bba29d48c9bc87328/f7c6e9f45780413fa237b36f7384a511') {
                                                          // 乡村门户
                                                        url = `/ruralPortal/index?uid=${account}&id=0`
                                                      } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/07e0821fe8134a468c0f5be25fc5a3df') {
                                                          // 农业龙头企业
                                                        url = `/farmHeadPortal/index?uid=${account}&id=0`
                                                      } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/0610764e02b140d49d0fa326390be91a') {
                                                          // 农民合作社
                                                        url = `/cooperativePortal/index?uid=${account}&id=0`
                                                      } else {
                                                          // 其余都进通用门户
                                                        url = `/portals/index?uid=${account}&id=0`
                                                      }
                                                    }
                                                    window.open(`${window.location.origin}${url}`)
                                                }
                                            },
                                        })
                                    } else {
                                        this.$Message.error('用户未实名')
                                    }
                                } else { // 说明没走过认证
                                    this.$Message.error('用户未实名')
                                }
                            }
                        },
                    })
                }
            };
            return menu;
        },
        menuChatLog: function(){
            var menu =  {
                text: '查看聊天记录',
                icon: '&#xe60e;',
                callback: function(ele) {
                    var eleParent = ele.parent();
                    var userId = eleParent.data('id');
                    var type = eleParent.data('type');
                    var name = eleParent.data('name');
                    layer.open({
                        type: 2
                        ,maxmin: true
                        ,title: '与 '+ name +' 的聊天记录'
                        ,area: ['450px', '600px']
                        ,shade: false
                        ,skin: 'layui-box'
                        ,anim: 2
                        ,id: 'layui-layim-chatlog'
                        ,content: cachedata.base.chatLog + '?id=' + userId + '&type=' + type
                    });
                }
            };
            return menu;
        }
    };
    exports('customRightClick', rightClick);
    exports('webIM', webIM);
});