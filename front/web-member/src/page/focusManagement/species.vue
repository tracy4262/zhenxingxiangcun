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
            <species-search ref="search" 
              :followValue ="labList[active].followValue" 
              :edit="labList[active].edit"
              @on-change="onChange"
              @on-search="onSearch" 
              @on-add="addFocus" 
              @on-edit="handleEdit" 
              @on-cancel="handleCancels"></species-search>
          </div>
          <div class="pd30">
            <species-list :defaultSel="labList[active].defaultSel" :data="labList[active].data" :pages="labList[active]" :edit="labList[active].edit" @on-init="pageChange" @on-cancel="handleCancel" >></species-list>
          </div>
        </Card>
      </Col>
    </Row>
    <vuiSpecies :input="false" ref="vuiSpecies" @on-save="onSaveFocus" :num="1000"></vuiSpecies>
  </div>
</template>
<script>
import speciesList from './components/speciesList'
import speciesSearch from './components/speciesSearch'
import vuiSpecies from '~components/vui-species'
  export default {
    name: 'species',
    components: {
      speciesList,
      speciesSearch,
      vuiSpecies
    },
    data () {
      return {
        labList: [
          {
            labName: '全部', 
            value: '',
            total: 0, 
            edit: false, 
            pageSize: 24,
            pageNum: 1,
            followValue: '',
            data: [],
            defaultSel: []
          }
        ],
        active: 0,
        templateId: '',
        followValue: ''
      }
    },
    created() {
      // 查询模板id
      this.$api.post('/member-reversion/realStep/findEnableStep', {
        account: this.$user.loginAccount
      }).then(response => {
        if (response.code === 200 && response.data) {
          this.templateId = response.data.templateId
          
          this.init(this.labList[this.active], this.active, 1)
          // this.getLabList()
          // this.init(this.labList[this.active], this.active)
          // this.labList.forEach((e, index) => {
          //   this.init(e, index)
          // })
        }
      })  
    },
    methods: {
       getLabList (type) {
        this.$api.post('/member/followManage/findSepciesList', {
          type: '1',
          templateId: this.templateId,
          account: this.$user.loginAccount
        }).then(res => {
          let d = res.data
          let arr = [this.labList[0]]
          if (type) { // 说明是更新数据
            let totals = 0
            d.forEach((e, index) => {
                totals += e.total
                this.labList[index + 1].total = e.total
            })
            this.labList[0].total = totals
          } else {
            d.forEach((e, index) => {
              arr[0].total += e.total
              arr.push({
                labName: e.name,
                value: e.type,
                total: e.total, 
                edit: false, 
                init: false,
                pageSize: 24,
                pageNum: 1,
                followValue: '',
                defaultSel: [],
                data: []
              })
            })
            this.labList = arr
          }
        })
      },
      init (e, index, type) {
        let data = {
          account: this.$user.loginAccount,
          templateId: this.templateId,
          type: '1',
          pageSize: e.pageSize,
          pageNum: e.pageNum,
          followType: e.value,
          label: e.followValue
        }
        this.$api.post('/member/followManage/findSpecByAccount', data).then(res => {
          if (res.code === 200) {
            this.labList[index].edit = false
            this.labList[index].data = res.data.list
            this.labList[index].defaultSel = []
            if (type) {
              this.getLabList()
            }
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
        this.init(this.labList[this.active], this.active)
      },
      // 分页回调
      pageChange (e) {
        this.labList[this.active].pageNum = e
        this.init(this.labList[this.active], this.active)
      },
      // 单选取消
      handleCancel (item, index) {
        this.$Modal.confirm({
            title: '操作提示',
            content: '是否确认取消？',
            onOk:()=>{
              this.canel([item])
            },
            okText:'确定',
            cancelText:'取消'
        })
      },
      canel (arr) {
        this.$api.post('/member/followManage/deleteFollowInfo', {dataList: arr}).then(response => {
          if (response.code === 200) {
            this.$Message.success('取消关注成功！')
            this.labList[this.active].pageNum = 1
            this.getLabList(1)
            this.init( this.labList[this.active] , this.active)
          } else {
            this.$Message.error('取消关注失败！')
          }
        })
      },
      // 点击添加关注 
      addFocus () {
        this.$refs['vuiSpecies'].handleFilterModal()
      },
      onSaveFocus (e) {
        let data = {
          account: this.$user.loginAccount,
          templateId: this.templateId,
          type: '1',
          dataList: e
        }
        this.$api.post('/member/followManage/insertFollow', data).then(response => {
          if (response.code === 200) {
            this.$Message.success('关注成功！')
            this.labList[this.active].pageNum = 1
            this.getLabList(1)
            this.init( this.labList[this.active] , this.active)
          } else {
            this.$Message.error('关注失败！')
          }
        })
      },
      // 获取 批量操作时 选中的数据
      getDefaultSel (data) {
        this.labList[this.active].defaultSel = data
      },
      // 点击 取消关注 多选取消
      handleCancels () {
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