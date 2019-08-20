<template>
  <div class="new-auth">
      <div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
        <div v-for="(item, index) in data">
          <Form :label-width="100" label-position="left" ref="data" :model="item" :rules="dataLine" class="pd20 mt40" style="background:#f9f9f9;">
            <Row  :gutter="38">
              <Col span="8">
                <FormItem label="权限">
                  <Switch class="ml20"  size="large" v-model="item.status">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
                </FormItem>
              </Col>
              <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col>
            </Row>
            <Row  :gutter="38">
              <Col span="8">
                <FormItem label="时间">
                  <DatePicker type="date" v-model="item.history_time" placeholder="请选择" :disabled="!item.edit" @on-change="dataChange"></DatePicker>
                </FormItem>
              </Col>
              <Col span="16">
                <FormItem label="批准单位名称">
                  <Input v-model="item.unit_name" :disabled="!item.edit" @on-change="handleChange" :maxlength="20"></Input>
                </FormItem>
              </Col>
              <Col span="24">
                <FormItem label="主要内容">
                  <Input v-model="item.content" :maxlength="200" @on-change="handleChange" :disabled="!item.edit" type="textarea" :autosize="{minRows: 2,maxRows: 5}" ></Input>
                </FormItem>
              </Col>
            </Row>
              <Row  :gutter="38">
                <Col span="12">
                  <FormItem label="新单位名称">
                    <Input v-model="item.new_unit_name" :maxlength="20" :disabled="!item.edit" @on-change="handleChange"></Input>
                  </FormItem>
                </Col>
                <Col span="8">
                  <FormItem label="隶属关系">
                    <Input v-model="item.affiliation" :maxlength="20" :disabled="!item.edit" @on-change="handleChange"></Input>
                  </FormItem>
                </Col>
              </Row>
              <div class="tc pt40" v-if="item.edit">
                <Button type="primary" @click="handleSave(item, index)">保存</Button>
              </div>
          </Form>
        </div>
        <Button type="primary" ghost icon="md-add" class="mt20 mb40 btn-light-primary" @click="handleAdd">添加</Button>
        <Title title="文字预览" class="mt40"></Title>
        <div class="pd20 tc pt30">
          <Input v-model="textPreview.text_preview" type="textarea" :autosize="{minRows: 4,maxRows: 10}" ></Input>
          <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
          <Button type="primary" v-else @click="onSave" class="mt40">保存</Button>
        </div>
      </div>
  </div>
</template>
<script>
import Title from '../../components/title'
export default {
  components: {
    Title
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
      data: [
        {
          history_time: '',
          unit_name: '',
          content: '',
          new_unit_name: '',
          affiliation: '',
          edit: true,
          status: true
        }
      ],
      textPreview: {},
      sys_dict_id: '',
      title: '历史沿革',
      dataLine: {},
      preview: '',
      account: '',
      time: '',
      templateId: '',
      isLoading: true
    }
  },
  created() {
    this.templateId = this.$route.query.templateId
    this.account = this.$user.loginAccount
    // this.handleInit()
  },
  methods: {
    initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                if (response.data.propertyName) {
                    this.title = response.data.propertyName
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    handleInit (type) {
      this.$api.post('/member-reversion/historyEvolution/findHistoryEvolution', {
        user_id: this.account,
        year_id: this.yearId,
        parent_id: this.id,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.handleInits(response.data, type)
        }
      })
    },
    handleInits (data, type) {
      this.isLoading = false
      if (data.historyEvolution.length) {
          this.data = data.historyEvolution
          this.data.forEach(e => {
              e.edit = false
          })
          this.time = this.data[0].create_time
      }
      if (!type) {
        this.textPreview = data.textPreview
      }
      this.sys_dict_id = this.id
      // this.title = data.historyEvolution_name
      // this.handleChange()
    },
    // 日期发生改变
    dataChange () {
      this.handleChange()
    },
    // 当数据发生改变
    handleChange () {
      if (this.data.length) {
        let time = ''
        this.time ? time = this.time : time = new Date()
        let str = `自${this.moment(time).format('YYYY-MM-DD')}以来，本村经历${this.data.length}次变革。`
        this.textPreview.text_preview = str
      }
    },
    // 点击添加
    handleAdd () {
      let list =  {
          history_time: '',
          unit_name: '',
          content: '',
          new_unit_name: '',
          affiliation: '',
          edit: true,
          status: true
        }
      this.data.push(list)
    },
    // 保存
    handleSave (item, index) {
      item.history_time = item.history_time ? this.moment(item.history_time).format('YYYY-MM-DD') : ''
       let list = {
            historyEvolution: item,
            sys_dict_id: this.sys_dict_id,
            historyEvolution_name: this.title,
            yearId: this.yearId,
            user_id: this.$user.loginAccount,
            templateId: this.templateId
        }
        if (item.id) { // 编辑保存
          list.flag = 0
        } else { // 新增保存
          list.flag = 1
        }
      this.$api.post('/member-reversion/historyEvolution/saveHistoryEvolution', list).then(response => {
        if (response.code === 200) {
          item.edit = false
          this.$Message.success('保存成功')
          this.handleInit(1)
        }
      })
    },
    // 保存文字预览
    onSave () {
      this.isLoading = true
      this.textPreview.is_complete = true
      let list = {
          textPreview: this.textPreview,
          sys_dict_id: this.sys_dict_id,
          historyEvolution_name: this.title,
          yearId: this.yearId,
          user_id: this.$user.loginAccount,
          templateId: this.templateId
      }
      this.$api.post('/member-reversion/historyEvolution/saveTextPreview',list).then(response => {
          if (response.code === 200) {
              this.$Message.success('保存成功')
              this.$emit('on-save')
              this.handleInit()
          }
      })
    },
    // 编辑
    handleEdit (item, index) {
      item.edit = true
      this.data.splice(index, 1, item)
    },
    // 删除
    handleDel (item, index) {
      this.$Modal.confirm({
          title: '是否确定删除',
          onOk:()=>{
            if (item.id) {
              this.$api.post('/member-reversion/historyEvolution/deleteHistoryEvolution', {id: item.id}).then(response => {
                if (response.code === 200) {
                  this.data.splice(index, 1)
                  this.$Message.success('删除成功！')
                  this.handleChange()
                }
              })
            } else {
              this.data.splice(index, 1)
              this.$Message.success('删除成功！')
              this.handleChange()
            }
          },
          okText:'确定',
          cancelText:'取消'
      })
     
    },
    leftRefresh () {
      this.$emit('left-refresh')
    }
  }
}
</script>

