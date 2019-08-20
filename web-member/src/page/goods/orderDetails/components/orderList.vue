<template>
    <div>
        <search @on-search="handleSearch" :searchStatus="searchStatus" ref="search"></search>
        <order-contents :datas="data" :pages="pages" @on-change="handleChangePage"></order-contents>
    </div>
</template>
<script>
import search from './search'
import orderContents from './orderContents'
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
