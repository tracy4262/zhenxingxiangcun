<template>
    <div>
        <Card :padding="0" style="height:288px;">
            <div class="ivu-server-item">
                <span class="tip">
                    咨询服务
                </span>
                </p>
                <img v-if="item.personalPicture" :src="item.personalPicture" @click="detail(item.id)" style="cursor: pointer;" width="100%" height="170">
                <img v-else src="../../../../static/img/goods-list-no-picture1.png" @click="detail(item.id, item.type, item.account)" style="cursor: pointer;" width="100%" height="170">
            </div>
            <div class="pd10">
                <p style="height:35px; line-height:35px;" class="ell" :title="item.expertName">{{item.expertName}}&nbsp;</p>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="17" class="ell" :title="item.adeptSpecies">
                        擅长物种：{{ item.adeptSpecies }}
                    </Col>
                    <Col span="7" class="tr"><Button type="default" size="small" @click="detail(item.id)">详情 <Icon type="ios-arrow-forward"></Icon></Button></Col>
                </Row>
                <Row type="flex" align="middle" class="pb5">
                    <Col span="17" class="ell" :title="item.adeptField">
                        擅长领域：{{ item.adeptField }}
                    </Col>
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
        }
    },
    created () {
    },
    methods: {
        detail (id) {
            this.$router.push({
                path: `/consultationService/detail`,
                query: {
                    id: id
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