<template>
    <div class="layouts">
        <div class="wrapper">
            <serviceSeach @on-search="onSearch" ></serviceSeach>
        </div>
        <serviceSort activeName="餐饮" @on-search="onSort"></serviceSort>
        <div class="perService" v-if="serverData.length > 0">
            <Row>
                <div class="mt20 mr20" style="width: 18%;float:left;" v-for="(item,index) in serverData" :key="index">
                    <server-item :item="item" isIndex></server-item>
                </div>
            </Row>
            <Page class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
        </div>
        <div class="ma-polic-img tc" v-if="serverData.length === 0">
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import serverItem from './components/server-item'
import serviceSort from './components/serviceSort'
import serviceSeach from './components/serviceSeach'
export default {
    name: 'person-service-picking',
    components: {
        serverItem,
        serviceSort,
        serviceSeach
    },
    data () {
        return {
            serverData: [],
            total: 0,
            pageSize: 10,
            current: 1,
            list: {
                account: '',
                type: '3', // 0垂钓 1采摘 2景区 3餐饮 4住宿
                default: '1',
                address: '',
                species: '',
                speciesId: '',
                industry: '',
                industryId: '',
                service_name:''
            }
        }
    },
    created () {
        this.handleInit(1)
    },
    methods: {
        handleInit (e) {
            this.list.pageSize = this.pageSize
            this.list.pageNum = this.current
            // this.list.service_name = this.$parent.info.service_name
            // this.list.address = this.$parent.info.address
            // this.list.species = this.$parent.info.species
            // this.list.industry = this.$parent.info.industry
            this.$api.post('/member/fishing/findProductServiceList', this.list).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.dataList
                    this.total = response.data.total
                }
            })
        },
        onSort (e, dataName) {
            console.log(e)
            console.log(dataName)
            this.current = 1
            this.list = {
                account: '',
                type: '3', // 0垂钓 1采摘 2景区 3餐饮 4住宿
                address: this.list.address,
                species: this.list.species,
                speciesId: this.list.speciesId,
                industry: this.list.industry,
                industryId: this.list.industryId,
                service_name: this.list.service_name,
            }
            if (e.name == '价格') {
                this.list.timePriceFlag = e.asc
            } else if (e.name == '好评率') {
                this.list.gradeFlag = e.asc
            } else if (e.name == '综合排序') {
                this.list.default = '1'
            }
            this.handleInit()
        },
        onSearch (info) {
            this.list = Object.assign(this.list, info)
            this.handleChangePage(1)
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit()
        }
    }
}
</script>
<style lang="scss">

</style>
