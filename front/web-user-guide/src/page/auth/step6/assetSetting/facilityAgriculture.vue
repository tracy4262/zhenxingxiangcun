<template>
<div class="pd20">
      <Title :title="title" edit :id="id" :yearId="yearId" ></Title>
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
        <Button type="primary"v-if="isLoading">保存</Button>
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
      title: '',
      isLoading:true
    }
  },
  methods: {
    //  初始化数据
    handleInit (type) {
        this.$api.post('/member-reversion/assetSeting/findFacilityAgricultureInfo',{
            account: this.$user.loginAccount,
            templateId: this.$template.id,
            yearId: this.yearId,
            parentId: this.id,
        }).then(response => {
             if (response.code == 200) {
              this.title = response.data.facilityAgricultureInfoName
              response.data.gardening.length ? this.$refs['gardening'].getData(response.data.gardening): ''
              response.data.aquaculture.length ? this.$refs['aquaculture'].getData(response.data.aquaculture): ''
              response.data.husbandry.length ? this.$refs['husbandry'].getData(response.data.husbandry): ''
              response.data.facilities.length ? this.$refs['facilities'].getData(response.data.facilities): ''
              if (!response.data.textPreview.textPreview) {
                response.data.textPreview.textPreview = `设施农业总计面积（）平方米，投资额（）元，其中设施园艺（）平方米，投资额（）元；水产养殖（）平方米，投资额（）元；畜牧养殖（）平方米，投资额（）元；设施食用菌（）平方米，投资额（）元。`
              }
              this.textPreview = response.data.textPreview
              this.textPreviewId = response.data.textPreview.id
              this.isLoading = false
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
      this.total = num
      this.areaTotal = area
    },
    // 保存文字预览
    onSave () {
        this.textPreview.account = this.$user.loginAccount
        this.textPreview.yearId = this.yearId
        this.textPreview.parentId = this.id
        this.textPreview.templateId= this.$template.id
        this.textPreview.isComplete = true
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
