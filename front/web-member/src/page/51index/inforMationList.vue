<template>
    <div>
        <div class="layouts">
            <Row>
                <Col span="18" class="left_grid">
                <search @searchInMa="searchInMa" :option="filterOpt" :count="total"/>
                    <div v-if="dataList.length>0 && isShow">
                        <detail :dataList="dataList" v-if="dataList.length>0"></detail>
                        <div class="fenye tc mt30 mb50">
                            <Page :total="total" :page-size="pageSize"  :current='currentPage'  @on-change="nextPage" ></Page>
                        </div>
                    </div>
                    <div v-if="dataList.length == 0 && isShow" class="tc pt30 pb50">
                        <img src="../../img/no-content.png"> 
                        <p style="margin-top: 10px;">暂无相关资讯</p>
                    </div>
                    &nbsp;
                </Col>
                <Col span="4" class="right_grid">
                    <div>
                        <h3 class="ma_infor_h">最新政策</h3>
                        <recommend :newData="policyData"></recommend> 
                    </div>

                    <div>
                        <h3 class="ma_infor_h mt50">最新知识</h3>
                        <recommend :newData="knowledgeData"></recommend>
                    </div>
                </Col>
            </Row>
        </div>
    </div>
</template>
<script>
import search from './components/head'
import detail from './components/detail.vue'
import recommend from './components/recommend.vue'
export default {
    name:'inforMationList',
    components:{
        search,
        detail,
        recommend
    },
    created() {
        // 无忧导航中 点击资讯类别的搜索带过来的关键字搜索
        if (this.$route.query.title !== undefined && this.$route.query.title !== '') {
            this.isObj.keywrod = this.$route.query.title;
            this.filterOpt.key = this.$route.query.title;
        }
        if (this.$route.query.id){
            this.isObj.docType = this.$route.query.id;
        }
        this.searchGet(1);
        this.getPolicyData();
        this.getKnowledgeData();
    },
    data() {
        return {
            activeItem:true,
            currentPage: 1,
            pageSize: 10,
            total: 0,
            allTotal: null,
            dataList: [],
            portal :'',
            filterOpt:{
                unit:false,
                wordSize:false,
                corpType: false,
                govLevel: false,
				expertType: false,
                adeptField: false,
                key: '',
                // 类型
                articleType :'information'
            },

            // 最新政策
            policyData: [],

            // 最新知识
            knowledgeData: [],

            //搜索详情数据 
            isObj: {
                regionDatas: '',
                industryDatas: '',
                informationDatas: '',
                productDatas: '',
                serviceDatas: '',
                speciesDatas: '',
                fileType: '',
                keywrod: '',
                docType: ''
            },
            isShow:false
        }
    },
    beforeCreate(){
    },
    methods: {
        // 获取推荐 政策
        getPolicyData(){
            let that = this;
            this.$api.get('/member/policy/newpolicy')
            .then(res => {
                if(res.code === 200){
                    if(res.data === undefined){
                        this.policyData = [];
                    }else{
                        this.policyData = res.data;
                        this.policyData.map(function(item){
                            item.createTime = item.createTime.split(" ")[0];
                            if(item.columnType === '图书'){
                                item.isSrc = `/InforMation/bookBlurb?id=${item.id}&informationDetailId=${item.informationDetailId}&book_type=policy`
                            }else{
                                item.isSrc = `/InforMation/policyDetail?id=${item.informationDetailId}`
                            }
                        })
                    }
                }
            }).catch(error => {
                console.error(error);
            })
        },

        // 获取推荐 知识
        getKnowledgeData(){
            let that = this;
            this.$api.get('/member/knowLege/newknowledge')
            .then(res => {
                if(res.code === 200){
                    this.knowledgeData = res.data;
                    this.knowledgeData.map(function(item){
                        item.createTime = item.createTime.split(" ")[0];
                        if(item.columnType === '图书'){
                            item.isSrc = `/InforMation/bookBlurb?id=${item.id}&informationDetailId=${item.informationDetailId}&book_type=knowledge`
                        }else{
                            item.isSrc = `/InforMation/knowledgeDetail?id=${item.informationDetailId}`
                        }
                    })
                    
                }
            }).catch(error => {
                console.error(error);
            })
        },

        // 搜索
        searchGet(currentPage){
            console.log('/member/inforMation/findInforMationTitle/'+ this.currentPage + '?district='+ this.isObj.regionDatas + '&industry=' + this.isObj.industryDatas + '&type=' + this.isObj.informationDatas + '&goodname=' + this.isObj.productDatas + '&servicename=' + this.isObj.serviceDatas + '&species=' + this.isObj.speciesDatas + '&leixin=' + this.isObj.fileType + '&title=' + this.isObj.keywrod)
            this.$api.get('/member/inforMation/findInforMationTitle/'+ currentPage + '?district='+ this.isObj.regionDatas + '&industry=' + this.isObj.industryDatas + '&type=' + this.isObj.informationDatas + '&goodname=' + this.isObj.productDatas + '&servicename=' + this.isObj.serviceDatas + '&species=' + this.isObj.speciesDatas + '&leixin=' + this.isObj.fileType + '&title=' + this.isObj.keywrod + '&docType=' + this.isObj.docType)
            .then(res => {
                if(res.code === 200){
                    this.isShow = true
                    this.dataList = res.data.list
                    this.dataList.map(function(item){
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
                    this.allTotal = res.data.total;
                    this.total = res.data.total;
                }
            }).catch(error => {
                console.error(error);
            })
        },

        // 搜索栏搜索
        searchInMa(e){
            console.log(e);
            if(e.regionDatas !== []){
                e.regionDatas = e.regionDatas.join('/');
            }else{
                e.regionDatas = '';
            }

            if(e.industryDatas !== []){
                e.industryDatas = e.industryDatas.join(' ');
            }else{
                e.industryDatas = '';
            }

            /*if(e.informationDatas !== []){
                e.informationDatas = e.informationDatas.join('/')
            }else{
                e.informationDatas = ''
            }*/

            if(e.productDatas !== []){
                e.productDatas = e.productDatas.join(' ');
            }else{
                e.productDatas = '';
            }

            if(e.serviceDatas !== []){
                e.serviceDatas = e.serviceDatas.join(' ');
            }else{
                e.serviceDatas = '';
            }

            if(e.speciesDatas !== []){
                e.speciesDatas = e.speciesDatas.join(' ');
            }else{
                e.speciesDatas = '';
            }

            if(e.keywrod !== undefined){
                e.keywrod = e.keywrod;
            }else{
                e.keywrod = '';
            }
            e.docType =  this.isObj.docType;
            this.isObj = e;
            this.searchGet(1);
        },

        navClick(index){
            window.sessionStorage.setItem('newsNav',index);
        },

        nextPage(val) {
            this.currentPage = val;
            this.searchGet(this.currentPage);
        }
    }
}
</script>
<style lang="scss" scoped>
    .ma_infor_h{
        display: block;
        border-left: 8px solid #00c587;
        height: 25px;
        line-height: 25px;
        font-size: 18px;
        font-weight: bold;
        padding-left: 10px;
    }
    .news-img {
        text-align: right;
    }
    .new-last li {
        border-bottom: 1px solid #ccc;
        padding: 15px 5px;
    }
    .new-last{
        a {
            color: #666;
        }
        .ivu-tag{
            margin:0;
        }
        .main-time,
        .label{
            display:inline-block;
            vertical-align:middle;
            padding: 0 8px;
            height: 24px; 
            line-height: 24px;
        }
    }
    .right_grid{
        width: 22%;
        background: #FDFDFD;
        border: 1px solid rgba(232,232,232,1);
        padding: 40px 18px 10px;
        margin: 36px 0 15px 20px;
    }
    .left_grid{
        padding: 0 20px 20px;
        margin: 90px 0 20px;
        border: 1px solid rgba(232,232,232,1);
    }
</style>