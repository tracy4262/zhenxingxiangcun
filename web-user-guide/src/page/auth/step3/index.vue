<template>
    <div class="new-authlayout">
      <Card class="pd20">
        <p class="pb20 template-name">{{$template.templateName}}</p>
        <Title :title="title"  subTitle="（设置栏目将帮助您建立栏目选单自由设置和发布栏目专属信息）" class="mt10"></Title>
        <column-setting ref="column"></column-setting>
        <div class="tc pd30">
          <Button type="primary" @click="handleClickBack" class="back-btn mr20">返回上一步</Button>
          <Button type="primary" @click="handleNext">保存并下一步</Button>
        </div>
      </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import columnSetting from './components/columnSetting'
export default {
  components: {
    Title,
    columnSetting
  },
  data: () => ({
    account: '',
    title: '网站栏目设置',
  }),
  created() {
    this.account = this.$user.loginAccount
    this.handleInit()
  },
  methods: {
    // 上一步
    handleClickBack () {
       this.$router.push('/auth/step2')
    },
    // 点击下一步保存
    handleNext () {
      let list = {
        templateId: this.$template.id,
        account: this.account,
        columnSetting:  this.$refs['column'].data,
        loginStep: {
          id: this.$step.id,
          account: this.$user.loginAccount,
          templateId: this.$template.id,
          step: 3
        }
      }
      this.$api.post('/member-reversion/columnSetting/saveOrUpdateSettingInfo', list).then(response => {
        console.log(response)
        if (response.code == 200) {
          this.$Message.success('保存成功')
          this.$router.push('/auth/step4')
        }
      })
      
    },
    handleInit () {
      this.$api.post('/member-reversion/columnSetting/findColumnSettingInfo',{account: this.account, templateId: this.$template.id}).then(response => {
        console.log(response)
        if (response.code == 200) {
          this.$refs['template']
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
