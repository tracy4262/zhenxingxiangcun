<template>
    <div>
        <div class="perService" v-if="serverData.length > 0">
            <Row>
                <div class="mt20 mr20" style="width: 18%;float:left;" v-for="(item,index) in serverData" :key="index">
                    <server-item :item="item"></server-item>
                </div>
            </Row>
            <div style="height: 20px;"></div>	
        </div>
        <div class="ma-polic-img" v-if="serverData.length === 0">
            <img src="../../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import serverItem from './server-item'
export default {
    name: 'person-service-list',
    components: {
        serverItem
    },
    data () {
        return {
            serverData: []
        }
    },
    created () {
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.$api.post('/member/fishing/findProductServiceList', {
                account: this.$route.query.uid,
                service_name: '',
                pageNum: 1,
                pageSize: 10,
                type: '' // 0垂钓 1采摘 2景区 3餐饮 4住宿 空为全部
            }).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.list.splice(0, 5)
                }
            })
        }
    }
}
</script>
<style lang="scss">

</style>
