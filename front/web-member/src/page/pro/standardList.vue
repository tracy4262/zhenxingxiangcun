<template>
    <div>
        <headNav>
        </headNav>
        <search :option="filterOpt" @titleSearch="search" @searchInMa="search"/>
        <div class="qiye mt10">
            <div class="layouts">
                <div v-if="standardList.length > 0 && isShow">
                    <div v-for="(item, index) in standardList" :key="index" class="standard-list">
                        <Row style="height: 30px; vertical-align: middle; font-family: PingFang SC; font-size: 14px;">
                            <Col span="6" class="ell" :title="item.standardNumber">
                            {{ item.standardNumber }}</Col>
                            <Col span="12" style="text-align: left;">
                            <a href="javascript:void(0);" class="ell" :title="item.chineseStandardName"
                               @click="goToDetail(item.standardDetailId)" style="margin-left: 5px;">{{
                                item.chineseStandardName }}</a></Col>
                            <Col span="6" style="text-align: right;">
                            {{ item.createTime }}</Col>
                        </Row>
                        <Row style="height: 30px; vertical-align: middle; line-height: 30px;">
                            <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked"
                                 :style="item.standardTrait === '强制性标准' ? 'border-width: 1px; border-style: solid; border-color: #FF7921;' : 'border-width: 1px; border-style: solid; border-color: #F5A623;'">
                                <span class="ivu-tag-text"
                                      :style="item.standardTrait === '强制性标准' ? 'color: #FF7921;' : 'color: #F5A623'">{{ item.standardTrait }}</span>
                            </div>
                            <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked"
                                 :style="item.standardStatus === '现行' ? 'border-width: 1px; border-style: solid; border-color: #4AB344;' : 'border-width: 1px; border-style: solid; border-color: #9B9B9B;'">
                                <span class="ivu-tag-text"
                                      :style="item.standardStatus === '现行' ? 'color: #4AB344;' : 'color: #9B9B9B'">{{ item.standardStatus }}</span>
                            </div>
                        </Row>
                    </div>
                    <div class="fenye tc pt30 pb50">
                        <Page :total='total' :current='currentPage' @on-change="nextPage"></Page>
                    </div>
                </div>
                <div v-if="standardList.length == 0 && isShow" class="tc pt30 pb50">
                    <img src="../../img/no-content.png">
                    <p style="margin-top: 10px;">暂无相关标准</p>
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
        name: 'standardListIndex',
        components: {
            headNav,
            search,
            divider
        },
        data() {
            return {
                isShow: false,
                standardList: [],
                total: 0,
                currentPage: 1,
                pageSize: 10,
                pageNum: 1,
                filterOpt: {
                    other: false,
                    govLevel: false,
                    expertType: false,
                    adeptField: false,
                    unit: false,
                    wordSize: false,
                    type: false,
                    corpType: false,
                    ics: true,
                    ccs: true,
                    standardTrait: true,
                    standardStatus: true,
                    publishDate: true,
                    key: '',
                    articleType: 'standard'
                },
                datas: {
                    keyword: '',
                    district: '',
                    species: '',
                    industry: '',
                    goodname: '',
                    servicename: '',
                    infoType: '',
                    ics: '',
                    ccs: '',
                    standardTrait: '',
                    standardStatus: '',
                    publishDate: ''
                }
            };
        },
        created() {
            if (this.$route.query.title !== undefined && this.$route.query.title !== '') {
                this.datas.keywrod = this.$route.query.title;
                this.filterOpt.key = this.$route.query.title;
                this.search(this.datas);
            }
            this.init(this.datas);
        },
        methods: {
            init(data) {
                this.$api.post('/member/standard/getForNswyHome', data).then(response => {
                    console.log('res11', response);
                    if (response.code === 200) {
                        this.isShow = true;
                        this.standardList = response.data.list;
                        this.total = response.data.total;
                    }
                });
            },
            search(obj) {
                this.currentPage = 1;
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
                    keyword: obj.keywrod,
                    district: s,
                    species: s2,
                    industry: s3,
                    goodname: s4,
                    servicename: s5,
                    infoType: obj.informationDatas,
                    ics: obj.ics,
                    ccs: obj.ccs,
                    standardTrait: obj.standardTrait,
                    standardStatus: obj.standardStatus,
                    publishDate: obj.publishDate
                };
                this.datas = data;
                console.log('data', data);
                this.init(data);
            },
            nextPage(page) {
                this.datas.pageNum = page;
                this.currentPage = page;
                console.log('datas', this.datas);
                this.init(this.datas);
            },
            goToDetail(id) {
                this.$router.push({
                    path: '/inforMation/standardDetail',
                    query: {
                        id: id,
                        status: 1
                    }
                });
            }
        }
    };
</script>
<style scoped>
    .ivu-tag-border-custom {
        height: 24px;
        line-height: 24px;
        /* border: 1px solid #e9eaec!important; */
        color: #657180 !important;
        background: #fff !important;
        position: relative;
    }

    .standard-list {
        padding: 10px;
        border: 1px solid #d8d7d7;
        margin-top: 10px;
    }
</style>
