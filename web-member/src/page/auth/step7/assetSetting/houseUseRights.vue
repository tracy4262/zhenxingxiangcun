<template>
  <div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" ></Title>
    <div class="pd20">
        <Form :label-width="82" label-position="left" style="background:#f9f9f9;" class="pd20 mt40" :model="item"  :rules="ruleInline" v-for="(item, index) in data" :ref="`data${index}`">
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="!item.edit" >
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
                  <Form-item  prop="rightHolderName" label="房屋权利人姓名">
                    <!-- 单选 默认为会员名称，可以从人口信息花名册中选择-->
                    <Select v-model="item.rightHolderName" style="width: 100%" :disabled="!item.edit" @on-change="changePreview" >
                        <Option v-for="(item, index) in rightHolderNames" :value="item.name" :key="index">{{item.name}}</Option>
                    </Select>
                      <!-- <Input v-model="item.rightHolderName"  :disabled="!item.edit" @on-change="changePreview"  /> -->
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="userName" label="房屋使用人姓名">
                      <Input v-model="item.userName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="buildingName" label="建筑物名称">
                    <Input v-model="item.buildingName" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="housingCategory" label="房屋类别">
                      <Input v-model="item.housingCategory" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>       
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="totalFloors" label="房屋总层数">
                      <Input v-model="item.totalFloors" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" >
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="buildingStructure" label="建筑结构">
                      <Input v-model="item.buildingStructure" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>                           
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="floorArea" label="占地面积">
                      <Input v-model="item.floorArea"  :maxlength="20" :disabled="!item.edit" @on-change="changePreview" >
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="constructionArea" label="建筑面积">
                     <Input v-model="item.constructionArea"  :maxlength="20" :disabled="!item.edit" @on-change="changePreview" >
                     <span slot="append">平方米</span>
                     </Input>
                  </Form-item>
              </Col>
               <Col span="8">
                  <Form-item prop="securityLevel" label="房屋安全等级">
                    <Select v-model="item.securityLevel" style="width: 100%" :disabled="!item.edit" @on-change="changePreview" >
                          <Option v-for="(item, index) in securityLevels" :value="item.label" :key="index">{{item.label}}</Option>
                      </Select>
                   
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="24">
                  <Form-item prop="securityStatus" label="房屋安全状况">
                      <Input v-model="item.securityStatus" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>
                  </Form-item>
              </Col>
              <Col span="24">
                  <Form-item prop="use" label="使用情况">
                     <Input v-model="item.use" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" ></Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
               <Col span="8">
                  <Form-item prop="getType" label="取得时间">
                    <DatePicker type="date" v-model="item.getTime" :disabled="!item.edit" @on-change="changePreview" ></DatePicker>
                   
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="getPrice" label="取得价格">
                      <Input v-model="item.getPrice" :maxlength="20" :disabled="!item.edit" @on-change="changePreview" >
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="24">
                <Form-item label="房屋照片">
                   <vui-upload
                    :ref="`upload${index}`"
                    @on-getPictureList="getPictureList($event, index)"
                    :total="9999999"
                    :disabled="!item.edit" @on-change="changePreview" 
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
   <div style="background: rgb(0, 197, 135); width: 925px; margin-left: -36px;" class="mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          合计：占地面积 {{floorAreas}} 平方米，建筑面积 {{constructionAreas}} 平方米 
        </div>
      </div>
   
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea"  v-model="textPreview.textPreview"  :autosize="{minRows: 3,maxRows: 5}"></Input>
    </div>
    <div class="tc pd40">
      <Button type="primary" v-if="isLoading">保存</Button>
      <Button type="primary" v-else @click="onSave">保存</Button>
    </div>
  </div>
</template>

