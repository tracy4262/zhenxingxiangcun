<template>
  <div class="asset-finance">
      <Card>
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
            <Row  type="flex" align="middle" class="pt5 t-orange">
                <Col span="10">
                    {{labelList.leftLabel}}：{{data.leftValue}}
                </Col>
                <Col span="10" v-if="data.rightValue instanceof Array && labelList.rightLabel">
                    {{labelList.rightLabel}}：{{moment(data.rightValue[0]).format("YYYY-MM-DD")}} —— {{moment(data.rightValue[1]).format("YYYY-MM-DD")}}
                </Col>
                <Col span="10" v-else>
                    {{labelList.rightLabel}}：{{data.rightValue}}
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
        labelList:{
            type:Object,
             default:()=>{
                return {
                    name:'',
                    leftLabel:'',
                    rightLabel:''
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
            showDel:false
        }
    },
    methods:{
        //编辑
        handleEdit(){
            this.$emit('on-edit',this.index)
        },
        // 删除
        handleDel(){
            this.showDel = true
            this.$Modal.confirm({
                title: '操作提示',
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
</style>

