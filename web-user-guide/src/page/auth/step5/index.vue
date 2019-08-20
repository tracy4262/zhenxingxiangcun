<template>
    <div>
      <Card class="layout pd20">
        <p class="pb20 template-name">{{$template.templateName}}</p>
        <Title :title="title.baseName" edit :id="'0'" idNumber url="/member-reversion/appSettings/updateAppTitle">
          <Button type="primary" @click="selectedAll('baseAppData')" size="small" class="mr10">全选</Button>
          <Button type="primary" @click="inverseSelection('baseAppData')" size="small">反选</Button>
        </Title>
        <app-list ref="baseApp" :appData="baseAppData" :templateId="$template.id" @on-refresh="init" @on-change="onBaseChange"/>
        
        <Title :title="title.commonName" edit :id="'1'" idNumber url="/member-reversion/appSettings/updateAppTitle">
          <Button type="primary" @click="selectedAll('commonAppData')" size="small" class="mr10">全选</Button>
          <Button type="primary" @click="inverseSelection('commonAppData')" size="small">反选</Button>
        </Title>
        <app-list ref="commonApp" :appData="commonAppData" :templateId="$template.id" @on-refresh="init" @on-change="onCommonChange"/>

        <Title :title="title.highName" edit :id="'2'" idNumber url="/member-reversion/appSettings/updateAppTitle" class="mt50">
          <Button type="primary" @click="selectedAll('highAppDatas')" size="small" class="mr10">全选</Button>
          <Button type="primary" @click="inverseSelection('highAppDatas')" size="small">反选</Button>
        </Title>
        <Tabs :value="activeHieght" @on-click="heightTab" class="mt50">
            <TabPane label="全部" name="all" ></TabPane>
            <TabPane v-for="(item, index) in highType" :label="item.userTypeName" :name="item.userType"></TabPane>
        </Tabs>
        <app-list ref="highApp" :appData="highAppData" :templateId="$template.id" @on-change="onHighChange" @on-refresh="init" />
        
        <Title title="服务应用" class="mt50">
          <Button type="primary" @click="selectedAll('serviceAppDatas')" size="small" class="mr10">全选</Button>
          <Button type="primary" @click="inverseSelection('serviceAppDatas')" size="small">反选</Button>
        </Title>
        <Tabs :value="activeService" @on-click="serviceTab" class="mt50">
            <TabPane label="全部" name="all" ></TabPane>
            <TabPane v-for="(item, index) in serviceType" :label="item.serviceTypeName" :name="item.serviceType"></TabPane>
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
    title: {},
    baseAppData: [],
    commonAppData: [],
    highAppData: [],
    highAppDatas: [],
    serviceAppData: [],
    serviceAppDatas: [],
    activeHieght: 'all',
    activeService: 'all',
    highType: [],
    serviceType: [],
  }),
  created () {
    // 查询名称
    this.getName()
    this.init()
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
    // 权限
    selectedAll (ref) {
      // this.$refs[ref].selectedAll()
      if (ref == 'baseAppData') {
        this.baseAppData.forEach((e, index) => {
          e.isAdd = true
        })
      } else if (ref == 'commonAppData') {
        this.commonAppData.forEach((e, index) => {
          e.isAdd = true
        })
      } else if (ref == 'highAppDatas') {
        this.highAppDatas.forEach((e, index) => {
          e.isAdd = true
        })
        this.heightTab(this.activeHieght)
      } else if (ref == 'serviceAppDatas') {
        this.serviceAppDatas.forEach((e, index) => {
          e.isAdd = true
        })
        this.serviceTab(this.activeService)
      }
    },
    // 反选
    inverseSelection (ref) {
      // this.$refs[ref].inverseSelection()
      if (ref == 'baseAppData') {
        this.baseAppData.forEach((e, index) => {
          e.isAdd = false
        })
      } else if (ref == 'commonAppData') {
        this.commonAppData.forEach((e, index) => {
          e.isAdd = false
        })
      } else if (ref == 'highAppDatas') {
        this.highAppDatas.forEach((e, index) => {
          e.isAdd = false
        })
        this.heightTab(this.activeHieght)
      } else if (ref == 'serviceAppDatas') {
        this.serviceAppDatas.forEach((e, index) => {
          e.isAdd = false
        })
        this.serviceTab(this.activeService)
      }
    },
    getName () {
      this.$api.post('/member-reversion/appSettings/findAppTitle', {
        account: this.$user.loginAccount,
        templateId: this.$template.id
      }).then(response => {
        if (response.code === 200) {
          this.title = response.data
        }
      })
    },
    init () {
      this.$api.post('/member-reversion/appSettings/findAppSettingsInfo', {
        account: this.$user.loginAccount,
        templateId: this.$template.id
      }).then(response => {
        this.baseAppData = []
        this.commonAppData = []
        this.highAppData = []
        this.highAppDatas = []
        if (response.code === 200) {
          response.data.forEach(element => {
            if (element.level === 0) {
              this.baseAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                userType: element.userType
              })
            } else if (element.level === 1) {
              this.commonAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                userType: element.userType
              })
            } else if (element.level === 2) {
              this.highAppData.push({
                icon: element.icon,
                appName: element.appName,
                price: element.cost,
                number: element.number,
                isAdd: element.checked,
                appId: element.id,
                userType: element.userType
              })
              this.highAppDatas.push({
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
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    handleClickBack () {
      this.$router.push('/auth/step4')
    },
    handleClickNext () {
      let data = {
        baseApp: this.baseAppData,
        commonApp: this.commonAppData,
        highApp: this.highAppDatas,
        serviceApp: this.serviceAppDatas,
        account: this.$user.loginAccount,
        templateId: this.$template.id,
        userType: this.$user.userType,
        loginStep: {
          id: this.$step.id,
          account: this.$user.loginAccount,
          templateId: this.$template.id,
          step: 5
        }
      }
      this.$api.post('/member-reversion/appSettings/saveOrCancelAppInfo', data).then(response =>{
        if (response.code === 200) {
          this.$Message.success('保存成功')
          this.$router.push('/auth/step6')
        } else {
          this.$Message.error('保存失败')
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
