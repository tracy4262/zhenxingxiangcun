<template>
    <div class="layout">
        <headNav>
        </headNav>
        <div class="tc pb100 pt50">
            <img src="../../img/developing.png" alt="">
            <p>开发中,敬请期待···！</p>
        </div>
        <!-- <div class="productList-top">
            <div class="productList-top-store">
                <span class="productList-top-store-icon"><Icon type="android-playstore"></Icon></span>
                <a :href="[this.$url.shop +'center/order/list.htm?uid='+loginuserinfo.uniqueId]" target="_blank">无忧商城</a>
            </div>
            <mall-search :datas="search" :store="true"/>
        </div> -->

        <!-- <Carousel autoplay v-model="value2">
            <Carousel-item>
                <div class="demo-carousel banner1"></div>
            </Carousel-item>
            <Carousel-item>
                <div class="demo-carousel banner2"></div>
            </Carousel-item>
            <Carousel-item>
                <div class="demo-carousel banner3"></div>
            </Carousel-item>
        </Carousel> -->
        <!--banner结束-->

        <!--content开始-->
        <!-- <div class="container" style="width: 1172px" > -->
        <!-- <div class="tuangou-title" v-for="item in columnName">
            <a :href="item.url">
                <Button type="default" style="float: right;margin-top: 16px;width: 68px;height: 30px;line-height: 18px">更多 ▸</Button>
            </a>
            <div style="width: 24px;height: 4px;margin: auto;background: #00c587;margin-top: 58px"></div>
            <h1 style="font-size: 34px;font-weight: normal;text-align: center;padding-bottom: 30px;width: 600px;margin: auto">{{item.text}}</h1>
            <div>
                <Row :gutter="16">
                    <Col span="6" class="shangping" v-for="goods in item.product">
                        <div>
                            <div style="padding: 10px 0 0 10px">
                                <span class="jiage">{{goods.price}}</span><span class="huajiage">248</span>
                            </div>
                            <div class="img-dong">
                                <img :src="goods.image" alt="" class="dongx">
                            </div>
                            <p class="shangpingming">{{goods.name}}</p> -->
        <!-- <div style="width: 201px;margin: auto;margin-top: 10px">
            <img src="../../img/daojishi.png" alt="" >
        </div> -->
        <!-- </div>
    </Col>
</Row>
</div>
</div>
<div >
<Row>
<Col span="13" style="width: 630px">
<h2 style="margin-top: 50px;border-left: 6px solid #00c587;line-height: 22px;padding-left: 6px;display: block;margin-bottom: 16px">特色推荐</h2>
<div class="down-l-img01"></div>
<div class="down-l-img02"></div>
<Row :gutter="16">
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
    <Col span="4">
    <div class="down-l-img03">
        <img src="../../img/down-img03.png" alt="">
        <p>¥168</p>
    </div>
    </Col>
</Row>
</Col>
<Col span="11" style="width: 520px;margin-left: 20px">
<Button type="default" style="float: right;margin-top: 44px;width: 68px;height: 30px;line-height: 18px">更多 ▸</Button>
<h2 style="margin-top: 50px;border-left: 6px solid #00c587;line-height: 22px;padding-left: 6px;display: block;margin-bottom: 16px">特色推荐</h2>
<Row>
    <Col span="12" style="width: 230px">
    <div class="down-r">
        <div class="down-text">
            <p>美国加州车厘子</p>
            <span>¥168</span>
        </div>
    </div>
    </Col>
    <Col span="12" style="width: 290px">
    <Row>
        <Col span="12" class="down-r-img">
        <div style="padding-top: 14px;padding-left: 14px"><p  style="font-size: 12px">苹果</p>
            <p  style="font-size: 14px;color: #00c587">¥12</p></div>
        <img src="../../img/apple.png" alt="" style="margin-left: 30px;">
        </Col>
        <Col span="12" class="down-r-img">
        <div style="padding-top: 14px;padding-left: 14px"><p  style="font-size: 12px">苹果</p>
            <p  style="font-size: 14px;color: #00c587">¥12</p></div>
        <img src="../../img/apple.png" alt="" style="margin-left: 30px;">
        </Col>
        <Col span="12" class="down-r-img">
        <div style="padding-top: 14px;padding-left: 14px"><p  style="font-size: 12px">苹果</p>
            <p  style="font-size: 14px;color: #00c587">¥12</p></div>
        <img src="../../img/apple.png" alt="" style="margin-left: 30px;">
        </Col>
        <Col span="12" class="down-r-img">
        <div style="padding-top: 14px;padding-left: 14px"><p  style="font-size: 12px">苹果</p>
            <p  style="font-size: 14px;color: #00c587">¥12</p></div>
        <img src="../../img/apple.png" alt="" style="margin-left: 30px;">
        </Col>
    </Row>
    </Col>
