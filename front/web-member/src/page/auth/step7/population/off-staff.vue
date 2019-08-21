<template>
  <div class="pd20">
    <Title :title="title" edit :id="modeId" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh"></Title>
    <Card v-for="(item,index) in data" :key="index" class="mt40">
      <Form :ref="`honorFormItem${index}`" :model="item" label-position="left" :label-width="80" :rules="formItemInline">
            <Row>
                <Col span="12">
                    <Form-item label="权限">
                        <i-switch  v-model="item.status" size="large" :disabled="!item.isAdd">
                            <span slot="open">公开</span>
                            <span slot="close">隐藏</span>
                        </i-switch>
                    </Form-item>
                </Col>
                <Col span="12" class="tr">
                    <div class="btn-toolbar" style="margin-top: -10px;">
                        <Button type="text" @click="edit(index)" v-if="!item.isAdd"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                        <Button type="text" @click="del(item, index)" v-if="item.isAdd && data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                    </div>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="编号">
                        <Input v-model="item.no" :maxlength="50" :disabled="!item.isAdd" readonly @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="姓名">
                        <Input v-model="item.name" :maxlength="50" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="性别">
                      <Select v-model="item.sex" :disabled="!item.isAdd" @on-change="change">
                        <Option value="男">男</Option>
                        <Option value="女">女</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="身份证号" prop="idCard">
                        <Input v-model="item.idCard" :maxlength="18" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="出生年月">
                        <DatePicker v-model="item.birthday" type="date" style="width: 100%;" :options="options"
                            :disabled="!item.isAdd" @on-change="change" format="yyyy-MM-dd"></DatePicker>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="户主">
                      <Select v-model="item.host" :disabled="!item.isAdd" @on-change="change">
                        <Option value="是">是</Option>
                        <Option value="否">否</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="联系方式" prop="tel">
                        <Input v-model="item.tel" :maxlength="11" :disabled="!item.isAdd" @on-change="change" />
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="民族">
                        <Select v-model="item.nation" :disabled="!item.isAdd" @on-change="change">
                          <Option v-for="(f,index) in nations" :value="f.value" :key="index">{{ f.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="党派">
                      <Select v-model="item.policy" :disabled="!item.isAdd" @on-change="change">
                        <Option v-for="(f,index) in policyList" :value="f.value" :key="index">{{ f.label }}</Option>
                      </Select>
                    </Form-item>
                </Col>
            </Row>
            <Row :gutter="32">
                <Col span="8">
                    <Form-item label="宗教信仰">
                        <Select v-model="item.religion" :disabled="!item.isAdd" @on-change="change">
                          <Option v-for="(f,index) in religionList" :value="f.value" :key="index">{{ f.label }}</Option>
                        </Select>
                    </Form-item>
                </Col>
                <Col span="12" style="z-index: 1;">
                  <Form-item label="住址">
                    <vui-cascander :values="item.location" @handle-get-result="handleGetData" @click.native="getIndex(index)" :disabled="!item.isAdd">
                    </vui-cascander>
                  </Form-item>
                </Col>
                <Col span="4">
                  <Form-item style="margin-left: -100px;">
                    <Input v-model="item.locationDetail" :maxlength="5" :disabled="!item.isAdd" style="width: 100px; margin-right: 10px;" @on-change="change" />号
                  </Form-item>
                </Col>
            </Row>
        </Form>
        <view-panel
            title="自定义表单"
            :data="item.formData"
            @on-data="fhandleGetForm"
            @on-add="handleAddBtnAddPanel(index)"></view-panel>
        <div class="tc">
            <Button type="primary" v-if="item.isAdd" @click="onSave(item, index)">保存</Button>
        </div>
    </Card>
    <Form label-position="left" :label-width="150" class="pb20 mt40">
        <Row :gutter="32">
            <Col span="12">
                <Button type="success" ghost @click="handleAdd" icon="md-add" class="btn-light-primary">添加</Button>
            </Col>
        </Row>
    </Form>
    <Title title="文字预览"/>
    <div class="pd20 tc pt30">
        <Input v-model="preview" type="textarea" :autosize="{minRows: 3,maxRows: 5}" />
        <Button type="primary" v-if="isLoading" class="mt40">保存</Button>
        <Button type="primary" v-else @click="handleSave()" class="mt40">保存</Button>
    </div>
    <!-- 添加面板 -->
    <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
  </div>
</template>

<script>
import Title from '../../components/title'
import addPanel from '../../../goods/components/vui-form-control/add-panel'
import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import vuiCascander from '~components/vuiCascader/index'
import {isIdCard2, isPhone2} from '~utils/validate'
export default {
  components: {
    Title,
    addPanel,
    viewPanel,
    vuiCascander
  },
  props: {
    id: String
  },
  data () {
    return {
      title: '编外人员花名册',
      data: [{
        no: '00001',
        name: '',
        sex: '',
        idCard: '',
        birthday: '',
        host: '',
        tel: '',
        nation: '',
        policy: '',
        religion: '',
        location: '',
        locationDetail: '',
        formData: [],
        isAdd: true,
        status: true
      }],
      formItemInline: {
        idCard: [
            {validator: isIdCard2, trigger: 'blur'},
        ],
        tel: [
            {validator: isPhone2, trigger: 'blur'},
        ],
      },
      locationList: [],
      nations: [],
      index: 0,
      policyList: [
        {
          label: '无',
          value: '无'
        },
        {
            label: '中国党员',
            value: '中国党员'
        },
        {
            label: '民主同盟会员',
            value: '民主同盟会员'
        },
        {
            label: '民主建国会会员',
            value: '民主建国会会员'
        },
        {
            label: '民主促进会会员',
            value: '民主促进会会员'
        },
        {
            label: '农工民主党党员',
            value: '农工民主党党员'
        },
        {
            label: '中国致公党党员',
            value: '中国致公党党员'
        },
        {
            label: '九三学社会员',
            value: '九三学社会员'
        },
        {
            label: '自定义',
            value: '自定义'
        }
      ],
      religionList: [
        {
          label: '无',
          value: '无'
        },
        {
            label: '佛教',
            value: '佛教'
        },
        {
            label: '道教',
            value: '道教'
        },
        {
            label: '基督新教',
            value: '基督新教'
        },
        {
          label: '天主教',
          value: '天主教'
        },
        {
            label: '伊斯兰教',
            value: '伊斯兰教'
        },
        {
            label: '自定义',
            value: '自定义'
        }
      ],
      preview: '',
      options: {
        disabledDate (date) {
            return date && date.valueOf() > Date.now()
        }
      },
      templateId: '',
      isLoading: true
    }
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
  watch: {
    modeId: {
        handler (newValue, oldValue) {
            this.init()
            this.initTitle()
        },
        deep: true
    }
  },
  created () {
    this.templateId = this.$route.query.templateId
    if (this.modeId !== '' && this.modeId !== undefined) {
        this.init()
        this.initTitle()
    }
    // 取民族数据
    this.$api.get("/member/race").then(response => {
      this.nations = response.data
    })
  },
  methods: {
      initTitle () {
        this.$api.post('/member-reversion/user/perfect/findTableHead', {
            account: this.$user.loginAccount,
            yearId: this.yearId,
            dictId: this.modeId,
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
    init (type = 0) {
        this.$api.post('/member-reversion/employeeRoster/findListOfExtraStaff', {
            user_id: this.$user.loginAccount,
            year_id: this.yearId,
            parent_id: this.modeId,
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.isLoading = false
                // if (response.data.listOfExtraStaff_name && response.data.listOfExtraStaff_name !== '') {
                //     this.title = response.data.listOfExtraStaff_name
                // }
                if (response.data.textPreview.text_preview && response.data.textPreview.text_preview !== '') {
                    this.preview = response.data.textPreview.text_preview
                    this.id = response.data.textPreview.id
                }
                if (response.data.listOfExtraStaff.length !== 0) {
                    this.data = []
                    response.data.listOfExtraStaff.forEach((element, index) => {
                        this.data.push({
                            id: element.id,
                            no: element.number,
                            name: element.name,
                            sex: element.sex,
                            idCard: element.card,
                            birthday: element.date_of_birth,
                            host: element.householder,
                            tel: element.phone,
                            nation: element.nation,
                            policy: element.partisan,
                            religion: element.faith,
                            location: element.address,
                            locationDetail: element.house_number,
                            formData: element.custom_field,
                            status: element.status,
                            isAdd: false
                        })
                    })
                }
                if (type === 1) {
                    this.change()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    // 保存单个人口信息
    onSave (item, index) {
        this.$refs[`honorFormItem${index}`][0].validate( (valid) => {
            if (valid) {
                this.$api.post('/member-reversion/employeeRoster/saveListOfExtraStaff', {
                    user_id: this.$user.loginAccount,
                    yearId: this.yearId,
                    parent_id: this.appId,
                    listOfExtraStaff_name: this.title,
                    templateId: this.templateId,
                    listOfExtraStaff: {
                        id: item.id === '' || item.id === undefined ? 0 : item.id,
                        number: item.no,
                        name: item.name,
                        sex: item.sex,
                        card: item.idCard,
                        date_of_birth: item.birthday ? this.moment(item.birthday).format('YYYY-MM-DD') : '',
                        householder: item.host,
                        phone: item.tel,
                        nation: item.nation,
                        partisan: item.policy,
                        faith: item.religion,
                        address: item.location,
                        house_number: item.locationDetail,
                        custom_field: item.formData,
                        status: item.status
                    }
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('保存成功！')
                        this.init(1)
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            } else {
                this.$Message.error('请核对表单信息')
            }
        })
    },
    handleAdd () {
      let no = this.data.length === 0 ? 1 : parseInt(this.data[this.data.length - 1].no) + 1
      this.data.push({
        // 不足五位数前面补零
        no: (Array(5).join(0) + no).slice(-5),
        name: '',
        sex: '',
        idCard: '',
        birthday: '',
        host: '',
        tel: '',
        nation: '',
        policy: '',
        religion: '',
        location: '',
        locationDetail: '',
        formData: [],
        isAdd: true,
        status: true
      })
      this.index = this.data.length - 1
    },
    edit (index) {
      this.data[index].isAdd = true
      this.index = index
    },
    del (item, index) {
      this.$Modal.confirm({
        title: '操作提示',
        content: '是否确认删除？',
        onOk:()=>{
            if (item.id !== undefined && item.id !== '') {
                this.$api.post('/member-reversion/employeeRoster/deleteListOfExtraStaff', {
                    id: item.id
                }).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('删除成功！')
                        this.data.splice(index, 1)
                        this.change()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            } else {
                this.$Message.success('删除成功！')
                this.data.splice(index, 1)
                this.change()
            }
        },
        okText:'确定',
        cancelText:'取消'
      })
    },
    // 地区控件选择后的返回值
    handleGetData (value, selectedData) {
        let labelArr = []
        selectedData.forEach(element => {
            labelArr.push(element.label)
        })
        this.data[this.index].location = labelArr.join('/')
        this.change()
    },
    // 取商品质量数据
    fhandleGetForm (data) {
    },
    // 质量信息添加
    handleAddBtnAddPanel (index) {
        this.index = index
        this.$refs.addPanel.showAddPanel = true
    },
    // 添加组件
    handleControlBtn (data) {
        this.data[this.index].formData.push(data)
    },
    getIndex (index) {
        this.index = index
    },
    change () {
        this.preview = ''
        this.preview += `编外人员总计 ${this.data.length} 人。`
    },
    handleSave () {
        this.isLoading = true
        this.$api.post('/member-reversion/employeeRoster/saveTextPreview', {
            user_id: this.$user.loginAccount,
            yearId: this.yearId,
            sys_dict_id: this.modeId,
            templateId: this.templateId,
            textPreview: {
                id: this.id === '' || this.id === undefined ? 0 : this.id,
                text_preview: this.preview,
                is_complete: this.data.length === 0 ? false : true,
            }
        }).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功！')
                this.init()
                this.$emit('on-save')
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="scss" scoped>
.fz {
  font-size: 12px;
}
</style>
