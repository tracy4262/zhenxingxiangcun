<template>
    <div>
        <!-- <h3 class="hd">相关应用</h3>
        <Row>
            <Col span="8">
            <div class="tc pd5">
                <img src="../../../img/xgyy-icon1.png" />
                <p>物种百科</p>
            </div>
            </Col>
            <Col span="8">
            <div class="tc pd5">
                <img src="../../../img/xgyy-icon2.png" />
                <p>物种资讯</p>
            </div>
            </Col>
        </Row>-->

        <div class="relation-title"><span>相关专家</span></div>
        <Row >
            <template v-if="relatedExpertList.length > 0">
                <template v-for="(item,index) in relatedExpertList ">
                    <Col span="8">
                    <div class="tc pd6 mb10 mt10">
                        <a :href="['../expertGate/index?uid='+item.link]"><img width="58px" height="58px":src="item.img" v-if="item.img !== ''" />
                            <img width="58px" height="58px" src="../../../../static/img/user-icon-big.png" v-else/>
                        </a>
                       <p class="ell" :title="item.name">{{item.name | getString}}</p>
                    </div>
                    </Col>
                </template>
            </template>
        </Row>

        <div class="relation-title"><span>相关企业</span></div>
        <Row>
            <template v-if="relatedEnterpriseList.length > 0">
                <template v-for="(item,index) in relatedEnterpriseList ">
                    <Col span="8">
                    <div class="tc pd6 mb10 mt10">
                        <a :href="['../companyGate/index?uid='+item.link]"><img width="58px" height="58px" :src="item.img" v-if="item.img !== ''"/>
                            <img width="58px" height="58px" src="../../../../static/img/user-icon-big.png" v-else/></a>
                       <p class="ell" :title="item.name">{{item.name | getString}}</p>
                    </div>
                    </Col>
                </template>
            </template>
        </Row>

        <!--<h3 class="hd">相关搜索</h3>
        <Row>
            <Col span="8">
            <div class="tc pd5">
                <img src="../../../img/xgss-icon1.png" />
                <p>水稻收割机</p>
            </div>
            </Col>
        </Row>-->
    </div>
</template>

<script>
    import api from '~api'
    export default {
        name: 'informationDetailLeft',
        props:['itemId','itemType'],
        data() {
            return{
                //相关专家
                relatedExpertList:[],
                //相关企业
                relatedEnterpriseList:[]
            }
        },
        methods: {

        },
        filters :{
            getString :function (value){
                let val =value.trim()
                if (val.length >8)
                    return val.substring(0,8)
                else
                    return val
            }
        },
        created(){
            // 根据文章获取到相关的专家和相关的企业的信息
            api.get('/member/inforMation/infoRelation/'+this.itemId+'/'+this.itemType)
                .then(resp => {
                    if(200 === resp.code){
                        this.relatedExpertList = resp.data.expertList
                        this.relatedEnterpriseList = resp.data.corpList
                    }
                })
        }

    }
</script>

<style scoped>
    .relation-title {
        font-size: 16px;
        color: #657180;
        font-weight:500;
    }
</style>