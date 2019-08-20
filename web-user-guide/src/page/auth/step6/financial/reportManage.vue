<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" />
    <Form ref="formItem" class="mt40" label-position="left" :label-width="100" :rules="formItemInline">
          <Form-item label="权限">
              <i-switch  v-model="status" size="large">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
              </i-switch>
          </Form-item>
          <Form-item label="报表类型">
            <Select v-model="reportType" style="width:300px" @on-change="change">
              <Option v-for="item in reportTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
           <Form-item label="适用对象">
            <Select v-model="suitable" style="width:300px" @on-change="change">
              <Option v-for="item in userTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
          <Form-item label="报表名称">
            <Select v-model="reportName" multiple style="width:300px"  @on-change="reportNameChange">
              <Option v-for="item in reportNames" :value="item.id" :key="item.id">{{ item.reportName }}</Option>
            </Select>
          </Form-item>
    </Form>
    <div style="background:#f9f9f9;" class="mt30 pd30" v-for="(item, index) in list">
      <p style="font-weight: 700;" class="pb10">{{item.reportName}}</p>
      <Row class="pb10">
        <Col span="3" class="tr">报表说明：</Col>
        <Col>{{item.description}}</Col>
      </Row>
      <Row class="pb10">
        <Col span="3" class="tr">报表模板：</Col>
        <Col span="21">
          <vuiUploadFile
          :ref="`template${index}`"
          preview
          @on-getFileList="getReportTemplate($event, index)">
          </vuiUploadFile>
        </Col>
      </Row>
      <Row class="pb10">
        <Col span="3"  class="tr">上传报表：</Col>
        <Col span="21">
          <vuiUploadFile
          :ref="`reports${index}`"
          cover
          @on-getFileList="getReports($event, index)">
          </vuiUploadFile>
        </Col>
      </Row>
    </div>
    <div class="tc pd20">
      <Button type="primary" class="mt30" @click="handleSave">保存</Button>
    </div>
  </div>
