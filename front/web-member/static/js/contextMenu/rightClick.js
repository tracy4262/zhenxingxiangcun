layui.define(['jquery','contextMenu'], function (exports) {
    var contextMenu = layui.contextMenu;
    var $ = layui.jquery;
    var ext = {
    	test: function (str) {
    		alert(str)
    	},
        init : function(opt){//定义右键操作
            var e = $(".layim-list-friend >li > ul > li[layim-event]")
            e.contextMenu({
                width: 140, // width
                itemHeight: 30, // 菜单项height
                bgColor: "#fff", // 背景颜色
                color: "#333", // 字体颜色
                fontSize: 15, // 字体大小
                hoverBgColor: "#009bdd", // hover背景颜色
                hoverColor: "#fff", // hover背景颜色
                target: function(ele) { // 当前元素
                    $(".ul-context-menu").attr("data-id",ele[0].id);
                    $(".ul-context-menu").attr("data-name",ele.find("span").html());
                    $(".ul-context-menu").attr("data-img",ele.find("img").attr('src'));
                },
                menu: [
                    { // 菜单项
                        text: "查看空间",
                        icon: "&#xe64c",
                        callback: function(ele) {
                            var p = e.parent().parent().index()
                            // 用的是 customRightClick 
                            // 查询用户是否实名
                            // let account = e.parent().parent().index()
                            // let list = {
                            //     account: account
                            // }
                            // let url = `${window.location.origin.split(':')[1]}:11101`
                            // $.ajax({
                            //     type: 'POST',
                            //     url: `${url}/member-reversion/realStep/findEnableStep`,
                            //     data: JSON.stringify(list),
                            //     dataType: 'json',
                            //     contentType: 'application/json; charset=UTF-8',
                            //     success: (response) => {
                            //         if (response.code === 200) {
                            //             if (response.data) {
                            //                 let step = Number(response.data.step)
                            //                 if (step >= 7) {
                            //                     $.ajax({
                            //                         contentType: 'application/json; charset=UTF-8',
                            //                         type: 'POST',
                            //                         url: `${url}/member-reversion/user/realCertification/findMemberClassByAccount`,
                            //                         data: JSON.stringify(list),
                            //                         dataType: 'json',
                            //                         success: (response) => {
                            //                             if (response.code === 200) {
                            //                                 let url = ''
                            //                                 if (response.data.member_class === '专家') {
                            //                                     // 专家门户
                            //                                     url = `/expertPortal/index?uid=${account}&id=0`
                            //                                 } else {
                            //                                   const member_class = response.data.member_class.join('/')
                            //                                   if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/6a9a0d73a3a546b59ee10ab7a2398a0e/c9ef371731d34c3bba29d48c9bc87328/f7c6e9f45780413fa237b36f7384a511') {
                            //                                       // 乡村门户
                            //                                     url = `/ruralPortal/index?uid=${account}&id=0`
                            //                                   } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/07e0821fe8134a468c0f5be25fc5a3df') {
                            //                                       // 农业龙头企业
                            //                                     url = `/farmHeadPortal/index?uid=${account}&id=0`
                            //                                   } else if (member_class === '1cb4efe86cf1499d83ae1138daa999c1/58e0d611a9804efa9817e1231326dcc6/0610764e02b140d49d0fa326390be91a') {
                            //                                       // 农民合作社
                            //                                     url = `/cooperativePortal/index?uid=${account}&id=0`
                            //                                   } else {
                            //                                       // 其余都进通用门户
                            //                                     url = `/portals/index?uid=${account}&id=0`
                            //                                   }
                            //                                 }
                            //                                 window.open(`${window.location.origin}${url}`)
                            //                             }
                            //                         },
                            //                     })
                            //                 } else {
                            //                     this.$Message.error('用户未实名')
                            //                 }
                            //             } else { // 说明没走过认证
                            //                 this.$Message.error('用户未实名')
                            //             }
                            //         }
                            //     },
                            // })
                        }
                    }                                                 
                ]
            });
        }
    }
  exports('rightClick', ext);
}); 