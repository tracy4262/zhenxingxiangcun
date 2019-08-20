<template>
<div>
    <detail :dataList="list"></detail>
    <div class="tc pt20">
        <a v-if="list.length > 0" href="/51index/inforMationList?flag=1">
            <Button type="default" class="mt20" style="width:200px;">更多</Button>
        </a>
    </div>
</div>
    <!-- <Row dis-hover>
        <Col span="13">
            <div class="new-last">
                <detail :dataList="list" :small="true"></detail>
                <a v-if="list.length > 0" href="/51index/inforMationList?flag=1">
                    <Button type="default" class="new-more mt20">更多</Button>
                </a>
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
                                <router-link :to="{ path: '/InforMation/findInforMationDetail', query: { 'id': item.informationDetailId }}">
                                    <p class="news-text-2">{{ item.title }}</p>
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
import detail from '../51index/components/newDetail.vue'
export default {
    components:{
        detail
    },
    created() {
        this.fetchData()
    },
    data() {
        return {
            currentPage: 1,
            pageSize: 5,
            total: 0,
            list: [],
            newsList: [],
            content: '',
            portal: '',
            value13:'',
            jubaoDetail:''
        }
    },
    methods: {
        // 简讯查询
        // newsData(){
        //     api.post('/member/inforMation/brief-news')
        //     .then(res => {
        //         if(res.code === 200){
        //             console.log(res)
        //             this.newsList = res.data
        //         }
        //     })
        // },

        fetchData: function() {
            this.list = []
            this.url = '/member/inforMation/findInforMation/' + this.currentPage + "?pageSize=" + this.pageSize
            this.$api.get(this.url)
                .then(response => {                    
                    this.list = response.data.list
                    this.list.map(function(item){
                        item.createTime = item.createTime.split(" ")[0];
                        if(item.columnType=="图书"){
                            item.isSrc = `/InforMation/bookBlurb?id=${item.id}&informationDetailId=${item.informationDetailId}&book_type=information`
                        }else{
                            item.isSrc = `/InforMation/findInforMationDetail?id=${item.informationDetailId}`
                        }
                        if(item.commentNum === undefined){
                            item.commentNum = 0
                        }
                    })
                })
        }
    }
}
</script>
<style lang="scss">
    .detail{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        line-height: 26px;
    }
    .zx-title {
    font-size: 24px;
    margin-bottom: 15px;
    margin-top: 30px;
    color: #333;
}
.zx-time {
    margin-bottom: 30px;
    >span{margin-right:30px;font-size:14px;color:#999}
}
.zx-favorite {
    float: right;
    font-size: 14px;
}
</style>