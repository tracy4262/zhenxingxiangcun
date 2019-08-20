<template>
    <div class="layout">
        <header>
            <div class="container">
                <Row>
                    <i-col span="2" class="region ">
                        <Select filterable class="select1">
                            <Option v-for="item in cityList" :value="item.value" :key="item">{{ item.label }}</Option>
                        </Select>
                    </i-col>

                    <i-col span="2" class="region ">
                        <Select filterable class="select1">
                            <Option v-for="item in cityList" :value="item.value" :key="item">{{ item.label }}</Option>
                        </Select>
                    </i-col>

                    <i-col span="12" class="nav-center">
                        <Menu mode="horizontal" active-name="/pro/index" @on-select="routeTo">
                            <Menu-item name="/index">
                                <Icon type="ios-home"></Icon>
                                首页
                            </Menu-item>
                            <Menu-item name="/pro/member">
                                应用中心
                            </Menu-item>
                            <Menu-item name="2">
                                地图导航
                            </Menu-item>

                            <Menu-item name="4">
                                移动导航
                            </Menu-item>
                            <Menu-item name="5">
                                招商代理
                            </Menu-item>
                        </Menu>
                    </i-col>
                    <i-col span="3" class="nav-r">
                        <Button type="default" class="btn1" @click="regist()">注册</Button>
                        <Button type="primary" size="large" class="btn1" @click="loginuser()">登录</Button>
                        <Modal v-model="modal2" width="360" :closable="false">
                            <Tabs class="loginTab" v-model="value">
                                <tab-pane label="注册" name="注册">
                                    <div style="text-align:center">
                                        <Form ref="formInline" :model="formInline" :rules="ruleInline">
                                            <Form-item prop="user">
                                                <i-input type="text" v-model="formInline.user" placeholder="请输入用户名">
                                                    <icon slot="prepend" type="ios-person-outline" size="20">
                                                    </Icon>
                                                </i-input>
                                            </Form-item>
                                            <Form-item prop="password">
                                                <i-input type="password" v-model="formInline.password"
                                                         placeholder="请输入密码">
                                                    <Icon type="ios-locked-outline" slot="prepend" size="20"></Icon>
                                                </i-input>
                                            </Form-item>
                                            <Form-item prop="password">
                                                <Input type="password" v-model="formInline.password1"
                                                       placeholder="请确认密码"/>
                                                <Icon type="ios-locked-outline" slot="prepend"></Icon>
                                            </Form-item>
                                            <Form-item>
                                                <Button type="primary" shape="circle"
                                                        @click="handleSubmit('formInline')" style="width:100%">注册无忧账号
                                                </Button>

                                            </Form-item>
                                        </Form>
                                    </div>
                                </tab-pane>
                                <tab-pane label="登录" name="登录">
                                    <Form ref="formInline" :model="formInline" :rules="ruleInline">
                                        <Form-item prop="user">
                                            <i-input type="text" v-model="formInline.user" placeholder="用户名">
                                                <icon slot="prepend" type="ios-person-outline" size="20">
                                                </Icon>
                                            </i-input>
                                        </Form-item>
                                        <Form-item prop="password">
                                            <i-input type="password" v-model="formInline.password" placeholder="密码">
                                                <Icon type="ios-locked-outline" slot="prepend" size="20"></Icon>
                                            </i-input>
                                        </Form-item>
                                        <Form-item>
                                            <span class="forgetPassWord">忘记密码</span>
                                        </Form-item>
                                        <Form-item>
                                            <ul class="loginList">
                                                <li>
                                                    <img src="../../img/weixin-icon.png"/>
                                                    <p>微信登录</p>
                                                </li>
                                                <li>
                                                    <img src="../../img/QQ-icon.png"/>
                                                    <p>QQ登录</p>
                                                </li>
                                            </ul>
                                        </Form-item>
                                        <Form-item>
                                            <Button type="primary" shape="circle" @click="login('formInline')"
                                                    style="width:100%">登录
                                            </Button>

                                        </Form-item>
                                    </Form>
                                </tab-pane>
                            </Tabs>
                            <div slot="footer">

                            </div>
                        </Modal>
                    </i-col>
                </Row>

            </div>
        </header>
        <!--header结束-->
        <div>
            <router-view></router-view>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import Qs from 'qs';
    import axios from 'axios';
    import api from '~api';
    import foot from '../../foot';

    axios.defaults.withCredentials = true;
    export default {
        name: 'app',
        components: {
            foot
        },
        data() {
            return {
                cityList: [
                    {
                        value: 'beijing',
                        label: '北京市'
                    },
                    {
                        value: 'shanghai',
                        label: '上海市'
                    },
                    {
                        value: 'shenzhen',
                        label: '深圳市'
                    },
                    {
                        value: 'hangzhou',
                        label: '杭州市'
                    },
                    {
                        value: 'nanjing',
                        label: '南京市'
                    },
                    {
                        value: 'chongqing',
                        label: '重庆市'
                    }
                ],
                portal: '',
                modal2: false,
                modal_loading: false,
                modal3: false,
                modal4: false,
                modal5: false,
                value: '',
                formInline: {
                    user: '',
                    password: ''
                },
                ruleInline: {
                    user: [
                        {
                            required: true,
                            message: '请填写用户名',
                            trigger: 'blur'
                        }
                    ],
                    password: [
                        {
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
                        }
                    }
                },
                cardShow: false,
                appShow: true
            };
        },
        created: function () {
            this.$router.push('/index');
        },
        methods: {
            modalShow() {
                this.cardShow = !this.cardShow;
                this.appShow = !this.appShow;
            },
            search: function () {
                this.$router.push({
                    path: '/search'
                });
            },
            routeTo(e) {
                if (e == '/portal') {
                    this.portal =
                        this.$url.shop + '/center/gateway.htm?uid=' +
                        this.loginuserinfo.uniqueId;
                    window.open(this.portal);
                } else {
                    this.$router.push(e);
                }
            },
            /*注册*/
            handleSubmit(name) {
                this.$refs[name].validate(valid => {
                    if (valid) {
                        api
                            .post('/login/insert', {
                                username: this.formInline.user,
                                pwd: this.formInline.password
                            })
                            .then(response => {
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
                this.$refs[name].validate(valid => {
                    if (valid) {
                        api.post('/member/login/login',
                            {
                                username: this.formInline.user,
                                pwd: this.formInline.password
                            }).then(response => {
                            if ('ok' === response.data) {
                                this.$Message.success('登录成功!');
                                this.modal2 = false;
                            } else {
                                this.$Message.error('用户名或密码错误!');
                            }
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
                api.get('/member/login/friendlist').then(response => {
                    this.friend = response.data;
                    this.friend.forEach(e => {
                        console.log(e.id);
                    });
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
        background: #ffffff;
        border-bottom: 1px solid #e7e7e7;
    }

    .container {
        width: 1196px;
        margin: 0 auto;
    }

    .header-top {
        height: 81px;
    }

    .region {
        float: left;
        margin-left: 14px;
        width: 80px;
        margin-top: 26px;
        height: 36px;
    }

    .nav-center {
        margin-left: 20px;
    }

    .nav-r {
        float: right;
        margin-top: 26px;
    }

    .btn1 {
        height: 36px;
        margin-left: 12px;
    }

    .banner {
        background: url("../../img/index-banner.png") center top no-repeat;
        height: 512px;
        background-size: cover;
    }

    .banner-main {
        width: 802px;
        padding: 24px;
        background: #272727;
        border-radius: 4px;
        opacity: 0.7;
        margin-top: 80px;
        margin: auto;
    }

    .banner-logo {
        width: 109px;
        margin: 20px auto;
    }

    .ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
        margin: 0;
        margin-right: 14px;
        height: 31px;
        padding: 5px 16px 4px;
        border: 1px solid #d7dde4;
        border-bottom: 0;
        border-radius: 4px 4px 0 0;
        transition: all 0.3s ease-in-out;
        background: #f5f7f9;
    }

    .ivu-input {
        display: inline-block;
        width: 100%;
        height: 41px;
        line-height: 1.5;
        padding: 4px 7px;
        font-size: 12px;
        border: 1px solid #d7dde4;
        border-radius: 4px;
        color: #657180;
        background-color: #fff;
        background-image: none;
        position: relative;
        cursor: text;
        transition: border 0.2s ease-in-out, background 0.2s ease-in-out,
        box-shadow 0.2s ease-in-out;
    }

    .ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab-active {
        height: 32px;
        padding-bottom: 5px;
        background: #fd1212;
        transform: translateZ(0);
        border-color: #d7dde4;
        color: #39f;
    }

    /*header样式结束*/

    .ivu-tabs-nav .ivu-tabs-tab-active {
        color: #fff;
    }

    .search {
        margin: 20px 0;
        border-radius: 0 4px 4px 0;
        width: 94px;
        margin-left: -4px;
    }

    .footer {
        height: 163px;
        border-top: 5px solid #00c587;
        background: #333333;
        padding-top: 22px;
    }

    .footer-l {
        float: left;
    }

    .footer-r {
        float: right;
        color: #fff;
    }

    .footer-l ul li {
        color: #fff;
        float: left;
        padding: 0 16px;
    }

    .footer-r ul li {
        color: #fff;
        float: left;
        padding: 0 16px;
    }

    .footer-l div {
        color: #b4b4b4;
        padding-left: 16px;
    }

    .cardList {
        width: 1000px;
        margin: 10px auto;
        box-shadow: 2px 2px 5px #efefef, -2px -2px 5px #efefef;
    }

    .ivu-card-head {
        border-bottom: none;
    }

    .ivu-card-head h3 {
        text-align: center;
        font-size: 22px;
        font-family: normal;
        color: #000;
    }

    .cardList-content h3 {
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #efefef;
    }

    .cardList-content h3 span {
        padding-left: 10px;
        border-left: 4px solid #00c587;
    }

    .forgetPassWord {
        float: right;
    }

    .loginList {
        text-align: center;
    }

    .loginList li {
        display: inline-block;
        margin: 0 15px;
    }

    .loginTab .ivu-tabs-bar {
        border-bottom: 1px solid #000;
    }
</style>