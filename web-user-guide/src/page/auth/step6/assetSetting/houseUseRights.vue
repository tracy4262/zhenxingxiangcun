<template>
  <div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" ></Title>
    <div class="pd20">
        <Form :label-width="82" label-position="left" style="background:#f9f9f9;" class="pd20 mt40" :model="item"  :rules="ruleInline" v-for="(item, index) in data" :ref="`data${index}`">
          <Row :gutter="16">
              <Col span="8">
                  <Form-item label="权限">
                      <i-switch size="large"  v-model="item.status" :disabled="!item.edit">
                          <span slot="open">公开</span>
                          <span slot="close">隐藏</span>
                      </i-switch>
                  </Form-item>
              </Col>
              <!-- <Col span="8" offset="8" class="tr">
                <span class="mr20 auth-btn-toolbar" @click="handleEdit(item, index)" v-if="!item.edit">编辑</span>
                <span class="auth-btn-toolbar" @click="handleDel(item, index)" v-if="data.length != 1 && item.edit">删除</span>
              </Col> -->
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="rightHolderName" label="房屋权利人姓名">
                    <!-- 单选 -->
                      <Input v-model="item.rightHolderName"  :disabled="!item.edit" />
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="userName" label="房屋使用人姓名">
                      <Input v-model="item.userName" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="buildingName" label="建筑物名称">
                    <Input v-model="item.buildingName" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="housingCategory" label="房屋类别">
                      <Input v-model="item.housingCategory" :maxlength="20" :disabled="!item.edit"></Input>       
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="totalFloors" label="房屋总层数">
                      <Input v-model="item.totalFloors" :maxlength="20" :disabled="!item.edit">
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="buildingStructure" label="建筑结构">
                      <Input v-model="item.buildingStructure" :maxlength="20" :disabled="!item.edit"></Input>                           
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="floorArea" label="占地面积">
                      <Input v-model="item.floorArea" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">平方米</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="constructionArea" label="建筑面积">
                     <Input v-model="item.constructionArea" :maxlength="20" :disabled="!item.edit">
                     <span slot="append">平方米</span>
                     </Input>
                  </Form-item>
              </Col>
               <Col span="8">
                  <Form-item prop="securityLevel" label="房屋安全等级">
                    <Select v-model="item.securityLevel" style="width: 100%" :disabled="!item.edit">
                          <Option v-for="(item, index) in securityLevels" :value="item.label" :key="index">{{item.label}}</Option>
                      </Select>
                   
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="24">
                  <Form-item prop="securityStatus" label="房屋安全状况">
                      <Input v-model="item.securityStatus" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
              <Col span="24">
                  <Form-item prop="use" label="使用情况">
                     <Input v-model="item.use" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
               <Col span="8">
                  <Form-item prop="getType" label="取得时间">
                    <DatePicker type="date" v-model="item.getTime" :disabled="!item.edit"></DatePicker>
                   
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="getPrice" label="取得价格">
                      <Input v-model="item.getPrice" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="24">
                <Form-item prop="introduction" label="上传权属资料">
                   <vui-upload
                    :ref="`upload${index}`"
                    @on-getPictureList="getPictureList($event, index)"
                    :total="3"
                    :disabled="!item.edit"
                    :multiple="false"
                    :hint="'支持拓展名称：png jpg'"
                    :size="[80,80]"
                    ></vui-upload>
                </Form-item>
            </Col>
          </Row>
          <!-- <div class="tc pd20" v-if="item.edit">
            <Button type="primary" @click="handleSave(item, index)">保存</Button>
          </div> -->
    </Form>
    <!-- <Button type="primary" ghost icon="md-add" class="mt20 mb40 btn-light-primary" @click="handleAdd">添加</Button> -->
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
        <Button type="primary"v-if="isLoading">保存</Button>
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
      data: [
        {
          status: true,
          rightHolderName: '', // 房屋权利人姓名
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
          edit: false
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
      isLoading: true
    }
  },
  methods: {
     //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findRightToUseHousingInfo',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            parentId: this.id
        }).then(response => {
             if (response.code == 200) {
                 this.isLoading = false
              this.title = response.data.rightToUseHousingInfoName
              if (response.data.rightToUseHousingInfo.length) {
                this.data = response.data.rightToUseHousingInfo
                this.data.forEach(e => {
                    e.edit = false
                })
              }
            if (!response.data.textPreview.textPreview) {
                response.data.textPreview.textPreview = `房屋权利人姓名（），房屋使用人姓名（），建筑物名称（），房屋类别（），房屋总层数（），建筑结构（），占地面积（）平方米，建筑面积（）平方米，房屋安全等级（），房屋安全状况（），使用情况（），取得时间（），取得价格（）。`
            }
              this.textPreview = response.data.textPreview
              this.textPreviewId = response.data.textPreview.id
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
          rightHolderName: '', // 房屋权利人姓名
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
                            this.$Message.success('删除成功')
                            this.data.splice(index,1)
                        }
                    })
                } else {
                    this.$Message.success('删除成功')
                    this.data.splice(index,1)
                }
            },
            okText:'确定',
            cancelText:'取消'
        });
    },
    // 保存
    handleSave (item, index) {
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
                rightToUseHousingInfo: item
            }
            this.$api.post('/member-reversion/assetSeting/saveRightToUseHousingInfo', list).then(response => {
                if (response.code === 200) {
                    this.$Message.success('保存成功')
                    item.edit = false
                    this.handleInit(1)
                }
            })
          } else {
            this.$Message.error('请核对表单信息')
          }
        })
       
    },
    // 保存文字预览
    onSave () {
        this.textPreview.account = this.$user.loginAccount
        this.textPreview.yearId = this.yearId
        this.textPreview.parentId = this.id
        this.textPreview.templateId= this.$template.id
        this.textPreview.isComplete = this.data.length === 0 ? false : true
        this.textPreview.id = this.textPreviewId === '' || this.textPreviewId === undefined ? 0 : this.textPreviewId
        this.isLoading = true
        this.$api.post('/member-reversion/assetSeting/saveTextPreview', {textPreview: this.textPreview}).then(response => {
            if (response.code === 200) {
                this.$Message.success('保存成功')
                this.$emit('on-save')
                this.handleInit()
            }
        })
    },
  }
}
</script>

<style lang="scss" scoped>
.subtotal{
  font-size: 16px;
}
</style>
