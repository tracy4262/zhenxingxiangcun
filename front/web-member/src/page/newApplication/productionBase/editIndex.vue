<!--
    作者：chenqim
    时间：2019-01-07
    描述：编辑生产基地管理首页
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
                    <div class="top-app-title mt20 mb40">{{ productionBaseName }}</div>
                    <Steps :current="current" class="pb40" style="margin-left: 100px;">
                        <div v-if="complete === 0">
                            <Step title="基础信息"></Step>
                            <Step title="物联设施"></Step>
                            <Step title="基地相册"></Step>
                            <Step title="详细信息"></Step>
                        </div>
                        <div v-else>
                            <Step title="基础信息" class="cp" @click.native="last1"></Step>
                            <Step title="物联设施" class="cp" @click.native="next1"></Step>
                            <Step title="基地相册" class="cp" @click.native="next2"></Step>
                            <Step title="详细信息" class="cp" @click.native="next3"></Step>
                        </div>
                    </Steps>
                </div>
            </div>
            <!-- 下半部分 -->
            <div class="back-inner back-center">
                <base-info v-if="current === 0" @next="next1"></base-info>
                <device-info v-if="current === 1" @last="last1" @next="next2"></device-info>
                <photo-info v-if="current === 2" @last="last2" @next="next3"></photo-info>
                <detail-info v-if="current === 3" @last="last3" @next="next4"></detail-info>
            </div>
        </div>
        <div style="height: 40px;" class="back"></div>
        <foot></foot>
    </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import baseInfo from './components/baseInfo'
import deviceInfo from './components/deviceInfo'
import photoInfo from './components/photoInfo'
import detailInfo from './components/detailInfo'
export default {
    name: 'productionBaseEditIndex',
    components: {
        top,
        foot,
        baseInfo,
        deviceInfo,
        photoInfo,
        detailInfo
    },
    data () {
        return {
            height: 0,
            productionBaseName: '',
            current: 0,
            complete: 0
        }
    },
    created () {
        this.initName()
    },
    methods: {
        initName () {
            this.$api.post('/member-reversion/productionBase/findBaseInfo', {
                account: this.$user.loginAccount,
                id: this.$route.query.id
            }).then(response => {
                if (response.code === 200) {
                    this.productionBaseName = response.data.baseInfo.productionBaseName
                    this.complete = response.data.baseInfo.complete
                } else {
                    this.$Message.error('服务器异常！')
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        last1 () {
            this.current = 0
        },
        next1 () {
            this.current = 1
            // 如果更改了生产基地的名称则要重新请求一下
            this.initName()
        },
        last2 () {
            this.current = 1
        },
        next2 () {
            this.current = 2
        },
        next3 () {
            this.current = 3
        },
        last3 () {
            this.current = 2
        },
        next4 () {
            this.$router.push('/member/productionBaseList')
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
.cp {
    cursor: pointer;
}
</style>