</Row>
</Col>
</Row>
</div> -->
    </div>
    <!--footer结束-->
    </div>
</template>
<script>
    import axios from 'axios';
    import api from '~api';
    import headNav from './components/headNav.vue';
    import mallSearch from '~components/mallSearch';

    export default {
        name: 'app',
        components: {
            headNav,
            mallSearch,
        },
        data() {
            return {
                search: {
                    value: '',
                    loading: false,
                    defOpt: [],
                    value2: '',
                    hotTag: [{
                        text: '苹果',
                        url: 'javascript:;'
                    }, {
                        text: '香蕉',
                        url: 'javascript:;'
                    }, {
                        text: '梨子',
                        url: 'javascript:;'
                    }, {
                        text: '西瓜',
                        url: 'javascript:;'
                    }, {
                        text: '葡萄',
                        url: 'javascript:;'
                    }, {
                        text: '哈密瓜',
                        url: 'javascript:;'
                    }],
                },
                loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
                modal1: false,
                modal2: false,
                modal_loading: false,
                modal3: false,
                modal4: false,
                modal5: false,
                formInline: {
                    user: '',
                    password: ''
                },
                columnName: [{
                    text: '热门团购',
                    url: '/mall/hotGroupBuy',
                    product: []
                }, {
                    text: '定价好货',
                    url: '/mall/fixPriceProduct',
                    product: []
                }, {
                    text: '优品竞拍',
                    url: '/mall/ypAuction',
                    product: []
                }, {
                    text: '新品预售',
                    url: '/mall/newPresell',
                    product: []
                }, {
                    text: '抢现货',
                    url: '/mall/stock',
                    product: []
                }, {
                    text: '可追溯商品',
                    url: '/mall/ascend',
                    product: []
                }],
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
                            this.modal_loading = true;
                            setTimeout(() => {
                                this.modal_loading = false;
                                this.modal2 = false;
                                this.$Message.success('删除成功');
                            }, 2000);
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
                appShow: true,
            };
        },
        created: function () {
            //            this.$router.push('/index')
            this.getRecommend();
        },
        methods: {
            modalShow() {
                this.cardShow = !this.cardShow;
                this.appShow = !this.appShow;
            },
            searchProduct: function () {
                this.$router.push({
                    path: '/search'
                });
            },
            routeTo(e) {
                this.$router.push(e);
            },
            /*注册*/
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        api.post('/member/login/insert',
                            {
                                username: this.formInline.user,
                                pwd: this.formInline.password
                            }
                        ).then(response => {
                            if ('exists' === response.msg) {
                                this.$Message.error('用户名已存在！');
                            } else {
                                this.$Message.success('注册成功!');
                                this.modal2 = false;
                            }
                        });
                    }
                });
            },
            //登录
            login(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        api.post('/member/login/login',
                            {
                                username: this.formInline.user,
                                pwd: this.formInline.password
                            }
                        ).then(response => {
                            console.log(response.data.data);
                            if (undefined != response.data.data) {
                                //this.$Message.success('登录成功!');
                                sessionStorage.setItem('user', JSON.stringify(response.data.data));
                                this.modal2 = false;
                                this.modal1 = true;
                            } else {
                                this.$Message.error('用户名或密码错误!');
                            }
                        }).catch(function (error) {
                            console.log(error);
                        });
                    }
                });
            },
            //点击登录显示登录界面
            loginuser() {
                this.value = '登录';
                this.modal2 = true;
            },
            //点击注册显示注册界面
            regist() {
                this.value = '注册';
                this.modal2 = true;
            },
            test() {
                api.get('/member/login/friend_list')
                    .then(response => {
                        this.friend = response.data;
                        this.friend.forEach(e => {
                            console.log(e.id);
                        });
                    });
            },
            getRecommend() {
                api.get('/member/shop/getAllProduct?pageSize=6&page=1')
                    .then(response => {
                        this.columnName[0].product = response.data.hotGroupBuy;
                        this.columnName[1].product = response.data.fixPriceProduct;
                        this.columnName[2].product = response.data.ypAuction;
                        this.columnName[3].product = response.data.newPresell;
                        this.columnName[4].product = response.data.spot;
                        this.columnName[5].product = response.data.ascend;
                    }).catch(function (error) {
                    console.log(error);
                });
            }
        }
    };
</script>

