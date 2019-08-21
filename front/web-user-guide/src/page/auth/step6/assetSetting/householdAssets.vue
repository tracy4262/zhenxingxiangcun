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
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item  prop="rightHolderName" label="权利人姓名">
                    <!-- 单选 默认为会员名称，可以从人口信息花名册中选择-->
                    <Select v-model="item.rightHolderName" style="width: 100%" :disabled="!item.edit">
                        <Option v-for="(item, index) in rightHolderNames" :value="item.name" :key="index">{{item.name}}</Option>
                    </Select>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="genericName" label="通用名称">
                      <!-- 从通用商品名中选择 -->
                  <vui-product-name :disabled="!item.edit" :values="item.genericName" @handle-get-result="handleResult($event, item, index)"></vui-product-name>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="brandName" label="品牌名称">
                    <Input v-model="item.brandName" :maxlength="20" :disabled="!item.edit"></Input>
                  </Form-item>
              </Col>
          </Row>
          <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="model" label="规格型号">
                      <Input v-model="item.model" :maxlength="20" :disabled="!item.edit"></Input>       
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="quantity" label="数量">
                      <Input v-model="item.quantity" :maxlength="20" :disabled="!item.edit">
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="unit" label="单位">
                      <Input v-model="item.unit" :maxlength="20" :disabled="!item.edit">
                      </Input>
                  </Form-item>
              </Col>
            </Row>
            <Row :gutter="16">
              <Col span="8">
                  <Form-item prop="univalent" label="单价">
                      <Input v-model="item.univalent" :maxlength="20" :disabled="!item.edit">
                      <span slot="append">元</span>
                      </Input>
                  </Form-item>
              </Col>
              <Col span="8">
                  <Form-item prop="totalPrice" label="总值">
                     <Input v-model="item.totalPrice" :maxlength="20" :disabled="!item.edit">
                     <span slot="append">元</span>
                     </Input>
                  </Form-item>
              </Col>
          </Row>
    </Form>
    </div>
    <Title title="文字预览"></Title>
    <div class="pd20 pt30">
      <Input type="textarea" v-model="textPreview.textPreview" :autosize="{minRows: 3,maxRows: 5}"></Input>
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
import vuiProductName from '~components/vui-productName'
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
    vuiProductName
  },
  data () {
    return {
      total: 0,
      status: true, 
      textPreview: {},
      title: '',
      rightHolderNames: [],
      data: [
        {
          status: true,
          rightHolderName: this.displayName, // 权利人姓名
          genericName: '', // 通用名称
          brandName: '', // 品牌名称
          model: '', // 规格型号
          quantity: '', // 数量
          unit: '', // 单位
          univalent: '', // 单价
          totalPrice: '', // 总值
          edit: false
        }
      ],
      ruleInline: {
        univalent: [
          {validator: isMoney3, trigger: 'blur'},
        ],
        totalPrice: [
          {validator: isMoney3, trigger: 'blur'},
        ]
      },
      activeIndex: 0,
      textPreviewId: 0,
      displayName:'',
      isLoading: true
    }
  },
  created() {
    this.$user.displayName ? this.displayName = this.$user.displayName : ''
    this.handleSelect()
  },
  methods: {
    // 取下拉数据
    handleSelect () {
        let list = {
            user_id: this.$user.loginAccount,
            year_id: this.yearId
        }
        this.$api.post('/member-reversion/administrationDivision/findRoster', list).then(response => {
            if (response.code === 200) {
            this.rightHolderNames = response.data
            this.rightHolderNames.unshift({name: this.displayName})
            }
        })
    },
    //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findHouseholdAssetsInfo',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            parentId: this.id
        }).then(response => {
             if (response.code == 200) {
                 this.isLoading = false
              this.title = response.data.householdAssetsInfoName
              if (response.data.householdAssetsInfo.length) {
                this.data = response.data.householdAssetsInfo
                this.data.forEach(e => {
                    e.edit = false
                })
              }
            if (!response.data.textPreview.textPreview) {
                response.data.textPreview.textPreview = `权利人姓名（），通用名称（），品牌名称（），规格型号（），数量（），单位（），单价（）元，总值（）元。`
            }
              this.textPreview = response.data.textPreview
              this.textPreviewId = response.data.textPreview.id
            }
        })
    },
    handleResult ($event, item, index) {
        this.data[index].genericName = $event
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
