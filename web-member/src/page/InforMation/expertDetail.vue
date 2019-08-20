<template>
    <div class="layout">
        <topNav :address="false" />
        <!--header结束-->
        <div class="zj-main">
            <div class="zj-banner">
                <div class="banner-inner">
                    <div class="zj-banner-user"></div>
                    <div class="banner-inner-text">
                        <h2>梁铭文</h2>
                        <p>华中农业大学畜牧研究所</p>
                        <div class="banner-inner-btn">
                            <Button>联系我</Button>
                            <Button>关注我</Button>
                            <Button class="btn-active">加为好友</Button>
                        </div>
                        <Menu mode="horizontal" :theme="theme1" active-name="1">
                            <Menu-item name="1">
                                专家介绍
                            </Menu-item>
                            <Menu-item name="2">
                                个人相册
                            </Menu-item>
                            <Menu-item name="3">
                                知识
                            </Menu-item>
                            <Menu-item name="4">
                                商品
                            </Menu-item>
                            <Menu-item name="5">
                                服务
                            </Menu-item>
                        </Menu>
                    </div>
                </div>
            </div>
            <div class="zj-main-bot">
                <div class="main-bot-inner">
                    <Row>
                        <Col span="7" class="zj-main-l">
                        <div class="zj-main-l-top">
                            <h3>个人资料</h3>
                            <div class="main-l-text ">
                                <p>擅长领域</p>
                                <span>蔬菜学、水稻</span>
                            </div>
                            <div class="main-l-text2">
                                <p>擅长物种</p>
                                <span>甜瓜、小麦、稻子、茄子、黄瓜</span>
                            </div>
                            <div class="main-l-text3">
                                <p>活跃圈子</p>
                                <span>暂无</span>
                            </div>
                            <div class="haopin">
                                <span>好评率</span> &nbsp;
                                <span>100%</span>
                                <div>
                                    <Rate disabled v-model="valueDisabled"></Rate>
                                </div>
                            </div>
                        </div>
                        <div class="zj-main-l-bot">
                            <h3>资质证书</h3>
                            <ul>
                                <li><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                                <li><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                                <li class="no-margin"><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                            </ul>
                            <ul>
                                <li><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                                <li><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                                <li class="no-margin"><img src="../../img/images/zj-l-icon4.png" alt=""></li>
                            </ul>
                        </div>
                        </Col>
                        <Col span="17" class="zj-main-r">
                        <!--专家介绍开始-->
                        <div class="zjjs">
                            <div class="zjjs-main-text">
                                <p class="zjjs-main-text1">个性签名：暂无，快去添加吧。</p>
                            </div>
                            <div class="zjjs-main-text">
                                <p>主要学习及工作经历：</p>
                                <p class="zjjs-main-text2">1995年至今：湖北省农业科学院湖北省农科院畜牧兽医研究所</p>
                                <p class="zjjs-main-text2">1990-1995年：华中农业大学畜牧兽医学院兽医专业</p>
                            </div>
                            <div class="zjjs-main-text">
                                <p>主要学习及工作经历：</p>
                                <p class="zjjs-main-text2">1995年至今：湖北省农业科学院湖北省农科院畜牧兽医研究所</p>
                                <p class="zjjs-main-text2">1990-1995年：华中农业大学畜牧兽医学院兽医专业</p>
                            </div>
                        </div>
                        <!--专家介绍结束-->
                        <!--知识开始-->
                        <div class="zjzs">
                            <div class="zjzs-box1">
                                <h3>营养互补助睡眠药膳 豆浆小米粥</h3>
                                <span style="float: right;margin-top: -20px;color: #999">2017.05.22 19:38</span>
                                <div class="zjzs-btn">
                                    <Button type="default">资讯</Button>
                                    <Button type="default">品种</Button>
                                    <Button type="default">有机</Button>
                                </div>
                                <p>大家都知道小米粥是养胃的典型药膳，而且做法也是最简单的，可是，如果把煮　　小米粥的清水换成豆浆，豆浆小米粥更能让黄豆 与小米两种食物营养互补，不仅利于消化吸收，还有安神助眠的作用...
                                    <span style="color: #3194d0">查看全文</span>
                                </p>
                                <div class="zjzs-bot">
                                    <span>
                                        <Icon type="android-star-outline"></Icon>收藏 106</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span>
                                        <Icon type="ios-chatboxes-outline"></Icon>评论 106</span>
                                    <span style="float: right">
                                        <Icon type="ios-eye-outline"></Icon>106</span>
                                </div>
                            </div>
                            <div class="zjzs-box1">
                                <h3>营养互补助睡眠药膳 豆浆小米粥</h3>
                                <span style="float: right;margin-top: -20px;color: #999">2017.05.22 19:38</span>
                                <div class="zjzs-btn">
                                    <Button type="default">资讯</Button>
                                    <Button type="default">品种</Button>
                                    <Button type="default">有机</Button>
                                </div>
                                <p>大家都知道小米粥是养胃的典型药膳，而且做法也是最简单的，可是，如果把煮　　小米粥的清水换成豆浆，豆浆小米粥更能让黄豆 与小米两种食物营养互补，不仅利于消化吸收，还有安神助眠的作用...
                                    <span style="color: #3194d0">查看全文</span>
                                </p>
                                <div class="zjzs-bot">
                                    <span>
                                        <Icon type="android-star-outline"></Icon>收藏 106</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span>
                                        <Icon type="ios-chatboxes-outline"></Icon>评论 106</span>
                                    <span style="float: right">
                                        <Icon type="ios-eye-outline"></Icon>106</span>
                                </div>
                            </div>
                            <div class="zjzs-box1">
                                <h3>营养互补助睡眠药膳 豆浆小米粥</h3>
                                <span style="float: right;margin-top: -20px;color: #999">2017.05.22 19:38</span>
                                <div class="zjzs-btn">
                                    <Button type="default">资讯</Button>
                                    <Button type="default">品种</Button>
                                    <Button type="default">有机</Button>
                                </div>
                                <p>大家都知道小米粥是养胃的典型药膳，而且做法也是最简单的，可是，如果把煮　　小米粥的清水换成豆浆，豆浆小米粥更能让黄豆 与小米两种食物营养互补，不仅利于消化吸收，还有安神助眠的作用...
                                    <span style="color: #3194d0">查看全文</span>
                                </p>
                                <div class="zjzs-bot">
                                    <span>
                                        <Icon type="android-star-outline"></Icon>收藏 106</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span>
                                        <Icon type="ios-chatboxes-outline"></Icon>评论 106</span>
                                    <span style="float: right">
                                        <Icon type="ios-eye-outline"></Icon>106</span>
                                </div>
                            </div>
                            <div class="zjzs-box1">
                                <h3>营养互补助睡眠药膳 豆浆小米粥</h3>
                                <span style="float: right;margin-top: -20px;color: #999">2017.05.22 19:38</span>
                                <div class="zjzs-btn">
                                    <Button type="default">资讯</Button>
                                    <Button type="default">品种</Button>
                                    <Button type="default">有机</Button>
                                </div>
                                <p>大家都知道小米粥是养胃的典型药膳，而且做法也是最简单的，可是，如果把煮　　小米粥的清水换成豆浆，豆浆小米粥更能让黄豆 与小米两种食物营养互补，不仅利于消化吸收，还有安神助眠的作用...
                                    <span style="color: #3194d0">查看全文</span>
                                </p>
                                <div class="zjzs-bot">
                                    <span>
                                        <Icon type="android-star-outline"></Icon>收藏 106</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span>
                                        <Icon type="ios-chatboxes-outline"></Icon>评论 106</span>
                                    <span style="float: right">
                                        <Icon type="ios-eye-outline"></Icon>106</span>
                                </div>
                            </div>
                        </div>
                        <!--知识结束-->
                        <!--专家相册开始-->
                        <div class="grxc">
                            <ul>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li class="no-margin">
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                            </ul>
                            <ul>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li class="no-margin">
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                            </ul>
                            <ul>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li class="no-margin">
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                            </ul>
                            <ul>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li>
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                                <li class="no-margin">
                                    <img src="../../img/images/zjxc-img.png" alt="">
                                    <p>长江</p>
                                    <span>更新与20170601</span>
                                </li>
                            </ul>

                        </div>
                        <!--专家相册结束-->
                        </Col>
                    </Row>
                </div>
            </div>
        </div>
        <foot></foot>
    </div>
