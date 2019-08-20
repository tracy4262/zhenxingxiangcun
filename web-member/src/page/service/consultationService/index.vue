<!--
    作者：chenqim
    时间：2019-02-22
    描述：咨询服务首页
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
                                <BreadcrumbItem>咨询服务</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">咨询服务</div>
                    <application-brief appId="121f18517b234175b7741ffc89248d43"></application-brief>
                    <div class="mt20">
                        <div v-if="type === 1" :class="activeIndex === 0 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(0)">发布咨询服务</div>
                        <div v-if="type === 1" :class="activeIndex === 1 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(1)">受聘管理</div>
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
            activeIndex: 2,
            type: 0, // 0非专家，1专家
            profile: '咨询服务即用户可以搜索专家，并与专家建立聘请关系。专家用户提供专有板块，可以对其他用户发来的邀请信息进行操作。浏览用户可以查看专家门户或者通过IM沟通专家。'
        }
    },
    created () {
        // 查询用户是否为专家
        this.$api.post('/member-reversion/consult/isExpert', {
            account: this.$user.loginAccount
        }).then(response => {
            if (response.code === 200) {
                if (response.data === 0) {
                    this.type = 0
                    this.activeIndex = 2
                } else if (response.data === 1) {
                    this.type = 1
                    this.activeIndex = 0
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
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
