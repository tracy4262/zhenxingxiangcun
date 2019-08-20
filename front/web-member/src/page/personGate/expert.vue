<template>
    <div style="background:#F8F8F8">
        <img src="../../img/com-banner6.jpg" height="400" width="100%" alt="">
        <div class="layouts">
            <expert
            :title="{cn: '专家团队', en: 'Team'}"
            @on-page-change="nextPage"
            :data="teamData"
            :page="page"></expert>
        </div>
    </div>
</template>
<script>
import api from '~api'
import expert from './components/expert'
import { navStatus } from '~page/companyGate/mixins/commonMixins'
export default {
    mixins: [navStatus],
    components:{
        expert
    },
    data () {
        return {
            index: 6,
            page: {
                show: true,
                current: 1,
                total: 0,
                pageSize: 8
            },
            teamData: [],
            loginAccount:''
        }
    },
    created(){
        this.loginAccount = this.$route.query.uid
        this.getData()
    },
    methods:{
        // 获取数据
        getData(){
            api.post('/portal/expert/listExpert',{
                account: this.loginAccount,
                pageNum: this.page.current,
                pageSize: this.page.pageSize
            })
            .then(res => {
                if(res.code === 200){
                    this.teamData = res.data
                    this.teamData.map(function(item){
                        item.name = item.expertName
                        item.job = item.expertType
                        item.detail = item.adeptField
                        item.src = item.personalPicture
                        item.url =  `/expertGate/index?uid=${item.loginAccount}`
                    })
                    this.page.total = res.total
                }

            }).catch(error => {
                this.$Message.error('操作异常！')
            })
        },

        // 分页事件
        nextPage(val) {
            this.page.current = val
            this.getData()
        }
    }
}
</script>
<style lang="scss">
</style>
