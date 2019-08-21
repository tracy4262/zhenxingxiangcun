<template>
   <div>
      <div ref="top">
        <top :address="false" />
      </div>
      <div :style="{'min-height': height}" style="background: #F5F5F5;">
        <div style="background: #ffffff;">
          <div class="layouts">
            <Breadcrumb class="pt30 pb20">
                <BreadcrumbItem to="/index">首页</BreadcrumbItem>
                <BreadcrumbItem to="/pro/member">会员中心</BreadcrumbItem>
                <BreadcrumbItem>订单管理</BreadcrumbItem>
            </Breadcrumb>
            <b style="font-size:20px">订单管理</b>
            <p class="pt20 pb40" style="font-size: 14px;font-family: PingFangSC-Regular;">
              订单管理为用户提供服务信息的相关订单。</p>
          </div>
        </div>
        <div class="pt30 pb30">
          <Card class="layouts">
            <!-- <router-view></router-view> -->
            <Row class="pt20">
              <Col span="16">
                <!-- 状态，0.待付款，1.待使用，2.已完成 ，3.退款中，4，已拒绝，5.已退款 ，6.待评价 ， 7 已取消 8 已入住 -->
                <Button type="text" size="large" :class="[tabActive === '' ? 't-green' : '']" @click.native="handleTabsClick('')">全部订单</Button>
                <Button type="text" size="large" :class="[tabActive === '0' ? 't-green' : '']" @click.native="handleTabsClick('0')">待付款</Button>
                <Button type="text" size="large" :class="[tabActive === '1' ? 't-green' : '']" @click.native="handleTabsClick('1')">待使用</Button>
                <Button type="text" size="large" :class="[tabActive === '6' ? 't-green' : '']" @click.native="handleTabsClick('6')">待评价</Button>
                <Button type="text" size="large" :class="[tabActive === '2' ? 't-green' : '']" @click.native="handleTabsClick('2')">已完成</Button>
                <Button type="text" size="large" :class="[tabActive === '7' ? 't-green' : '']" @click.native="handleTabsClick('7')">已取消</Button>
                <Button type="text" size="large" :class="[tabActive === '5' ? 't-green' : '']" @click.native="handleTabsClick('5')">退款</Button>
              </Col>
              <Col span="8">
                <Form :label-width="90">
                    <FormItem label="服务类型">
                        <Select v-model="orderType" @on-change="onChange" clearable>
                            <Option v-for="(item, index) in orderTypes" :key="index" :value="item.value">{{item.label}}</Option>
                        </Select>
                    </FormItem>
                </Form>
              </Col>
            </Row>
            <div>
                <orderList :datas="data" @on-init="init"></orderList>
                <div class="mt30 mb50 tc" v-if="data.length">
                    <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChangePage"></Page>
                </div>
            </div>
          </Card>
        </div>
      </div>
      <div ref="foot">
        <foot></foot>
      </div>
   </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import orderList from './components/order-list'
import {Subtr} from '~utils/utils'
import {timeFormat} from '../goods/orderDetails/components/mixins'
export default {
  mixins: [timeFormat],
  components: {
    top,
    foot,
    orderList
  },
  data () {
    return {
      height:'',
      tabActive: '',
      orderTypes: [
        //   {label: '全部', value: ''},
          {label: '农家乐', value: '3'},
          {label: '景区', value: '2'},
          {label: '民宿', value: '4'},
          {label: '咨询服务', value: '5'},
      ],
      orderType:'',
      pageSize: 10,
      pageNum: 1,
      total: 1,
      data: []
    }
  },
  created(){
    this.init()
  },
  methods: {
    init () {
        this.$api.post('/member/fishing/findOrderList', {
            type: this.orderType,
            sellAccount: this.$user.loginAccount,
            status: this.tabActive,
            pageSize: this.pageSize,
            pageNum: this.pageNum
        }).then(response => {
            if (response.code === 200) {
                this.data = response.data.list
                this.data.forEach(e => {
                  let time = new Date(e.create_time).getTime() + 60*60*1000
                  e.create_times = time
                  e.time = time
                  if (e.status == 0) {
                    if (time > new Date().getTime()){
                      e.status = 0
                    } else {
                      e.status = 7
                    }
                  }
                })
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
<style>

</style>
