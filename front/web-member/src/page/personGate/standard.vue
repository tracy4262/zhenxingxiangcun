<template>
    <div class="wrapper ma-perDynamic">
        <img src="../../img/com-banner3.jpg" height="400" width="100%" alt="">
        
        <div class="layouts pt30 pb50">
            <div class="tc mb50 person-tab-theme">
                <RadioGroup v-model="lawsTab" type="button" class="mt30 mb20 " @on-change="handleTabChange">
                    <Radio v-for="(item,index) in tab" :key="index" :label="item"></Radio>
                </RadioGroup>
            </div>
            <div v-if="data.length > 0">
                <div class="com-dynamic-list">
                    <standardList @on-change="handleClick" :data="data"></standardList>
                </div>
                <div class="tc mt30">
                    <Page class="country" :total="total" :page-size="pageSize" @on-change="handlePageChange"></Page>
                </div>
            </div>

            <div class="ma-polic-img" v-if="data.length === 0">
                <img src="../../img/ma-img-002.png"> 
                <p style="margin-top: 10px;">暂无数据</p>
            </div>
        </div>
    </div>
</template>
<script>
import { navStatus } from '~page/companyGate/mixins/commonMixins'
import standardList from './components/standardList'
export default {
    mixins: [navStatus],
    components:{
        standardList
    },
    data () {
        return {
            loginAccount: '',
            index: 5,
            data: [],
            total: 0,
            tab:[],
            lawsTab: '全部',
            pageSize:10
        }
    },
    created () {
        this.loginAccount = this.$route.query.uid
        this.init({
                account: this.loginAccount,
                label: this.lawsTab,
                pageSize: this.pageSize,
                pageNum: 1
            })
        this.getTab({
            loginAccount: this.loginAccount,
            columnName: '标准'
        })
    },
    methods: {
        // 获取tab数据
        getTab(data){
            this.$api.post('/portal/myGate/getLabel', data).then(response => {
                if (response.code === 200) {
                    if(response.data !== undefined){
                        this.tab = response.data.label
                    } 
                }
            }).catch(error => {
                this.$Message.error('操作异常！')
            })
        },
        //初始化获取列表数据
        init (data) {
            this.$api.post('/portal/standard/standard-list', data).then(response => {
                if (response.code === 200) {
                    if(response.data !== undefined){
//                        console.log('动态',response.data)
                        this.data = response.data.list
                        this.total = response.data.total
                    }  
                }
            }).catch(error => {
                this.$Message.error('操作异常！')
            })
        },
        //分页
        handlePageChange (page) {
            this.init({
                account: this.loginAccount,
                label: this.lawsTab,
                pageSize: this.pageSize,
                pageNum: page
            })
        },
        //点击进入详情页
        handleClick (id) {
            this.$router.push({
                path: '/InforMation/standardDetail',
                query: {
                    id: id
                }
            })
        },
        //切换标签
        handleTabChange(){
            this.init({
                account: this.loginAccount,
                label: this.lawsTab
            })
        }
    }
}
</script>
