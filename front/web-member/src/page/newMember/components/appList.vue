<template>
  <div class="bg-white member-app-list">
    <!-- 应用列表 -->
    <Card :bordered="false">
       <Row class="pt10" type="flex" align="middle">
         <Col span="15" class="app-list-title">
          <img src="../../../img/app-list-icon1.png" alt="" class="mr10" width="20px" height="20px">
          <span>我的应用</span>
         </Col>
         <Col span="9"><Button type="text" size="large" @click="onAdd" icon="md-add">添加</Button></Col>
       </Row>
       <p class="pt20 list-title">基础应用</p>
       <Row>
         <Col class="list" span="12" v-for="(item, index) in baseAppData" v-show="item.isAdd" :key="index">
          <Tooltip placement="top" :content="item.appName" :delay="1000">
            <p class="ell app" @click="handleBaseAppData(item, index)">{{item.appName}}</p>
          </Tooltip>
         </Col>
       </Row>
       <p class="pt20 list-title">通用应用</p>
       <Row>
         <Col class="list" span="12" v-for="(item, index) in commonAppData" v-show="item.isAdd" :key="index">
          <Tooltip placement="top" :content="item.appName" :delay="1000">
            <p class="ell app" @click="handleCommonAppData(item)">{{item.appName}}</p>
          </Tooltip>
         </Col>
       </Row>
       <p class="pt20 list-title">高级应用</p>
       <Row>
         <Col class="list" span="12" v-for="(item, index) in highAppData" v-show="item.isAdd" :key="index">
          <Tooltip placement="top" :content="item.appName" :delay="1000">
            <p class="ell app" @click="handleHighAppData(item)" >{{item.appName}}</p>
          </Tooltip>
         </Col>
       </Row>
    </Card>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        templateId: '',
        baseAppData: [],
        commonAppData: [],
        highAppData: [],
      }
    },
    created() {
      this.$api.post('/member-reversion/user/userTemplateManage/find', {
            account: this.$user.loginAccount
        }).then(response => {
            if (response.code === 200) {
                if (response.data.userTemplate) {
                    this.templateId = response.data.userTemplate.templateId
                    this.init()
                }
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    methods: {
      init () {
        // url若为0则调用管理员侧的接口，不为0则调用用户侧的接口
        let url = this.templateId === '0' ? '/member-reversion/appSettings/findAppSettingsInfo' : '/member-reversion/user/appSettings/findAppSettingsInfo'
        this.$api.post(url, {
          account: this.$user.loginAccount,
          templateId: this.templateId
        }).then(response => {
          console.log('app init response', response)
          this.baseAppData = []
          this.commonAppData = []
          this.highAppData = []
          if (response.code === 200) {
            response.data.forEach(element => {
              if (element.level === 0) {
                this.baseAppData.push({
                  icon: element.icon,
                  appName: element.appName,
                  price: element.cost,
                  number: element.number,
                  isAdd: element.checked,
                  url: element.url,
                  appId: element.id
                })
              } else if (element.level === 1) {
                this.commonAppData.push({
                  icon: element.icon,
                  appName: element.appName,
                  price: element.cost,
                  number: element.number,
                  isAdd: element.checked,
                  url: element.url,
                  appId: element.id
                })
              } else if (element.level === 2) {
                this.highAppData.push({
                  icon: element.icon,
                  appName: element.appName,
                  price: element.cost,
                  number: element.number,
                  isAdd: element.checked,
                  url: element.url,
                  appId: element.id
                })
              }
            })
          }
          console.log('highAppData', this.highAppData)
        }).catch(error => {
          this.$Message.error('服务器异常！')
        })
      },
      onAdd () {
        this.$router.push('/center')
      },
      // 点击基础应用
      handleBaseAppData (item, index) {
        this.$router.push(`/auth/step7?templateId=${this.templateId}&active=${item.url}`)
      },
      // 点击 通用应用
      handleCommonAppData (item) {
        this.$router.push(`${item.url}`)
      },
      // 点击 高级应用
      handleHighAppData (item) {
        if (item.appName == '物种百科') {
          window.open(`${window.location.origin}/wiki/`)
          // window.location.href = `${window.location.origin}/wiki/`
        } else if (item.appName == '我的地图') {
          window.open(`${window.location.origin}/map/`)
          // window.location.href = `${window.location.origin}/map/`
        } else {
          this.$router.push(`${item.url}`)
        }
      }
    }
  }
</script>
<style lang="scss">
.member-app-list{
  color: #4A4A4A;
  .app-list-title{
    span{
      font-family: PingFangSC-Semibold;
      font-weight: 700;
      vertical-align: middle;
    }
  }
  .list-title{
    font-family: PingFangSC-Semibold;
    font-weight: 700;
    border-bottom: 1px solid #eee;
    padding-bottom: 8px;
    margin-bottom: 8px;
  }
  .list{
    .app{
      font-weight: 400;
      padding: 5px;
      max-width: 110px;
      font-family: PingFangSC-Regular;
      cursor: pointer;
      &:hover{
        color:#00c587;
      }
    }
  }
}
</style>

