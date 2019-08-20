<template>
    <div class="layouts">
        <div class="wrapper">
            <serviceSeach @on-search="onSearch" ></serviceSeach>
        </div>
        <Breadcrumb class="mt30 pl5">
            <BreadcrumbItem to="/51index/serviceList/all">服务首页</BreadcrumbItem>
            <BreadcrumbItem>咨询服务</BreadcrumbItem>
        </Breadcrumb>
        <div class="filter-bar">
            <template v-for="(item, index) in expertTypeData">
                <Button :type="activeIndex === index ? 'primary' : 'text'" @click="change(index)">{{ item.label }}</Button>
            </template>
        </div>
        <div class="perService" v-if="serverData.length > 0">
            <Row>
                <div class="mt20 mr20" style="width: 18%;float:left;" v-for="(item,index) in serverData" :key="index">
                    <consultation-server-item :item="item"></consultation-server-item>
                </div>
            </Row>
            <Page class="mt30 tc pb50" :page-size="pageSize" :total="total" :current="current" @on-change="handleChangePage"></Page>
        </div>
        <div class="ma-polic-img tc mt40" v-if="serverData.length === 0">
            <img src="../../img/ma-img-002.png">
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import consultationServerItem from './components/consultation-server-item'
import serviceSeach from './components/serviceSeach'
export default {
    name: 'person-service-consultation',
    components: {
        serviceSeach,
        consultationServerItem
    },
    data () {
        return {
            serverData: [],
            expertTypeData: [
                {
                    value: '',
                    label: '全部分类'
                },
                {
                    value: '农业',
                    label: '农业'
                },
                {
                    value: '畜牧',
                    label: '畜牧'
                },
                {
                    value: '渔业',
                    label: '渔业'
                },
                {
                    value: '林业',
                    label: '林业'
                },
                {
                    value: '土肥',
                    label: '土肥'
                },
                {
                    value: '饲料',
                    label: '饲料'
                },
                {
                    value: '农机',
                    label: '农机'
                },
                {
                    value: '法律',
                    label: '法律'
                },
                {
                    value: '兽药',
                    label: '兽药'
                },
                {
                    value: '加工',
                    label: '加工'
                },
                {
                    value: '农药',
                    label: '农药'
                },
                {
                    value: '包装',
                    label: '包装'
                },
                {
                    value: '质检',
                    label: '质检'
                },
                {
                    value: '运输',
                    label: '运输'
                },
                {
                    value: '信息传输',
                    label: '信息传输'
                },
                {
                    value: '金融',
                    label: '金融'
                },
                {
                    value: '保险业',
                    label: '保险业'
                }
            ],
            activeIndex: 0,
            total: 0,
            pageSize: 10,
            current: 1,
            list: {
                address: '',
                species: '',
                industry: '',
                serviceName: '',
                expertType: ''
            }
        }
    },
    created () {
        this.handleInit()
    },
    methods: {
        handleInit () {
            this.list.pageSize = this.pageSize
            this.list.pageNum = this.current
            this.$api.post('/member-reversion/consult/serviceList', this.list).then(response => {
                if (response.code === 200) {
                    this.serverData = response.data.list
                    this.total = response.data.total
                }
            })
        },
        onSearch (info) {
            let list = {
                address: info.address,
                species: info.species,
                industry: info.industry,
                serviceName: info.service_name,
                expertType: this.list.expertType
            }
            this.list = list
            this.current = 1
            this.handleInit()
        },
        handleChangePage (page) {
            this.current = page
            this.handleInit()
        },
        change (index) {
            this.activeIndex = index
            this.list.expertType = this.expertTypeData[index].value
            this.current = 1
            this.handleInit()
        }
    }
}
</script>
<style lang="scss" scoped>
.filter-bar{
  margin-top: 20px;
  padding: 10px;
  background: #F9F9F9;
  .ivu-btn{
    margin-right: 10px;
    min-width: 50px;
    padding: 2px 5px;
  }
  .ivu-icon{
    display: block;
    line-height: .5;
    padding-left: 3px;
  }
}
</style>
