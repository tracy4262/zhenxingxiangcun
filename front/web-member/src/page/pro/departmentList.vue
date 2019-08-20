<template>
    <div>
        <headNav>
        </headNav>
        <search :option="filterOpt" @titleSearch="search" @searchInMa="search"/>
        <div>
            <div class="layouts">
                <div class="qiye mt10">
                    <div v-if="depart.length>0 && isShow">
                        <Row :gutter="16" style="margin-bottom: 20px">
                            <Col span="6" class="pt20" v-for="(item,index) in depart" :key="index">
                            <Card :padding="0">
                                <router-link :to="{path:'../govGate/index',query: {uid: item.loginAccount}}"
                                             class="block pd10" v-if="item.userType == 3">
                                    <img v-if="item.logoPictureList" :src="item.logoPictureList" alt="" width="100%"
                                         height="220">
                                    <img v-else src="../../img/default_header.png" alt="" width="100%" height="220">
                                </router-link>
                                 <router-link :to="{path:'../ruralGate/index',query: {uid: item.loginAccount}}"
                                             class="block pd10" v-else>
                                    <img v-if="item.logoPictureList" :src="item.logoPictureList" alt="" width="100%"
                                         height="220">
                                    <img v-else src="../../img/default_header.png" alt="" width="100%" height="220">
                                </router-link>
                                <div class="pd20">
                                    <div class="gov-info-ivu-list">
                                        <span class="h4">{{item.govName}}</span>
                                    </div>
                                    <divider solid class="mt10 mb10"/>
                                    <router-link :to="{path:`../govGate/index`,query: {uid: item.loginAccount}}" v-if="item.userType == 3">
                                        <div style="text-align:center">
                                            <Button type="default">更多信息
                                                <Icon type="ios-arrow-right"></Icon>
                                            </Button>
                                        </div>
                                    </router-link>
                                     <router-link :to="{path:`../ruralGate/index`,query: {uid: item.loginAccount}}" v-else>
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
                        <div class="fenye tc pt30 pb50">
                            <Page :total='total' :page-size="pageSize" :current='currentPage'
                                  @on-change="nextPage"></Page>
                        </div>
                    </div>
                    <div v-if="depart.length == 0 && isShow" class="tc pt30 pb50">
                        <img src="../../img/no-content.png">
                        <p style="margin-top: 10px;">暂无相关机关</p>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>
<script>
    import api from '~api';
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
                depart: [],
                currentPage: 1,
                pageSize: 12,
                filterOpt: {
                    speci: false,
                    trade: false,
                    product: false,
                    service: false,
                    other: false,
                    infoType: false,
                    corpType: false,
                    expertType: false,
                    adeptField: false,
                    unit: false,
                    wordSize: false,
                    type: false,
                    key: ''
                },
                datas: {
                    addr: '',
                    level: '',
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
                this.$api.post('/member/govInfo/findByName/' + page, this.datas).then(response => {
                    if (response.code === 200) {
                        this.isShow = true;
                        this.depart = response.data.list;
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
                let data = {
                    addr: s,
                    level: obj.govLevel,
                    title: obj.keywrod
                };
                this.datas = data;
                this.nextPage(1)
            }
        }
    };
</script>
<style lang="scss">
    .gov-info-ivu-list {
        height: 30px;
        overflow: hidden;
        .h4 {
            display: inherit !important;
            text-align: center;
            font-size: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 1;
        }
    }
</style>