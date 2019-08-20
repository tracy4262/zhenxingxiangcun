<template>
  <div class="bg-white member-mall-list">
    <!-- 应用列表 -->
    <Card :bordered="false">
       <p class="pt20 list-title">商城管理</p>
       <Row>
         <Col class="list" span="12" v-for="(item, index) in mallList" v-show="item.isAdd" :key="index">
          <Tooltip placement="top" :content="item.appName" :delay="1000">
            <p class="ell app" @click="handleMallData(item)">{{item.appName}}</p>
          </Tooltip>
         </Col>
       </Row>
       <p class="pt20 list-title">综合服务</p>
       <Row>
         <Col class="list" span="12" v-for="(item, index) in serviceList" v-show="item.checked" :key="index">
          <Tooltip placement="top" :content="item.appName" :delay="1000">
            <p class="ell app" @click="handleServiceData(item)" >{{item.appName}}</p>
          </Tooltip>
         </Col>
       </Row>
    </Card>
    <publishService ref="publishService"></publishService>
  </div>
</template>
<script>
import publishService from './publishService.vue'
  export default {
    components: {
      publishService
    },
    data () {
      return {
        templateId: '',
        mallList: [
          {appName: '库存管理', isAdd: true, url: '/inventoryControl/config'},
          {appName: '发布商品', isAdd: true, url: '/release-goods/step1'},
          {appName: '货架商品', isAdd: true, url: '/goods/goods-shelf'},
          {appName: '正在出售', isAdd: true, url: '/goods/on-sale'},
          {appName: '地址管理', isAdd: true, url: '/address'},
          {appName: '我的购物车', isAdd: true, url: '/shop-cart'},
          {appName: '订单管理', isAdd: true, url: '/orderDetails/soldGoods'},
          // {appName: '诚信档案', isAdd: true, url: '/inventoryControl/config'},
        ],
        serviceList: [
          // {appName: '发布服务', checked: true, url: 'publishService'},
          // {appName: '订单管理', checked: true, url: '/service-order/index'},
          // {appName: '垂钓', checked: true, url: '/fishing/service'},
          // {appName: '采摘', checked: true, url: '/picking/service'},
          // {appName: '民宿', checked: true, url: '/stay/roomType'},
          // {appName: '景区', checked: true, url: '/scenicSpot/ticket'},
          // {appName: '农家乐', checked: true, url: '/restaurant/menuType'},
          // {appName: '咨询服务', checked: true, url: '/service/consultationService'},
        ],
        baseAppData: []
      }
    },
    created() {
      // 服务应用 查询
      this.init('3', '', '')
    },
    methods: {
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
            if (level === '3') {
              this.serviceList = [
                {appName: '发布服务', checked: true, url: 'publishService'},
                {appName: '订单管理', checked: true, url: '/service-order/index'}
              ]
              this.serviceList = this.serviceList.concat(response.data)
            }
          }
        })
      },
      // 点击商城管理
      handleMallData (item) {
        this.$router.push(`${item.url}`)
      },
      // 点击综合服务
      handleServiceData (item) {
        console.log(item)
        if (item.url === 'publishService') {
          this.$refs['publishService'].show = true
        } else {
          this.$router.push(`${item.url}`)
        }
      }
    }
  }
</script>
<style lang="scss">
.member-mall-list{
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

