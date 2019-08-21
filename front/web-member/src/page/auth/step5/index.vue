<template>
    <div>
      <Card class="layout pd20">
        <Title :title="title.baseName" class="mt50"></Title>
        <app-list ref="baseApp" :appData="baseAppData" @on-refresh="init"  @on-change="onBaseChange"/>
        <Title :title="title.commonName" class="mt50"></Title>
        <app-list ref="commonApp" :appData="commonAppData" @on-refresh="init"  @on-change="onCommonChange"/>
        <Title :title="title.highName" class="mt50"></Title>
        <Tabs :value="activeHieght" @on-click="heightTab" class="mt50">
            <TabPane label="全部" name="all" ></TabPane>
            <TabPane v-for="(item, index) in highType" :key="index" :label="item.userTypeName" :name="item.userType"></TabPane>
        </Tabs>
        <app-list ref="highApp" :appData="highAppData" @on-refresh="init" @on-change="onHighChange"/>
        <Title :title="title.serviceName" class="mt50"></Title>
        <Tabs :value="activeService" @on-click="serviceTab" class="mt50">
            <TabPane label="全部" name="all" ></TabPane>
            <TabPane v-for="(item, index) in serviceType" :key="index" :label="item.serviceTypeName" :name="item.serviceType"></TabPane>
        </Tabs>
        <app-list ref="highApp" :appData="serviceAppData" @on-refresh="init" @on-change="onServiceChange"/>
        <div class="tc pd20">
          <Button type="primary" @click="handleClickBack" class="back-btn mr20">返回上一步</Button>
          <Button type="primary" @click="handleClickNext">保存并下一步</Button>
        </div>
      </Card>
    </div>
</template>
<script>
import Title from '../components/title'
import appList from './components/app-list'
export default {
  components: {
    Title,
    appList
  },
  data: () => ({
    baseAppData: [],
    commonAppData: [],
    highAppData: [],
    highAppDatas: [],
    serviceAppData: [],
    serviceAppDatas: [],
    templateId: '',
    stepId: '',
    title: {
      baseName: '',
      commonName: '',
      highName: '',
      serviceName: '服务应用',
    },
    activeHieght: 'all',
    activeService: 'all',
    highType: [],
    serviceType: [],
  }),
  created () {
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
    this.init()
    this.initTitle()
    this.initHeightType()
    this.initServiceType()
  },
  methods: {
    onBaseChange (id) {
      this.baseAppData.forEach((e, index) => {
        if (e.appId === id) {
          e.isAdd = !e.isAdd
        }
      })
    },
    onCommonChange (id) {
      this.commonAppData.forEach((e, index) => {
        if (e.appId === id) {
          e.isAdd = !e.isAdd
        }
      })
    },
    onHighChange (id) {
      this.highAppDatas.forEach((e, index) => {
        if (e.appId === id) {
          e.isAdd = !e.isAdd
        }
      })
      this.heightTab(this.activeHieght)
    },
    onServiceChange (id) {
      this.serviceAppDatas.forEach((e, index) => {
        if (e.appId === id) {
          e.isAdd = !e.isAdd
        }
      })
      this.serviceTab(this.activeService)
    },
    initHeightType () {
      this.$api.post('/member/applicationCentrality/findUserTypeList', {}).then(response => {
        if (response.code === 200) {
          this.highType = response.data
        }
      })
    },
    initServiceType () {
      this.$api.post('/member/applicationCentrality/findServiceTypeList', {}).then(response => {
        if (response.code === 200) {
          this.serviceType = response.data
          this.serviceTypes = response.data
        }
      })
    },
    heightTab (name) {
      this.activeHieght = name
      if (name === 'all') {
        this.highAppData = this.highAppDatas
      } else {
        this.highAppData = this.highAppDatas.filter((item) => {
          if (item.userType === name) {
            return item
          }
        })
      }
    },
    serviceTab (name) {
      this.activeService = name
      if (name === 'all') {
        this.serviceAppData = this.serviceAppDatas
      } else {
        this.serviceAppData = this.serviceAppDatas.filter(item => item.serviceType === name)
      }
    },
    initTitle () {
      this.$api.post('/member-reversion/user/appSettings/findAppTitle', {
        account: this.$user.loginAccount,
        templateId: this.templateId
      }).then(response => {
        if (response.code === 200) {
          this.title = Object.assign(this.title, response.data)
        }
      })
    },
    init () {
      // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
      let url = this.templateId === '0' ? '/member-reversion/appSettings/findAppSettingsInfo' : '/member-reversion/user/appSettings/findAppSettingsInfo'
      this.$api.post(url, {
        account: this.$user.loginAccount,
        templateId: this.templateId
      }).then(response => {
        this.baseAppData = []
        this.commonAppData = []
        this.highAppData = []
        this.highAppDatas = []
        this.serviceAppData = []
        this.serviceAppDatas = []
        if (response.code === 200) {
          response.data.forEach(element => {
            if (element.level === 0) {
              this.baseAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id
              })
            } else if (element.level === 1) {
              this.commonAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id
              })
            } else if (element.level === 2) {
              this.highAppDatas.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                userType: element.userType
              })
              this.highAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                userType: element.userType
              })
            } else if (element.level === 3) {
              this.serviceAppDatas.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                serviceType: element.serviceType
              })
              this.serviceAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                serviceType: element.serviceType
              })
            }
          })
        }
      })
    },
    handleClickBack () {
      this.$router.push({
        path: '/auth/step4',
        query: {
          templateId: this.templateId
        }
      })
    },
    handleClickNext () {
      let data = {
        account: this.$user.loginAccount,
        templateId: this.templateId,
        baseApp: this.$refs.baseApp.appDatas,
        commonApp: this.$refs.commonApp.appDatas,
        // highApp: this.$refs.highApp.appDaappIdtas,
        highApp: this.highAppDatas,
        serviceApp: this.serviceAppDatas,
        userType: '1',
        loginStep:{
          id: this.stepId ? this.stepId : 0,
          account: this.$user.loginAccount,
          templateId: this.templateId,
          step: 5
        }
      }
      // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
      let url = this.templateId === '0' ? '/member-reversion/appSettings/saveOrCancelAppInfo' : '/member-reversion/user/appSettings/saveOrCancelAppInfo'
      this.$api.post(url, data).then(response => {
          if (response.code === 200) {
              this.$Message.success('保存成功！')
              this.$router.push({
                path: '/auth/step6',
                query: {
                  templateId: this.templateId
                }
              })
          }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.back-btn {
  background-color: #9B9B9B;
  border-color: #9B9B9B;
  &:hover {
    background-color: #9B9B9B;
    border-color: #9B9B9B;
  }
}
.layout {
  width: 1000px;
  margin: auto;
  margin-top: 20px;
}
</style>
