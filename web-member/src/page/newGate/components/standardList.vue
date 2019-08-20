<template>
    <div>
         <div class="information-new-standard-list pb20 pt20"  v-for="(item, index) in data" :key="index">
            <Row type="flex" align="middle" @click.native="goToDetail(item.standardDetailId)">
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
                                <Col class="ell standard-list-title" span="19" :title="item.chineseStandardName" >
                                    {{item.chineseStandardName}}
                                </Col>
                            </Row>
                            <Row class="time">
                                <Col span="5" class="t-grey"><span class="pd10">发布日期：{{moment(item.createTime).format('YYYY-MM-DD')}}</span></Col>
                                <Col span="19" style="color:#F24D61;" v-if="item.standardTrait == '强制性标准'">{{item.standardTrait}}</Col>
                                <Col span="19" style="color:#4a4a4a;" v-else>{{item.standardTrait}}</Col>
                            </Row>
                        </Col>
                    </Row>
                </Col>
                <Col span="2" class="tc">
                    <Icon type="ios-arrow-dropright arrow" size="26" />
                </Col>
            </Row>
        </div>
        <div v-if="data.length == 0">
          <p class="tc pd30">暂无数据</p>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        data: {
            type: Array
        }
    },
    data() {
        return {
        }
    },
    created(){
    },
    methods:{
        goToDetail(id){
            let url = `/inforMation/standardDetail?id=${id}&status=2`
            window.open(url, "_blank")
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