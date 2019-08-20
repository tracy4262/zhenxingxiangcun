<!--
    作者：chenqim
    时间：2018-12-14
    描述：代理首页
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
                                <BreadcrumbItem>代理管理</BreadcrumbItem>
                            </Breadcrumb>
                        </Col>
                    </Row>
                    <div class="top-app-title mt20">代理管理</div>
                    <application-brief appId="08eab6ffc30f408db28865a2cda0d2bf"></application-brief>
                    <div class="mt20">
                        <div :class="activeIndex === 0 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(0)">已代理</div>
                        <div :class="activeIndex === 1 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(1)">申请代理</div>
                        <div :class="activeIndex === 2 ? 'tab-cus-active' : 'tab-cus'" @click="tabClick(2)">待审</div>
                    </div>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <router-view></router-view>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import proxy from './components/proxy'
import applyProxy from './components/applyProxy'
import pendingApproval from './components/pendingApproval'
import applicationBrief from '~components/application-brief'

export default {
    name: 'proxyIndex',
    components: {
        top,
        foot,
        proxy,
        applyProxy,
        pendingApproval,
        applicationBrief
    },
    data () {
        return {
            height: 0,
            activeIndex: 0
        }
    },
    watch: {
      // 根据路由来判断 tab 的显示状态
      '$route': {
        handler (newVal, oldVal) {
          this.tabClass(newVal)
        }
      }
    },
    created () {
      this.tabClass(this.$route)
    },
    methods: {
        tabClick (index) {
          this.activeIndex = index
          if (index === 0) {
            this.$router.push('/member/proxy')
          } else if (index === 1) {
            this.$router.push('/member/applyProxy')
          } else if (index === 2) {
            this.$router.push('/member/pendingApproval')
          }
        },
        tabClass (newVal) {
          if (newVal.path === '/member/proxy') {
            this.activeIndex = 0
          } else if (newVal.path === '/member/applyProxy') {
            this.activeIndex = 1
          } else if (newVal.path === '/member/pendingApproval') {
            this.activeIndex = 2
          }
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
</style>
