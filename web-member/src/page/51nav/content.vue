<!--
    作者：chenqim
    时间：2018-09-27
    描述：首页
-->
<template>
    <div>
        <!-- banner + 查询框开始 -->
        <div class="banner">
            <div style="height: 200px">
                <div class="index-title">专注农业的服务平台</div>
                <div class="index-title-next">互联网+智慧大数据</div>
            </div>
            <Card class="banner-main" :padding="0">
                <div style="background: rgba(248,251,253,0.6); padding: 26px;">
                    <div style="margin: auto; width: 730px;">
                        <span v-for ="(item,index) in indexTab" :key="index">
                            <Button :type="item.type" size="large" class="search ml10" @click="choose(index)" style="border-radius: 5px;">{{ item.value }}</Button>
                        </span>
                    </div>
                </div>
                <div class="tc pt20">
                    <Input v-model="searchValue" placeholder=" 请输入物种、资讯、新闻等查询" size="large" style="width: 720px;border-radius: 4px 0 0 4px" />
                    <Button type="primary" size="large" class="search" @click="search(searchValue)">查询</Button>
                </div>
            </Card>
        </div>
        <!-- banner + 查询结束 -->
        <!-- app开始 -->
        <div class="app" ref="app" style="background: rgba(248,251,253,0.6);padding-top:150px;">
            <div class="container">
                <div class="title mb60 pb100">
                    <div class="text tc mb100">
                        <h5 class="title-h5">应用导航</h5>
                        <p class="mt10 title-2">发现更多有意思的农业</p>
                    </div>
                </div>
                <div class="app-list">
                    <Row>
                        <i-col span="6">
                            <div class="app1" @click="infomation">
                                <div class="app1-l"><img src="../../img/app-icon1.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>查看资讯</h5>
                                    <!-- <p>农业资讯快速观看</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1" @click="policy">
                                <div class="app1-l"><img src="../../img/app-icon2.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>寻找政策</h5>
                                    <!-- <p>农业资讯</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1" @click="knowledge">
                                <div class="app1-l"><img src="../../img/app-icon3.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>查看知识</h5>
                                    <!-- <p>农业资讯快速查看</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1" @click="wiki">
                                <div class="app1-l"><img src="../../img/app-icon4.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>种养什么</h5>
                                    <!-- <p>农业资讯快速观看</p> -->
                                </div>
                            </div>
                        </i-col>
                    </Row>
                    <Row>
                        <i-col span="6">
                            <div class="app1" @click="goods">
                                <div class="app1-l"><img src="../../img/app-icon5.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>买卖产品</h5>
                                    <!-- <p>农业资讯</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1"  @click="products">
                                <div class="app1-l"><img src="../../img/app-icon6.png" alt="" width="76" height="77"></div>
                                <div class="app1-text">
                                    <h5>买卖服务</h5>
                                    <!-- <p>农业资讯快速观看</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1" @click="expert">
                                <div class="app1-l"><img src="../../img/app-icon7.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>寻找专家</h5>
                                    <!-- <p>农业资讯快速观看</p> -->
                                </div>
                            </div>
                        </i-col>
                        <i-col span="6">
                            <div class="app1" @click="enterprise">
                                <div class="app1-l"><img src="../../img/app-icon8.png" alt=""></div>
                                <div class="app1-text">
                                    <h5>寻找企业</h5>
                                    <!-- <p>农业资讯快速观看</p> -->
                                </div>
                            </div>
                        </i-col>
                    </Row>
                    <Row class="pb20">
                        <div class="tc app-more"><span @click="getMore">更多</span></div>
                        <div class="tc">
                            <Icon type="chevron-down" color="#00C587" size="20" @click.native="getMore" style="cursor: pointer;"></Icon>
                        </div>
                    </Row>
                </div>
            </div>
        </div>
        <!-- app结束 -->
        <!-- 农业动态开始 -->
        <!-- <div class="main" style="padding-bottom: 10px">
            <div class="container">
                <div class="main-title">
                    <div class="main-text">
                        <h5 class="main-title-h5">农业动态</h5>
                        <p class="mt10 title-2">农业资讯头条</p>
                    </div>
                </div>
                <div class="new-main-nav">
                    <div class="new-main-nav-tabs mr30 ml30">
                        <Button :type="activeMain == '资讯' ? 'primary' : 'text'" @click="activeMain = '资讯'">资讯</Button>
                        <Button :type="activeMain == '政策' ? 'primary' : 'text'" @click="activeMain = '政策'">政策</Button>
                        <Button :type="activeMain == '知识' ? 'primary' : 'text'" @click="activeMain = '知识'">知识</Button>
                        <Button :type="activeMain == '标准' ? 'primary' : 'text'" @click="activeMain = '标准'">标准</Button>
                        <Button :type="activeMain == '产品' ? 'primary' : 'text'" @click="activeMain = '产品'">产品</Button>
                        <Button :type="activeMain == '服务' ? 'primary' : 'text'" @click="activeMain = '服务'">服务</Button>
                        <Button :type="activeMain == '企业' ? 'primary' : 'text'" @click="activeMain = '企业'">企业</Button>
                        <Button :type="activeMain == '机关' ? 'primary' : 'text'" @click="activeMain = '机关'">机关</Button>
                        <Button :type="activeMain == '专家' ? 'primary' : 'text'" @click="activeMain = '专家'">专家</Button>
                    </div>
                    <div class="pt20 pb20">
                        <inforMation class="pd20" v-if="activeMain == '资讯'"></inforMation>
                        <policy class="pd20" v-if="activeMain == '政策'"></policy>
                        <knowledge class="pd20" v-if="activeMain == '知识'"></knowledge>
                        <standard class="pd20" v-if="activeMain == '标准'"></standard>
                        <product @on-login="handleLogin" v-if="activeMain == '产品'"></product>
                        <service v-if="activeMain == '服务'"></service>
                        <enterprise v-if="activeMain == '企业'"></enterprise>
                        <department v-if="activeMain == '机关'"></department>
                        <expert v-if="activeMain == '专家'"></expert>
                    </div>
                </div> -->
                <!-- <div class="main-nav">
                    <Tabs :animated="false">
                        <Tab-pane label="资讯">
                            <inforMation class="pd20"></inforMation>
                        </Tab-pane>
                        <Tab-pane label="政策">
                            <policy class="pd20"></policy>
                        </Tab-pane>
                        <Tab-pane label="知识">
                            <knowledge class="pd20"></knowledge>
                        </Tab-pane>
                        <Tab-pane label="标准">
                            <standard class="pd20"></standard>
                        </Tab-pane>
                        <Tab-pane label="产品">
                            <product @on-login="handleLogin"></product>
                        </Tab-pane>
                        <Tab-pane label="服务">
                            <service></service>
                        </Tab-pane>
                        <Tab-pane label="企业">
                            <enterprise></enterprise>
                        </Tab-pane>
                        <Tab-pane label="机关">
                            <department></department>
                        </Tab-pane>
                        <Tab-pane label="专家">
                            <expert></expert>
                        </Tab-pane>
                    </Tabs>
                </div> -->
            <!-- </div>
        </div> -->
        <!-- 农业动态结束 -->
    </div>