</template>
<script>
import topNav from '~src/top'
import foot from '../../foot'
import api from '~api'
export default {
    components: {
        topNav,
        foot
    },
    data() {
        return {
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            portal: '',
            loginUserMyNo: true,
            valueDisabled: 4,
            value1: [],
            modal1: false,
            modal2: false,
            modal_loading: false,
            modal3: false,
            modal4: false,
            modal5: false,
            value: '',
            friend: [],
            currentPage: 0,
            formInline: {
                user: '',
                password: ''
            },
            ruleInline: {
                user: [{
                    required: true,
                    message: '请填写用户名',
                    trigger: 'blur'
                }],
                password: [{
                    required: true,
                    message: '请填写密码',
                    trigger: 'blur'
                },
                {
                    type: 'string',
                    min: 6,
                    message: '密码长度不能小于6位',
                    trigger: 'blur'
                }
                ],
                methods: {
                    del() {
                        this.modal_loading = true
                        setTimeout(() => {
                            this.modal_loading = false
                            this.modal2 = false
                            this.$Message.success('删除成功')
                        }, 2000)
                    },
                    ok() {
                        this.$Message.info('点击了确定');
                    },
                    cancel() {
                        this.$Message.info('点击了取消');
                    }
                }
            },
            cardShow: false,
            appShow: true
        }
    },
    created: function() {
        this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
    },
    methods: {
        routeTo(e) {
            if (e == '/portal') {
                this.portal = this.$url.shop + '/center/gateway.htm?uid=' + this.loginuserinfo.uniqueId
                window.open(this.portal)
            } else {
                this.$router.push(e)
            }
        }
    }
}
</script>
<style scoped>

