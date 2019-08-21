<template>
  <div class="asset-finance">
      <Card class="asset-finance-card mb20">
            <Row  type="flex" align="middle">
                <Col span="16" class="title-name">
                    {{data.name}}
                </Col>
                <Col span="8" class="tr">
                    <div class="btn-toolbar">
                        <Button type="text" @click="handleEdit" size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                        <Button type="text" @click="handleDel" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row  type="flex" align="middle" class="pt5 t-grey">
                <Col span="24">
                    <div class="content ell-2" v-if="!more && data.eplain">
                        {{data.eplain.slice(0,140)}}...<Button type="text" size="small" v-if="data.eplain.length > 140" @click="handleMore">查看更多</Button>
                    </div>
                    <div class="content" v-if="more && data.eplain">
                        {{data.eplain}}
                        <div class="tr"><Button type="text" size="small" @click="handleMore">收起</Button></div>
                    </div>
                </Col>
            </Row>
        </Card>
  </div>
</template>
<script>
export default{
    props:{
        data:{
            type:Object,
            default:()=>{
                return {
                    term:[],
                    bankName:'',
                    quota:''
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
    data(){
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
<style lang="scss">
.asset-finance{
    .title-name{
        min-height:33px;
        line-height:33px;
    }
    .content{
        font-size: 12px;
    }
}
</style>

