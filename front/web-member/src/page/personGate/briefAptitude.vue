<template>
    <div class="pb50 briefHonor">
        <div v-if="honorList.length > 0">
            <Row :gutter="16">
                <Col span="4" v-for="(sub,index) in honorList" :key="index" class="tc mb20">
                    <img :src="sub.image" alt="" height="140" width="100%">
                    <!-- <p>{{sub.columns}}</p> -->
                </Col>
            </Row>
            <Row style="text-align: center;">
                <Page class="country" :total="total" size="small" @on-change="changePage"></Page>
            </Row>
        </div>
        <div class="ma-polic-img" v-if="honorList.length === 0">
            <img src="../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
export default {
    data () {
        return {
            honorList: [],
            total: 0,
            loginAccount: ''
        }
    },
    created(){
        this.loginAccount = this.$route.query.uid
        this.getData({
            loginAccount: this.loginAccount,
            column: '资质'
        })
    },
    methods: {
        // 获取数据
        getData(data){
            this.$api.post('/portal/introduction/honor-aptitude',data)
            .then(res => {
                if(res.code === 200){
                    if(res.data !== undefined){
                        this.honorList = res.data.list
                    }
                }
            }).catch(error => {
                this.$Message.error('操作异常！')
            })
        },
        changePage(val){
            this.getData(
                {
                    loginAccount: this.loginuserinfo.loginAccount,
                    column: '资质',
                    pageSize: 10,
                    pageNum: val
                }
            )
        }
    },
    mounted() {
    }
}
</script>
<style lang="scss">
.ma-polic-img{margin-top: 60px;}
</style>
