<template>
    <div class="wrapper layout">
        <top :address="false" />
        <member-header />        
        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="4" class="main-l">
                        <high-app name="高级应用" />
                        <Divider />
                        <base-app name="基础应用" />
                        <Divider />
                        <user-app name="通用应用" />
                    </Col>
                    <Col span="20">
                        <div>
                            <h3 class="guide-title">生产基地管理</h3>
                            <div class="warp">
                                <Icon type="android-alert" size="100" class="mt-5"></Icon>
                                <p class="mt-5">欢迎您使用生产基地管理，本应用将帮助建立生产基地信息库。</p>
                                <div class="mt-5">
                                    <Button type="primary" class="btn" @click="addProductionBase">开始</Button>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;或者</span>
                                    <Button type="text" @click="goToProductionBaseList">以后再说</Button>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../../../top'
    import foot from '../../../foot'
    import highApp from '~components/memberHighApp'
    import BaseApp from '~components/memberBaseApp'
    import UserApp from '~components/memberUserApp'
    import memberHeader from '../components/memberHeader'
    import api from '~api'
    export default {
        components:{
            top,
            foot,
            highApp,
            BaseApp,
            UserApp,
            memberHeader
        },
        data() {
            return {}
        },
        methods: {
            addProductionBase () {
                this.$router.push('/member/addProductionBase')
            },
            goToProductionBaseList () {
                this.$router.push('/member/productionBaseList')
            }
        }
    }
</script>
<style scoped>
    .guide-title {
        margin-top: 20px;
        margin-left: 40px;
    }
    .warp {
        text-align: center;
    }
    .mt-5 {
        margin-top: 25px;
    }
    .btn {
        width: 100px;
    }
</style>