/*专家样式开始*/

.zj-banner {
    height: 420px;
    width: 100%;
    background: url("../../img/images/zj-banner.png") no-repeat center top;
    background-size: cover;
}

.banner-inner {
    width: 1106px;
    margin: auto;
    padding-left: 36px;
    padding-top: 210px;
}

.zj-banner-user {
    height: 176px;
    width: 176px;
    background: url("../../img/images/zj-users.png") no-repeat center top;
    float: left;
}

.banner-inner-text {
    float: left;
    margin-left: 45px;
}

.banner-inner-text h2 {
    font-size: 24px;
    line-height: 40px;
}

.banner-inner-text p {

    line-height: 40px;
    font-size: 14px;
    color: #666666;
    line-height: 24px;
}

.banner-inner-btn button {
    background: rgba(0, 0, 0, 0.35);

    color: #fff;
    width: 98px;
    height: 44px;
    font-size: 16px;
    border: none;
    margin-right: 8px;
    margin-top: 18px;
}

.banner-inner-btn button:hover {
    background: #00c587
}

.banner-inner-btn .btn-active {
    background: #00c587
}

.banner-inner-text .ivu-menu-light {
    background: none;
}


.banner-inner-text .ivu-menu-light.ivu-menu-horizontal .ivu-menu-item{
    height: inherit;
    line-height: inherit;
    color: #495060;
    margin-top: 18px;
    height: 56px;
}

.main-bot-inner {
    width: 1106px;
    margin: auto;
    margin-top: 10px;
}

.banner-inner-text .ivu-menu-horizontal.ivu-menu-light:after {

    height: 0px !important;
}

.zj-main-l {
    width: 242px;
    margin-right: 10px;
}

.zj-main-l-top {

    border: 1px solid #ededed;
    padding: 0 20px;
    padding-bottom: 26px;
    margin-bottom: 10px;
}

.main-l-text,
.main-l-text2,
.main-l-text3 {
    margin-bottom: 10px;
}



.zj-main-l-top h3,
.zj-main-l-bot h3 {
    font-size: 20px;
    text-align: center;
    line-height: 70px;
    font-weight: normal;
}

.main-l-text p,
.main-l-text2 p,
.main-l-text3 p {
    display: block;
    font-size: 16px;
    padding-left: 24px;
}

.main-l-text span,
.main-l-text2 span,
.main-l-text3 span {

    font-size: 14px;
    color: #999999;

    line-height: 30px;
}

.haopin span {
    font-size: 14px;
    color: #999999;

    line-height: 24px;
}

.main-l-text p {
    background: url("../../img/images/zj-l-icon1.png") no-repeat center left;
}

.main-l-text2 p {
    background: url("../../img/images/zj-l-icon2.png") no-repeat center left;
}

.main-l-text3 p {
    background: url("../../img/images/zj-l-icon3.png") no-repeat center left;
}

.zj-main-l-bot {

    height: 234px;
    border: 1px solid #ededed;
    padding: 0 20px;
    padding-bottom: 26px;
    margin-bottom: 10px;
}

.zj-main-l-bot ul li {
    float: left;
    margin-right: 10px;
    margin-bottom: 10px;
}

.zj-main-l-bot ul .no-margin {
    margin-right: 0px;
}

.zj-main-r {
    border: 1px solid #ededed;

    min-height: 700px;
    width: 854px;
    padding: 26px;
}

.zjjs-main-text {
    margin-bottom: 10px;
    line-height: 1.8;
    font-size: 14px;
}

.zjjs-main-text2 {
    text-indent: 25px
}

.zjzs .ivu-btn-ghost {
    color: #00c587;
    background-color: transparent;
    border-color: #00c587;
    width: 44px;
    height: 24px;
    padding: 0;
}

.zjzs-box1 {
    padding-bottom: 20px;
    border-bottom: 1px solid #ededed;
    margin-bottom: 20px;
}

.zjzs h3 {
    font-size: 18px;
}

.zjzs p {
    font-size: 14px;
    color: #999999;
    line-height: 1.6;
}

.zjzs-btn {
    margin: 6px 0 14px 0;
}

.zjzs-bot {
    margin-top: 20px;
    font-size: 13px;
    color: #999999;
}

.zjzs-bot .ivu-icon {
    display: inline-block;
    font-family: Ionicons;
    /* speak: none; */
    font-style: normal;
    font-weight: 400;
    font-variant: normal;
    text-transform: none;
    text-rendering: auto;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    font-size: 22px;
    margin-right: 4px;
    vertical-align: bottom;
    color: #999999;
}

.grxc ul li {
    width: 143px;
    height: 192px;
    border: 1px solid #ededed;
    margin: 0 20px 20px 0;
    float: left;
}

.grxc p {
    font-size: 14px;
    padding-left: 10px;
}

.grxc span {
    font-size: 12px;
    color: #999;
    padding-left: 10px;
}

.grxc .no-margin {
    margin-right: 0px;
}


/*专家样式结束*/
</style>