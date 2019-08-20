<template>
   <div>
      <div ref="top">
        <top :address="false" />
      </div>
      <div :style="{'min-height': height}">
        <div class="layouts">
          <Breadcrumb class="pt30 pb20">
              <BreadcrumbItem to="/index">首页</BreadcrumbItem>
              <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
              <BreadcrumbItem>关系圈管理</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">关系圈管理</b>
          <application-brief appId="7e98159e0d8641c0a5f9ce5dc2a7aa47"></application-brief>
        </div>
        <div style="background: #F5F5F5;" >
          <div class="pt20 pb20 layouts">
            <Row :gutter="16">
              <Col span="6">
              <!-- 左侧分组 -->
                <Card class="pb20" :padding="0">
                  <tree @on-change="onChange" ref="tree"  @on-init="checkStatus"></tree>
                </Card>
              </Col>
              <Col span="18">
              <!-- 右侧列表 -->
                <Card class="pt10">
                  <div v-if="isShow && !flag">
                    <p class="tc pd20 pb50">暂无数据，请先同步员工花名册</p>
                    <div class="tc">
                      <span class="mr10" style="vertical-align: middle;">选择分组</span>
                      <Input v-model="updateGroupName" placeholder="请选择所在分组" :maxlength="18" style="width: 300px;" readonly @on-focus="handleGroupList"/>
                      <br/>
                      <Button type="primary" @click="update" class="mt30">确定</Button>
                    </div>
                  </div>
                  <div class="pl10 pr10" v-if="isShow && flag">
                    <div class="tr pb10">
                      <Button @click="getInviteList" type="text">
                        好友邀请 <span v-if="total">({{total}})</span>
                      </Button>
                    </div>
                    <Title :title="`${groupName}(${friendTotal})`">
                      <Input v-model="keyWord" search  suffix="ios-search" placeholder="请输入好友名称" style="width: 240px" @on-search="onSearch" />
                    </Title>
                    <friendList :data="friendData" class="pt20" @on-add="handleAdd" @on-del="gitInit" @on-move="onMove"></friendList>
                    <div class="tc pt40 pb20" v-if="friendData.length">
                      <Page :total="friendTotal" @on-change="getNextPage" :page-size="friendPageSize" :current="friendPageNum"></Page>
                    </div>
                  </div>
                </Card>
              </Col>
            </Row>
          </div>
        </div>
      </div>
      <div ref="foot">
        <foot></foot>
      </div>
      <add-modal ref="addModal" ></add-modal>
      <invite-list ref="inviteList"  @get-total="getTotal"></invite-list>
      <groupList ref="groupList" @on-save="onSave" :init="isInit"></groupList>
   </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import tree from './components/tree'
