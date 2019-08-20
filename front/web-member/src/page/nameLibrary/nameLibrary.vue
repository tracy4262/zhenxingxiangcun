<template>
    <div class="layout">
        <top :address="false"/>
        <div class="main">
            <div class="container">
                <Row :gutter="20">
                    <Col span="24">
                        <app-banner src="../../../../static/img/app-banner-species.png" title="名称库管理">
                        </app-banner>
                        <Row>
                            <Col span="24" class="mt20">
                                <Tabs type="card" :animated="false" :value="tabValue" @on-click="tabClick">
                                    <TabPane label="物种管理" name="tab1">
                                        <species-manage v-if="tabValue === 'tab1'" />
                                    </TabPane>
                                    <TabPane label="品种管理" name="tab2">
                                        <variety-manage v-if="tabValue === 'tab2'" />
                                    </TabPane>
                                    <TabPane label="病害管理" name="tab3">
                                        <disease-manage v-if="tabValue === 'tab3'" />
                                    </TabPane>
                                    <TabPane label="虫害管理" name="tab4">
                                        <pest-manage v-if="tabValue === 'tab4'" />
                                    </TabPane>
                                    <TabPane label="通用商品名管理" name="tab5">
                                        <common-product v-if="tabValue === 'tab5'" />
                                    </TabPane>
                                    <TabPane label="通用服务名管理" name="tab6">
                                        <common-service v-if="tabValue === 'tab6'" />
                                    </TabPane>
                                </Tabs>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </div>
        </div>
        <foot></foot>
    </div>
</template>

<script>
    import top from '../../top'
    import foot from '../../foot'
    import commonProduct from './components/commonProduct'
    import commonService from './components/commonService'
    import speciesManage from './components/speciesManage'
    import varietyManage from './components/varietyManage'
    import diseaseManage from './components/diseaseManage'
    import pestManage from './components/pestManage'
    import appBanner from '~components/app-banner'

    export default {
        components: {
            top,
            foot,
            appBanner,
            commonProduct,
            commonService,
            varietyManage,
            diseaseManage,
            speciesManage,
            pestManage
        },
        data () {
            return {
                tabValue: 'tab1'
            }
        },
        created () {
            console.log('this.$route', this.$route)
            if (this.$route.query.tabValue && this.$route.query.tabValue !== '') {
                this.tabValue = this.$route.query.tabValue
            }
            console.log('tabValue', this.tabValue)
        },
        methods: {
            tabClick (name) {
                this.tabValue = name
            }
        }
    }
</script>

<style lang="scss">
    .uploadClass{
        margin-left: auto;
    }
    .listDemo {
        position: relative;
        display: block;
        width: 184px;
        float: left;
        margin-right: 3px;
        margin-left: 3px;
        margin-top: 3px;
        margin-bottom: 3px;
        overflow: hidden;
        img {
            width: 184px;
            height: 184px;
            vertical-align: middle;
        }
        &:hover {
            .species-tool {
                top: 0;
                .p3 {
                    bottom: 10px;
                }
            }
        }
        .species-tool {
            top: 80%;
            bottom: 0;
            margin: 0;
            z-index: 9;
            background: rgba(0, 0, 0, .5);
            transition: top .3s;
            color: white;
            font-size: 16px;
            .p2 {
                font-size: 12px;
                display: none;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: normal;
                display: -webkit-box;
                -webkit-line-clamp: 4; // number行数
                -webkit-box-orient: vertical; //方向 vertical

            }
            &,
            .p3 {
                position: absolute;
                left: 0;
                right: 0;
            }
            .p3 {
                bottom: -1000px;
                a {
                    margin: 0 10px;
                }
            }
        }

    }
</style>