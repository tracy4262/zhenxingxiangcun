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
                    <list @on-change="handleClick" :data="data"  bookType="information"></list>
                </div>
                <div class="tc mt30">
                    <Page class="country" :total="total" @on-change="handlePageChange"></Page>
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
import list from './components/list'
export default {
    mixins: [navStatus],
    components:{
        list
    },
    data () {
        return {
            loginAccount: '',
            index: 2,
            data: [],
            total: 0,
            tab:[],
            lawsTab: '全部'
        }
    },
    created () {
        this.loginAccount = this.$route.query.uid
        this.init({
            loginAccount: this.loginAccount,
            label: this.lawsTab
        })
        this.getTab({
            loginAccount: this.loginAccount,
            columnName: '动态'
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


        init (data) {
            this.$api.post('/portal/dynamic/getDynamicInfo', data).then(response => {
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
        handlePageChange (page) {
            this.init({
                loginAccount: this.loginAccount,
                label: this.lawsTab,
                pageSize: 10,
                pageNum: page
            })
        },
        handleClick (id) {
            this.$router.push({
                path: '/InforMation/findInforMationDetail',
                query: {
                    id: id
                }
            })
        },
        handleTabChange(){
            this.init({
                loginAccount: this.loginAccount,
                label: this.lawsTab
            })
        }
    }
}
</script>
<style lang="scss">
.ma-polic-img{text-align: center;} 

.ma-perDynamic .person-tab-theme .ivu-radio-group-button .ivu-radio-wrapper-checked, .ma-perDynamic .person-tab-theme .ivu-radio-group-button .ivu-radio-wrapper-checked:active, .ma-perDynamic .person-tab-theme .ivu-radio-group-button .ivu-radio-wrapper-checked:first-child, .ma-perDynamic .person-tab-theme .ivu-radio-group-button .ivu-radio-wrapper-checked:hover{
    border-color: #f5a623; 
    box-shadow: -1px 0 0 0 #f5a623;
    color: #f5a623; 
}
</style>
