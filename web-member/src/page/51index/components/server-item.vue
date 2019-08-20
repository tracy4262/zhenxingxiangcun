<template>
    <div>
        <!-- <Card :padding="10">
            <div style="text-align:center">
                <img v-if="item.image_url !== undefined && item.image_url !== ''" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="220">
                <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="220">
                <span style="line-height: 40px;" class="ell" :title="item.service_name">{{ item.service_name }}</span>
                <Row>
                    <Col span="12" class="tl">
                        <Tag color="#00c587" type="border">
                            {{ item.type === '0' ? '垂钓' : item.type === '1' ? '采摘' : item.type === '2' ? '景区' : item.type === '3' ? '农家乐' : '民宿'}}
                        </Tag>
                    </Col>
                    <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item.id, item.type, item.account)">详情 <Icon type="ios-arrow-right" class="ml10"></Icon></Button></Col>
                </Row>
            </div>
        </Card> -->
        <Card :padding="0" style="height:288px;">
            <div class="ivu-server-item">
                <span class="tip">
                    {{ item.type === '0' ? '垂钓' : item.type === '1' ? '采摘' : item.type === '2' ? '景区' : item.type === '3' ? '农家乐' : '民宿'}}
                </span>
                <p class="ell related-service pl5 pr5" :title="joinServiceName"> {{joinServiceName}}
                    <!-- <span v-for="(i, index) in item.joinService" v-if="i.service_name">
                        {{i.service_name}}
                        <span v-if="index+1 < item.joinService.length">、</span>
                        <span v-else>。</span>
                    </span> -->
                </p>
                <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="170">
                <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="170">
            </div>
            <div class="pd10">
                <p style="height:35px; line-height:35px;" class="ell" :title="item.service_name">{{item.service_name}}&nbsp;</p>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="17" v-if="item.type === '0'" class="ell" :title="`${item.timeCharging ? '按垂钓时间收费' : ''}${item.timeVariety ? '按垂钓品种收费' : ''}`">
                        <span v-if="item.timeCharging">按垂钓时间收费</span>
                        <span v-if="item.timeVariety">按垂钓品种收费</span>
                    </Col>
                    <Col span="17" v-else-if="item.type === '1'" :title="`${item.timeVariety ? '按采摘品种收费' : ''}`">
                        <span v-if="item.timeVariety">按采摘品种收费</span>
                    </Col>
                    <Col span="17" v-else>
                        <span v-if="item.price">
                            <span class="t-red">{{item.price ? parseFloat(item.price).toFixed(2) : parseFloat(0).toFixed(2)}}</span> 起
                        </span>
                    </Col>
                    <Col span="7" class="tr"><Button type="default" size="small" @click="detail(item.id, item.type, item.account)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col>
                </Row>
                <p class="ell" style="height:30px; line-height:30px;" v-if="item.contact && item.contact.length" :title="item.contact[0].detailAddress"><Icon type="md-pin" />{{item.contact[0].detailAddress}}</p>
            </div>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        item: Object,
        isIndex: {
            type: Boolean,
            default: false
        }
    },
    components: {
    },
    data () {
        return {
            path:'',
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
            if (this.isIndex) {
                this.$router.push({
                    path: `/InforMation/serviceDetail`,
                    query: {
                        id: id,
                        uid: account,
                        type: type,
                    }
                })
            } else {
                this.$router.push({
                    path: `/${this.path}/serviceDetail`,
                    query: {
                        id: id,
                        uid: this.$route.query.uid,
                        type: type
                    }
                })
            }
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