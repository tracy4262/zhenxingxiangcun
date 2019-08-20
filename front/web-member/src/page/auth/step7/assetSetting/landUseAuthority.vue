<template>
  <div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" ></Title>
    <div class="pd20">
        <Form :label-width="82" 
            label-position="left" 
            style="background:#f9f9f9;" 
            class="pd20 mt40" 
            :model="item"  
            :rules="ruleInline" 
            v-for="(item, index) in data" 
            @click.native="handleGetIndex(index)"
            :ref="`data${index}`">
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="!item.edit">
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col>
              <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="userName" label="土地使用权利人名称">
                    <!-- 单选 -->
                      <Input v-model="item.userName"  :disabled="!item.edit" @on-change="changePreview"/>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="parcelCoding" label="地块编码">
                      <Input v-model="item.parcelCoding" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="parcelName" label="地块名称">
                    <Input v-model="item.parcelName" :maxlength="50" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="landUseStatus" label="土地利用现状" @click.natcive="">
                      <Cascader :data="landUseStatuss" v-model="item.landUseStatusArr" :disabled="!item.edit" @on-change="landUseStatusChange">
                          <Input v-model="item.landUseStatus" icon="md-arrow-dropdown" readonly :disabled="!item.edit" />
                      </Cascader>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="aeasuredArea" label="实测面积">
                      <Input v-model="item.aeasuredArea" :maxlength="20" :disabled="!item.edit" @on-change="changePreview">
                        <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="aerialSurveyArea" label="航测面积" @on-change="changePreview">
                      <Input v-model="item.aerialSurveyArea" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="contractArea" label="合同面积" @on-change="changePreview">
                      <Input v-model="item.contractArea" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="getType" label="取得方式">
                     <Select v-model="item.getType" style="width: 100%" :disabled="!item.edit" @on-change="changePreview">
                          <Option v-for="(item, index) in getTypes" :value="item.label" :key="index">{{item.label}}</Option>
                      </Select>
                  </Form-item>
              </Col>
               <Col span="8">
                  <Form-item prop="getTime" label="取得时间" @on-change="changePreview">
                    <DatePicker type="date" v-model="item.getTime" :disabled="!item.edit"></DatePicker>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="getPrice" label="取得价格" @on-change="changePreview">
                      <Input v-model="item.getPrice" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="userType" label="使用类型" @on-change="changePreview">
                     <Select v-model="item.userType" style="width: 100%" :disabled="!item.edit">
                          <Option v-for="(item, index) in userTypes" :value="item.label" :key="index">{{item.label}}</Option>
                      </Select>
                  </Form-item>
              </Col>
               <Col span="8">
                  <Form-item prop="IDName" label="证件名称">
                    <Input v-model="item.IDName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="IDNumber" label="证件号码">
                      <Input v-model="item.IDNumber" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="ownerName" label="发包方名称">
                     <Input v-model="item.ownerName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
               <Col span="8">
                  <Form-item prop="ownerCode" label="发包方编码">
                    <Input v-model="item.ownerCode" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="ownerConcatName" label="发包方联系人姓名">
                      <Input v-model="item.ownerConcatName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="ownerPhone" label="发包方联系电话">
                     <Input v-model="item.ownerPhone" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
                <Col span="8">
                  <Form-item prop="ownerPostCode" label="发包方邮政编码">
                    <Input v-model="item.ownerPostCode" :maxlength="20" :disabled="!item.edit" @on-change="changePreview"></Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
               <Col span="16">
                  <Form-item prop="ownerAddress" label="发包方地址">
                    <vui-cascander :values="item.ownerAddress" :disabled="!item.edit" @handle-get-result="handleGetData" ></vui-cascander>
                  </Form-item>
              </Col>
                <Col span="4" class="group-number">
                  <Form-item prop="group" label="">
                      <Input v-model="item.group" :maxlength="20" :disabled="!item.edit" style="width: 80px;" @on-change="changePreview"></Input> 
                      <span class="pl10">组</span>
                  </Form-item>
              </Col>
               <Col span="4" class="group-number">
                  <Form-item prop="number" label="">
                      <Input v-model="item.number" :maxlength="20" :disabled="!item.edit" style="width: 80px;" @on-change="changePreview"></Input>
                      <span class="pl10">号</span>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
               <!-- <Col span="24">
                  <Form-item prop="ownerAddress" label="组">
                    <vui-cascander :values="item.ownerAddress" :disabled="!item.edit" @handle-get-result="handleGetData" ></vui-cascander>
                  </Form-item>
              </Col>
               <Col span="24">
                  <Form-item prop="ownerAddress" label="号">
                    <vui-cascander :values="item.ownerAddress" :disabled="!item.edit" @handle-get-result="handleGetData" ></vui-cascander>
                  </Form-item>
              </Col> -->
              <Col span="24">
                  <Form-item prop="introduction" label="介绍">
                      <Input type="textarea" v-model="item.introduction" :maxlength="200" @on-change="changePreview" :autosize="{minRows: 3,maxRows: 5}" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
              <Col span="24">
                <Form-item prop="introduction" label="上传权属资料">
                   <vui-upload
                    :ref="`upload${index}`"
                    @on-getPictureList="getPictureList($event, index)"
                    :total="9999999"
                    :disabled="!item.edit"
                    :multiple="false"
                    :hint="'支持拓展名称：png jpg'"
                    :size="[80,80]"
                    ></vui-upload>
                </Form-item>
            </Col>
          </Row>
          <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div>
    </Form>
    <Button type="primary" ghost icon="md-add" class="mt20 mb40 btn-light-primary" @click="handleAdd">添加</Button>
    </div>
   
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="textPreview.textPreview" :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd40">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" v-else @click="onSave">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import {isTelephoneAndphone, isPostCode, isMoney3} from '~utils/validate'
import {numAdd} from '~utils/utils'
import vuiUpload from '~components/vui-upload'
import vuiCascander from '~components/vuiCascader/index'
export default {
  props: {
    yearId: {
      type: String
    },
    id: {
      type: String
    },
    appId: {
      type: String
    }
  },
  components: {
    Title,
    vuiUpload,
    vuiCascander
  },
  data () {
    return {
      total: 0,
      status: true, 
      textPreview: {},
      title: '土地使用权信息',
      landUseStatuss: [
          {
              label: '农用地', 
              value: '1',
              children: []
          },
          {
              label: '建设用地',
              value: '2',
              children: []
          },
          {
              label: '未利用地', 
              value: '3',
              children: []
          }
      ],
      getTypes: [
        {label: '划拨', value: '划拨'},
        {label: '出让', value: '出让'},
        {label: '划定（村集体占用）', value: '划定（村集体占用）'},
        {label: '承包', value: '承包'},
        {label: '经营', value: '经营'},
      ],
      userTypes: [
        {label: '土地承包经营权', value: '土地承包经营权'},
        {label: '集体土地使用权', value: '集体土地使用权'},
        {label: '建设用地使用权', value: '建设用地使用权'},
        {label: '宅基地使用权', value: '宅基地使用权'},
        {label: '林地林木所有权', value: '林地林木所有权'},
        {label: '经济养殖水面所有权', value: '经济养殖水面所有权'},
      ],
      data: [
        {
          status: true,
          userName: '', // 土地使用权利人名称
          parcelCoding: '', // 地块编码
          parcelName: '', // 地块名称
          landUseStatus: '', // 土地利用现状
          landUseStatusArr: [], // 土地利用现状
          aeasuredArea: '', // 实测面积
          aerialSurveyArea: '', // 航测面积
          contractArea: '', // 合同面积
          getType: '', // 取得方式
          getTime: '', // 取得时间
          getPrice: '', // 取得价格
          userType: '', // 使用类型
          IDName: '', // 证件名称
          IDNumber: '', // 证件号码
          ownerName: '', // 发包方名称
          ownerCode: '', // 发包方编码
          ownerConcatName: '', // 发包方联系人姓名
          ownerPhone: '', // 发包方联系电话
          ownerAddress: '', // 发包方地址
          group: '', // 组
          number: '', // 号
          ownerPostCode: '', // 发包方邮政编码
          introduction: '', // 介绍
          images: [], // 图片
          edit: true
        }
      ],
      ruleInline: {
        aeasuredArea: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        aerialSurveyArea: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        contractArea: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        getPrice: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        ownerPostCode:[
        // {required: true, message: '请填写邮政编码', trigger: 'blur'},
            {validator: isPostCode, trigger: 'blur'},
        ],
        ownerPhone:[
            {validator: isTelephoneAndphone, trigger: 'blur'},
        ]
      },
      activeIndex: 0,
      textPreviewId: 0,
      templateId: '',
      isLoading: true,
      saveisloading: false
    }
  },
created() {
    this.templateId = this.$route.query.templateId
    // 取初始化下拉列表的数据
    this.handleSelectList()
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
    landUseStatusChange (value, selectedData ) {
        let arr = []
        selectedData.forEach(e => {
            arr.push(e.label)
        })
        this.data[this.activeIndex].landUseStatus = arr.join('/')
    },
    // 取初始化下拉列表的数据
    handleSelectList () {
        // 农用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '1',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
            this.landUseStatuss[0].children = response.data
            }
        })
        // 建设用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '2',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.landUseStatuss[1].children = response.data
            }
        })
        // 未利用地
        this.$api.post('/member-reversion/landUse/dict', {
            type : '3',
            templateId: this.templateId
        }).then(response => {
            if (response.code === 200) {
                this.landUseStatuss[2].children = response.data
            }
        })
    },
     //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findLandUseRightInfo',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            parentId: this.id,
            templateId: this.templateId
        }).then(response => {
            if (response.code == 200) {
                this.isLoading = false
                // this.title = response.data.landUseRightInfoName
                if (response.data.landUseRightInfo.length) {
                    this.data = response.data.landUseRightInfo
                    this.data.forEach((e, index) => {
                        e.edit = false
                        e.landUseStatusArr = e.landUseStatus.split('/')
                        this.$nextTick(() => {
                            this.$refs['upload' + index][0].handleGive(e.images)
                        })
                    })
                }
                if (!type) {
                    this.textPreview = response.data.textPreview
                    this.textPreviewId = response.data.textPreview.id
                }
            }
        })
    },
    // 编辑
    handleEdit (item, index) {
      item.edit = true
      this.data.splice(index,1, item)
    },
    handleGetIndex (index) {
      console.log(index)
      this.activeIndex = index
    },
    // 获取地址
    handleGetData (value, selectedData) {
      let labelArr = []
      selectedData.forEach(element => {
          labelArr.push(element.label)
      })
      this.data[this.activeIndex].ownerAddress = labelArr.join('/')
      this.changePreview()
    },
    // 获取图片
    getPictureList ($event, e) {
      var arr = []
      $event.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      });
      this.data[e].images = arr
    },
    // 添加
    handleAdd () {
        this.data.push(
         {
          status: true,
          edit: true,
          userName: '', // 土地使用权利人名称
          parcelCoding: '', // 地块编码
          parcelName: '', // 地块名称
          landUseStatus: '', // 土地利用现状
          landUseStatusArr: [],
          aeasuredArea: '', // 实测面积
          aerialSurveyArea: '', // 航测面积
          contractArea: '', // 合同面积
          getType: '', // 取得方式
          getTime: '', // 取得时间
          getPrice: '', // 取得价格
          userType: '', // 使用类型
          IDName: '', // 证件名称
          IDNumber: '', // 证件号码
          ownerName: '', // 发包方名称
          ownerCode: '', // 发包方编码
          ownerConcatName: '', // 发包方联系人姓名
          ownerPhone: '', // 发包方联系电话
          ownerAddress: '', // 发包方地址
          group: '', // 组
          number: '', // 号
          ownerPostCode: '', // 发包方邮政编码
          introduction: '', // 介绍
          images: [] // 图片
         }
        )
    },
    // 删除
    handleDel (item, index) {
       this.$Modal.confirm({
            title: '是否确定删除',
            content: '是否确认删除？',
            onOk:()=>{
                if (item.id) {
                    this.$api.post('/member-reversion/assetSeting/deleteLandUseRightInfo', {id: item.id}).then(response => {
                        if (response.code === 200) {
                            this.$Message.success('删除成功！')
                            this.data.splice(index,1)
                            this.changePreview()
                        }
                    })
                } else {
                    this.$Message.success('删除成功！')
                    this.data.splice(index,1)
                    this.changePreview()
                }
            },
            okText:'确定',
            cancelText:'取消'
        });
    },
    // 保存
    handleSave (item, index) {
        if (!this.saveisloading) {
            this.saveisloading = true
            this.$refs[`data${index}`][0].validate(v => {
            if (v) {
                if (item.getTime) {
                    item.getTime = this.moment(item.getTime).format('YYYY/MM/DD')
                }
                let list = {
                    account: this.$user.loginAccount,
                    yearId: this.yearId,
                    parentId: this.id,
                    landUseRightInfoName: this.title,
                    landUseRightInfo: item,
                    templateId: this.templateId
                }
                this.$api.post('/member-reversion/assetSeting/saveLandUseRightInfo', list).then(response => {
                    this.saveisloading = false
                    if (response.code === 200) {
                        this.$Message.success('保存成功')
                        item.edit = false
                        this.handleInit(1)
                    }
                })
            } else {
                this.saveisloading = false
                this.$Message.error('请核对表单信息')
            }
            })
        }
    },
    // 文字预览
    changePreview () {
      let str = ''
        this.data.forEach(e => {
            if (e.userName && e.parcelCoding && e.parcelName) {
               str += `${e.userName}拥有地块 ${e.parcelCoding}${e.parcelName}，`
               if (e.aeasuredArea) {
                   str += `实测面积${e.aeasuredArea}平方米，`
               }
               if (e.aerialSurveyArea) {
                   str += `航测面积${e.aerialSurveyArea}平方米，`
               }
               if (e.contractArea) {
                   str += `合同面积${e.contractArea}平方米，`
               }
            }
            
        })
      if (str) {
        str = `${str.substring(0, str.length - 1)}。`
      }
      this.$set(this.textPreview, 'textPreview', str)
    },
    // 保存文字预览
    onSave () {
        this.isLoading = true
        this.textPreview.account = this.$user.loginAccount
        this.textPreview.yearId = this.yearId
        this.textPreview.parentId = this.id
        this.textPreview.isComplete = this.data.length === 0 ? false : true
        this.textPreview.id = this.textPreviewId === '' || this.textPreviewId === undefined ? 0 : this.textPreviewId
        this.textPreview.templateId = this.templateId
        this.$api.post('/member-reversion/assetSeting/saveTextPreview', {textPreview: this.textPreview}).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.handleInit()
                this.$emit('on-save')
            }
        })
    },
    leftRefresh () {
        this.$emit('left-refresh')
    }
  }
}
</script>

<style lang="scss">
.group-number{
    .ivu-form-item-content{
        margin-left: 0px  !important;
    }
}
</style>
