<template>
    <div class="index">
       
         <top :address="false" goShop />
        <!--header结束-->
        <!--mian开始-->
        <div class="main">
            <div class="wrapper-container">
                <div class="mianbaoxie">当前位置:  圈子>优品黄豆圈</div>
                <div class="main-top" style=" background: #fff;padding: 20px">
                    <div class="main-top-title">
                        <div style="float: right;margin-top: 6px; margin-right:1%;">
                            <Button type="success">申请加入</Button>
                        </div>
                        <span class="text1" style="margin-left:1%;">优品黄豆圈</span>
                        <span class="text2">成员:</span>
                        <span class="text3">123</span>
                        <span class="text2">活跃值:</span>
                        <span class="text3">123</span>
                        <p class="text4" style="margin-left:1%;">来这里发表技术文章将有机会上中文站首页直接推广，还有机会上专场，参与专家红人来了等，赶紧来发帖吧</p>
                    </div>
                    <div style="margin: 10px 0"><img src="../../img/quanzi-banner.png" alt="" style="width: 98%;margin:1%;"></div>
                   <!--
                   	作者：786905664@qq.com
                   	时间：2017-11-09
                   	描述：版主
                   -->
                   <Row style="margin-left:1%;">
                        <Col span="2" v-for="(m,index) in moderator" :key="index">
                        <div style="float: left">
                            <img :src="m.avatar" width="50px" height="50px">
                        </div>
                        <div style="float: left;margin-left: 10px;margin-top: 5px;">
                            <p style="font-size: 14px;color: #333">{{m.display_name}}</p>
                            <span style="font-size: 12px;color: #a6a6a6;line-height: 20px" >{{m.TYPE}}</span>
                        </div>
                        </Col>
                        
                    </Row>
                </div>             
                <Row style="margin-top: 20px">
                    <Col span="18" style="margin-right: 20px;width: 877px;">
                    <div class="main-bot" style="margin: 0;width: auto">
                    <div style="margin-bottom: 10px">
                        <Row>
                            <Col span="12">
                            <Button type="success">发表帖子</Button>
                            </Col>
                            <Col span="12" >
                            <div style="float: right">
                            <Page :total="total" :page-size="pageSize" show-total :current="currentPage"  @on-change="nextPage"></Page>
                            </div>
                            </Col>
                        </Row>
                    </div>
                    <!--
                   	作者：786905664@qq.com
                   	时间：2017-11-09
                   	描述：圈子详情
                   -->
                    <div class="tab-main" style="border: 1px solid #ededed">
                       <Row style="border-bottom: 1px solid #ededed;padding: 14px 0 " v-for="(c,index) in content" :key="index">
                            <Col span="1" >
                            <img src="../../img/tab-title.png" alt="" style="margin: 12px 0 0 16px;">
                            </Col>
                            <Col span="19">
                            <span>{{c.title}}</span>
                            <span style="vertical-align: middle;"><img src="../../img/tu.png" alt=""></span>&nbsp;
                            <span style="vertical-align: middle;"><img src="../../img/huo.png" alt=""></span>&nbsp;
                            <span style="vertical-align: middle;"><img src="../../img/jing.png" alt=""></span>
                            <p style="font-size: 12px">{{c.content}}</p>
                            </Col>
                            <Col span="2">
                            <span style="font-size: 12px;line-height: 26px;">{{c.creatorName}}</span>
                            <p style="font-size: 12px;line-height: 26px;">{{c.createTime}}</p>
                            </Col>
                            <Col span="1">
                            <p style="font-size: 12px;line-height: 26px;">{{c.commentNum}}</p>
                            <p style="font-size: 12px;line-height: 26px;">{{c.tourNum}}</p>
                            </Col>
                        </Row>
                    </div>
					</div>
                    </Col>
                      <Col span="6">
                    <div style="margin-bottom: 20px">
                        <div class="main-r-title2">
                            <h4>达人
                            <!--<div style="float: right;font-size: 12px" size="small" > 
                            	<Page :current="5" :total="50" simple></Page>
                            </div>-->
                            </h4>
                        </div>
                        <div class="daren-list" style="width: auto;height: 64px">
                            <div style="padding: 14px 0;width: 100%">
                                <img src="../../img/quanzi-user1.png" alt="" style="float: left">
                                <div style="float: left;margin-top: 3px;margin-left: 10px">
                                    <p class="text2">朱建威</p>
                                    <p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
                                    </p>
                                </div>
                                <Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
                            </div>
                        </div>
                        <div class="daren-list" style="width: auto;height: 64px">
                            <div style="padding: 14px 0;width: 100%">
                                <img src="../../img/quanzi-user2.png" alt="" style="float: left">
                                <div style="float: left;margin-top: 3px;margin-left: 10px">
                                    <p class="text2">朱建威</p>
                                    <p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
                                    </p>
                                </div>
                                <Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
                            </div>
                        </div>
                        <div class="daren-list" style="width: auto;height: 64px">
                            <div style="padding: 14px 0;width: 100%">
                                <img src="../../img/quanzi-user1.png" alt="" style="float: left">
                                <div style="float: left;margin-top: 3px;margin-left: 10px">
                                    <p class="text2">朱建威</p>
                                    <p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
                                    </p>
                                </div>
                                <Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
                            </div>
                        </div>
                        <div class="daren-list" style="width: auto;height: 64px">
                            <div style="padding: 14px 0;width: 100%">
                                <img src="../../img/quanzi-user2.png" alt="" style="float: left">
                                <div style="float: left;margin-top: 3px;margin-left: 10px">
                                    <p class="text2">朱建威</p>
                                    <p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
                                    </p>
                                </div>
                                <Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
                            </div>
                        </div>
                        <div class="daren-list" style="width: auto;height: 64px">
                            <div style="padding: 14px 0;width: 100%">
                                <img src="../../img/quanzi-user1.png" alt="" style="float: left">
                                <div style="float: left;margin-top: 3px;margin-left: 10px">
                                    <p class="text2">朱建威</p>
                                    <p class="text2"><span style="margin-left: 0;color: #999999">发帖  <span style="color: #ff8b3f;">765</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span style="margin-left: 0;color: #999999">粉丝 <span style="color: #00c587;">75</span></span>
                                    </p>
                                </div>
                                <Button style="float: right;margin-top: 12px" size="small">+ 关注</Button>
                            </div>
                        </div>
                    </div>
                    </Col>
                </Row>
            </div>
        </div>
        <!--mian结束-->
        <foot></foot>
    </div>
