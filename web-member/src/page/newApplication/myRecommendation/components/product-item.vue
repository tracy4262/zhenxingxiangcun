<template>
    <div>
        <Card :padding="0" style="height:310px;">
            <div class="ivu-server-item">
                <span class="tip">
                    {{ item.salesWay }}
                </span>
                <img v-if="item.notarizationCertificate" :src="item.notarizationCertificate[0]" @click="detail(item)" style="cursor: pointer;" width="100%" height="170">
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" @click="detail(item)" style="cursor: pointer;" width="100%" height="170">
            </div>
            <div class="pd10">
                <Row type="flex" align="middle" class="pb5">
                    <Col span="16" style="height:35px; line-height:35px;" class="ell t-orange">
                        <div v-if="item.salesWay === '竞价销售'" class="ell" :title="item.startPrice"><span>起拍价：</span>￥<span style="font-size: 20px;">{{ item.startPrice }}</span></div>
                        <div v-if="item.salesWay === '预售'" class="ell"><span>预售价：</span>￥<span style="font-size: 20px;">{{ item.orderPrice }}</span></div>
                        <div v-if="item.salesWay === '定价销售'" class="ell"><span>时价：</span>￥<span style="font-size: 20px;">{{ item.discountPrice === '' ? item.currentPrice : item.discountPrice }}</span></div>
                        <div v-if="item.salesWay === '团购销售'" class="ell"><span>时价：</span>￥<span style="font-size: 20px;">{{ item.groupBuyingPrice === '' ? item.originalPrice : item.groupBuyingPrice }}</span></div>
                        <div v-if="item.salesWay === '面议'" class="ell"><span>价格：</span>￥<span style="font-size: 20px;">面议</span></div>
                    </Col>
                    <Col span="8" class="tr"><Tag color="orange" style="margin-right: 0;" v-if="item.paymentMethod === '卖方承担'">包邮</Tag></Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="16" class="ell" :title="item.commodityName">{{ item.commodityName }}</Col>
                    <Col span="8" class="tr"><Tag color="green" style="margin-right: 0;" v-if="item.isRetrospect === '是'">可追溯</Tag></Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="14" class="ell" :title="item.productLocation">{{ item.productLocation }}</Col>
                    <Col span="10" class="tr">
                        <span v-if="item.salesWay === '竞价销售'">{{ item.participantCount }} 人出价</span>
                        <span v-else-if="item.salesWay === '预售'">{{ item.buyers }} 人已预约</span>
                        <span v-else>{{ item.buyers }} 人已购</span>
                    </Col>
                </Row>
                <Row type="flex" align="middle">
                    <Col span="16" style="text-decoration:underline;color:#b1b1b1;" class="ell" :title="item.name">{{ item.name }}</Col>
                    <Col span="8" class="tr">
                        <Button :type="item.isRecommend === '未推荐' ? 'primary' : 'info'" size="small" @mouseover.native="over(item.isRecommend)" @mouseout.native="out(item.isRecommend)" @click="click(item)">{{ text }}</Button>
                    </Col>
                    <!-- <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col> -->
                </Row>
            </div>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        item: Object
    },
    components: {
    },
    data () {
        return {
            text: this.item.isRecommend,
            joinServiceName: ''
        }
    },
    watch: {
        item: {
            handler(curVal, oldVal) {
                this.strs(curVal)
            },
            deep: true
        }
    },
    created () {
    },
    methods: {
        detail (item) {
            let url = `/goods/newDetail?id=${item.id}&account=${item.account}`
            window.open(url, '_blank')
        },
        over (isRecommend) {
            if (isRecommend === '未推荐') {
                this.text = '添加推荐'
            } else {
                this.text = '取消推荐'
            }
        },
        out (isRecommend) {
            if (isRecommend === '未推荐') {
                this.text = '未推荐'
            } else {
                this.text = '已推荐'
            }
        },
        click (item) {
            if (item.isRecommend === '未推荐') {
                // 调用添加推荐的接口
                this.op(1, [{id: item.id}])
            } else {
                // 调用取消推荐的退口
                this.op(0, [{id: item.id}])
            }
        },
        op (flag, list) {
            this.$Modal.confirm({
                title: '操作提示',
                content: flag === 1 ? '设置为推荐的产品将在您的门户对外宣传展示！请确认是否设置为推荐产品！' : '取消推荐的产品将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: flag, // 0:取消推荐, 1:推荐
                        type: 4, // 1:推荐服务, 2:推荐基地, 3:推荐专家, 4:推荐产品
                        list: list
                    }).then(response => {
                        if (response.code === 200) {
                            this.$Message.success(flag === 0 ? '取消推荐成功！' : '推荐成功！')
                            this.$emit('refresh')
                        }
                    }).catch(error => {
                        this.$Message.error('服务器异常！')
                    })
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.ivu-server-item {
    position: relative;
    .tip{
        position: absolute;
        display: block;
        width: 65px;
        height: 25px;
        top: 0px;
        left: 0px;
        line-height: 25px;
        text-align: center;
        background: rgba(102, 102, 102, 0.86);
        color: #fff;
        font-size: 12px;
    }
    .related-service{
        position: absolute;
        display: block;
        bottom: 0px;
        left: 0px;
        color: #fff;
        line-height: 25px;
        width: 100%;
    }
}
</style>