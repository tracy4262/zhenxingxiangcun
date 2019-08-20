<template>
	<div style="background: #F9F9F9;" class="ivu-app-center">
		<!-- 导航栏 -->
		<Affix>
			<top :address="false"></top>
		</Affix>
      <div :style="{'min-height': height}">
          <div class="layouts">
             <Row  type="flex" align="middle">
              <Col span="16">
                <Breadcrumb class="pd20">
                  <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                  <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
                  <BreadcrumbItem>应用中心</BreadcrumbItem>
                </Breadcrumb>
              </Col>
              <Col span="6" offset="2">
              <Input placeholder="请输入你要查找的应用" v-model="keyword" search @on-search="onSearch"></Input>
              </Col>
            </Row>
            
            <Row :gutter="16">
              <Col span="5">
                <Card :bordered="false">
                    <p style="font-size:16px;" class="pt20 pl10">
                      <img src="../../img/app-list-icon1.png" alt=""class="mr10" width="20px" height="20px"> 
                      应用分类
                    </p>
                    <div class="pl30 pt20 pb20">
                      <Button type="text"  size="large" :class="active === 1 ? 't-green' : ''" @click="handleSelected(1)">推荐应用</Button>
                    </div>
                      <div class="pl30 pb20">
                      <Button type="text"  size="large" :class="active === 2 ? 't-green' : ''" @click="handleSelected(2)">基础设置</Button>
                    </div>
                    <div class="pl30 pb20">
                      <Button type="text"  size="large" :class="active === 3 ? 't-green' : ''" @click="handleSelected(3)">通用应用</Button>
                    </div>
                    <div class="pl30 pb20">
                      <Button type="text"  size="large" :class="active === 4 ? 't-green' : ''" @click="handleSelected(4)">高级应用</Button>
                    </div>
                    <div class="pl30 pb20">
                      <Button type="text"  size="large" :class="active === 5 ? 't-green' : ''" @click="handleSelected(5)">服务应用</Button>
                    </div>
                </Card>
                <Card :bordered="false" class="mt20 mb20 app-ranking">
                    <p style="font-size:16px;" class="pt20 pl10">
                      <img src="../../img/app-list-icon.png" alt=""class="mr10" width="20px" height="20px"> 
                      应用排行
                    </p>
                    <div class="tc pt20 pb20 mt20">
                      <Button :type="ranking ? 'primary' : 'text'" @click="switchRanking" class="mr10">最热排行</Button>
                      <Button :type="!ranking ? 'primary' : 'text'"  @click="switchRanking">最新上架</Button>
                    </div>
                    <div v-if="ranking">
                      <rankingList :data="rankingData"></rankingList>
                    </div>
                    <div v-else>
                      <rankingList :data="newData"></rankingList>
                    </div>
                </Card>
              </Col>
              <Col span="19" class="pb30">
                <Card :bordered="false" v-if="active === 0">
                  <!-- 搜索内容 -->
                  <app-list :data="searchData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
                <Card :bordered="false" v-if="active === 1">
                  <!-- 推荐内容 -->
                  <app-list :data="recommendData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
                <Card :bordered="false" v-if="active === 2">
                  <!-- 基础内容 -->
                  <app-list :data="baseData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
                <Card :bordered="false" v-if="active === 3">
                  <!-- 通用内容 -->
                  <app-list :data="commonData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
                <Card :bordered="false"  v-if="active === 4">
                  <Tabs :value="activeHieght" @on-click="heightTab">
                      <TabPane label="全部" name="all" ></TabPane>
                      <TabPane v-for="(item, index) in highType" :label="item.userTypeName" :name="item.userType"></TabPane>
                  </Tabs>
                  <!-- 高级应用 -->
                  <app-list :data="highAppData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
                <Card :bordered="false"  v-if="active === 5">
                  <Tabs :value="activeService" @on-click="serviceTab">
                      <TabPane label="全部" name="all" ></TabPane>
                      <TabPane v-for="(item, index) in serviceType" :label="item.serviceTypeName" :name="item.serviceType"></TabPane>
                  </Tabs>
                  <!-- 服务应用 -->
                  <app-list :data="serviceData" :templateId="templateId" @on-change="onChange"></app-list>
                </Card>
              </Col>
            </Row>
          </div>
      </div>
		<foot></foot>
  </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import appList from './components/appList'
