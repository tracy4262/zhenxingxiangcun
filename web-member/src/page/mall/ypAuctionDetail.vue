<template>
    <div class="wrapper">
        <top :address="false" goShop />
        <!-- banner -->
        <section class="layouts">
            <div class="detail-info mt20" style="position:relative">
                <img src="../../../static/datas/img/yp-auction-detail.png" width="100%">
                <clocker time="2019-10-01" slot="value">距离结束：
                    <span class="item">%D</span>天
                    <span class="item">%H</span>时
                    <span class="item">%M</span>分
                    <span class="item">%S</span>秒
                </clocker>
            </div>
            <div class="mb20">
                <img src="../../../static/datas/img/detail-recom.png" width="100%">
            </div>
            <Row>
                <Col span="4">
                    <img src="../../../static/datas/img/shop-info.png" width="100%">
                </Col>
                <Col span="1">&nbsp;</Col>
                <Col span="19">
                    <Tabs value="name1" :animated="false" class="tab-detail">
                        <Tab-pane label="商品介绍" name="name1">
                            <img src="../../../static/datas/img/product-info.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="包装规格" name="name2">
                            <img src="../../../static/datas/img/product-spec.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="售后服务" name="name3">
                            <img src="../../../static/datas/img/product-service.png" width="100%">
                        </Tab-pane>
                        <Tab-pane label="商品评价（788）" name="name4">
                            <img src="../../../static/datas/img/product-eval.png" width="100%">
                        </Tab-pane>
                    </Tabs>
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                </Col>
            </Row>
        </section>
    </div>
</template>
<script>
import  top from '../../top'
import  clocker from '~components/clocker'
export default {
    components:{
        top,
        clocker
    },
    data () {
        return {
        }
    },
    methods: {
        
    }
}
</script>
<style lang="scss">


</style>