</template>
<script>
  import Title from '../../components/title'
  import vuiUploadFile from '~components/vui-upload-file'
  export default {
    components: {
      vuiUploadFile,
      Title
    },
    props: {
        modeId: {
            type: String
        },
        yearId: {
            type: String
        },
        appId: {
            type: String
        }
    },
    data () {
      return {
        title: '报表管理',
        status: true,
        formItemInline: {},
        reportType: '', // 报表类型
        // 报表类型 下拉数据
        reportTypes: [],
        suitable: '', // 适用对象
        userTypes: [], // 适用对象 的下拉数据
        reportName: [], // 报表名称
        // 报表名称 筛选后的数据
        reportNames: [],
        list: [
          {
            reportName:'乡村报表乡村报表', // 报表名称
            description: '乡村报表描述', // 报表描述
            reportTemplate: '', // 报表模板
            reportTemplateName: '', //报表模板名称
            templatePreview: '', //报表模板 预览
            reports: '', // 上传报表
            reportsName: '', //上传报表名称
            uploadPreview: '', //报表模板 预览
          }
        ],
        tag: true,
        id:''
      }
    },
    watch: {
        modeId: {
            handler (newValue, oldValue) {
                this.init()
            },
            deep: true
        }
    },
    created () {
      // 用户类型查询
      this.getUserType()
      // 报表类型查询
      this.getReportType()
      if (this.modeId !== '' && this.modeId !== undefined) {
          this.init()
      }
    },
    methods: {
      // 用户类型查询
      getUserType () {
        this.$api.post('/member-reversion/manage/templateConfig/getUserType').then(response => {
          if (response.code === 200) {
            this.userTypes = response.data
          }
        })
      },
      // 报表类型查询
      getReportType () {
        this.$api.post('/member-reversion/reportManage/findReportType').then(response => {
          if (response.code === 200) {
            this.reportTypes = response.data
          }
        })
      },
      // 初始化取数据
      init () {
        this.$api.post('/member-reversion/reportManage/find',{
          account: this.$user.loginAccount,
          dictId: this.modeId,
          templateId: this.$template.id,
        }).then(response => {
          if (response.code === 200) {
            this.list = response.data.list
            this.status = response.data.status
            this.title = response.data.propertyName
            if (response.data.reportType !== 'undefined') {
              this.reportType = response.data.reportType
            }
            if (response.data.suitable !== 'undefined') {
             this.suitable = response.data.suitable
            }
            // 如果 报表名称 有回显数据 那么 就会触发  reportNameChange 方法 ，则无需处理  tag
            // 如果 报表名称 没有回显数据 那么 就不会触发 reportNameChange 方法 ，则需要处理 tag
            // tag 为false 则根据 报表名称的下拉数据 过滤 已经选择的报表名称
            if (response.data.reportName.length) { // 存在
              this.reportName = response.data.reportName
            } else {
              this.tag = false
            }
            if (this.reportType || this.suitable || this.list.length) {
              this.change()
            }
            this.$nextTick(() => {
                this.list.forEach((item, index) =>{
                  this.$refs[`template${index}`][0].handleGive(item.reportTemplate, item.reportTemplateName, item.templatePreview)
                  this.$refs[`reports${index}`][0].handleGive(item.reports, item.reportsName, item.uploadPreview)
                })
              })
          }
        })
      },
      // 点击保存
      handleSave () {
        let list = {
          list: this.list,
          dictId: this.modeId,
          status: this.status,
          reportType: this.reportType,
          suitable: this.suitable,
          reportName: this.reportName,
          account: this.$user.loginAccount,
          templateId: this.$template.id,
          isComplete: true,
          textPreview: '',
        }
        this.$api.post('/member-reversion/reportManage/save', list).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功')
            this.init()
            this.$emit('on-save')
          }
        })
      },
      // 报表类型 适用对象 发生改变
      change () {
        // 根据 报表类型 适用对象 来选择 筛选 报表名称
        this.$api.post('/member-reversion/reportManage/filterReport',{
          reportType: this.reportType,
          userType: this.suitable,
          account: this.$user.loginAccount,
          templateId: this.$template.id,
        }).then(response => {
          if (response.code === 200) {
            this.reportNames = response.data
          }
        })
      },
      // 报表名称 下拉框收起 
      reportNameChange () {
        // 如果 报表名称 第一次 发生改变（回显数据） 那么数据 tag 为false 
        // 第二次发生改变的时候则表示是用户手动选择了数据
        if (this.tag) {
          this.tag = false
        } else {
          let arr = []
          this.reportNames.filter((item, index) => {
            this.reportName.forEach(e => {
              if (item.id === e) {
                arr.push(item)
              }
            })
          })
          this.list = arr
          this.$nextTick(() => {
                this.list.forEach((item, index) =>{
                  this.$refs[`template${index}`][0].handleGive(item.reportTemplate, item.reportTemplateName, item.templatePreview)
                  this.$refs[`reports${index}`][0].handleGive(item.reports, item.reportsName, item.uploadPreview)
                })
              })
        }
      },
      getReports ($event, index) {
        if ($event[0]) {
          // origin 地址 src pdf name 名称
          this.list[index].reports = $event[0].response.data.origin
          this.list[index].uploadPreview = $event[0].response.data.src
          this.list[index].reportsName = $event[0].response.data.name
        } else {
          this.list[index].reports = ''
          this.list[index].uploadPreview = ''
          this.list[index].reportsName = ''
        }
      },
      getReportTemplate ($event, index) {
         if ($event[0]) {
          // origin 地址 src pdf name 名称
          this.list[index].reportTemplate = $event[0].response.data.origin
          this.list[index].templatePreview = $event[0].response.data.src
          this.list[index].reportTemplateName = $event[0].response.data.name
        } else {
          this.list[index].reportTemplate = ''
          this.list[index].templatePreview = ''
          this.list[index].reportTemplateName = ''
        }
      },
    }
  }
</script>
