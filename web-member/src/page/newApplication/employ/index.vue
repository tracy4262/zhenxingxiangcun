<!--
    作者：chenqim
    时间：2018-12-20
    描述：聘请管理首页
-->
<template>
    <div>
        <top></top>
        <div class="back" :style="{'min-height': height}">
            <!-- 上半部分 -->
            <div class="back-inner">
                <div class="back-center">
                    <Row type="flex" align="middle" class="mt20">
                        <Col span="24">
                            <Breadcrumb>
                                <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                                <BreadcrumbItem :to="'/pro/member?uid=' + $user.loginAccount">会员中心</BreadcrumbItem>
                                <BreadcrumbItem>聘请管理</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">聘请管理</div>
                    <application-brief appId="121f18517b234175b7741ffc89248d43"></application-brief>
                    <div class="mt20">
                        <div :class="activeIndex === 0 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(0)">受聘管理</div>
                        <div :class="activeIndex === 1 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(1)">受邀管理</div>
                        <div :class="activeIndex === 2 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(2)">我要聘请</div>
                        <div :class="activeIndex === 3 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(3)">聘请管理</div>
                    </div>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <employee v-if="activeIndex === 0"></employee>
                <invitee v-if="activeIndex === 1"></invitee>
                <employ v-if="activeIndex === 2"></employ>
                <employ-manage v-if="activeIndex === 3"></employ-manage>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import employee from './components/employee'
import invitee from './components/invitee'
import employ from './components/employ'
import employManage from './components/employManage'
import applicationBrief from '~components/application-brief'
export default {
    name: 'employIndex',
    components: {
        top,
        foot,
        employee,
        invitee,
        employ,
        employManage,
        applicationBrief
    },
    data () {
        return {
            height: 0,
            activeIndex: 0
        }
    },
    created () {

    },
    methods: {
        tabClick (index) {
            this.activeIndex = index
        }
    },
    mounted () {
      this.height = `${window.innerHeight}px`
    }
}
</script>
<style scoped>
.back {
    background-color: #f5f5f5;
}
.back-inner {
    background-color: #ffffff;
}
.back-center {
    width: 1000px;
    margin: 0 auto;
    margin-top: 10px;
}
.top-app-title {
    font-size: 20px;
    color: #000 85%;
}
.tab-cus {
    padding: 8px 16px;
    font-size: 14px;
    display: inline-block;
    cursor: pointer;
}
.tab-cus-active {
    padding: 8px 16px;
    font-size: 14px;
    display: inline-block;
    cursor: pointer;
    color: #00C587;
    border-bottom: 2px solid #00C587;
}
</style>
