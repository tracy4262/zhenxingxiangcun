<template>
    <div class="mb50">
        <div class="tc mb50">
            <RadioGroup v-model="knowledgeTab" type="button" class="mt30 mb20" @on-change="handleTabChange">
                <Radio v-for="(item,index) in tab" :key="index" :label="item"></Radio>
            </RadioGroup>
        </div>
        <div v-if="data.length > 0">
            <div class="com-dynamic-list">
                <template v-for="(item, index) in data">
                    <Card :key="index">
                        <Row type="flex" align="middle" class="mb10">
                            <Col span="18">
                                <h5>{{item.title}}</h5>
                                <p class="t-grey mt5">{{item.detail}}</p>
                            </Col>
                            <Col span="3" offset="3" class="tc">
                                <p class="t-grey mb5">{{item.date}}</p>
                                <Button type="primary" @click="handleClick(item.id)" shape="circle" icon="chevron-right"></Button>
                            </Col>
                        </Row>
                    </Card>
                    <div style="height: 20px;"></div>
                </template> 
            </div>
            <div class="tc mt30">
                <Page class="country" :total="page.total" :current="page.current" @on-change="handlePageChange" v-if="page.show"></Page>
            </div>
        </div>
        <div class="ma-polic-img" v-if="data.length === 0">
            <img src="../../../img/ma-img-002.png"> 
            <p style="margin-top: 10px;">暂无数据</p>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        title: {
            type: Object,
            default () {
                return {}
            }
        },
        tab: Array,
        data: Array,
        page: {
            type: Object,
            default () {
                return {
                    show: false,
                    current: 1,
                    total: 0
                }
            }
        }
    },
    data () {
        return {
            knowledgeTab: this.tab[0],
        }
    },
    created(){
    },
    methods:{
        // tab事件
        handleTabChange () {
            this.$emit('on-tab-change', this.knowledgeTab)
        },
        // 分页事件
        handlePageChange (page) {
            this.$emit('on-page-change', page)
        },
        // 点击事件
        handleClick (id) {
            this.$router.push({
                path: '/InforMation/knowledgeDetail',
                query: {
                    id: id 
                }
            })
        }
    }
}
</script>
<style lang="scss">
    .com-dynamic-list {
        .ivu-card:hover{
            .ivu-btn{
                // background: #00c587;
                // color:#fff;
                // border-color: #00c587;
                margin-left: 20px;
                transition: margin .3s
            }
        }
    }
    .ma-polic-img{text-align: center;} 
</style>