</template>
<script>
import api from '~api'
import inforMation from '../InforMation/InforMation.vue'
import policy from '../InforMation/policy.vue'
import knowledge from '../InforMation/knowledge.vue'
import product from '../InforMation/product.vue'
import service from '../InforMation/service.vue'
import enterprise from '../InforMation/enterprise.vue'
import department from '../InforMation/department.vue'
import expert from '../InforMation/expert.vue'
import standard from '../InforMation/standard.vue'
export default {
    name: 'app',
    components: {
        'inforMation': inforMation,
        'policy': policy,
        'knowledge': knowledge,
        'product': product,
        'service': service,
        'enterprise': enterprise,
        'department': department,
        'expert': expert,
        'standard': standard
    },
    data() {
        return {
            activeMain: '资讯',
            index: 1,
            searchValue: '',
            modal2: false,
            modal_loading: false,
            modal3: false,
            modal4: false,
            modal5: false,
            formInline: {
                user: '',
                password: ''
            },
            indexTab:[
                {value: '资讯', type: 'primary'},
                {value: '政策', type: 'text'},
                {value: '标准', type: 'text'},
                {value: '知识', type: 'text'},
                {value: '产品', type: 'text'},
                {value: '服务', type: 'text'},
                {value: '会员', type: 'text'}
            ],
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
                    }
                }
            },
            cardShow: false
        }
    },
    methods: {
        search (searchValue) {
            let flag = this.index
            if (flag === 1) {
                this.$router.push({
                    path: '/51index/inforMationList',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 2) {
                this.$router.push({
                    path: '/51index/policyList',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 3) {
                this.$router.push({
                    path: '/51index/standardList',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 4) {
                this.$router.push({
                    path: '/51index/knowledgeList',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 5) {
                this.$router.push({
                    path: '/goods/index',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 6) {
                // window.open(this.$url.shop + '/product/search.htm?name=' + this.fuwu)
                this.$router.push({
                    path: '/51index/serviceList/all',
                    query: {
                        title: this.searchValue
                    }
                })
            } else if (flag === 7) {
                this.$router.push({
                    path: '/51index/getMember',
                    query: {
                        title: this.searchValue
                    }
                })
            }
        },
        handleLogin (){
            this.$parent.$refs['top'].loginuser()
        },
        goods () {
            this.$router.push({
                path: '/goods/index'
            })
        },
        products () {
             this.$router.push({
                path: '/51index/serviceList/all'
            })
        },
        infomation () {
            this.$router.push({
                path: '/51index/inforMationList'
            })
        },
        policy () {
            this.$router.push({
                path: '/51index/policyList'
            })
        },
        knowledge () {
            this.$router.push({
                path: '/51index/knowledgeList'
            })
        },
        wiki () {
            window.location.href = '/wiki'
        },
        expert () {
            this.$router.push({
                path: '/51index/getMember',
                query: {
                  type: '5'
                }
            })
        },
        enterprise () {
            this.$router.push({
                path: '/51index/getMember',
                query: {
                  type: '3'
                }
            })
        },
        choose (index1) {
            this.index = index1 + 1;
            this.indexTab.forEach((element, index) => {
                if (index1 === index) {
                    element.type = 'primary'
                } else {
                    element.type = 'text'
                }
            })
        },
        getMore () {
            if (JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))) {
                this.$router.push({
                    path: '/center',
                    query: {
                        uid: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))).loginAccount
                    }
                })
            } else {
                this.$Message.error('请先登录')
                this.$parent.$refs['top'].loginuser()
            }

        }
    }
}
</script>
<style scoped lang="scss">
@import '../../css/index.css';
.app-list .ivu-row:nth-of-type(1){
    padding-bottom: 68px;
}
.app-list p {
    font-size: 14px;
    color: #666;
    margin-top: 7px;
}

.app1-text {
    float: left;
    margin-left: 18px;
    margin-top: 8px;
}

.app1:hover p, .app1:hover h5 {
    color: #00C587;
    cursor: pointer;
}

.app1:hover {
    color: #00C587;
    cursor: pointer;
}

.cardList {
    width: 1200px;
    box-shadow: none;
    .cardList-title {
        text-align: center;
    }
}

.vui-top {
    width: 300px;
    margin: 10px auto;
    overflow: hidden;
}
.app-poptip {
    margin-top:58px;
    text-align: center;
    .ivu-poptip-rel {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        bottom: 0;
    }
    .ivu-poptip-popper {
        min-width: 74%;
        transform: translateX(-50%);
        left: 50% !important;
    }

}
.banner{
    min-width: 1200px;
    background: url("../../../static/img/index-back.png") center top no-repeat;
    height: 300px;
    background-size: cover;
}
.banner-main{
    width: 1000px;
    height: 200px;
    padding: 0px;
    background: #FFFFFF;
    border-radius: 4px;
    margin-top: 80px;
    margin: auto;
    opacity: 1.0;
    z-index: 9;
    box-shadow: 0 25px 20px 0 rgba(18,88,48,0.09);
}
.index-title {
    font-family: FZLTZHK--GBK1-0;
    color: #FFFFFF;
    font-size: 34px;
    text-align: center;
    padding-top: 50px;
}
.index-title-next {
    font-family: FZLTZHK--GBK1-0;
    color: #FFFFFF;
    font-size: 20px;
    text-align: center;
    padding-top: 10px;
}
.app-more {
    color: #00C587;
    font-size: 16px;
    cursor: pointer;
    margin-top: 50px;
}
.title-2 {
    font-size: 14px;
    color: #4A4A4A;
    font-family: PingFangSC-Regular;
}
.new-main-nav {
    .new-main-nav-tabs{
        .ivu-btn{
            margin: 0px 29px;
            font-size: 16px;
        }
    }
}
</style>