</template>
<script>
    import top from '../../top'
    import foot from '../../foot'
export default { 
	name:'cirleDetail',
	components:{
        top,
        foot
    },
    data() {
        return {
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            portal :'',
            theme1: 'light',
            value13:'',
            pageSize: 5,
            modal2: false,
            modal_loading: false,
            modal3: false,
            modal4: false,
            modal5: false,
            content: [],
            total: 0,
            currentPage: 1,
            moderator: [],
            formInline: {
                user: '',
                password: ''
            },
            ruleInline: {
                user: [
                    { required: true, message: '请填写用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
                ],
                methods: {
                    del() {
                        this.modal_loading = true;
                        setTimeout(() => {
                            this.modal_loading = false;
                            this.modal2 = false;
                            this.$Message.success('删除成功');
                        }, 2000);
                    }
                }
            }
        }
    },
    methods: {
    	/**
    	 * 圈子详情
    	 */
        show() {
            this.$api.get('/member/CoterieArticle/circleDetail/' + this.$route.query.id + '/' + this.currentPage+'/'+this.pageSize)
                .then(response => {
                    this.content = response.data.list
                    this.total = response.data.total
                    console.log("圈子详情。。。")
                    console.log(this.content)
                }).catch(function (error) {
                    console.log(error)
                })
        },
        nextPage(page) {
            this.currentPage = page;
            this.$api.get('/member/CoterieArticle/circleDetail/' + this.$route.query.id + '/' + this.currentPage+'/'+this.pageSize)
                .then(response => {
                    this.content = response.data.list;
                    this.total = response.data.total;
                    console.log("圈子详情。。。" + this.total)
                    console.log(response.total)
                    console.log(this.content)
                }).catch(function (error) {
                    console.log(error)
                })
        },
        /*
             	作者：786905664@qq.com
                   	时间：2017-11-09
                   	描述：版主
                  */
        moderators() {
            this.$api.get('/member/CoterieArticle/moderator/' + this.$route.query.id)
                .then(response => {
                    this.moderator = response.data
                    console.log("副版主。。。")
                    console.log(this.moderator)
                }).catch(function (error) {
                    console.log(error)
                })
        },
       
    },
    created: function () {
        this.show();
        this.moderators();
    }
}
</script>

<style scoped>
    @import '../../css/circle.css';
    .ivu-col-span-2 {
        display: block;
        width: 11%;
    }
    .main-r-title2 {
    height: 50px;
    background: #f8f8f8;
    padding-top: 13px;


    /*border-bottom: 2px solid #00c587;*/
}
    .main .main-r-title2 h4 {
        color: #333;
        font-size: 16px;
        font-weight: normal;
        padding-left: 10px;
        display: block;
    }  

</style>
