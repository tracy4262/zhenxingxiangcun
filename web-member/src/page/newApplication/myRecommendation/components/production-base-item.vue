<template>
    <div>
        <Card :padding="0" style="height: 310px;">
            <div class="ivu-server-item">
                <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.latitude},${item.longitude}&output=html`">
                    <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.longitude},${item.latitude}&zoom=15&scale=2&markers=${item.longitude},${item.latitude}`" alt="" width="100%" height="150px">
                </a>
            </div>
            <div class="pd10">
                <p style="height:35px; line-height:35px;" class="ell" :title="item.productionBaseName">{{item.productionBaseName}}&nbsp;</p>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" class="ell" :title="item.name">
                        发布人：{{ item.name }}
                    </Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" class="ell" :title="item.location">
                        地址：{{ item.location }}
                    </Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" class="ell" :title="item.coordinate">
                        坐标：{{ item.latitude }},{{ item.longitude }}
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <Button :type="item.isRecommend === '未推荐' ? 'primary' : 'info'" size="small" @mouseover.native="over(item.isRecommend)" @mouseout.native="out(item.isRecommend)" @click="click(item)">{{ text }}</Button>
                    </Col>
                    <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col>
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
            text: this.item.isRecommend
        }
    },
    created () {
    },
    methods: {
        detail (item) {
            let url = `/member/productionBaseDetail?id=${item.id}&account=${item.account}`
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
                content: flag === 1 ? '设置为推荐的基地将在您的门户对外宣传展示！请确认是否设置为推荐基地！' : '取消推荐的基地将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: flag, // 0:取消推荐, 1:推荐
                        type: 2, // 1:推荐服务, 2:推荐基地, 3:推荐专家
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