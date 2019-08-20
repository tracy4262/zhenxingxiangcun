<template>
    <div>
       <Card class="mb20">
          <Row type="flex" align="middle">
            <Col span="2" class="tc">
              <Avatar :src="item.avatar[0]" class="ivu-avatar-super" />
            </Col>
            <Col span="22">
              <div class="t-small mt5">
                <Col span="6">职务：{{item.job}}</Col>
                <Col span="6" v-if="item.educate">学历：{{item.educate}}</Col>
                <Col span="6" v-if="item.idCard">身份证：{{item.idCard}}</Col>
                <Col span="6" v-if="item.phone">手机号：{{item.phone}}</Col>
              </div>
            </Col>
          </Row>
           <Row class="pb10">
              <Col :span="24" class="detailCol">
                <div class="content" v-if="item.intro && item.intro.length > 130">
                    <span class="ft12">简介：</span>
                    <span class="t-grey ft12" v-if="item.intro && !more">{{item.intro.slice(0,130)}}...</span>
                    <span class="t-grey ft12" v-if="item.intro && more">{{item.intro}}</span>
                    <Button type="text" size="small" @click="handleMore" v-if="!more">查看更多</Button>
                    <Button type="text" size="small" @click="handleMore" v-if="more">收起</Button>
                </div>
                <div class="content" v-else>
                    <span class="ft12">简介：</span>
                    <span class="t-grey ft12">{{item.intro}}</span>
                </div>  
              </Col>
          </Row>
        </Card>
    </div>
</template>


<script>
export default {
    props:{
        item:{
            type:Object,
            default: () => {
                return {}
            }
        },
        index:{
            type:Number,
            default: () => {
                return 0
            }
        }
    },
    data () {
        return {
            moreKnowledge:false,
            more:false

        }
    },
    methods:{
        //查看更多
        handleMore(){
        this.more = !this.more
        }
    }
  
}
</script>
<style scoped>
 .honor-picture-list {
        width: 10%;
        height: 100px;
        float:left;
        margin-right: 10px;
    }
</style>

