<template>
  <div class="pt30 pl10 pr10 family-deatil">
      <Form ref="edits" label-position="left" :label-width="150">
        <Row :gutter="32">
           <Col span="12">
                <Form-item label="家庭成员">
                    <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon> 添加</Button>
                </Form-item>
           </Col>
        </Row>
        <Card v-for="(item , index) in data" :key="index" class="mb20 card" :bordered="false" @click="getIndex(index)">
            <div class="btn-toolbar-card">
                <div class="btn-toolbar">
                    <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    <!-- <Button type="text"  @click="handleSubmit(index)" size="small"></Icon> 确定</Button>  -->
                </div>
            </div>
            <!-- <Row>
                <Col span="22"> -->
                <Form  :ref="`edit${index}`" :model="item" label-position="left" :label-width="82" :rules="ruleInline">
                    <Row :gutter="16">
                        <Col span="24">
                            <Form-item  label="权限">
                                <i-switch  v-model="item.family_status" size="large">
                                    <span slot="open">公开</span>
                                    <span slot="close">隐藏</span>
                                </i-switch>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item  prop="name" label="成员名称">
                                <Input v-model="item.name" placeholder="请输入成员名称" :maxlength="36"></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="relationship" label="与户主关系">
                                <Input v-model="item.relationship" :maxlength="36"></Input>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="sex" label="性别">
                                <Select v-model="item.sex">
                                    <Option v-for="item in sexs" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                </Select>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="birthday" label="出生日期">
                                <DatePicker v-model="item.birthday" type="date" @on-change="change" :editable="false" placeholder="请选择日期" :options="options3" placement="bottom-end"></DatePicker>
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="phone" label="手机号码">
                                <Input v-model="item.phone" :maxlength="11"></Input>                           
                            </Form-item>
                        </Col>
                        <Col span="8">
                            <Form-item prop="skill" label="劳动技能">
                                <Input v-model="item.skill" :maxlength="200"></Input>
                            </Form-item>
                        </Col>
                    </Row>
                </Form>
                <!-- </Col>
                <Col span="2" class="btn-toolbar-card">
                &nbsp;
                    <div class="btn-toolbar">
                        <Button type="text"  @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row> -->
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
                        return date && date.valueOf() > Date.now();
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
            //选择日期改变时触发
            change(e){
                this.data[this.index].birthday = e
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
            },
            // 添加
            handleAdd() {
                this.data.unshift(
                    {
                        family_status: true,
                        name: '',
                        relationship: '',
                        sex: '',
                        birthday: '',
                        birthday: '',
                        skill: ''
                    }
                )
            },
            // 删除
            handleDel (index) {
                this.$Modal.confirm({
                    title: '是否确定删除',
                    content: '是否确认删除？',
                    onOk:()=>{
                        this.data.splice(index,1)
                    },
                    okText:'确定',
                    cancelText:'取消'
                });
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

