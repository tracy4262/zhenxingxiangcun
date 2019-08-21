<template>
  <div>
    <Row :gutter="16">
      <Col span="4">
        <Card class="pt20">
          <div class="tc pb20" v-for="(item, index) in labList">
            <Button type="text"  size="large" :class="active === index ? 't-green' : ''" @click="handleSelected(index)">
              {{item.labName}}
              （{{item.total}}）
            </Button>
          </div>
        </Card>
      </Col>
      <Col span="20">
        <Card :padding="0">
          <div class="pd20" style="border-bottom: 1px solid #f5f5f5;">
            <species-search 
              ref="search"
              :focusType ="labList[active].value"
              :followValue ="labList[active].followValue"
              :edit="labList[active].edit" 
              @on-change="onChange" 
              @on-search="onSearch" 
              @on-add="addFocus" 
              @on-focus="handleAdd" 
              @on-edit="handleEdit" 
              @on-cancel="handleCancels"></species-search>
          </div>
          <div class="pd30">
            <member-list 
              :focusType ="labList[active].value"
              :defaultSel="labList[active].defaultSel" 
              :data="labList[active].data" 
              :pages="labList[active]" 
              :edit="labList[active].edit" 
              @on-init="pageChange" 
              @on-cancel="handleCancel" ></member-list>
          </div>
        </Card>
      </Col>
    </Row>
    <memberAdd ref="add" @on-init="getList"></memberAdd>
  </div>
</template>
<script>
import memberList from './components/memberList'
import speciesSearch from './components/speciesSearch'
import memberAdd from './components/memberAdd'
  export default {
    name: 'member',
    components: {
      memberList,
      speciesSearch,
      memberAdd
    },
    data () {
      return {
        labList: [
          {
            labName: '我关注的', 
            value: '0',
            total: 0, 
            edit: false, 
            pageSize: 24,
            pageNum: 1,
            followValue: '',
            defaultSel: [],
            data: []
          },
          {
            labName: '关注我的',
            value: '1',
            total: 0, 
            edit: false, 
            pageSize: 24,
            pageNum: 1,
            followValue: '',
            defaultSel: [],
            data: []
          }
        ],
        active: 0,
        templateId: '',
        followValue: ''
      }
    },
    created() {
      this.getList ()
    },
    methods: {
      // 初始化
      getList () {
        this.labList.forEach((element, index) => {
          element.pageNum = 1
          this.init(element , index)
        })
      },
      init (e, index) {
        let data = {
          type: e.value,
          account: this.$user.loginAccount,
          pageSize: e.pageSize,
          pageNum: e.pageNum,
          keyword: e.followValue
        }
        this.$api.post('/member/followManage/findLoginByMemberList', data).then(res => {
          if (res.code === 200) {
            this.labList[index].data = res.data.list
            this.labList[index].total = res.data.total
            this.labList[index].defaultSel = []
            this.labList[index].edit = false
          }
        })
      },
      onChange (keyWord) {
        this.labList[this.active].followValue = keyWord
      },
      // 查询
      onSearch (keyWord) {
        this.labList[this.active].followValue = keyWord
        this.pageChange(1)
      },
      // 左侧选中列表切换
      handleSelected (index) {
        this.active = index
        // this.init(this.labList[this.active], this.active)
      },
      // 分页回调
      pageChange (e) {
        this.labList[this.active].pageNum = e
        this.init(this.labList[this.active], this.active)
      },
      // 单选取消
      handleCancel (item, index) {
        if (item.followType === '0') { // 未关注 做关注
          this.onSaveFocus([item])
        } else { // 已关注 做取消
          this.$Modal.confirm({
              title: '操作提示',
              content: '是否确认取消？',
              onOk:()=>{
                this.canel([item])
              },
              okText:'确定',
              cancelText:'取消'
          })
        }
      },
      // 批量或者单击取消关注调用的接口
      canel (arr) {
        this.$api.post('/member/followManage/deleteFollowMemberInfo', {dataList: arr}).then(response => {
          if (response.code === 200) {
            this.$Message.success('取消关注成功！')
            this.getList()
          } else {
            this.$Message.error('取消关注失败！')
          }
        })
      },
      // 点击添加关注 
      addFocus () {
        // this.labList[this.active]
        console.log('点击添加关注')
        this.$refs['add'].init()
      },
      handleAdd () {
        console.log(this.labList[this.active].defaultSel)
        if (this.labList[this.active].defaultSel.length) {
           this.$Modal.confirm({
              title: '操作提示',
              content: '是否确认关注？',
              onOk:()=>{
                  this.onSaveFocus(this.labList[this.active].defaultSel)
              },
              okText:'确定',
              cancelText:'取消'
          })
        } else {
          this.$Message.warning('请选择！')
        }
      },
      // 在我关注的页面，批量添加关注调用的接口
      onSaveFocus (arr) {
        let array = []
        arr.forEach((elemnet, index) => {
          array.push({
            memberName: elemnet.followAccountName,
            avatar: elemnet.followAvatar,
            account: elemnet.account,
          })
        })
        this.$api.post('/member/followManage/insertFollowMemberInfo', {account: this.$user.loginAccount, dataList: array}).then(response => {
          if (response.code === 200) {
            this.$Message.success('关注成功')
            this.getList()
          } else {
            this.$Message.error('关注失败')
          }
        })
      },
      // 在我关注的页面 点击 取消关注 多选取消
      handleCancels () {
        console.log(this.labList[this.active].defaultSel)
        if (this.labList[this.active].defaultSel.length) {
           this.$Modal.confirm({
              title: '操作提示',
              content: '是否确认取消？',
              onOk:()=>{
                  this.canel(this.labList[this.active].defaultSel)
              },
              okText:'确定',
              cancelText:'取消'
          })
        } else {
          this.$Message.warning('请选择！')
        }
      },
      // 切换多选状态
      handleEdit () {
        this.labList[this.active].edit = !this.labList[this.active].edit
      },
    }
  }
</script>