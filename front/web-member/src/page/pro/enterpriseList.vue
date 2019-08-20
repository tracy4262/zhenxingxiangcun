<template>
    <div>
        <headNav>
        </headNav>
        <search :option="filterOpt" @titleSearch="search" @searchInMa="search"/>
        <div class="qiye mt10">
            <div class="layouts">
                <div v-if="enterprise.length != 0 && isShow">
                    <Row :gutter="16" style="margin-bottom: 20px">
                        <Col span="6" class="pt20" v-for="(item,index) in enterprise" :key="index">
                        <Card :padding="0">
                            <router-link :to="{path:'../companyGate/index',query: {uid: item.loginAccount}}"
                                         class="block pd10">
                                <img v-if="item.logoUrl" :src="item.logoUrl" alt="" width="100%" height="220">
                                <img v-else src="../../img/default_header.png" alt="" width="100%" height="220">
                            </router-link>
                            <div class="pd20">
                                <div class="gov-info-ivu-list">
                                    <span class="h4">{{item.corpName}}</span>
                                </div>
                                <divider solid class="mt10 mb10"/>
                                <router-link :to="{path:'../companyGate/index',query: {uid: item.loginAccount}}">
                                    <div style="text-align:center">
                                        <Button type="default">更多信息
                                            <Icon type="ios-arrow-right"></Icon>
                                        </Button>
                                    </div>
                                </router-link>
                            </div>
                        </Card>
                        <!-- <a :href="'/companyGate/index?uid='+e.loginAccount">
                            <img :src="e.logoUrl" alt="" width="100%" height="200px">
                            <p class="ell mt5" :title="e.corpName">{{e.corpName}}</p>
                        </a> -->
                        </Col>
                    </Row>
                    <div class="fenye tc pt30 pb50">
                        <Page :total='total' :pageSize="pageSize" :current='currentPage' @on-change="nextPage"></Page>
                    </div>
                </div>
                <div v-if="enterprise.length == 0 && isShow" class="tc pt30 pb50">
                    <img src="../../img/no-content.png">
                    <p style="margin-top: 10px;">暂无相关企业</p>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import search from './head';
    import headNav from './components/headNav.vue';
    import divider from '~components/divider';

    export default {
        components: {
            headNav,
            search,
            divider
        },
        data() {
            return {
                isShow: false,
                enterprise: [],
                currentPage: 1,
                pageSize: 12,
                total: 0,
                filterOpt: {
                    other: false,
                    infoType: false,
                    govLevel: false,
                    expertType: false,
                    adeptField: false,
                    unit: false,
                    wordSize: false,
                    type: false,
                    key: ''
                },
                datas: {
                    district: '',
                    species: '',
                    industry: '',
                    goodname: '',
                    servicename: '',
                    type: '',
                    title: ''
                }
            };
        },
        props: ['data'],
        created() {
            this.nextPage(1);
        },
        methods: {
            nextPage(page) {
                this.$api.post('/member/corpInfo/findCorpInfoTitle/' + page, this.datas).then(response => {
                    if (response.code === 200) {
                        this.isShow = true;
                        this.enterprise = response.data.list;
                        this.total = response.data.total;
                    }
                }).catch(error => {
                    this.$Message.error('操作异常！');
                });
            },
            search(obj) {
                let s = '';
                if (obj.regionDatas !== '' && obj.regionDatas !== undefined) {
                    s = obj.regionDatas.join('/');
                }
                let s2 = '';
                if (obj.speciesDatas !== [] && obj.speciesDatas !== undefined) {
                    s2 = obj.speciesDatas.join(' ');
                }
                let s3 = '';
                if (obj.industryDatas !== [] && obj.industryDatas !== undefined) {
                    s3 = obj.industryDatas.join(' ');
                }
                let s4 = '';
                if (obj.productDatas !== [] && obj.productDatas !== undefined) {
                    s4 = obj.productDatas.join(' ');
                }
                let s5 = '';
                if (obj.serviceDatas !== [] && obj.serviceDatas !== undefined) {
                    s5 = obj.serviceDatas.join(' ');
                }
                let data = {
                    district: s,
                    species: s2,
                    industry: s3,
                    goodname: s4,
                    servicename: s5,
                    type: obj.corpType,
                    title: obj.keywrod
                };
                this.datas = data;
                this.nextPage(1)
            }
        }
    };
</script>
<style scoped>
    /*企业样式开始  */

    .main-nav ul li a {
        float: left;
        color: #666;
        font-size: 16px;
        margin-right: 0px;
        line-height: 20px;
    }
</style>