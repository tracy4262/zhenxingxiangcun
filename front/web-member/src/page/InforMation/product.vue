<template>
    <div class="container pb20">
        <!-- <div class="tc pb100 pt50">
            <img src="../../img/developing.png" alt="">
            <p>开发中,敬请期待···！</p>
        </div> -->
       <!-- <list ref="list"  @on-login="handleLogin"></list> -->
        <ul class="goods-list mb20" v-if="product.length > 0">
            <li v-for="(item, index) in product" :key="index">
                <div @click="handleDetail(item)">
                    <img v-if="item.src && item.src[0]" :src="item.src[0]" width="100%" height="216">
                    <img v-else src="../../../static/img/goods-list-no-picture1.png" width="100%" height="216">
                    <div class="pd5">
                        <div class="t-orange pt10" style="font-size:20px;">
                            <span v-if="item.price && item.finish">{{item.price}}/斤</span>
                            <span v-else > {{item.discount}}/斤</span>
                        </div>
                        <p class="ell name pb5" :title="item.name">{{item.name}}</p>
                        <p class="ell address pb5" :title="item.address">{{item.address}}</p>
                        <p class="ell address pb5" :title="item.seller">{{item.seller}}</p>
                    </div>
                </div>
            </li>
        </ul>
       <div v-if="product.length" class="tc pt20">
           	<Button type="default" class="mt10" @click="goods" style="width:200px;">更多</Button>
       </div>
    </div>
</template>
<script>
import list from '../goods/index/components/list'
    export default {
        components: {
            list
        },
        data(){
            return {
                currentPage:1,
                product:[],
                total: 0,
            }
        },
        created () {
            this.handleGetList()
        },
        methods:{
            // 到详情页
            handleDetail (item) {
                this.$router.push(`/goods/detail?id=${item.id}&account=${item.account}`)
            },
            handleLogin (){
                this.$emit('on-login')
            },
            goods () {
                this.$router.push({
                    path: '/goods/index'
                })
            },
            handleGetList() {
                // default :1 默认排序。timePriceFlag: 0 价格正序 1价格倒序 ,name 关键字
                let list ={
                    pageSize: 5,
                    pageNum: 1,
                    isNavDisplay: 1,
                    default: 1
                }
                this.$api.post('/portal/shopCommdoity/findShopCommodityList', list).then(response => {
                    if (response.code == 200) {
                        // this.$refs['list'].listData = response.data.list
                        this.product = response.data.list
                        // this.$refs['list'].isShow = true
                    }
                })
            },
        }
    }
</script>
<style lang="scss" scoped>
.goods-list{
  li{
    height: 346px;
    position: relative;
    background: #fff;
    margin: 15px 10px 0 5px;
    display: inline-block;
    width: calc(100% / 5 - 15px);
    list-style: none;
    padding: 2px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    &:nth-child(5n){
      margin-right: 0;
    }
    .name {
        color: #4a4a4a;
        font-size: 16px;
    }
    .address {
        color: #9B9B9B;
        font-size: 12px;
    }
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
    }
    a {
      display: block;
    }
  }
}
</style>