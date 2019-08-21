<template>
  <div class="pt30 pl10 pr10 family-deatil">
      <Form ref="edits" label-position="left" :label-width="150">
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" v-if="item.family_status">
                <Form  :ref="`edit${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="8">
                            <Form-item  prop="name" label="成员名称">
                                {{item.name}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="relationship" label="与户主关系">
                                {{item.relationship}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="sex" label="性别">
                                {{item.sex}}
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="birthday" label="出生日期">
                                <span v-if="item.birthday">{{moment(item.birthday).format('YYYY/MM/DD')}}</span>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="phone" label="手机号码">
                                {{item.phone}}                       
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="skill" label="劳动技能">
                                {{item.skill}}
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
        </Card>
      </Form>
  </div>
</template>
<script>
    import {isPhone2,} from '~utils/validate'
    export default{
        components: {

        },
        data () {
            return {
                index:0,
                sexs:[
                    {label:'男',value:'男'},
                    {label:'女',value:'女'}
                ],
                options3:{
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now() - 86400000;
                    }
                },
                data: [],
                ruleInline:{
                    phone:[{validator:isPhone2,trigger:'blur'}]
                },
                submit:true
            }
        },
        created () {
        },
        methods: {            
            getData(val){
                this.data = val
            },
            //当前点击的index
            getIndex (index) {
                this.index = index
            },
            //提交表单验证
            handleSubmit () {
                var data = this.data
                this.submit = true
                for(var i = 0 ;i < data.length ; i++){
                    this.$refs[`edit${i}`][0].validate((valid)=>{
                        if(!valid){
                            this.submit = false
                        }
                    })
                }
                this.$emit('on-submit',this.submit)
            }
        }
    }

</script>
<style lang="scss">
.family-deatil{
    .ivu-card{
      .btn-toolbar{
        position: absolute;
        right: 0px;
        top: -60px;
        transition: top .3s
      }
      .btn-toolbar-card{
        overflow: hidden;
        position:relative;
        height:35px;
      }
      &:hover{
        .btn-toolbar{
          top: 0px;
        }
      }
    }
}
</style>

