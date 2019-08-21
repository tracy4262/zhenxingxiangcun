<!--
    作者：chenqim
    时间：2018-12-27
    描述：代理管理首页
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
                                <BreadcrumbItem>收藏管理</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">收藏管理</div>
                    <application-brief appId="0b4cdad0734941f2b1b967fb830620d2"></application-brief>
                    <div class="mt20">
                        <div :class="activeIndex === 0 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(0)">收藏夹管理</div>
                        <div :class="activeIndex === 1 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(1)">收藏内容管理</div>
                    </div>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <favorite v-if="activeIndex === 0"></favorite>
                <collect-content v-if="activeIndex === 1"></collect-content>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import favorite from './components/favorite'
import collectContent from './components/content'
import applicationBrief from '~components/application-brief'
export default {
    name: 'employIndex',
    components: {
        top,
        foot,
        favorite,
        collectContent,
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
