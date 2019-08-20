<template>
    <div class="index">
        <!--header开始-->
        <header>
            <div class="container">
                <Row>
                    <Col span="4" class="region ">
                    <img src="../../img/huiyuan-logo.png" alt="">
                    </Col>
                    <Col span="10" class="nav-center" style="width:45%;">
                    <Menu mode="horizontal" :theme="theme1" active-name="/pro/member" @on-select="routeTo">
                        <Menu-item name="/index">
                            <Icon type="ios-home"></Icon>
                            <a href ="/">首页</a>
                        </Menu-item>
                        <Menu-item name="/pro/member">
                          <a href ="/pro/member">会员中心</a>
                        </Menu-item>
                        <Menu-item name="/profile">
                            <a href ="/profile">我的无忧</a>
                        </Menu-item>
                        <Menu-item name="/personGate/baseInfo">
                            <a href ="/personGate/baseInfo">我的门户</a>
                        </Menu-item>
                    </Menu>
                    </Col>
                    <Col span="7">
                    <Input v-model="value13" style="margin-top: 25px;margin-left: 60px;    width: 238px;">
                    <Button slot="append" icon="ios-search"></Button>
                    </Input>
                    </Col>
                    <Col span="3" style="float: right;height:81px;">
                    <p href="#" style=";float: right;line-height: 81px;font-size: 14px;">
                        <Dropdown v-if="loginuserinfo != null">
                            <a href="javascript:void(0)" style="color: #00c587">
                                {{loginuserinfo.displayName}}
                                <Icon type="ios-arrow-down"></Icon>
                            </a>
                            <Dropdown-menu slot="list" trigger="click">
                                <Dropdown-item>账户安全</Dropdown-item>
                                <Dropdown-item>
                                    <a :href="[this.$url.shop + '/center/address.htm?uid='+loginuserinfo.uniqueId]" target="_blank">收货地址</a>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <a :href="[this.$url.shop + '/center/order/list.htm?uid='+loginuserinfo.uniqueId]" target="_blank">消费记录</a>
                                </Dropdown-item>
                                <Dropdown-item>
                                    <a href="javascript:void(0)" @click="logout">退出</a>
                                </Dropdown-item>
                            </Dropdown-menu>
                        </Dropdown>
                    </p>
                    <!--<Badge count="3" style="margin-top: 30px;float: right;margin-right: 20px">-->
                        <!--<a href="#" class="demo-badge">-->
                            <!--<Icon type="ios-bell" size="22" style="color: #00c587"></Icon>-->
                        <!--</a>-->
                    <!--</Badge>-->
                    </Col>
                </Row>
            </div>
        </header>
        <!--header结束-->

        <!--mian开始-->
        <div class="main">
            <div class="main-top">
                <div class="wrapper-container">
                    <div class="gongsi-title">
                        <Row>
                            <Col span="4" style="width: 166px">
                            <img src="../../img/gongsi-img.png" alt="">
                            </Col>
                            <Col span="20" style="border: 1px solid #ededed">
                            <div style="height: 96px;background: #fff;padding-left: 20px">
                                <h2 style="padding-top: 20px;font-size: 24px;font-weight: normal">武汉湖广农业发展有限公司</h2>
                                <p style="line-height: 24px">主要经营中药材种植开发、优良品种推广、中药材加工及生物技术研发业务</p>
                            </div>
                            <div style="height: 68px;background: #fafafa;padding-left: 20px;font-size: 12px;color: #999999">
                                <Row style="padding-top: 16px">
                                    <Col span="12">
                                    <p>相关物种：天麻,前胡,木瓜,桔梗,丹参,北柴胡,白术</p>
                                    <p>相关物种：天麻,前胡,木瓜,桔梗,丹参,北柴胡,白术</p>
                                    </Col>
                                    <Col span="12">
                                    <p>相关物种：天麻,前胡,木瓜,桔梗,丹参,北柴胡,白术</p>
                                    <p>相关物种：天麻,前胡,木瓜,桔梗,丹参,北柴胡,白术</p>
                                    </Col>
                                </Row>
                            </div>
                            </Col>
                        </Row>
                        <div class="main-nav">
                            <ul>
                                <li>
                                    <a href="#" style="  color: #00c587;border-bottom: 2px solid #00c587;">公司首页</a>
                                </li>
                                <li>
                                    <a href="#">公司首页</a>
                                </li>
                                <li>
                                    <a href="#">公司首页</a>
                                </li>
                                <li>
                                    <a href="#">公司首页</a>
                                </li>
                                <li>
                                    <a href="#">公司首页</a>
                                </li>
                                <li>
                                    <a href="#">公司首页</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-bot">
                <div class="main-bot-title">
                    <div style="padding-bottom: 20px">
                        <h3>特色介绍</h3>
                        <p>湖北帝昊源生物工程有限公司主要经营中药材种植开发、优良品种推广、中药材加工及生物技术研发业务，下辖两个分公司（湖北保康帝豪中药材种植有限责任公司、 湖北帝旺豪药业有限公司），两个合作社（保康县帝歌药材种植农民专业合作社、南漳帝乡农民专业合作社）。
                            <br/><br/> 公司围绕“规模化种植、产业化经营、标准化生产、品牌化战略”方针，走稳健、务实、创新、高效的企业发展之路。并成立了院士（专家）工作站，与湖北华中农 业大学对接，对中药材种苗进行优质培育，打造道地中药材品牌；与湖北文理学院化学工程与食品科学学院合作研发，集药用、食用、保健、强神、美容为一体的系 列化中药材研发和深加工项目建设。目前，菊花保健食品、饮品、丹参提取液等产品己研发成功，现已形成完整的服务体系。
                            <br/><br/> 湖北帝昊源生物工程有限公司有限公司规划在3-5年时间打造电子商务、智慧物流管理、智慧养老、网络传媒方面的运营体系。同时，公司通过企业资源整合、消费者 数据库储备、全链条运维体系，为项目、合作伙伴提供多渠道优质整合平台，为企业提供专业的“互联网＋”的综合解决方案。
                        </p>
                    </div>
                    <div>
                        <h3>特色介绍</h3>
                        <Row style="text-align: center">
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                            <Col span="4" style="width: 177px; margin-right: 28px;">
                            <div>
                                <div class="img-border">
                                    <img src="../../img/baicai.png" alt="">
                                </div>
                                <p style="line-height: 30px;margin-bottom: 10px">¥180</p>
                            </div>
                            </Col>
                        </Row>
                    </div>
                </div>
            </div>
        </div>
        <!--mian结束-->
        <foot></foot>
    </div>
</template>
<script>
import api from '~api'
import foot from '../../foot'
export default {
    components: {
        foot
    },
    data() {
        return {
            theme1: 'light',
            loginuserinfo: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
            portal: '',
            modal2: false,
            modal_loading: false,
            modal3: false,
            modal4: false,
            modal5: false,
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
                        this.modal_loading = true
                        setTimeout(() => {
                            this.modal_loading = false
                            this.modal2 = false
                            this.$Message.success('删除成功')
                        }, 2000);
                    },
                    logout() {
                        api.get('/member/login/logout')
                            .then(response => {
                                console.log(response.data)
                            })
                        sessionStorage.removeItem("user")
                        this.$router.push('/index')
                    }
                }
            }
        }
    }
}
</script>
<style scoped>
@import '../../css/enterprise.css';
</style>