import rankingList from './components/rankingList'
  export default {
    components: {
      top,
      foot,
      appList,
      rankingList
    },
    data () {
      return {
        height: 0,
        rankingData: [],
        newData: [],
        searchData: [],
        recommendData: [],
        baseData: [],
        commonData: [],
        highAppData: [],
        highAppDatas: [],
        highType: [],
        activeService: 'all',
        serviceType: [{userTypeName: '999', userType: '5555'}],
        serviceData: [],
        serviceDatas: [],
        activeHieght: 'all',
        active: 1,
        ranking: true,
        keyword: '',
        templateId: ''
      }
    },
    created() {
      // 初始化数据
      this.$api.post('/member-reversion/realStep/findEnableStep', {
					account: this.$user.loginAccount
				}).then(response => {
          if (response.code === 200) {
            this.templateId = response.data.templateId
            // level 0 基础 1 通用 2 高级  recommend 1 推荐 0 不推荐 1 最新，0 最热
            // 基础应用 查询
            this.init('0', '', '')
            // 通用应用 查询
            this.init('1', '', '')
            // 高级应用 查询
            this.init('2', '', '')
            // 服务应用 查询
            this.init('3', '', '')
            // 推荐应用 查询
            this.init('', '1', '')
            // 最新应用 查询
            this.init('', '', '1')
            // 最热应用 查询
            this.init('', '', '0')
          }
        })
        this.initType()
        this.initServiceType()
    },
    methods: {
      onChange (item) {
        if (item.level == 0) { // 基础应用
          this.baseData.forEach(e => {
            if (e.appSettingId == item.appSettingId) {
              e.checked = item.checked
            }
          })
        } else if (item.level == 1) { // 通用应用
          this.commonData.forEach(e => {
            if (e.appSettingId == item.appSettingId) {
              e.checked = item.checked
            }
          })

        } else if (item.level == 2) { // 高级应用
          this.highAppData.forEach(e => {
            if (e.appSettingId == item.appSettingId) {
              e.checked = item.checked
            }
          })
        }
        this.recommendData.forEach(e => {
          if (e.appSettingId == item.appSettingId) {
            e.checked = item.checked
          }
        })
      },
      init (level, recommend, flag) {
        this.$api.post('/member/applicationCentrality/findList', 
          {
            level: level, // level 0 基础 1 通用 2 高级
            recommend: recommend, // recommend 1 推荐 0 不推荐
            account: this.$user.loginAccount,
            templateId: this.templateId,
            appName: this.keyword,
            flag: flag // 1 最新，0 最热
          }
        ).then(response => {
          if (response.code === 200) {
            // level 0 基础 1 通用 2 高级 3 服务
            if (level === '0') {
              this.baseData = response.data
            }
            if (level === '1') {
              this.commonData = response.data
            }
            if (level === '2') {
              this.highAppData = response.data
              this.highAppDatas = response.data
            }
            if (level === '3') {
              this.serviceData = response.data
              this.serviceDatas = response.data
            }
            if (recommend === '1') {
              this.recommendData = response.data
            }
            // 1 最新，0 最热
            if (flag === '1') {
              this.newData = response.data
            }
            if (flag === '0') {
              this.rankingData = response.data
            }
            // 搜全部
            if (!recommend && !flag && !level) {
              this.searchData = response.data
              this.active = 0
            }
          }
        })
      },
      initType () {
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
        if (name === 'all') {
          this.highAppData = this.highAppDatas
        } else {
          this.highAppData = this.highAppDatas.filter(item => item.userType === name)
        }
      },
      serviceTab (name) {
        if (name === 'all') {
          this.serviceData = this.serviceDatas
        } else {
          this.serviceData = this.serviceDatas.filter(item => item.serviceType === name)
        }
      },
      switchRanking () {
        this.ranking = !this.ranking
      },
      // 选中
      handleSelected (index) {
        this.keyword = ''
        this.active = index
      },
      // 搜索
      onSearch () {
        this.init('', '', '')
      },
      add (item, index) {
        this.$api.post('/member/applicationCentrality/saveOrCancelAppInfo',{
          account: this.$user.loginAccount,
          appId: item.appSettingId,
          appName: item.appName
        }).then(response =>{
          if (response.code === 200) {
            this.$Message.success('添加成功')
          } else {
            this.$Message.error('添加失败')
          }
        })
      },
      cancel (item, index) {
        this.$api.post('/member/applicationCentrality/saveOrCancelAppInfo',{
          account: this.$user.loginAccount,
          appId: item.appSettingId,
          appName: item.appName
        }).then(response =>{
          if (response.code === 200) {
            this.$Message.success('取消成功')
          } else {
            this.$Message.error('取消失败')
          }
        })
      }
    },
    mounted () {
      this.height = `${window.innerHeight}px`
    }
  }
</script>
<style>
.ivu-tabs-nav .ivu-tabs-tab{

}
</style>
