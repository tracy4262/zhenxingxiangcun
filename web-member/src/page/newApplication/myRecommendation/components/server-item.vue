<template>
    <div>
        <Card :padding="0" style="height:310px;">
            <div class="ivu-server-item">
                <span class="tip">
                    {{ item.type === '0' ? '垂钓' : item.type === '1' ? '采摘' : item.type === '2' ? '景区' : item.type === '3' ? '农家乐' : '民宿'}}
                </span>
                <p class="ell related-service pl5 pr5" :title="joinServiceName">{{joinServiceName}}</p>
                <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="170">
                <img v-else src="../../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="170">
            </div>
            <div class="pd10">
                <p style="height:35px; line-height:35px;" class="ell" :title="item.service_name">{{item.service_name}}&nbsp;</p>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="24" v-if="item.type === '0'" class="ell" :title="`${item.timeCharging ? '按垂钓时间收费' : ''}${item.timeVariety ? '按垂钓品种收费' : ''}`">
                        <span v-if="item.timeCharging">按垂钓时间收费</span>
                        <span v-if="item.timeVariety">按垂钓品种收费</span>
                        <span v-if="!item.timeCharging&&!item.timeVariety">暂无收费方式</span>
                    </Col>
                    <Col span="24" v-else-if="item.type === '1'" :title="`${item.timeVariety ? '按采摘品种收费' : ''}`">
                        <span v-if="item.timeVariety">按采摘品种收费</span>
                        <span v-else>暂无收费方式</span>
                    </Col>
                    <Col span="24" v-else>
                        <span v-if="item.price">
                            <span class="t-red">{{item.price ? parseFloat(item.price).toFixed(2) : parseFloat(0).toFixed(2)}}</span> 起
                        </span>
                        <span v-else>
                            暂无价格
                        </span>
                    </Col>
                </Row>
                <p class="ell" style="height:30px; line-height:30px;" v-if="item.contact && item.contact.length" :title="item.contact[0].detailAddress"><Icon type="md-pin" />{{item.contact[0].detailAddress}}</p>
                <Row>
                    <Col span="12">
                        <Button :type="item.isRecommend === '未推荐' ? 'primary' : 'info'" size="small" @mouseover.native="over(item.isRecommend)" @mouseout.native="out(item.isRecommend)" @click="click(item)">{{ text }}</Button>
                    </Col>
                    <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item.id, item.type, item.account)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col>
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
            path:'',
            joinServiceName: '',
            text: this.item.isRecommend
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
        let path = this.$route.path
        let arr = path.split('/')
        this.path = arr[1]
        this.strs(this.item)
    },
    methods: {
        strs (curVal) {
            let str = ``
            curVal.joinService.forEach((element, index) => {
                if (element.service_name) {
                    str += `${element.service_name}、`
                }
            })
            if (str) {
                str = str.substring(0, str.length - 1) + '。'
            }
            this.joinServiceName = str
        },
        detail (id, type, account) {
            let url = `/InforMation/serviceDetail?id=${id}&uid=${account}&type=${type}`
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
                content: flag === 1 ? '设置为推荐的服务将在您的门户对外宣传展示！请确认是否设置为推荐服务！' : '取消推荐的服务将从您的门户删除！请确认是否取消推荐！',
                onOk: () => {
                    this.$api.post('/member-reversion/myRecommend/operation', {
                        account: this.$user.loginAccount,
                        flag: flag, // 0:取消推荐, 1:推荐
                        type: 1, // 1:推荐服务, 2:推荐基地, 3:推荐专家
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