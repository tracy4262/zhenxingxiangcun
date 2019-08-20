<template>
    <div>
        <search @on-search="handleSearch" ref="search" :searchStatus="searchStatus" :tradingStatuss="tradingStatuss" isSeller></search>
        <order-contents :datas="data" :pages="pages" @on-change="handleChangePage"></order-contents>
    </div>
</template>
<script>
import search from './search'
import orderContents from './soldOrderContents'
    export default {
        components: {
            search,
            orderContents
        },
        props: {
            data: {
                type:Array,
                default: ()=>{
                    return []
                }
            },
            searchStatus: {
                type: Boolean,
                default: false
            },
            pages: {
                type: Object,
                default: ()=>{
                    return {}
                }
            }
        },
        data () {
            return {
                tradingStatuss: [
                    {label: '待买家付款', value: 6},
                    {label: '待发货', value: 1},
                    {label: '已发货', value: 2},
                    {label: '待评价', value: 3},
                    {label: '成功的订单', value: 4},
                    {label: '关闭的订单', value: 5},
                    {label: '退货/退款', value: 7},
                    // {label: '等待发货', value: 1},
                    // {label: '已发货', value: 2},
                    // {label: '等待评价', value: 3},
                    // {label: '成功的订单', value: 4},
                    // {label: '关闭的订单', value: 5},
                    // {label: '申请取消的订单', value: 6},
                    // {label: '申请退货的订单', value: 7}
                ]
            }
        },
        methods: {
            // 父页面初始化搜索
            hendleInitSearch () {
                this.$refs['search'].handleSearch()
            },
            handleSearch (e) {
                console.log('9877', e)
                this.$emit('on-search', e)
            },
            handleChangePage (e) {
                this.$emit('on-change', e)
            }
        }
    }
</script>
