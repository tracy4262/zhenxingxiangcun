<template>
  <div>
    <Form style="width:600px;">
      <view-panel
          title="自定义表单"
          :labelWtdit="180"
          :data="safeFormData"
          @on-data="handleGetSafeForm"
          @on-add="handleAddBtn"></view-panel>
    </Form>
      <div class="tc">
        <Button type="primary" @click="onSave">保存</Button>
      </div>
      <!-- 添加面板 -->
      <add-panel ref="addPanel" @on-save="handleControlBtn"></add-panel>
  </div>
</template>
<script>
import addPanel from '../../../newMember/components/add-panel'
import viewPanel from '../../../newMember/components/view-panel'
export default {
  components: {
    addPanel,
    viewPanel
  },
  data () {
    return {
      safeFormData: [],
      yearId: '',
      wikiId: '',
      id: ''
    }
  },
  created() {
    if (this.$route.query.yearId) {
      this.yearId = this.$route.query.yearId
      this.$parent.$parent.yearId = this.$route.query.yearId
    }
    if (this.$route.query.year) {
      this.$parent.$parent.year = this.$route.query.year
    }
    if (this.$route.query.id) {
      this.wikiId = this.$route.query.id
      this.$parent.$parent.id = this.$route.query.id
      this.init()
    }
    if (this.$route.query.name) {
      this.$parent.$parent.name = this.$route.query.name
    }
  },
  methods: {
    // 初始化查询数据
    init () {
      this.$api.post('/shop/plant/findPlantCustomInfo',).then(response => {
        if (response.code === 200) {
          this.safeFormData = response.data.length ? response.data[0].custom : []
          this.id = response.data.length ? response.data[0].id : ''
        }
      })
    },
    // 添加
    handleAddBtn () {
      this.$refs.addPanel.showAddPanel = true
    },
    // 添加组件 保存时候的回调
    handleControlBtn (data) {
        this.safeFormData.push(data)
    },
    // 取商品安全数据
    handleGetSafeForm (data) {
      console.log(data)
    },
    // 保存
    onSave () {
      if (this.safeFormData.length) {
        console.log(this.safeFormData)
        let data = {
          custom: this.safeFormData,
          wikiId: this.wikiId,
          yearId: this.yearId,
          id: this.id,
          account: this.$user.loginAccount
        }
        this.$api.post('/shop/plant/saveOrUpdatePlantCustomInfo', data).then(response => {
          if (response.code === 200) {
            this.$Message.success('保存成功！')
            this.init()
          } else {
            this.$Message.error('保存失败！')
          }
        })
      } else {
        this.$Message.warning('请添加！')
      }
    }
  },
}
</script>
<style>

</style>

