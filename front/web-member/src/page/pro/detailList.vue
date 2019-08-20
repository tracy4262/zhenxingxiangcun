<template>
    <div class="new-last">
        <!-- <ul id="box">
            <li v-for="item in dataList">
                <Row type="flex" align="middle">
                    <Col span="19">
                        <router-link :to="{ path: '../InforMation/findInforMationDetail', query: { 'id': item.informationDetailId }}"><p class="h4 ell mb20">{{ item.title }}</p></router-link>
                        <Row>
                            <Col span="20">
                                <Row type="flex" align="middle">
                                  <Col span="3">
                                    <Tag type="border" color="primary">{{item.docType}}</Tag>
                                  </Col>
                                  <Col span="17">
                                    <p class="ell pr10">{{item.label}}</p>
                                  </Col>
                                  <Col span="4">
                                    <span class="main-time">{{item.day}}前</span>
                                  </Col>
                                </Row>
                            </Col>
                            <Col span="4" class="tr">
                                <Icon type="ios-chatbubble-outline" style="font-size: 22px">
                                </Icon>
                                <span>{{item.commentNum}}</span>
                            </Col>
                        </Row>
                    </Col>
                    <Col span="4" offset="1">
                        <div  class="news-img" ><img src="../../../img/news-img.png" alt="" width="100%"></div>
                    </Col>
                </Row>
            </li>
        </ul> -->
    </div>
</template>
<script>
    export default {
        data() {
            return {};
        },
        props: {
            dataList: {}
        },
        created() {

        },
        methods: {}
    };
</script>
<style scoped>
    .news-img {
        text-align: right;
    }

    .new-last li {
        border-bottom: 1px solid #ccc;
        padding: 5px;
    }

    .new-last {

    a {
        color: #666;
    }

    .ivu-tag {
        margin: 0;
    }

    .main-time,
    .label {
        display: inline-block;
        vertical-align: middle;
        padding: 0 8px;
        height: 24px;
        line-height: 24px;
    }

    }
</style>