<script>
import Title from '../../components/title'
import {isMoney3} from '~utils/validate'
import {numAdd} from '~utils/utils'
import vuiUpload from '~components/vui-upload'
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
    vuiUpload
  },
  data () {
    return {
      total: 0,
      textPreview: {},
      title: '房屋使用权信息',
      securityLevels: [
        {label: 'A级', value: 'A级'},
        {label: 'B级', value: 'B级'},
        {label: 'C级', value: 'C级'},
        {label: 'D级', value: 'D级'},
      ],
      rightHolderNames: [],
      data: [
        {
          status: true,
          rightHolderName: this.$parent.$parent.$parent.$parent.displayName, // 房屋权利人姓名
          userName: '', // 房屋使用人姓名
          buildingName: '', // 建筑物名称
          housingCategory: '', // 房屋类别
          totalFloors: '', // 房屋总层数
          buildingStructure: '', // 建筑结构
          floorArea: '', // 占地面积
          constructionArea: '', // 建筑面积
          securityLevel: '', // 房屋安全等级
          securityStatus: '', // 房屋安全状况
          use: '', // 使用情况
          getTime: '', // 取得时间
          getPrice: '', // 取得价格
          images: [], // 图片
          edit: true
        }
      ],
      floorAreas: '',
      constructionAreas: '',
      ruleInline: {
        floorArea: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        constructionArea: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        getPrice: [
          {validator: isMoney3, trigger: 'blur'},
        ],
      },
      activeIndex: 0,
      textPreviewId: 0,
      templateId: '',
      isLoading: true,
      saveisloading: false
    }
  },
  created () {
      this.templateId = this.$route.query.templateId
      this.handleSelect()
  },
  methods: {
    // 取下拉数据
    handleSelect () {
        let list = {
            user_id: this.$user.loginAccount,
            year_id: this.yearId,
            templateId: this.templateId
        }
        this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
            if (response.code === 200) {
            this.rightHolderNames = response.data
            this.rightHolderNames.unshift({name: this.$parent.$parent.$parent.$parent.displayName})
            }
        })
    },
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
     //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findRightToUseHousingInfo',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            parentId: this.id,
            templateId: this.templateId
        }).then(response => {
             if (response.code == 200) {
                 this.isLoading = false
              // this.title = response.data.rightToUseHousingInfoName
              if (response.data.rightToUseHousingInfo.length) {
                this.data = response.data.rightToUseHousingInfo
                this.floorAreas = 0
                this.constructionAreas = 0
                this.data.forEach((e, index) => {
                    e.edit = false
                    this.$nextTick(() => {
                        this.$refs['upload' + index][0].handleGive(e.images)
                        this.floorAreas = numAdd(parseFloat(this.floorAreas ? this.floorAreas : 0).toFixed(2), parseFloat(e.floorArea ? e.floorArea : 0).toFixed(2))
                    this.constructionAreas = numAdd(parseFloat(this.constructionAreas ? this.constructionAreas : 0).toFixed(2), parseFloat(e.constructionArea ? e.constructionArea : 0).toFixed(2))
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
          rightHolderName: this.$parent.$parent.$parent.$parent.displayName, // 房屋权利人姓名
          userName: '', // 房屋使用人姓名
          buildingName: '', // 建筑物名称
          housingCategory: '', // 房屋类别
          totalFloors: '', // 房屋总层数
          buildingStructure: '', // 建筑结构
          floorArea: '', // 占地面积
          constructionArea: '', // 建筑面积
          securityLevel: '', // 房屋安全等级
          securityStatus: '', // 房屋安全状况
          use: '', // 使用情况
          getTime: '', // 取得时间
          getPrice: '', // 取得价格
          images: [], // 图片
          edit: true
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
                    this.$api.post('/member-reversion/assetSeting/deleteRightToUseHousingInfo', {id: item.id}).then(response => {
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
                rightToUseHousingInfoName: this.title,
                rightToUseHousingInfo: item,
                templateId: this.templateId
            }
            this.$api.post('/member-reversion/assetSeting/saveRightToUseHousingInfo', list).then(response => {
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
      this.floorAreas = 0
      this.constructionAreas = 0
      if (this.data.length) {
          str += `${this.data.length}套房屋，`
      }
       this.data.forEach(e => {
            this.floorAreas = numAdd(parseFloat(this.floorAreas ? this.floorAreas : 0).toFixed(2), parseFloat(e.floorArea ? e.floorArea : 0).toFixed(2))
            this.constructionAreas = numAdd(parseFloat(this.constructionAreas ? this.constructionAreas : 0).toFixed(2), parseFloat(e.constructionArea ? e.constructionArea : 0).toFixed(2))
        })
        // 占地面积{{floorAreas}} 平方米 建筑面积{{constructionAreas}} 平方米 
      if (this.floorAreas) {
        str += `合计占地面积${this.floorAreas}平方米，`
      }
      if (this.constructionAreas) {
        str += `建筑面积${this.constructionAreas}平方米，`
      }
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

<style lang="scss" scoped>
.subtotal{
  font-size: 16px;
}
</style>
