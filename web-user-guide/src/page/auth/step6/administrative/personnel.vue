<template>
  <div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId"></Title>
      <Form :label-width="80" label-position="left" v-for="(item, index) in data"  :ref="`data${index}`" :model="item" :rules="dataLine">
        <div class="pd20 mt30" style="background:#f9f9f9;" >
            <Row  :gutter="24">
              <Col span="8">
                <FormItem label="权限">
                  <Switch class="ml20"  size="large" v-model="item.status" :disabled="!item.edit">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
                </FormItem>
              </Col>
              <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1">删除</span>
              </Col> -->
            </Row>
            <Row  :gutter="24">
              <Col span="8">
                <FormItem label="姓名">
                  <Select v-model="item.name" :disabled="!item.edit">
                      <Option v-for="item in names" :value="item.name" :key="item.name">{{ item.name }}</Option>
                  </Select>
                  <!-- <Input v-model="item.name" :maxlength="20"></Input> -->
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="性别">
                  <Select v-model="item.sex" :disabled="!item.edit">
                      <Option v-for="item in genders" :value="item.label" :key="item.label">{{ item.label }}</Option>
                  </Select>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="24">
              <Col span="8">
                <FormItem label="所属部门">
                  <Input v-model="item.department" :disabled="!item.edit"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="职务">
                  <Input v-model="item.job" :maxlength="20" :disabled="!item.edit"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="24">
              <Col span="24">
                <FormItem label="职责">
                  <Input type="textarea" v-model="item.duty" :maxlength="50"  :disabled="!item.edit":autosize="{minRows: 3,maxRows: 5}"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="24">
              <Col span="8">
                <FormItem label="联系方式" prop="phone">
                  <Input :maxlength="11" v-model="item.phone" :disabled="!item.edit"></Input>
                </FormItem>
              </Col>
            </Row>
            <Row  :gutter="24">
              <Col span="24">
                <FormItem label="照片">
                  <vui-upload 
                    :disabled="!item.edit"
                    :ref="`upload${index}`"
                    @on-getPictureList="getPictureList($event, index)"
                    :total="1"
                    :multiple="false"
                    :hint="'支持拓展名称：png jpg'"
                    :size="[80,80]"
                    :cover="true"
                    ></vui-upload>
                </FormItem>
              </Col>
            </Row>
            <!-- <div class="tc pd20" v-if="item.edit">
              <Button type="primary" @click="handleSave(item, index)">保存</Button>
            </div> -->
        </div>
      </Form>
      <!-- <Button type="primary" ghost @click="handleAdd" class="mt20 btn-light-primary" icon="md-add">增加</Button> -->
      <Title title="文字预览" class="mt50"></Title>
      <div class="pd20 pt30">
        <Input type="textarea" v-model="textPreview.text_preview" :autosize="{minRows: 3,maxRows: 5}"></Input>
      </div>
    <div class="pd40 tc">
        <Button type="primary" v-if="isLoading">保存</Button>
        <Button type="primary" @click="onSave" v-else>保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import vuiUpload from '~components/vui-upload'
import {isTelephoneAndphone, isPhone2, isDomainNameDeep} from '~utils/validate'
export default {
  components: {
    Title,
    vuiUpload
  },
  props: {
    yearId: {
      type: String
    },
    appId: {
      type: String
    },
    id: {
      type: String
    }
  },
  data () {
    return {
      data: [{
            edit: false,
            status: true,
            name: '',
            sex: '',
            department: '',
            job: '',
            duty: '',
            phone: '',
            image: []
          }],
      title: '',
      textPreview: {},
      account: '',
      names: [],
      genders: [
        {label: '女', value: '1'},
        {label: '男', value: '2'}
      ],
      dataLine: {
        phone: [
          {
            validator: isTelephoneAndphone,
            trigger: 'blur'
          }
        ]
      },
      isLoading: true
    }
  },
  created() {
    this.account = this.$user.loginAccount
    this.handleSelect ()
    // this.handleInit()
  },
  methods: {
    handleInit () {
      this.$api.post('/member-reversion/administrationDivision/findManagerialStaff', {templateId: this.$template.id, user_id: this.account, year_id: this.yearId, parent_id: this.id}).then(response => {
            if (response.code === 200) {
              if (response.data.managerialStaff.length) {
                this.data = response.data.managerialStaff
                this.data.forEach(e => {
                  e.edit = false
                })
                this.$nextTick(e => {
                  for(var i = 0 ;i < this.data.length ; i++){
                      var list = this.data[i].image
                      this.$refs[`upload${i}`][0].handleGive(list)
                  }
                })
              }
              this.isLoading = false
              this.title = response.data.managerialStaff_name
              if (!response.data.textPreview.text_preview) {
                response.data.textPreview.text_preview = `姓名（），性别（），所属部门（），职务（），职责（），联系方式（）。`
              }
              this.textPreview = response.data.textPreview
            }
        })
    },
    // 取下拉数据
    handleSelect () {
      let list = {
        user_id: this.account,
        year_id: this.yearId
      }
      this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
        if (response.code === 200) {
          this.names = response.data
        }
      })
    },
    // 增加
    handleAdd () {
      let list = {
            edit: true,
            status: true,
            name: '',
            sex: '',
            department: '',
            job: '',
            duty: '',
            phone: '',
            image: []
          }
          this.data.push(list)
    },
    // 编辑
    handleEdit (item, index) {
      item.edit = true
      this.data.splice(index, 1, item)
    },
    // 删除
    handleDel (item, index) {
      this.$Modal.confirm({
          title: '操作提示',
          content: '是否确认删除？',
          onOk:()=>{
              if (item.id) {
                  this.$api.post('/member-reversion/administrationDivision/deleteManagerialStaff', {id: item.id}).then(response => {
                      if (response.code === 200) {
                          this.$Message.success('保存成功')
                          this.data.splice(index, 1)
                      }
                  })
              } else {
                  this.$Message.success('保存成功')
                  this.data.splice(index, 1)
              }
          },
          okText:'确定',
          cancelText:'取消'
      })
    },
    // 获取照片
    getPictureList ($event, index) {
      var arr = []
      $event.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      });
      this.data[index].image = arr
    },
    // 单个保存
    handleSave (item, index) {
      console.log(this.$refs)
      this.$refs[`data${index}`][0].validate((valid) => {
        if (valid) {
          let list = {
              managerialStaff: item,
              sys_dict_id: this.id,
              managerialStaff_name: '管理人员',
              yearId: this.yearId,
              user_id: this.$user.loginAccount
          }
          this.$api.post('/member-reversion/administrationDivision/saveManagerialStaff', list).then(response => {
              if (response.code === 200) {
                  this.$Message.success('保存成功')
                  this.data[index].edit = false
                  this.$emit('on-init')
              }
          })
        } else {
          this.$Message.error('请核对表单信息')
        }
        
      })
      
    },
    // 保存
    onSave () {
      this.textPreview.is_complete = true
      let list = {
          textPreview: this.textPreview,
          sys_dict_id: this.id,
          managerialStaff_name: this.title,
          yearId: this.yearId,
          user_id: this.$user.loginAccount,
          templateId: this.$template.id,
      }
      this.isLoading = true
      this.$api.post('/member-reversion/administrationDivision/saveTextPreview',list).then(response => {
          if (response.code === 200) {
              this.$Message.success('保存成功')
              this.$emit('on-save')
              this.handleInit()
          }
      })
    },
  },
  mounted() {
    this.textPreview.text_preview = `管理人员有： 姓名（），性别（），所属部门（），职务（），职责（），联系方式（）。`
  }
}
</script>

<style lang="scss" scoped>
</style>
