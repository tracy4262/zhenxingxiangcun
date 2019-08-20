<template>
    <div class="wrapper">
        <img src="../../img/com-banner5.jpg" height="400" width="100%" alt="">
        <div class="layouts  preBase per-gate-input">
            <item-tab
                :breadcrumb="breadcrumb"
            ></item-tab>
            <divider solid style="margin:0" />
            <div style="height: 20px;"></div>
            <Col span="24">
                <Form class="mt10" inline>
                    <FormItem>
                        <Input v-model="baseName" placeholder="基地名称"></Input>
                    </FormItem>
                    <FormItem>
                        <Input v-model="contactName" placeholder="联系人"></Input>
                    </FormItem>
                    <FormItem>
                        <Input v-model="contactTel" placeholder="电话号码"></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="warning" @click.native="handleSearch">查询</Button>
                    </FormItem>
                    <FormItem>
                        <Button type="warning" @click.native="addProductionBase">新增</Button>
                    </FormItem>
                </Form>
                <div v-if="mapItemData.length > 0">
                    <Row :gutter="16" class="pre-base-btn">
                        <Col span="6" v-for="(item,index) in mapItemData" :key="index">
                            <map-item
                            :to="`/member/productionBaseDetail?id=${item.productId}`"
                            :data="item"
                            class="mb10"></map-item>
                        </Col>
                    </Row>
                    <Page class="mt20 country tc" :page-size="9" :total="total" @on-change="handleChangePage"></Page>
                    <div style="height: 20px;"></div>
                </div>
                <div class="ma-polic-img" v-if="mapItemData.length === 0">
                    <img src="../../img/ma-img-002.png"> 
                    <p style="margin-top: 10px;">暂无数据</p>
                </div>
            </Col>
        </div>
    </div>
</template>
<script>
import { navStatus } from '~page/companyGate/mixins/commonMixins'
import itemTab from './components/item-tab'
import divider from '~components/divider'
import mapItem from '~page/member/components/productionMapList'

export default {
    mixins: [navStatus],
    components:{
        itemTab,
        divider,
        mapItem
    },
    data () {
        return {
            index: 8,
            breadcrumb: [{
                title: '首页',
                url: 'index'
            }, {
                title: '基地'
            }],
            mapItemData: [],
            total: 0,
            baseName: '',
            contactName: '',
            contactTel: '',
            loginuserinfo:{}
        }
    },
    created () {
        this.loginuserinfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        this.loadMapData()
    },
    methods:{
        // 搜索
        handleSearch () {
            this.loadMapData(this.baseName, this.contactName, this.contactTel)
        },
        // 分页
        handleChangePage (pageNum) {
            this.loadMapData(this.baseName, this.contactName, this.contactTel, pageNum)
        }, 
        loadMapData (baseName = '', contactName = '', contactTel = '', pageNum = 1) {
            this.$api.post('/member/product-base/select-all', {
                loginAccount: this.loginuserinfo.loginAccount,
                baseName: baseName,
                contactName: contactName,
                contactTel: contactTel,
                pageNum: pageNum,
                pageSize: 9
            }).then(res => {
                this.total = res.data.total
                this.mapItemData = res.data.list
            })
        },
        addProductionBase () {
            this.$router.push('/member/addProductionBase')
        }
    }
}
</script>
<style lang="scss">
.ma-polic-img{text-align: center;}  
.preBase .ivu-breadcrumb a:hover{color: #f5a623;}
.preBase .ivu-input:focus, .preBase .ivu-input:hover {
        border-color: #ffad33 !important;
        box-shadow: 0 0 0 2px rgb(255, 238, 213);
    }
.ivu-card-body:hover #per-arrow-right {
    background-color: #ffad33;
    color: #fff;
    border: #ffad33;
}
.pre-base-btn .ivu-btn-ghost:hover{
        color: #ffad33;
        background-color: transparent;
        border-color: #ffad33;
    }
</style>