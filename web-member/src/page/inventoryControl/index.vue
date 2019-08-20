<template>
  <div class="inventoryControl">
    <div ref="top">
      <top :address="false" />
    </div>
    <div class="inv_main" :style="{'min-height': height}">
      <div class="main_top">
        <div class="main_top_wrap">
          <Breadcrumb>
            <BreadcrumbItem to="/index">首页</BreadcrumbItem>
            <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
            <BreadcrumbItem>库存管理</BreadcrumbItem>
          </Breadcrumb>
          <div class="main_top_title">库存管理</div>
          <p class="main_top_desc">库存管理通过对入/出库类型、入/出库单据、入/出库商品数量的管理，及时反映各种物资的仓储、流向情况，为生产管理和成本核算提供依据</p>
          <div class="btn_list">
            <Button class="btn_item" @click="baseConfig=true">
              <Icon type="ios-options" size="18" color="#00C587"/>
              基础设置
            </Button>
            <Button class="btn_item" @click="initStore">
              <Icon type="ios-options" size="18" color="#00C587"/>
              库存初始化
            </Button>
            <Button class="btn_item" @click="enterStore">
              <Icon type="ios-options" size="18" color="#00C587"/>
              入库
            </Button>
            <Button class="btn_item" @click="exitStore">
              <Icon type="ios-options" size="18" color="#00C587"/>
              出库
            </Button>
            <Button class="btn_item" @click="openStore">
              <Icon type="ios-options" size="18" color="#00C587"/>
              拆装库存
            </Button>
            <!-- <Button class="btn_item" @click="modalShow=true;currentView='allotmentStore';title='调拨库存'"> -->
            <Button class="btn_item" @click="storeTransfer">
              <Icon type="ios-options" size="18" color="#00C587"/>
              调拨库存
            </Button>
            <Button class="btn_item">
              <Icon type="ios-options" size="18" color="#00C587"/>
              追溯码/防伪码
            </Button>
            <Button class="btn_item"  @click="modalShow=true;currentView='recordStore';title='出入库记录'">
              <Icon type="ios-options" size="18" color="#00C587"/>
              出入库记录
            </Button>
          </div>
        </div>
      </div>
      <router-view ref="storeIndex"></router-view>
    </div>
    <div ref="foot">
      <foot ></foot>
    </div>
    <!-- 基础设置模态框 -->
    <Modal v-model="baseConfig" width="1200" class="baseDailog" :mask-closable="false" :styles="{top: '60px'}">
      <p slot="header" style="color:rgb(0, 0, 0, .8);;font-size:16px;">
        <span>基础设置</span>
      </p>
      <div>
        <Tabs v-model="tabView" @on-click="tabChange">
          <TabPane label="自定义分组及商品名称管理" name="baseGoods"></TabPane>
          <TabPane label="仓库设置" name="baseStore"></TabPane>
          <!-- <TabPane label="库存预警设置" name="baseStock"></TabPane> -->
          <TabPane label="单据设置" name="baseBills"></TabPane>
          <TabPane label="入库类型设置" name="baseinType"></TabPane>
          <TabPane label="出库类型设置" name="baseoutType"></TabPane>
        </Tabs>
        <components :is="tabView"></components>
      </div>
      <div slot="footer">
          <!-- <Button type="error" size="large" >Delete</Button> -->
      </div>
    </Modal>
    <!-- 模态框 -->
    <Modal v-model="modalShow" width="1200" :mask-closable="false" :styles="{top: '60px'}">
      <p slot="header">
        {{title}}
      </p>
      <components :is="currentView" @close="close" :ref="currentView"></components>
      <div slot="footer">
      </div>
    </Modal>
    <storage ref="storage"></storage>
    <outboundOrder ref="outboundOrder"></outboundOrder>
  </div>
</template>

<script>
import top from '../../top'
import foot from '../../foot'
import baseGoods from './component/baseconfig/baseGoods'
import baseStore from './component/baseconfig/baseStore'
import baseBills from './component/baseconfig/baseBills'
import baseinType from './component/baseconfig/baseinType'
import baseoutType from './component/baseconfig/baseoutType'
import baseStock from './component/baseconfig/baseStock'

