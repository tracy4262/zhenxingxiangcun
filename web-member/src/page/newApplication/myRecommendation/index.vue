<!--
    作者：chenqim
    时间：2019-03-15
    描述：我的推荐首页
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
                                <BreadcrumbItem>我的推荐</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">我的推荐</div>
                    <application-brief appId="f21f125171264175b7741ffc89248d43"></application-brief>
                    <div class="mt20">
                        <div v-for="(item, index) in menuList" :class="activeIndex === index ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(index, item)">{{ item.name }}</div>
                    </div>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <component :is="is"></component>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import product from './components/product'
import service from './components/service'
import productionBase from './components/productionBase'
import expert from './components/expert'
import applicationBrief from '~components/application-brief'
export default {
    name: 'employIndex',
    components: {
        top,
        foot,
        product,
        service,
        productionBase,
        expert,
        applicationBrief
    },
    data () {
        return {
            is: 'product',
            menuList: [
                { name: '推荐产品', is: 'product' },
                { name: '推荐服务', is: 'service' },
                { name: '推荐基地', is: 'productionBase' },
                { name: '推荐专家', is: 'expert' }
            ],
            height: 0,
            activeIndex: 0,
        }
    },
    created () {

    },
    methods: {
        tabClick (index, item) {
            this.activeIndex = index
            this.is = item.is
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
