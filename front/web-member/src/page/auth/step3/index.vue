<template>
    <div class="new-authlayout">
      <Card class="pd20">
        <Title title="展示模板设置" class="mt10"></Title>
        <template-components ref="template"></template-components>
        <Title title="网站栏目设置"  subTitle="（设置栏目将帮助您建立栏目选单自由设置和发布栏目专属信息）" class="mt10"></Title>
        <column-setting ref="column"></column-setting>
        <div class="tc pd30">
          <Button type="primary" @click="handleBack" class="back-btn mr20">返回上一步</Button>
          <Button type="primary" @click="handleNext">保存并下一步</Button>
        </div>
      </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import templateComponents from './components/template'
import columnSetting from './components/columnSetting'
export default {
  components: {
    Title,
    templateComponents,
    columnSetting
  },
  data: () => ({
    templateId: '',
    stepId: ''
  }),
  created() {
    this.templateId = this.$route.query.templateId
    // 查询模板 步骤
    this.$api.post('/member-reversion/realStep/findStep', {
          account: this.$user.loginAccount,
          templateId: this.templateId
      }).then(response => {
          if (response.code === 200) {
              // reload 存在， 说明是点击模板后查询的 如果 response.data 存在 那么则需要修改url
              if (response.data) {
                  this.stepId = response.data.id
              }
          }
      })
    this.handleInit()
  },
  methods: {
    handleBack () {
      this.$router.push({
        path: '/auth/step2',
        query: {
          templateId: this.templateId
        }
      })
    },
    // 点击下一步保存
    handleNext () {
      let columnData = this.$refs['column'].submit()
      if (columnData) {
        let list = {
          account: this.$user.loginAccount,
          templateType: this.$refs['template'].active,
          columnSetting: columnData,
          templateId: this.templateId,
          loginStep:{
            id: this.stepId ? this.stepId : 0,
            account: this.$user.loginAccount,
            templateId: this.templateId,
            step: 3
          }
        }
        // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
        let url = this.templateId === '0' ? '/member-reversion/columnSetting/saveOrUpdateSettingInfo' : '/member-reversion/user/columnSetting/saveOrUpdateSettingInfo'
        this.$api.post(url, list).then(response => {
          console.log('save res', response)
          if (response.code === 200) {
            this.$Message.success('保存成功！')
            this.$router.push({
              path: '/auth/step4',
              query: {
                templateId: this.templateId
              }
            })
          }
        })
      }
    },
    handleInit () {
      // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
      let url = this.templateId === '0' ? '/member-reversion/columnSetting/findColumnSettingInfo' : '/member-reversion/user/columnSetting/findColumnSettingInfo'
      this.$api.post(url, {
        account: this.$user.loginAccount,
        templateId: this.templateId
      }).then(response => {
        console.log('init res', response)
        if (response.code === 200) {
          this.$refs['template'].init(response.data.templateType)
          this.$refs['column'].init(response.data.columnSetting)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.new-authlayout {
  width: 1000px;
  margin: auto;
  margin-top: 20px;
}
.back-btn {
  background-color: #9B9B9B;
  border-color: #9B9B9B;
  &:hover {
    background-color: #9B9B9B;
    border-color: #9B9B9B;
  }
}
</style>
