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
            <!-- <species-search 
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
                @on-cancel="handleCancels"></species-search> -->
            <componentsSearch
            :edit="labList[active].edit" 
            :focusType ="labList[active].value"
            :searchList="labList[active].search"
            @on-change="onChange" 
            @on-search="onSearch"
            @on-edit="handleEdit"
            @on-del="handleDel" 
            @on-cancel="handleCancels"></componentsSearch>
          </div>
          <div class="pd30">
            <components-list 
              path="addCommodity"
              :data="labList[active].data" 
              :pages="labList[active]" 
              :edit="labList[active].edit" 
              :type="labList[active].value" 
              :defaultSel="labList[active].defaultSel"
              @on-add="addCollection"
              @on-init="pageChange" 
              @on-cancel="handleCancel" ></components-list>
          </div>
        </Card>
      </Col>
    </Row>
    <vuiCommodity ref="vuiCommodity" @on-save="onSaveFocus" :input="false" :num="1000"></vuiCommodity>
  </div>
</template>
<script>
import componentsList from './components/componentsList'
import componentsSearch from './components/componentsSearch'
import vuiCommodity from '~components/vui-commodity'
  export default {
    components: {
      componentsList,
      componentsSearch,
      vuiCommodity
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
            search: {
              commonProductName: '',
              productTypeName: '',
              productType: '', // 传的是商品分类的ID
              relatedIndustry: '',
              relatedIndustryId: '',
              relatedSpeciesName: '',
              relatedSpeciesId: '',
            },
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
            search: {
              commonProductName: '',
              productTypeName: '',
              productType: '', // 传的是商品分类的ID
              relatedIndustry: '',
              relatedIndustryId: '',
              relatedSpeciesName: '',
              relatedSpeciesId: '',
            },
            defaultSel: [],
            data: []
          }
        ],
        active: 0,
        types: '4',
        listType: '1'
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
            type: this.listType, // type 1 通用商品 2通用服务
            pageNum: e.pageNum,
            pageSize: e.pageSize,
            name: e.search.commonProductName,  //筛选条件--通用商品名
            productType: e.search.productType, //筛选条件--产品分类
            industryType: e.search.relatedIndustryId, //筛选条件--行业分类
            relatedSpecies: e.search.relatedSpeciesId, //筛选条件--关联物种
            account: this.$user.loginAccount
          }
          this.$api.post('/portal/currencyCommodity/list', data).then(response => {
                    console.log('res', response)
                    if (response.code === 200) {
                        this.labList[index].data = response.data.list
                        this.labList[index].total = response.data.total
                        this.labList[index].defaultSel = []
                        this.labList[index].edit = false
                    }
                }).catch(error => {
                    this.$Message('查询列表出错！')
                })
        } else { // 查询我收藏的
          let data = {
            type: e.value,
            account: this.$user.loginAccount,
            pageSize: e.pageSize,
            pageNum: e.pageNum,
            commodityName: e.search.commonProductName,  //筛选条件--通用商品名
            productId: e.search.productType, //筛选条件--产品分类
            industryId: e.search.relatedIndustryId, //筛选条件--行业分类
            classId: e.search.relatedSpeciesId, //筛选条件--关联物种
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
      // 触发分页
      pageChange (e) {
        this.labList[this.active].pageNum = e
        this.init(this.labList[this.active], this.active)
      },
      // 单个 点击 取消 或者 删除
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
      // 批量 删除物种
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
      // 删除 调用的统一接口 
      dels (data) {
        this.$api.post('/portal/currencyCommodity/deleteCommodity', {dataList: data, type: this.types, account:this.$user.loginAccount}).then(response => {
          if (response.code === 200) {
            this.$Message.success('删除成功！')
            this.labList[this.active].pageNum = 1
            this.init(this.labList[this.active] , this.active)
          } else {
            this.$Message.error('删除失败！')
          }
        })
      },
      // 点击添加收藏
      addCollection () {
        this.$refs['vuiCommodity'].handleFilterModal()
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
      // 搜内容发生改变
      onChange (list) {
        console.log(list)
        this.labList[this.active].search = list
      },
      // 搜索
      onSearch (list) {
        console.log('点击了搜索', list)
        this.labList[this.active].search = list
        this.init(this.labList[this.active] , this.active)
      },
      //  切换多选状态
      handleEdit () {
        this.labList[this.active].edit = !this.labList[this.active].edit
      },
      // 左侧选中列表切换
      handleSelected (index) {
        this.active = index
        console.log(this.labList[this.active])
      },
    }
  }
</script>