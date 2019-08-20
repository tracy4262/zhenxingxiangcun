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
                path="addDisease"
                :focusType ="labList[active].value"
                :followValue ="labList[active].followValue"
                :followType ="labList[active].followType"
                :edit="labList[active].edit"
                @on-change="onChange"
                @on-type-change="onTypeChange"
                @on-search="onSearch"
                @on-del="handleDel"
                @on-edit="handleEdit"
                @on-cancel="handleCancels"></species-search>
          </div>
          <div class="pd30">
            <species-list
              path="addDisease"
              :data="labList[active].data"
              :pages="labList[active]"
              :edit="labList[active].edit"
              :type="labList[active].value"
              :defaultSel="labList[active].defaultSel"
              @on-add="addCollection"
              @on-init="pageChange"
              @on-cancel="handleCancel" ></species-list>
          </div>
        </Card>
      </Col>
    </Row>
    <!-- type 1 品种 2 病害 3 虫害 -->
    <vuiVariety :input="false" ref="vuiVariety" @on-save="onSaveFocus" :num="1000" type="2"></vuiVariety>
  </div>
</template>
<script>
import speciesList from './components/speciesList'
import speciesSearch from './components/speciesSearch'
import vuiVariety from '~components/vui-variety'
  export default {
    components: {
      speciesList,
      speciesSearch,
      vuiVariety
    },
    data () {
      return {
        labList: [
          {
            labName: '我收藏的',
            value: '0',
            total: 0,
            edit: false,
            pageSize: 24,
            pageNum: 1,
            followValue: '',
            followType: '',
            defaultSel: [],
            data: []
          },
          {
            labName: '我新增的',
            value: '1',
            total: 0,
            edit: false,
            pageSize: 24,
            pageNum: 1,
            followValue: '',
            followType: '',
            defaultSel: [],
            data: []
          }
        ],
        active: 0,
        templateId: '',
        followValue: '',
        types: '2'
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
        let followType = e.followType
        if (index) { // 查询我新增的
        let data = {
            keywords: e.followValue,
            pageNum: e.pageNum,
            pageSize: e.pageSize,
            sortType: 2,
            userId: this.$user.loginAccount,
            auditstatus: 6
          }
          this.$api.post('/wiki/api/wiki/listSpeciesDisease', data).then(response => {
                if (response.code === 200) {
                    this.labList[index].data = response.data
                    this.labList[index].total = response.total
                    this.labList[index].defaultSel = []
                    this.labList[index].edit = false
                } else {
                    this.$Message.error('查询病害列表出错！')
                }
            }).catch(error => {
                this.$Message.error('查询病害列表出错！')
            })
        } else { // 查询我收藏的
          let data = {
            type: e.value,
            account: this.$user.loginAccount,
            pageSize: e.pageSize,
            pageNum: e.pageNum,
            keyword: e.followValue,
            className: followType,
            type: this.types
          }
          this.$api.post('/member/nameLibrary/findList', data).then(res => {
            if (res.code === 200) {
              this.labList[index].data = res.data.list
              this.labList[index].total = res.data.total
              this.labList[index].defaultSel = []
              this.labList[index].edit = false
            }
          })
        }
      },
      // 搜索关键字发生改变
      onChange (e) {
        // console.log(list)
        this.labList[this.active].followValue = e
        // this.labList[this.active].followType = list.type
        console.log(this.labList[this.active])
      },
      onTypeChange (e) {
        this.labList[this.active].followType = e
      },
      // 查询
      onSearch (list) {
        console.log(list)
        this.labList[this.active].followValue = list.keyWord
        this.labList[this.active].followType = list.type
        this.pageChange(1)
      },
      // 左侧选中列表切换
      handleSelected (index) {
        this.active = index
        console.log(this.labList[this.active])
      },
      // 分页回调
      pageChange (e) {
        this.labList[this.active].pageNum = e
        this.init(this.labList[this.active], this.active)
      },
      // 单个 取消收藏 ,删除
      handleCancel (item, index) {
        if (!this.active) { // 取消收藏
          this.$Modal.confirm({
                title: '操作提示',
                content: '<p>您确定取消收藏？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.cancels([item])
                }
            })
        } else { // 删除
          this.$Modal.confirm({
                title: '操作提示',
                content: '<p>您确定删除此物种？</p>',
                cancelText: '取消',
                onOk: () => {
                    this.dels([item])
                }
            })
        }
      },
      // 取消收藏调用的统一接口
      cancels (data) {
        this.$api.post('/member/nameLibrary/deleteLibrary', {dataList: data, type: this.types}).then(response => {
          if (response.code === 200) {
            this.$Message.success('取消收藏成功！')
            this.labList[this.active].pageNum = 1
            this.init(this.labList[this.active] , this.active)
          } else {
            this.$Message.error('取消收藏失败！')
          }
        })
      },
      // 删除物种
      handleDel () {
        let data = this.labList[this.active].defaultSel
        if (data.length) {
          this.$Modal.confirm({
              title: '操作提示',
              content: '<p>您确定删除此物种？</p>',
              cancelText: '取消',
              onOk: () => {
                  this.dels(data)
              }
          })
        } else {
          this.$Message.warning('请选择！')
        }
      },
      // 删除 调用的统一接口
      dels (data) {
        this.$api.post('/wiki/api/wiki/deleteDiseaseSpecies', {dataList: data, type: this.types, account:this.$user.loginAccount}).then(response => {
          if (response.code === 200) {
            this.$Message.success('删除成功！')
            this.labList[this.active].pageNum = 1
            this.init(this.labList[this.active] , this.active)
          } else {
            this.$Message.error('删除失败！')
          }
        })
      },
      // 添加收藏
      addCollection () {
        this.$refs['vuiVariety'].handleFilterModal()
      },
      // 添加收藏后保存
      onSaveFocus (e) {
        if (e.length) {
         let data = {
            account: this.$user.loginAccount,
            type: this.types,
            dataList: e
          }
          console.log(data)
          this.$api.post('/member/nameLibrary/saveLibrary', data).then(response => {
            if (response.code === 200) {
              this.$Message.success('收藏成功！')
              this.labList[this.active].pageNum = 1
              this.init( this.labList[this.active] , this.active)
            } else {
              this.$Message.error('收藏失败！')
            }
          })
        } else {
          this.$Message.warning('请选择！')
        }
      },
      // 取消收藏 批量操作
      handleCancels () {
        console.log(this.labList[this.active].defaultSel)
        let data = this.labList[this.active].defaultSel
        if (data.length) {
          this.$Modal.confirm({
                title: '操作提示',
                content: '<p>您确定取消收藏</p>',
                cancelText: '取消',
                onOk: () => {
                this.cancels(data)
                }
            })
        } else {
          this.$Message.warning('请选择！')
        }
      },
      //  切换多选状态
      handleEdit () {
        this.labList[this.active].edit = !this.labList[this.active].edit
      },
    }
  }
</script>
