<template>
    <div class="wrapper">
        <img src="../../img/com-banner1.jpg" height="400" width="100%" alt="">
        <!-- 产品介绍 -->
        <div class="layouts">
            <commodity
            class="person-tab-theme"
            :title="{cn: '产品介绍', en: 'Product introduction'}"
            :tab="['全部', '养鸡', '养鸭', '养猪']"
            :data="productData"
            :page="page"
            :simple="template"
            @on-tab-change="handleProTab"
            @on-page-change="nextPage"
            ></commodity>
        </div>
    </div>
</template>
<script>
import api from '~api'
import { navStatus } from '~page/companyGate/mixins/commonMixins'
import commodity from './components/commodity'
export default {
    mixins: [navStatus],
    components:{
        commodity
    },
    data () {
        return {
            index: 9,
            productTab: '全部',
            productData: [],
            page: {
                show: true,
                current: 1,
                total: 20
            },
            // 企业模版 还是 商业模版
            template: true,
            loginuserinfo:JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
        }
    },
    created(){
        this.getDataAll()
    },
    methods: {
        // 产品 tab 切换TAB赋值，此处做演示数据格式，自行按需求修改
        handleProTab (name) {
            this.productTab = name
            this.getDataAll()
        },

        // 获取企业全部数据
        getDataAll(){
            let that = this
            api.get('/member/shop/getAllProduct/'+ '?page=' + that.page.current + '&pageSize=' + 8)
            .then(res => {
                if(res.code === 200){
                    if(that.productTab === '全部'){
                        that.productData = res.data.ascend
                    }else if (that.productTab === '养鸡') {
                        that.productData = res.data.fixPriceProduct
                    }else if (that.productTab === '养鸭') {
                        that.productData = res.data.hotGroupBuy
                    }else{
                        that.productData = res.data.ypAuction
                    }
                    that.page.total = that.productData.length
                    that.productData.map(function(item){
                        item.adr = that.$url.shop + item.adr
                    })
                }
            }).catch(error => {
                this.$Message.error('操作异常！')
            })
        },

        // 分页事件
        nextPage(val) {
            this.page.current = val
            this.getDataAll()
        }
    }
}
</script>
<style lang="scss">
    .ma_com_pro_p{
        white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
    }
</style>