<!--
    作者：chenqim
    时间：2019-02-25
    描述：发布咨询服务首页
-->
<template>
    <div>
        <top></top>
        <div class="back">
            <!-- 上半部分 -->
            <div class="back-inner">
                <div class="back-center">
                    <Row type="flex" align="middle" class="mt20">
                        <Col span="24">
                            <Breadcrumb>
                                <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                                <BreadcrumbItem :to="'/pro/member?uid=' + $user.loginAccount">会员中心</BreadcrumbItem>
                                <BreadcrumbItem to="/service/consultationService">咨询服务</BreadcrumbItem>
                                <BreadcrumbItem>发布服务</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">发布服务流程</div>
                    <Steps :current="current" class="pb40 mt40" style="margin-left: 70px;">
                        <div v-if="complete === 0">
                            <Step title="第一步" content="上传通用服务名基本信息"></Step>
                            <Step title="第二步" content="上传服务基本信息"></Step>
                            <Step title="第三步" content="上传服务营销信息"></Step>
                            <Step title="第四步" content="上传诚信承诺信息"></Step>
                            <Step title="第五步" content="加入相关服务"></Step>
                        </div>
                        <div v-else>
                            <Step title="第一步" content="上传通用服务名基本信息" class="cp" @click.native="to"></Step>
                            <Step title="第二步" content="上传服务基本信息" class="cp" @click.native="to"></Step>
                            <Step title="第三步" content="上传服务营销信息" class="cp" @click.native="to"></Step>
                            <Step title="第四步" content="上传诚信承诺信息" class="cp" @click.native="to"></Step>
                            <Step title="第五步" content="加入相关服务" class="cp" @click.native="to"></Step>
                        </div>
                    </Steps>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <router-view @last="last" @next="next"></router-view>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import step1 from './components/step1'
import step2 from './components/step2'
import step3 from './components/step3'
import step4 from './components/step4'
import step5 from './components/step5'
export default {
    name: 'employIndex',
    components: {
        top,
        foot,
        step1,
        step2,
        step3,
        step4,
        step5
    },
    data () {
        return {
            height: 0,
            current: 0,
            complete: 0
        }
    },
    created () {
        // 解决不是在第一步时刷新页面步骤跑到第一步的问题
        // console.log('router', this.$route.path.substring(this.$route.path.length - 1))
        this.current = parseInt(this.$route.path.substring(this.$route.path.length - 1)) - 1 
    },
    methods: {
        last () {
            this.current -= 1
        },
        next () {
            this.current += 1
        },
        to () {}
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
.top-description {
    font-size: 14px;
    width: 800px;
    color: #000 65%;
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
.cp {
    cursor: pointer;
}
</style>
