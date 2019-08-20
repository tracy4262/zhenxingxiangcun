<template>
    <div>
        <div class="information-new-standard-list pb20 pt20"  v-for="(item, index) in standardList" :key="index">
            <Row type="flex" align="middle">
                <Col span="22">
                    <Row class="time">
                        <Col span="2" class="tc">
                            <div class="standard-status tc" :class="{'standard-status-green' : item.standardStatus == '现行', 'standard-status-grey' : item.standardStatus !== '现行'}">
                                <span v-if="item.standardStatus == '现行'">{{item.standardStatus}}</span>
                                <span v-else>即将</span>
                            </div>
                        </Col>
                        <Col span="22" class="standard-title">
                            <Row class="list-title pb10">
                                <Col style="color: rgba(0,0,0,0.65);" span="5" :title="item.standardNumber">【{{item.standardNumber}}】</Col>
                                <Col class="ell standard-list-title" span="19" :title="item.chineseStandardName" @click="goToDetail(item.standardDetailId)">
                                    {{item.chineseStandardName}}
                                </Col>
                            </Row>
                            <Row class="time">
                                <Col span="5" class="t-grey"><span class="pd10">发布日期：{{item.createTime}}</span></Col>
                                <Col span="19" style="color:#F24D61;" v-if="item.standardTrait == '强制性标准'">{{item.standardTrait}}</Col>
                                <Col span="19" style="color:#4a4a4a;" v-else>{{item.standardTrait}}</Col>
                            </Row>
                        </Col>
                    </Row>
                </Col>
                <Col span="2" class="tc">
                    <Icon type="ios-arrow-dropright arrow" size="26"  @click="goToDetail(item.standardDetailId)"/>
                </Col>
            </Row>
        </div>
        <div class="tc pt20">
            <Button type="default" class="mt20" @click="standards()" style="width:200px;">更多</Button>
        </div>
        <!-- <div v-for="(item, index) in standardList" :key="index" class="standard-list">
            <Row style="height: 30px; vertical-align: middle; font-family: PingFang SC; font-size: 14px;">
                <Col span="6" class="ell" :title="item.standardNumber">{{ item.standardNumber }}</Col>
                <Col span="12" style="text-align: left;"><a href="javascript:void(0);" class="ell" :title="item.chineseStandardName" @click="goToDetail(item.standardDetailId)" style="margin-left: 5px;">{{ item.chineseStandardName }}</a></Col>
                <Col span="6" style="text-align: right;">{{ item.createTime }}</Col>
            </Row>
            <Row style="height: 30px; vertical-align: middle; line-height: 30px;">
                <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked" 
                    :style="item.standardTrait === '强制性标准' ? 'border-width: 1px; border-style: solid; border-color: #FF7921;' : 'border-width: 1px; border-style: solid; border-color: #F5A623;'">
                    <span class="ivu-tag-text" :style="item.standardTrait === '强制性标准' ? 'color: #FF7921;' : 'color: #F5A623'">{{ item.standardTrait }}</span>
                </div>
                <div class="ivu-tag ivu-tag-border-custom ivu-tag-checked" 
                    :style="item.standardStatus === '现行' ? 'border-width: 1px; border-style: solid; border-color: #4AB344;' : 'border-width: 1px; border-style: solid; border-color: #9B9B9B;'">
                    <span class="ivu-tag-text" :style="item.standardStatus === '现行' ? 'color: #4AB344;' : 'color: #9B9B9B'">{{ item.standardStatus }}</span>
                </div>
            </Row>
        </div> -->
        <!-- <Button type="default" class="new-more mt20" @click="standards()" long>更多</Button> -->
	</div>
</template>
<script>
    export default {
        name: 'standardInGuide',
        components: {
        },
        data() {
            return {
                standardList: [],
                pageSize: 5,
                pageNum: 1,
            }
        },
        created () {
            this.init()
        },
        methods: {
            init () {
                this.$api.post('/member/standard/getNswyNavigation', {pageSize: 5}).then(response => {
                    if (response.code === 200) {
                        this.standardList = response.data.list
                    }
                }).catch(error => {
                    console.log('error', error)
                })
            },
            standards () {
                this.$router.push('/51index/standardList')
            },
            goToDetail (id) {
                this.$router.push({
                    path: '/inforMation/standardDetail',
                    query: {
                        id: id,
                        status: 2
                    }
                })
            }
        }
    }
</script>
<style  lang="scss" scoped>
.information-new-standard-list{
    border-bottom: 1px solid#E8E8E8;
    &:hover{
        .arrow{
            color:#00C587;
        }
    }
    .standard-status{
        border-radius: 4px;
        width:48px;
        height:48px;
        color: #fff;
        vertical-align: middle;
        display: inline-block;
        line-height: 48px;
    }
    .standard-status-green {
        background:#00C587;
    }
    .standard-status-grey {
        background:#9B9B9B;
    }
    .standard-title {
        display: inline-block;
        vertical-align: middle;
        .list-title{
            font-size:16px;
            .standard-list-title{
                cursor: pointer;
                color:rgba(74,74,74,1);
                &:hover{
                    color:rgba(74,74,74,0.85);
                }
            }
        }
        .time{
            font-size: 12px;
        }
    }

}
    .ivu-tag-border-custom {
        height: 24px;
        line-height: 24px;
        /* border: 1px solid #e9eaec!important; */
        color: #657180!important;
        background: #fff!important;
        position: relative;
    }
    .standard-list {
        padding: 10px;
        border: 1px solid #F6F6F6;
        margin-top: 10px;
    }
</style>