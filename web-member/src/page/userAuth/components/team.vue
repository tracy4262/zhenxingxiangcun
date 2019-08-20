<template>
  <div  class="education pt30 pl10 pr10">
    <Form :label-width="150" label-position="left" class="mt20 mb20">
      <Row :gutter="32">
        <Col span="12">
          <Form-item  label="团队成员">
            <Button type="primary" @click="handleAdd"> <Icon type="plus"></Icon>增加</Button>
          </Form-item>
        </Col>
      </Row>      
      <div  v-for="(item, index) in data" :key="index">
        <team-card :item="item" :index="index" @on-del="handleDel" @on-edit="handleEdit"></team-card>
        <!-- <Card class="mb20">
          <Row type="flex" align="middle">
            <Col span="2" class="tc">
              <Avatar :src="item.avatar[0]" class="ivu-avatar-super" />
            </Col>
            <Col span="22">
              <Row>
                <Col span="4">
                  <span>{{item.name}}</span>
                  <span class="t-orange t-small ml5">{{item.role}}</span>
                </Col>
                <Col span="6" offset="14">
                  <div class="btn-toolbar">
                    <Button type="text" @click="handleEdit(index)" size="small"><Icon type="edit" size="16" class="pr5"></Icon> 编辑</Button>
                    <Button type="text" @click="handleDel(index)" size="small"><Icon type="trash-a"  size="16"  class="pr5"></Icon> 删除</Button>
                  </div>
                </Col>
              </Row>
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
                <div class="content" v-if="data.intro && data.intro.length > 140">
                    <span class="ft12">简介：</span>
                    <span class="t-grey ft12" v-if="data.intro && !more">{{data.intro.slice(0,140)}}...</span>
                    <span class="t-grey ft12" v-if="data.intro && more">{{data.intro}}</span>
                    <Button type="text" size="small" @click="handleMore" v-if="!more">查看更多</Button>
                    <Button type="text" size="small" @click="handleMore" v-if="more">收起</Button>
                </div>
                <div class="content" v-else>
                    <span class="ft12">简介：</span>
                    <span class="t-grey ft12">{{data.intro}}</span>
                </div>  
              </Col>
          </Row>
        </Card> -->
      </div>
    </Form>
    <Modal
        v-model="teamAccount"
        :title="title"
        width="800"
        :mask-closable="false">
        <div class="pd20">
            <Form  ref="formItem" :model="formItem" label-position="left" :label-width="80" :rules="formItemInline">
              <Row :gutter="32">
                <Col span="24">
                  <FormItem label="权限">
                    <i-switch  v-model="formItem.team_status" size="large">
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                    </i-switch>
                  </FormItem>
                </Col>
              </Row>
              <Row :gutter="32">
                <!-- <Col span="12">
                  <Form-item prop="role" label="角色">
                    <Select v-model="formItem.role" >
                      <Option v-for="item in roles" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                  </Form-item>
                </Col> -->
                <Col span="12">
                  <Form-item prop="job" label="职务">
                    <Input v-model="formItem.job" :maxlength="20"></Input>
                  </Form-item>
                </Col>
              <!-- </Row>
              <Row :gutter="32"> -->
                <Col span="12">
                  <Form-item prop="name" label="姓名">
                    <Input v-model="formItem.name" :maxlength="20"></Input>
                  </Form-item>
                </Col>
                <Col span="12">
                  <Form-item prop="educate" label="学历">
                     <Select v-model="formItem.educate">
                      <Option v-for="item in educates" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                  </Form-item>
                </Col>
              <!-- </Row>
              <Row :gutter="32"> -->
                <Col span="12">
                  <Form-item prop="idCard" label="身份证">
                    <Input v-model="formItem.idCard" :maxlength="18"></Input>
                  </Form-item>
                </Col>
                <Col span="12">
                  <Form-item prop="phone" label="手机号">
                    <Input v-model="formItem.phone" :maxlength="11"></Input>
                  </Form-item>
                </Col>
              </Row>
              <Row :gutter="32">
                <Col span="24">
                  <Form-item prop="avatar" label="照片">
                    <vui-upload
                      ref="upload"
                      @on-getPictureList="getPictureList"
                      :pictureLists="formItem.avatar"
                      :total="1"
                      :multiple="false"
                      :hint="'图片大小小于2M'"
                     ></vui-upload>
                  </Form-item>
                </Col>
              </Row>
              <Row :gutter="32">
                <Col span="24">
                  <Form-item prop="intro" label="简介">
                    <Input v-model="formItem.intro" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="500"></Input>
                  </Form-item>
                </Col>
              </Row>
            </Form>
        </div>
        <div slot="footer" class="tc">
            <Button type="default" @click="teamAccount = false">取消</Button>
            <Button type="primary" @click.native="teamAccountOk">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import {isIdCard2,isPhone2} from '~utils/validate'