import initStore from './component/initStore'
import inStore from './component/inStore'
import outStore from './component/outStore'
import separateStore from './component/separateStore'
import allotmentStore from './component/allotmentStore'
import recordStore from './component/recordStore'

import storage from './component/storage'
import outboundOrder from './component/outboundOrder'


export default {
  components: {
    top,
    foot,
    baseGoods,
    baseStore,
    baseBills,
    baseinType,
    baseoutType,
    baseStock,
    initStore,
    inStore,
    outStore,
    separateStore,
    allotmentStore,
    recordStore,
    storage,
    outboundOrder
  },
  data () {
    return {
      height: '',
      tabView: 'baseGoods',
      currentView: '',
      baseConfig: false,
      modalShow: false,
      inStore: false,
      outStore: false,
      separateStore: false,
      allotmentStore: false,
      title: ''
    }
  },
  created () {
    console.log(this.$route.path, '$router.path')
  },
  mounted () {
    this.handleGetHeight()
  },
  methods: {
    handleGetHeight () {
      let clientHeight = document.documentElement.clientHeight
      let topHeight = this.$refs.top.offsetHeight
      let footHeight = this.$refs.foot.offsetHeight
      this.height = `${clientHeight-topHeight-footHeight}px`
    },
    tabChange () {
      console.log(this.tabView,'tabChange')
    },
    // 库存初始化保存
    saveStore () {},
    close (type, order) {
      // 关闭页面后刷新当前页的数据表格
      this.modalShow = false
      this.$refs.storeIndex.initStoreList()
      // 没有返回order则说明是否生成单据设置了否
      if (order !== undefined && order !== '') {
        // 1 入库 2 出库
        if (type === '1') {
          this.$api.post('/shop/inventory/basicSetting/enterOrder', {
            account: this.$user.loginAccount,
            order: order
          }).then(response => {
            if (response.code === 200) {
              this.$refs['storage'].init(response.data, response.data.list)
            }
          })
        } else if (type === '2') {
          this.$api.post('/shop/inventory/basicSetting/exitOrder', {
            account: this.$user.loginAccount,
            order: order
          }).then(response => {
            if (response.code === 200) {
            this.$refs['outboundOrder'].init(response.data, response.data.list)
            }
          })
        }
      }
    },
    initStore () {
      this.modalShow = true
      this.currentView = 'initStore'
      this.title = '库存初始化'
      this.$nextTick(() => {
        this.$refs[this.currentView].initAdd()
      })
    },
    enterStore () {
      this.modalShow = true
      this.currentView = 'inStore'
      this.title = '入库'
      this.$nextTick(() => {
        this.$refs[this.currentView].initAdd()
      })
    },
    exitStore () {
      this.modalShow = true
      this.currentView = 'outStore'
      this.title = '出库'
      this.$nextTick(() => {
        this.$refs[this.currentView].outStoreInit()
      })
    },
    // 拆装库存
    openStore () {
      this.modalShow = true
      this.currentView = 'separateStore'
      this.title = '拆装库存'
      this.$nextTick(() => {
        this.$refs[this.currentView].$refs['separateStore'].separateStoreInit()
      })
    },
    // 调拨
    storeTransfer () {
      // modalShow=true;currentView='allotmentStore';title='调拨库存'
      this.modalShow = true
      this.currentView = 'allotmentStore'
      this.title = '调拨库存'
      this.$nextTick(() => {
        this.$refs[this.currentView].reset()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.inventoryControl{
  .inv_main{
    width: 100%;
    background: rgb(249, 249, 249);
    padding-bottom: 40px;
    .main_top{
      background: #fff;
      margin-bottom: 20px;
      .main_top_wrap{
        width: 1000px;
        margin: 0 auto;
        padding-top: 28px;
      }
      .main_top_title{
        font-size: 20px;
        color: #000 85%;
        font-weight: bold;
        margin: 16px 0;
      }
      .main_top_desc{
        width: 760px;
        line-height: 22px;
        font-size: 14px;
        color: rgba(0, 0, 0, .6);
        padding-bottom: 20px;
      }
      .btn_list{
        padding-bottom: 20px;
        .btn_item{
          margin-right: 12px;
          &:hover{
          background: #E2F6F2;
          }
        }
        
      }
    }
  }
}
</style>
<style>
.modalShow .ivu-modal-footer{
  border-top: none;
}
</style>


