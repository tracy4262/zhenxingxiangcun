<template>
<div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" :templateId="templateId" @left-refresh="leftRefresh" ></Title>
    <div class="pd20">
      <Form :label-width="80" label-position="left" ref="data">
        <Row  :gutter="38">
            <Col span="8">
              <FormItem label="权限">
                <Switch class="ml20"  size="large" v-model="status">
                  <span slot="open">公开</span>
                  <span slot="close">隐藏</span>
                </Switch>
              </FormItem>
            </Col>
        </Row>
      </Form>
    </div>
    <!-- `type`   '0 设施园艺 1水产养殖 2 畜牧养殖 3设施食用菌', -->
    <agriculture-list title="设施园艺" type="0" ref="gardening" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="水产养殖" type="1" ref="aquaculture" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="畜牧养殖" type="2" ref="husbandry" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
    <agriculture-list title="设施食用菌" type="3" ref="facilities" @on-init="handleInit"  @on-numAdd="handleNumAdd" :yearId="yearId" :id="id"></agriculture-list>
      <div style="background: rgb(0, 197, 135); width: 925px; margin-left: -36px;" class="mt40 mb30">
        <div class="tr" style="padding: 20px 36px; color: #fff; font-size: 18px;">
          总计：面积 {{areaTotal}} 平方千米，投资额 {{total}} 元
        </div>
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
import {numAdd} from '~utils/utils'
import agricultureList from './components/agricultureList'
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
    agricultureList
  },
  data () {
    return {
      total: 0,
      areaTotal: 0,
      status: true, 
      textPreview: {},
      title: '设施农业信息',
      templateId: '',
      index: 0,
      isLoading: true
    }
  },
  created () {
    this.templateId = this.$route.query.templateId
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
    //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findFacilityAgricultureInfo',{
            account: this.$user.loginAccount,
            yearId: this.yearId,
            parentId: this.id,
            templateId: this.templateId
        }).then(response => {
             if (response.code == 200) {
              this.isLoading = false
              // this.title = response.data.facilityAgricultureInfoName
              response.data.gardening.length ? this.$refs['gardening'].getData(response.data.gardening): ''
              response.data.aquaculture.length ? this.$refs['aquaculture'].getData(response.data.aquaculture): ''
              response.data.husbandry.length ? this.$refs['husbandry'].getData(response.data.husbandry): ''
              response.data.facilities.length ? this.$refs['facilities'].getData(response.data.facilities): ''
              if (!type) {
                this.textPreview = response.data.textPreview
                this.textPreviewId = response.data.textPreview.id
              }
            }
        })
    },
     // 计算总产值
    handleNumAdd () {
      // 面积 
      let area = 0
      area = numAdd(parseFloat(area ? area : 0).toFixed(2), parseFloat(this.$refs['gardening'].areaTotal ? this.$refs['gardening'].areaTotal : 0).toFixed(2))
      area = numAdd(parseFloat(area ? area : 0).toFixed(2), parseFloat(this.$refs['aquaculture'].areaTotal ? this.$refs['aquaculture'].areaTotal : 0).toFixed(2))
      area = numAdd(parseFloat(area ? area : 0).toFixed(2), parseFloat(this.$refs['husbandry'].areaTotal ? this.$refs['husbandry'].areaTotal : 0).toFixed(2))
      area = numAdd(parseFloat(area ? area : 0).toFixed(2), parseFloat(this.$refs['facilities'].areaTotal ? this.$refs['facilities'].areaTotal : 0).toFixed(2))
      // 投资额 husbandry
      let num = 0
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['gardening'].total ? this.$refs['gardening'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['aquaculture'].total ? this.$refs['aquaculture'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['husbandry'].total ? this.$refs['husbandry'].total : 0).toFixed(2))
      num = numAdd(parseFloat(num ? num : 0).toFixed(2), parseFloat(this.$refs['facilities'].total ? this.$refs['facilities'].total : 0).toFixed(2))
      this.total = num.toFixed(2)
      this.areaTotal = area
      if (this.index > 3) { 
        //  有4个模块，每个模块取到数据之后会调用计算的方法， index+1 ，
        // 当4个都调用完成后 index > 3 时，即是用户在修改数据时候，那么就调用文字预览的拼接方法
        this.changePreview()
      } else {
        this.index += 1
      }
    },
    // 文字预览
    changePreview () {
      let str = ''
      if (this.areaTotal && this.total) {
        str += `设施农业总计面积${this.areaTotal}平方米，投资额${this.total}元，其中，设施园艺面积${this.$refs['gardening'].areaTotal}平方米，投资额${this.$refs['gardening'].total}元；其中，水产养殖面积${this.$refs['aquaculture'].areaTotal}平方米，投资额${this.$refs['aquaculture'].total}元；其中，畜牧养殖面积${this.$refs['husbandry'].areaTotal}平方米，投资额${this.$refs['husbandry'].total}元；其中，设施食用菌面积${this.$refs['facilities'].areaTotal}平方米，投资额${this.$refs['facilities'].total}元；`
      } else {
        if (this.total) {
          str += `设施农业总投资额${this.total}元，其中，设施园艺投资额${this.$refs['gardening'].total}元；其中，水产养殖投资额${this.$refs['aquaculture'].total}元；其中，畜牧养殖投资额${this.$refs['husbandry'].total}元；其中，设施食用菌投资额${this.$refs['facilities'].total}元；`
        }
        if (this.areaTotal) {
          str += `设施农业总计面积${this.areaTotal}平方米，其中，设施园艺面积${this.$refs['gardening'].areaTotal}平方米；其中，水产养殖面积${this.$refs['aquaculture'].areaTotal}平方米；其中，畜牧养殖面积${this.$refs['husbandry'].areaTotal}平方米；其中，设施食用菌面积${this.$refs['facilities'].areaTotal}平方米；`
        }
      }
      if (str) {
        str = `${str.substring(0, str.length - 1)}。`
      }
      this.textPreview.textPreview = str
    },
    // 保存文字预览
    onSave () {
        this.isLoading = true
        this.textPreview.account = this.$user.loginAccount
        this.textPreview.yearId = this.yearId
        this.textPreview.parentId = this.id
        this.textPreview.isComplete = true
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
