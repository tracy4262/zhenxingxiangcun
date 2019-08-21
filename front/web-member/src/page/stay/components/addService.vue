<template>
<div>
    <div ref="top">
        <top :address="false" />
    </div>
    <div :style="{'min-height': height}">
        <div class="layouts">
            <Breadcrumb class="pd20">
                <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
                <BreadcrumbItem to="/stay/service">住宿管理</BreadcrumbItem>
                <BreadcrumbItem>发布服务</BreadcrumbItem>
            </Breadcrumb>
            <h2 class="pl20 pr20 pb20">上传服务流程</h2>
            <div class="pb30" style="margin-left: 180px;">
                <Steps :current="currentStep" class="pl120">
                    <Step  content="上传通用服务名基本信息"></Step>
                    <Step  content="上传服务基本信息"></Step>
                    <Step  content="上传服务营销信息"></Step>
                    <Step content="上传诚信承诺信息"></Step>
                    <Step content="加入相关服务"></Step>
                </Steps>
            </div>
        </div>
        <div style="background: #F5F5F5;" class="pt30 pb30">
          <Card class="layouts">
            <div class="pb50 pt30">
                <router-view></router-view>
            </div>
          </Card>
        </div>
    </div>
    <div ref="foot">
        <foot></foot>
    </div>
</div>
</template>
<script>
import top from "../../../top";
import foot from '../../../foot';
import serviceStep1 from './step1'
import serviceStep2 from './step2'
import serviceStep3 from './step3'
import serviceStep4 from './step4'
import appBanner from '~components/app-banner'
export default {
    components: {
        top,
        foot,
        serviceStep1,
        serviceStep2,
        serviceStep3,
        serviceStep4,
        appBanner
    },
    data(){
        return{   
            currentStep: 0, 
            height:''
        }
    },
    created(){
        let path = this.$route.path
        this.currentStep = parseInt(path.slice(path.length - 1, path.length)) - 1  
    },
    watch: {
        '$route' (to, path) {
            var step = parseInt(to.path.slice(to.path.length - 1, to.path.length))
            // if(step == 5){
            //     step =5
            // }
            this.currentStep = step - 1
        }
    },
    methods:{
        // 获取页面高度
        handleGetHeight () {
            let clientHeight = document.documentElement.clientHeight
            let topHeight = this.$refs.top.offsetHeight
            let footHeight = this.$refs.foot.offsetHeight
            this.height = `${clientHeight-topHeight-footHeight}px`
        }   
    },
    mounted () {
        this.handleGetHeight()
    }
}
</script>