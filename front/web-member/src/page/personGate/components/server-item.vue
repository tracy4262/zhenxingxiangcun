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
        <Card :padding="10">
            <div>
                <img v-if="item.image_url && item.image_url[0]" :src="item.image_url[0]" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="220">
                <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="220">
            </div>
            <div>
                <p>武昌鱼</p>
                <Row>
                    <Col span="12">
                        <span class="t-red">¥20.00</span>起
                    </Col>
                    <Col span="12" class="tr"><Button type="default" size="small" @click="detail(item.id, item.type, item.account)">详情 <Icon type="ios-arrow-right" class="ml10"></Icon></Button></Col>
                </Row>
                <p class="ell" ><Icon type="md-pin" />武汉市黄陂区长轩岭街道</p>
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
            path:''
        }
    },
    created () {
        let path = this.$route.path
        let arr = path.split('/')
        this.path = arr[1]
    },
    methods: {
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
                    // path: `/${this.path}/serviceDetail`,
                    path: `/InforMation/serviceDetail`,
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
<style lang="scss">
</style>