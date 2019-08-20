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
              <BreadcrumbItem>员工门户管理</BreadcrumbItem>
          </Breadcrumb>
          <b style="font-size:20px">员工门户管理</b>
          <application-brief appId="2ea8a791e5cf488e9712aed3d87ca262"></application-brief>
        </div>
        <div style="background: #F5F5F5;" >
          <div class="pt20 pb20 layouts">
            <Row :gutter="16">
              <Col span="6">
              <!-- 左侧分组 -->
                <Card class="pb20" :padding="0">
                  <tree @on-change="onChange" ref="tree" @on-init="checkStatus"></tree>
                </Card>
              </Col>
              <Col span="18">
              <!-- 右侧列表 -->
                <Card class="pt10" style="min-height: 200px;">
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
                      <Button @click="handleAdd" type="text">
                        添加成员 <span v-if="total">({{total}})</span>
                      </Button>
                    </div>
                    <Title :title="`${groupName}(${friendTotal})`">
                      <Input v-model="keyWord" search  suffix="ios-search" placeholder="请输入好友名称" style="width: 240px" @on-search="onSearch" />
                    </Title>
                     <Table :columns="columns" :data="friendData" class="mt20"></Table>
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
      <add-modal ref="addModal" @on-ok="addSuccess"></add-modal>
      <groupList ref="groupList" @on-save="onSave"></groupList>
      <edit ref="edit" @on-save="onInit"></edit>
   </div>
</template>
<script>
import top from '../../../top'
import foot from '../../../foot'
import tree from './components/tree'
import Title from '../relationManage/components/title'
import addModal from './components/addModal'
import groupList from './components/groupList'
import edit from './components/edit'
import applicationBrief from '~components/application-brief'
export default {
  components: {
    top,
    foot,
    tree,
    Title,
    addModal,
    groupList,
    edit,
    applicationBrief
  },
  data () {
    return {
      height:'',
      friendData: [],
      groupName: '工作圈',
      updateGroupName: '',
      keyWord: '',
      total: 0,
      friendPageNum: 1,
      friendTotal: 0,
      friendPageSize: 24,
      groupId:'',
      updateGroupId: '',
      moveData: {},
      columns: [
        {
            title: '编号',
            key: 'name',
            width: 100,
            align: 'center',
            fixed: 'left',
            render: (h, params) => {
              return h ('span', `${params.index+1}`)
            }
        },
        {
            title: '姓名',
            align: 'center',
            width: 120,
            key: 'groupFriendAccountName',
        },
        {
            title: '登录账号',
            align: 'center',
            width: 120,
            key: 'friendAccount'
        },
        {
            title: '性别',
            align: 'center',
            width: 80,
            key: 'sex',
        },
        {
            title: '身份证号',
            align: 'center',
            width: 160,
            key: 'card',
        },
        {
            title: '联系方式',
            align: 'center',
            width: 120,
            key: 'phone',
        },
        {
            title: '操作',
            align: 'center',
            width: 300,
            fixed: 'right',
            render: (h, params) => {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                          click: () => {
                            // this.$router.push(`/portals/index?uid=${params.row.account}`)
                            // 先移除 再存进去，再打开新的页面
                            console.log(params.row)
                            sessionStorage.removeItem(sessionStorage.getItem(`${params.row.friendAccount}`))
                            sessionStorage.setItem(params.row.friendAccount, JSON.stringify(params.row.session))
                            window.open(`${window.location.origin}/pro/member?uid=${params.row.friendAccount}&type=proxy`, "_blank")
                          }
                        }
                    }, '会员中心'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                          click: () => {
                            this.$toPortals(params.row.friendAccount)
                            // window.open(`${window.location.origin}/portals/index?uid=${params.row.friendAccount}`, "_blank")
                          }
                        }
                    }, '会员门户'),
                     h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                          click: () => {
                            this.onMove(params.row)
                          }
                        }
                    }, '移动'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                          click: () => {
                            this.$refs['edit'].init(params.row)
                            this.activeIndex = params.index
                          }
                        }
                    }, '编辑'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                          click: () => {
                            this.onDel(params.row)
                          }
                        }
                    }, '删除')
                ]);
            }
        },
      ],
      yearId: '',
      templateId: '',
      isInit: false,
      flag: false,
      isShow: false,
      activeIndex: 0
    }
  },
  created(){
    // 查询模板id
    this.$api.post('/member-reversion/realStep/findEnableStep', {
      account: this.$user.loginAccount
    }).then(response => {
      if (response.code === 200 && response.data) {
        this.templateId = response.data.templateId
      }
    })
    this.getYearId()
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
    // 左侧列表展开 取分组id 根据分组id查询分组内的好友
    onChange (id, groupName) {
      this.groupId = id
      this.groupName = groupName
      this.keyWord = ''
      this.isInit = false
      this.getNextPage (1)
    },
    // 编辑后数据更新
    onInit (data) {
      let obj = Object.assign({}, data)
      console.log(data)
      this.friendData.splice(this.activeIndex, 1, obj)
      this.$refs['edit'].resetField()
    },
    // 选择分组
    handleGroupList () {
      this.$refs['groupList'].init()
      this.isInit = true
    },
    // 初始化更新 数据 拉取的是当前年度的 人口信息数据
    update () {
      if (this.updateGroupId) {
        let data = {
          templateId: this.templateId,
          yearId: this.yearId,
          account: this.$user.loginAccount,
          groupId: this.updateGroupId
        }
        this.$api.post('/member/staffGateway/insertStaff', data).then(response => {
          if (response.code === 200) {
            this.$Message.success('添加成功！')
            this.$refs['tree'].init()
            this.$refs['tree'].reload = true
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
          keyword: this.keyWord
      }
      // 查询好友邀请列表
      this.$api.post('/member/staffGateway/findGroupFriendList', data).then(response => {
          if (response.code === 200) {
              this.friendData = response.data.list.dataList
              this.friendTotal = response.data.list.total
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
        this.updateGroupName = data.groupName
        this.updateGroupId = data.id
        this.$refs['groupList'].isShow = false
      } else {
        let groupId = data.id
        let list = {
          oldGroupId: this.moveData.groupId,
          id: this.moveData.id,
          newGroupId : groupId
        }
        this.$api.post('/member/staffGateway/moveGroupFriendInfo', list).then(response => {
            if (response.code === 200) {
                this.$Message.success('操作成功！')
                this.$refs['groupList'].isShow = false
                this.$refs['tree'].init()
                this.$refs['tree'].reload = true
            } else {
                this.$Message.error('操作失败！')
            }
        })
      }
    },
    // 删除
    onDel (item) {
      this.$Modal.confirm({
            title: '操作提示',
            content: '是否确认删除？',
            onOk:()=>{
              let data = {
                    account: this.$user.loginAccount,
                    dataList: [item],
                }
                // 删除
                this.$api.post('/member/staffGateway/deleteGroupFriendInfo', data).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('删除成功！')
                        this.$refs['tree'].init()
                        this.$refs['tree'].reload = true
                    } else {
                        this.$Message.error('删除失败！')
                    }
                })
            },
            okText:'确定',
            cancelText:'取消'
        })
    },
    //
    addSuccess () {
      this.$refs['tree'].init()
      this.$refs['tree'].reload = true
    },
    // 点击搜索，当前分组数据
    onSearch () {
      this.getNextPage(1)
    },
    // 点击添加 成员
    handleAdd () {
      this.$refs['addModal'].init()
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