import friendList from './components/friendList'
import Title from './components/title'
import addModal from './components/addModal'
import inviteList from './components/inviteList'
import groupList from './components/groupList'
import applicationBrief from '~components/application-brief'
export default {
  components: {
    top,
    foot,
    tree,
    friendList,
    Title,
    addModal,
    inviteList,
    groupList,
    applicationBrief
  },
  data () {
    return {
      height:'',
      friendData: [],
      groupName: '',
      updateGroupName: '',
      keyWord: '',
      total: 0,
      friendPageNum: 1,
      friendTotal: 0,
      friendPageSize: 24,
      groupId:'',
      updateGroupId: '',
      moveData: {},
      isInit: false,
      flag: false,
      isShow: false,
      yearId: '',
      templateId: ''
    }
  },
  created(){
    this.getYearId()
    // 查询模板id
    this.$api.post('/member-reversion/realStep/findEnableStep', {
      account: this.$user.loginAccount
    }).then(response => {
      if (response.code === 200 && response.data) {
        this.templateId = response.data.templateId
      }
    })
  },
  methods: {
    getYearId () {
        // 先查询年度id
        this.$api.post('/member-reversion/perfect/findYearInfo', {
            account: this.$user.loginAccount
        }).then(response => {
            console.log('response', response)
            if (response.code === 200) {
                let arr = []
                response.data.forEach(element => {
                    arr.push({
                    name: element.fileName,
                    id: element.id,
                    checked: element.fileName.substring(0, 4) === new Date().getFullYear().toString() ? true : false
                    })
                })
                console.log(arr)
                // 默认年份id
                arr.forEach(element => {
                    if (element.checked) {
                    this.yearId = element.id
                    }
                })
            }
        })
    },
    getTotal (total) {
      this.total = total
    },
    // 左侧列表展开 取分组id 根据分组id查询分组内的好友
    onChange (id, groupName) {
      this.groupId = id
      this.groupName = groupName
      this.keyWord = ''
      this.isInit = false
      this.getNextPage (1)
    },
    checkStatus (status) {
      console.log('statusstatusstatusstatus', status)
      // status 0 没有拉取数据 1是拉取了数据
      if (!status) {
        console.log('没有的的的得得得')
        this.flag = false
        this.isShow = true
      } else {
        this.flag = true
        this.isShow = true
      }
    },
    // 选择分组
    handleGroupList () {
      this.$refs['groupList'].init(1)
      this.isInit = true
    },
    gitInit (e) {
      this.$refs['tree'].init()
      this.$refs['tree'].reload = true
    }, // 初始化更新 数据 拉取的是当前年度的 人口信息数据
    update () {
      if (this.updateGroupId) {
        let data = {
          templateId: this.templateId,
          yearId: this.yearId,
          account: this.$user.loginAccount,
          groupId: this.updateGroupId
        }
        this.$api.post('/member/relationshipCircle/insertStaff', data).then(response => {
          if (response.code === 200) {
            this.$Message.success('添加成功！')
            this.$refs['tree'].init()
          } else {
            this.$Message.error('添加失败！')
          }
        })
      } else {
        this.$Message.error('请先选择分组！')
      }
    },
    // 分页查询
    getNextPage (e) {
      this.friendPageNum = e
      let data = {
          pageSize: this.friendPageSize,
          pageNum: this.friendPageNum,
          account: this.$user.loginAccount,
          groupId: this.groupId,
          type: '0',
          invite: '1',
          keyword: this.keyWord
      }
      // 查询好友邀请列表
      this.$api.post('/member/relationshipCircle/findGroupFriendList', data).then(response => {
          if (response.code === 200) {
              this.friendData = response.data.dataList
              this.friendTotal = response.data.total
          }
      })
    },
    // 移动好友
    onMove (item) {
      this.moveData = item
      this.$refs['groupList'].init()
    },
    // 移动好友 选择分组后保存
    onSave (data) {
      if (this.isInit) {
        this.updateGroupName = data[0].groupName
        this.updateGroupId = data[0].id
        this.$refs['groupList'].isShow = false
      } else {
        let groupId = data[0].id
        let list = {
          oldGroupId: this.moveData.groupId,
          id: this.moveData.id,
          newGroupId : groupId
        }
        this.$api.post('/member/relationshipCircle/moveGroupFriendInfo', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('操作成功！')
                this.$refs['groupList'].isShow = false
                this.$refs['tree'].reload = true
                this.$refs['tree'].init()
            } else {
                this.$Message.error('操作失败！')
            }
        })
      }
    },
    // 点击搜索，当前分组数据
    onSearch () {
      this.getNextPage(1)
    },
    // 点击添加好友
    handleAdd () {
      this.$refs['addModal'].init()
    },
    getInviteList () {
      this.$refs['inviteList'].init()
    },
     // 获取页面高度
    handleGetHeight () {
      let clientHeight = document.documentElement.clientHeight
      let topHeight = this.$refs.top.offsetHeight
      let footHeight = this.$refs.foot.offsetHeight
      this.height = `${clientHeight-topHeight-footHeight}px`
    }
  },
  mounted () {
    this.handleGetHeight()
  },
}
</script>
<style lang="scss">
</style>