import vuiUpload from '~components/vui-upload'
import teamCard from './teamCard'
export default {
  components:{
    vuiUpload,
    teamCard
  },
  data: () => ({
    more:false,
    teamAccount:false,
    roles:[
      {value: '董事',label: '董事'},
      {value: '监理',label: '监理'},
      {value: '高管',label: '高管'}
    ],
    educates:[{
        value: '小学',
        label: '小学'
      },
      {
        value: '初中',
        label: '初中'
      },
      {
        value: '高中',
        label: '高中'
      },
      {
        value: '高职高专',
        label: '高职高专'
      },
      {
        value: '大专',
        label: '大专'
      },
      {
        value: '本科',
        label: '本科'
      },
      {
        value: '研究生',
        label: '研究生'
      },
      {
        value: '博士',
        label: '博士'
      },
    ],
    formItem:{
      // role: '',
      team_status: true,
      job: '',
      name: '',
      avatar: [],
      idCard: '',
      phone: '',
      intro: '',
      educate: ''
    },
    formItemInline:{
      // role:[{required: true, message:'请选择角色',trigger: 'blur'}],
      job:[{required: true, message:'请输入职务',trigger: 'blur'}],
      name:[{required: true, message:'请输入姓名',trigger: 'blur'}],
      phone:[{validator: isPhone2,trigger: 'blur'}],
      idCard:[ { validator: isIdCard2,  trigger: 'blur'}],
    },
    data: [],
    index: 0,
    isAdd: true,
    title: '添加成员'
  }),
  mounted () {
    this.$refs.formItem.resetFields()
  },
  methods: {
    //接收数据
    getData(val){
      this.data = val
    },
     handleSubmit(){
      this.$emit('on-submit',true)
    },
    getPictureList(e){
      var arr = []
      e.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      })
      this.formItem.avatar = arr
    },
    //点击添加
    handleAdd(){
      this.formItem = {
        // role: '',
        team_status: true,
        job: '',
        name: '',
        avatar: [],
        idCard: '',
        phone: '',
        intro: '',
        educate: ''
      }
      this.teamAccount = true
      this.title = '添加成员'
      this.isAdd=true
      this.$refs['upload'].handleGive(this.formItem.avatar)
    },
    // 点击确认
    teamAccountOk(){
      this.$refs['formItem'].validate((valid) => {
          if (valid) {
            if(this.isAdd){
              this.data.unshift(this.formItem)
            } else {
              this.data[this.index] = this.formItem
            }
            this.teamAccount = false
          }else{
            this.$Message.error('请核对表单信息')
          }
      })
    },
    // 编辑
    handleEdit (index) {
      console.log(index)
      this.isAdd = false
      this.title = '编辑成员'
      this.index = index
      this.formItem =  Object.assign({}, this.data[index])
      this.teamAccount = true
      this.$refs['upload'].handleGive(this.formItem.avatar)
    },
    // 删除
    handleDel (index) {
      this.data.splice(index,1)
    }
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
</style>