<style scoped>
    .layout {
        background: #fff;
    }

    /* header样式开始*/

    header {
        height: 81px;
        background: #FFFFFF;
        border-bottom: 1px solid #e7e7e7;
    }

    /*本页出了头和底部样式开始*/
    .tabs-nav {
        width: 100%;
        height: 62px;
        border-bottom: 2px solid #00c587;
    }

    .tabs-nav ul {
        width: 850px;
        margin: 0 auto;
    }

    .tabs-nav ul li {

    }

    .tabs-nav ul li a {
        float: left;
        margin-right: 60px;
        font-size: 16px;
        color: #333;
        line-height: 62px;
        display: block;
        padding: 0 10px;
    }

    .tabs-nav ul li .nav-end {
        float: right;
        margin-right: 0;
    }

    .tabs-nav ul li a:hover {
        background: #00c587;
        color: #fff;
    }

    .tabs-nav ul li .diss {
        background: #00c587;
        color: #fff;
    }

    .banner1 {
        background: url("../../img/cp-banner.png") no-repeat center top;
        height: 440px;
        background-size: cover;
    }

    .banner2 {
        background: url("../../img/cp-banner.png") no-repeat center top;
        height: 440px;
        background-size: cover;
    }

    .banner3 {
        background: url("../../img/cp-banner.png") no-repeat center top;
        height: 440px;
        background-size: cover;
    }

    .dianshang {
        width: 200px;
        height: 440px;
        position: absolute;
        background: rgba(255, 255, 255, 0.88);
        z-index: 999;
        margin: auto
    }

    .dianshang {
        padding-top: 26px;
        text-align: center;
        font-size: 14px;
        line-height: 44px;
    }

    .dianshang li:hover {
        color: #00c587;
        cursor: pointer;
    }

    .shangping {
        height: 410px;
        border: 1px solid #ededed;
        width: 278px;
        margin-right: 24px;
        padding-bottom: 10px;
        margin-top: 10px;
    }

    .shangping2 {
        border: 1px solid #ededed;
        height: 360px;
        width: 280px;
        margin-right: -24px;
    }

    .shangping:hover {
        border: 1px solid #00c587;
    }

    .shangping2:hover {
        border: 1px solid #00c587;
    }

    img {
        transition: all 0.45s ease-in;
    }

    .dongx {
        height: 100%;
    }

    .img-dong {
        overflow: hidden;
        height: 270px;
        margin-top: 20px;
    }

    .jiage {
        font-size: 24px;
        color: #333;
        padding-right: 6px;
    }

    .huajiage {
        font-size: 16px;
        text-decoration: line-through;
        color: #999999;
    }

    .shangpingming {
        font-size: 18px;
        text-align: center;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        margin-top: 10px;
    }

    .down-l-img01 {
        background: url("../../img/xiaheitu.png") no-repeat;
        height: 155px;
        width: 310px;
        float: left;
    }

    .down-l-img02 {
        background: url("../../img/xiaheitu.png") no-repeat;
        height: 155px;
        width: 310px;
        float: right;
    }

    .down-l-img03 {
        border: 1px solid #ededed;
        width: 97px;
        font-size: 14px;
        text-align: center;
        margin-top: 10px;
    }

    .down-l-img03:hover {
        border: 1px solid #00c587;
    }

    .down-l-img03 p {
        padding-bottom: 4px;
    }

    .down-r {
        width: 230px;
        height: 290px;
        background: url("../../img/down-r-img1.png") no-repeat;
        border: 1px solid #ededed;
    }

    .down-r-img {
        height: 145px;
        border: 1px solid rgba(237, 237, 237, 0.51);
    }

    .down-r-img:hover {
        border: 1px solid #00c587;
    }

    .down-text {
        background: rgba(81, 173, 121, .9);
        padding: 18px 0 10px 20px;
        width: 100%;
        position: absolute;
        bottom: 0;
    }

    .down-text p {
        font-size: 18px;
        color: #fff;
    }

    .down-text span {
        font-size: 22px;
        color: #fff;
    }

    .container .dianshang {
        padding-top: initial;
    }

    /*本页出了头和底部样式结束*/
</style>
<style lang="scss" scoped>
    .container {
        width: auto;
        text-align: center
    }
</style>
<style>
    /* 商店部分样式 */
    .productList-top {
        position: relative;
        width: 1172px;
        margin: 0 auto;
    }

    .productList-top .productList-top-store {
        position: absolute;
        width: 100px;
        height: 98px;
        background-color: #71BA8F;
        text-align: center;
        top: -30px;
    }

    .productList-top .productList-top-store .productList-top-store-icon {
        display: block;
        text-align: center;
        font-size: 28px;
        color: #ffffff;
        padding-top: 10px;
    }

    .productList-top .productList-top-store a {
        color: #fff;
        display: block;
        width: 80px;
        height: 34px;
        border-top: 1px solid #fff;
        border-bottom: 1px solid #fff;
        line-height: 34px;
        margin: 0 auto;
    }
</style>
