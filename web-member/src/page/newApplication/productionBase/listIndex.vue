<!--
    作者：chenqim
    时间：2019-01-07
    描述：生产基地管理首页
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
                                <BreadcrumbItem>生产基地管理</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">生产基地管理</div>
                    <application-brief appId="1b36352b42a74878b149a591fa4b02ef"></application-brief>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <production-base-list></production-base-list>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import productionBaseList from './components/productionBaseList'
import applicationBrief from '~components/application-brief'
export default {
    name: 'productionBaseListIndex',
    components: {
        top,
        foot,
        productionBaseList,
        applicationBrief
    },
    data () {
        return {
            height: 0,
            isList: true
        }
    },
    created () {
    },
    methods: {
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
