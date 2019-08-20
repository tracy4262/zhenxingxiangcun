<template>
    <div class="pb50">
        <div v-if="data.length > 0">
            <div class="tc mb50">
                <h5 class="mt30">{{title.cn}}</h5>
                <p class="mt10">{{title.en}}</p>
            </div>
            <Row :gutter="20" class="per-com-team">
                <Col span="6" v-for="(item,index) in data" :key="index"  class="pb20">
                    <Card :padding="0" :bordered="false">
                        <a v-on:click="webimchat(item.userId, item.name, item.src)" >
                            <img v-if="item.src" :src="item.src" alt="" width="100%" height="220">
                            <img v-else src="../../../img/default_header.png" alt="" width="100%" height="220">
                        </a>
                        <div class="info pd20">
                            <div class="expert-info-ivu" >
                                <span class="h4">{{item.name}}</span>
                                <span>{{item.job}}</span>
                                <p class="mt10 mb10">专家电话：{{item.phone}}</p>
                                <p class="mb10 detail">{{item.detail}}</p>
                            </div>
                            <divider solid class="mt10 mb10"/>
                            <a :href="item.url" class="mt10"><Button type="default">更多信息 <Icon type="ios-arrow-right"></Icon></Button></a>
                        </div>
                    </Card>
                </Col>
            </Row>
            <div class="tc mt30">
                <Page class="country" :total="page.total" :current="page.current" @on-change="handlePageChange" :page-size="page.pageSize"  v-if="page.show"></Page>
            </div>
        </div>
        <div class="ma-polic-img" v-if="data.length === 0">
            <img src="../../../img/no-content.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
import divider from '~components/divider'
export default {
     components:{
        divider
    },
    props: {
        title: {
            type: Object,
            default () {
                return {}
            }
        },
        data: Array,
        page: {
            type: Object,
            default () {
                return {
                    show: false,
                    current: 1,
                    total: 0,
                    pageSize:8
                }
            }
        }
    },
    data () {
        return {
        }
    },
    created(){
    },
    methods:{
        // 分页事件
        handlePageChange (page) {
            this.$emit('on-page-change',page)
        },
        webimchat (userId, name, avatar) {
            layui.layim.chat({
                id: userId,
                name: name,
                avatar: avatar,
                type: 'friend'
            });
        }
    }
}
</script>
<style lang="scss">
.per-com-team{
    .ivu-card{
        .info{
            &:hover{
                border-color: #f5a623;
                background: #f5a623;
                color:#fff;
                .ivu-btn{
                    background: #fff;
                    border-color: #fff;
                    color: #f5a623;
                }
            }
        }        
    }
}  
.ma-polic-img{text-align: center;} 
</style>