<template>
  <div>
    <Row class="pt20 pb20">
        <Col span="24">
            <!-- 状态，0.待付款，1.待使用，2.已完成 ，3.退款中，4，已拒绝，5.已退款 ，6.待评价 ， 7 已取消 -->
            <Button type="text" size="large" :class="[tabActive === '' ? 't-green' : '']" @click.native="handleTabsClick('')">全部订单</Button>
            <Button type="text" size="large" :class="[tabActive === '0' ? 't-green' : '']" @click.native="handleTabsClick('0')">待付款</Button>
            <Button type="text" size="large" :class="[tabActive === '1' ? 't-green' : '']" @click.native="handleTabsClick('1')">待处理</Button>
            <Button type="text" size="large" :class="[tabActive === '2' ? 't-green' : '']" @click.native="handleTabsClick('2')">已完成</Button>
            <Button type="text" size="large" :class="[tabActive === '5' ? 't-green' : '']" @click.native="handleTabsClick('5')">退款处理</Button>
        </Col>
    </Row>
    <div>
        <orderList :datas="data" @on-init="init" type="1"></orderList>
        <div class="mt30 mb50 tc" v-if="data.length">
            <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChangePage"></Page>
        </div>
    </div>
  </div>
</template>
<script>
import orderList from './components/orderList'
export default {
  components: {
    orderList
  },
  data () {
    return {
      tabActive: '',
      data: [],
      pageSize: 10,
      pageNum: 1,
      total: 1,
      data: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    init () {
        //0 垂钓 1采摘 2景区 3餐饮 4住宿。 type
        this.$api.post('/member/fishing/findOrderList', {
            type: '3',
            account: this.$user.loginAccount,
            status: this.tabActive,
            pageSize: 10,
            pageNum: this.pageNum
        }).then(response => {
            console.log('rs', response)
            if (response.code === 200) {
                this.data = response.data.list
                this.total = response.data.total
            }
        })
    },
    handleTabsClick (name) {
      this.tabActive = name
      this.handleChangePage(1)
    },
    onChange () {
      this.handleChangePage(1)
    },
    handleChangePage (e) {
      this.pageNum = e 
      this.init()
    },
  },
}
</script>
<style scoped>
    .farm-group-btn {
        color: #9B9B9B;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
    .farm-group-btn-active {
        color: #00c587;
        cursor: pointer;
        font-family: 'PingFangSC-Medium';
    }
</style>
