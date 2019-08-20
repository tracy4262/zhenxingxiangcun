<template>
    <div class="leader">
        <Card class="mb20">
          <Row type="flex" align="middle">
            <Col span="2" class="tc">
              <Avatar :src="data.avatar" class="ivu-avatar-super" />
            </Col>
            <Col span="22">
              <Row>
                <Col span="4" class="colDetail">
                  <span>{{data.name}}</span>
                  <!-- <span class="t-orange t-small ml5">{{data.role}}</span> -->
                </Col>
                <Col span="6" offset="14">
                  <div class="btn-toolbar">
                    <Button type="text" @click="handleEdit(data)" size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                    <Button type="text" @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button>
                  </div>
                </Col>
              </Row>
              <div class="t-small mt5 colDetail">
                <Col span="4">职务：{{data.job}}</Col>
                <Col span="4" v-if="data.degree">学历：{{data.degree}}</Col>
                <Col span="7" v-if="data.idcard">身份证：{{data.idcard}}</Col>
                <Col span="5" v-if="data.phone">手机号：{{data.phone}}</Col>
              </div>
            </Col>
          </Row>
          <div class="t-small content ell-2" v-if="!more && data.introduction">
              {{data.introduction.slice(0,140)}}...<Button type="text" size="small" @click="handleMore" v-if="data.introduction.length > 140">查看更多</Button>
          </div>
          <div class="content t-grey" v-if="more && data.introduction">
              {{data.introduction}}
              <div class="tr"><Button type="text" size="small" @click="handleMore">收起</Button></div>
          </div>
          <!-- <p class="t-small ell-2 mt10 t-grey">{{data.introduction}}</p> -->
        </Card>
    </div>
</template>

<script>
export default {
    props:{
        data:{
            type:Object,
            default:()=>{
                return {
                }
            }
        },
        index:{
            type:Number,
            default:()=>{
                return 0
            }
        }
    },
    data () {
        return{
            more:false
        }
    },
    methods:{
        // 查看更多
        handleMore(){
            this.more = !this.more
        },
        //编辑
        handleEdit(){
            this.$emit('on-edit',this.index)
        },
        // 删除
        handleDel(){
            this.$Modal.confirm({
                title: '是否确定删除',
                content: '是否确认删除？',
                onOk:()=>{
                    this.$emit('on-del',this.index)
                },
                okText:'确定',
                cancelText:'取消'
            });
        },
    }
}
</script>

<style lang="scss" scoped>
.ivu-avatar-super{
    width: 54px;
    height: 54px;
    line-height: 54px;
    border-radius: 50px;
}
.leader{
    .colDetail{
        line-height: 20px;
        font-size: 14px;
        color:#4A4A4A ;
    }
    .content{
        color:#9B9B9B;
        padding-left: 10px;
    }
}
</style>


