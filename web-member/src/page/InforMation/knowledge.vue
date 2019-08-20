<template>
<div>
    <detail :dataList="list"></detail>
    <div class="tc pt20">
        <a v-if="list.length > 0" href="/51index/knowledgeList?flag=3">
            <Button type="default" class="mt20" style="width:200px;">更多</Button>
        </a>
    </div>
</div>
    <!-- <Row dis-hover>
        <Col span="13">
            <div class="new-last">
                <detail :dataList="list" :small="true"></detail>
                    <a v-if="list.length > 0">
                        <Button type="default" class="new-more mt20" @click="knowledge()">更多</Button>
                    </a>
                </ul>
            </div>
        </Col>
        <Col span="10" offset="1">
            <Card dis-hover class="mt30">
                <div class="new2">
                    <div class="new-title">
                        <div class="new-text">
                            <h5 class="new-title-h5">简讯</h5>
                        </div>
                    </div>
                    <ul>
                        <template v-for="item in newsList">
                            <li>
                                <router-link :to="{ path: '/InforMation/knowledgeDetail', query: { 'id': item.informationDetailId }}">
                                    <p>{{ item.title }}</p>
                                </router-link>
                            </li>
                        </template>
                    </ul>
                </div>
            </Card>
        </Col>
    </Row> -->
</template>
<script>
import api from '~api'
import detail from '../pro/components/newDetail.vue'
export default {  
    components:{
        detail
    },
    created() {
        this.fetchData()
        // this.newsData()
    },
    data() {
        return {
            currentPage: 1,
            pageSize: 5,
            total: 0,
            list: [],
            newsList: [],
            portal: ''
        }
    },
    methods: {
        // 简讯查询
        // newsData(){
        //     api.post('/member/knowLege/brief-news')
        //     .then(res => {
        //         if(res.code === 200){
        //             this.newsList = res.data
        //         }
        //     })
        // },

        fetchData: function() {
            this.list = []
            api.get('/member/knowLege/findKnowLedge/' + this.currentPage + "?pageSize=" + this.pageSize)
                .then(response => {
                    if(response.code === 200){
                        this.list = response.data.list
                        this.list.map(function(item){
                            item.createTime = item.createTime.split(" ")[0]
                            if(item.columnType === '图书'){
                                item.isSrc = `/InforMation/bookBlurb?id=${item.id}&informationDetailId=${item.informationDetailId}&book_type=knowledge`
                            }else{
                                item.isSrc = `/InforMation/knowledgeDetail?id=${item.informationDetailId}`
                            }
                        })
                    }
                }).catch(function(error) {
                   
                })
        }
    }
}
</script>