<template>
    <div class="vui-map-list-item">
        <Card>
            <p slot="title" :title="data.baseName">{{data.baseName}}</p>
            <Tooltip always placement="top">
                <template slot="content">
                    <!-- <div class="h6 t-orange">{{data.baseName}}</div> -->
                    <p class="ell">地址：{{data.geographicalPosition}}</p>
                    <p>坐标：{{data.coordinate}}</p>
                </template>
                <router-link :to="to" class="map-img">
                    <img :src="`//api.map.baidu.com/staticimage?width=286&height=180&center=${data.coordinate.split(',')[0]},${data.coordinate.split(',')[1]}&zoom=11&markers=${data.coordinate.split(',')[0]},${data.coordinate.split(',')[1]}`" alt="">
                </router-link>
            </Tooltip>
            <div class="vui-map-list-info mb5 mt10" :title="data.baseSynopsis">{{data.baseSynopsis === '' ? '&nbsp;' :  data.baseSynopsis}}</div>
            <div class="mb10">
                <span>联系人：{{data.contactName === '' ? '&nbsp;' :  data.contactName}}</span>
                <span class="fr">联系电话：{{data.contactTel === '' ? '&nbsp;' :  data.contactTel}}</span>
            </div>

            <Row>
                <Col span="12">
                    <router-link :to="to">
                        <Button size="small" type="default" long><Icon type="eye" size="16"></Icon></Button>
                    </router-link>
                </Col>
                <Col span="12">
                    <Poptip
                        transfer
                        confirm
                        title="您确认删除吗？"
                        @on-ok="handleDel(data.productId)">
                        <Button size="small" type="default" long><Icon type="trash-a" size="16"></Icon></Button>
                    </Poptip>
                </Col>
            </Row>
        </Card>
    </div>
</template>
<script>
export default {
    props: {
        to: String,
        data: Object
    },
    data() {
        return {
        }
    },
    created(){

    },
    methods:{
        handleDel (id) {
            this.$api.post('/member/product-base/delete', {
                productId: id
            }).then(res => {
                if(res.code === 200) {
                    this.$Message.info('删除成功')
                    this.$emit('del')
                }else {
                    this.$Message.error('删除失败')
                }
            })
        }
    }
}
</script>

<style lang="scss">
@import '../../../scss/text-overflow';
.vui-map-list-info{
    height:40px;
    @include ell(true, 2, vertical)
}
.vui-map-list-item{
    .map-img{
        display: block;
        height: 180px;
    }
    .ivu-tooltip, .ivu-tooltip-rel,
    .ivu-poptip, .ivu-poptip-rel{
        display: block;
        cursor: pointer;
    }
    .ivu-tooltip-popper{
        left: 50% !important;
        top: 50% !important;
        transform: translate3D(-50%, -100%, 0) !important
    }
}
</style>