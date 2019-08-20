<template>
    <div>
        <div class="expert_top">
            <search :option="filterOpt" @titleSearch="search" @searchInMa="search" :count="total"/>
        </div>
        <div class="layouts">
            <div v-if="experts.length > 0 && isShow">
                <Row :gutter="16" style="margin-bottom: 20px">
                    <Col span="6" class="pt20" v-for="(item,index) in experts" :key="index">
                    <Card :padding="0">
                        <router-link :to="{path:'../expertGate/index',query: {uid: item.loginAccount}}"
                                     class="block pd10">
                            <img v-if="item.avatar" :src="item.avatar" alt="" width="100%" height="220">
                            <img v-else src="../../img/default_header.png" alt="" width="100%" height="220">
                        </router-link>
                        <div class="info pd20">
                            <div class="expert-info-ivu-list">
                                <span class="h4">{{item.displayName}}</span>
                                <p class="pt10 mb10 detail ell-2" :title="item.adeptField">{{item.adeptField}}</p>
                            </div>
                            <divider solid class="mt10 mb10"/>
                            <router-link :to="{path:'../expertGate/index',query: {uid: item.loginAccount}}">
                                <div style="text-align:center">
                                    <Button type="default">更多信息
                                        <Icon type="ios-arrow-right"></Icon>
                                    </Button>
                                </div>
                            </router-link>
                        </div>
                    </Card>
                    </Col>
                </Row>
                <div class="tc pt30 pb50">
                    <Page :total='total' :pageSize="pageSize" :current='currentPage' @on-change="nextPage"></Page>
                </div>
            </div>
            <div v-if="experts.length == 0 && isShow" class="tc pt30 pb50">
                <img src="../../img/no-content.png">
                <p style="margin-top: 10px;">暂无相关专家</p>
            </div>
        </div>
    </div>
</template>
<script>
    import search from './components/head';
    import divider from '~components/divider';

    export default {
        components: {
            search,
            divider
        },
        data() {
            return {
                isShow: false,
                experts: [],
                currentPage: 1,
                pageSize: 12,
                total: 0,
                filterOpt: {
                    other: false,
                    infoType: false,
                    corpType: false,
                    govLevel: false,
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
                    adeptField: '',
                    title: ''
                }
            };
        },
        props: ['data'],
        created: function () {
            this.nextPage(1);
        },
        methods: {
            nextPage(page) {
                this.$api.post('/member/expertInfo/findExpertTitle/' + page, this.datas).then(response => {
                    if (response.code === 200) {
                        this.isShow = true;
                        this.experts = response.data.list;
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
                /* let s2 = '';
                if (obj.speciesDatas !== [] && obj.speciesDatas !== undefined) {
                    s2 = obj.speciesDatas.join(' ');
                }
                let s3 = '';
                if (obj.industryDatas !== [] && obj.industryDatas !== undefined) {
                    s3 = obj.industryDatas.join(' ');
                } */
                let s4 = '';
                if (obj.productDatas !== [] && obj.productDatas !== undefined) {
                    s4 = obj.productDatas.join(' ');
                }
                /* let s5 = '';
                if (obj.serviceDatas !== [] && obj.serviceDatas !== undefined) {
                    s5 = obj.serviceDatas.join(' ');
                } */
                /* let s6 = '';
                if (obj.adeptField !== [] && obj.adeptField !== undefined) {
                    s6 = obj.adeptField.join(' ');
                } */
                let data = {
                    district: s,
                    species: obj.speci,
                    industry: obj.trade,
                    goodname: s4,
                    servicename: obj.service,
                    type: obj.expertType,
                    adeptField: obj.adeptField,
                    title: obj.keywrod
                };
                this.datas = data;
                console.log('data', data)
                this.nextPage(1)
            }
        }
    };
</script>
<style scoped>
    /* 列表样式 */
    .expert-info-ivu-list {
        height: 85px;
    }

    .expert-info-ivu-list .h4 {
        display: inherit !important;
        text-align: center;
        font-size: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
    }

    .expert-info-ivu-list p {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .expert_top{
        margin: 60px 40px 40px;
    }
</style>