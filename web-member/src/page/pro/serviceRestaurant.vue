<template>
    <div>
        <div class="perService" v-if="serverData.length > 0">
            <Row>
                <div class="mt20 mr20" style="width: 18%;float:left;" v-for="(item,index) in serverData" :key="index">
                    <server-item :item="item" isIndex></server-item>
                </div>
            </Row>
            <Page class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
        </div>
        <div class="ma-polic-img" v-if="serverData.length === 0">
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import serverItem from './components/server-item'
export default {
    name: 'person-service-restaurant',
    components: {
        serverItem
    },
    data () {
        return {
            serverData: [],
            total: 0,
            pageSize: 10,
            current: 1
        }
    },
    created () {
        this.handleInit(1)
    },
    methods: {
        handleInit (e) {
            this.$api.post('/member/fishing/findProductServiceList', {
                account: '',
                service_name: '',
                pageNum: e,
                pageSize: this.pageSize,
                type: '3' // 0垂钓 1采摘 2景区 3餐饮 4住宿
            }).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.list
                    this.total = response.data.total
                    console.log('serviceData', this.serverData)
                }
            })
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit(page)
        }
    }
}
</script>
<style lang="scss">

</style>
