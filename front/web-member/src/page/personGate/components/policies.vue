<template>
    <div class="mb50">
        <div class="tc mb50">
            <RadioGroup v-model="lawsTab" type="button" class="mt30 mb20" @on-change="handleTabChange">
                <Radio v-for="(item,index) in tab" :key="index" :label="item"></Radio>
            </RadioGroup>
        </div>
        
        <div v-if="data.length > 0">
            <Row :gutter="20" class="com-laws-list mb50" :class="type">
                <template v-if="type === 'text-list'">
                    <Card :key="index" v-for="(item,index) in data" :key="index" class="policies-ma">
                        <Row type="flex" align="middle" class="mb10">
                            <Col span="18">
                                <h5>{{item.title}}</h5>
                                <p class="t-grey mt5">{{item.detail}}</p>
                            </Col>
                            <Col span="3" offset="3" class="tc">
                                <p class="t-grey mb5">{{item.date}}</p>
                                <a :href="item.url"><Button type="primary" shape="circle" icon="chevron-right"></Button></a>
                            </Col>
                        </Row>
                    </Card>
                </template>
                <template v-else>
                    <Col span="6" v-for="(item,index) in data" :key="index">
                        <a :href="item.url" class="item">
                            <img :src="item.src" alt="" width="100%" height="300">
                            <template v-if="type === 'video-list'">
                                <div class="mask">
                                    <Icon type="ios-play"></Icon>
                                </div>
                            </template>
                            <template v-if="type === 'audio-list'">
                                <div class="mask">
                                    <Icon type="android-volume-up"></Icon>
                                </div>
                            </template>
                        </a>
                        <h5 class="mt10 mb5 ell">{{item.title}}</h5>
                        <p class="info t-grey mb30">{{item.detail}}</p>
                    </Col>
                </template>
            </Row>
            <div class="tc mt30">
                <Page class="country" :total="page.total" :current="page.current" @on-change="handlePageChange" v-if="page.show" :page-size="page.pageSize"></Page>
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
                    show: true,
                    current: 1,
                    total: 0,
                    pageSize: 10
                }
            }
        }
    },
    data () {
        return {
            lawsTab: this.tab[0],
            type: 'default-list',
        }
    },
    created(){
    },
    methods:{
        // tab事件
        handleTabChange () {
            if(this.lawsTab === '图文'){
                this.type = 'default-list'
            }else if (this.lawsTab === '视频') {
                this.type = 'video-list'
            }else if (this.lawsTab === '音频') {
                this.type = 'audio-list'
            }else{
                this.type = 'text-list'
            }
            this.$emit('on-tab-change', this.lawsTab)
        },
        // 分页事件
        handlePageChange (val) {
            this.$emit('on-page-change',val)
        }

    }
}
</script>
<style lang="scss">
.ma-polic-img{text-align: center;} 
.policies-ma{margin-top: 10px;}
</style>