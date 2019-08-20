<template>
  <div>
    <div class="detailTop">
      <Breadcrumb>
        <BreadcrumbItem @click.native="sendShow" to="/orderDetails/soldBidding">订单首页</BreadcrumbItem>
        <BreadcrumbItem>订单详情</BreadcrumbItem>
      </Breadcrumb>
      <Row class="mt10" type="flex">
        <Col>
          <img v-if="info.image" :src="info.image[0]" style="width: 100px; height: 100px;">
          <img v-else src="../../../../../static/img/goods-list-no-picture.png" style="width: 100px; height: 100px;" />
        </Col>
        <Col class="ml20">
          <Row type="flex" align="middle" class="mt10">
            <Col>
              <p style="font-size:20px">{{ info.productName }}</p>
            </Col>
            <Col class="ml20">
              <div class="statusBtn" v-if="num === 1">即将竞拍</div>
              <div class="statusBtn2" v-if="num === 2">竞拍中</div>
              <div class="statusBtn2" v-if="num === 3">竞拍结果确认</div>
            </Col>
          </Row>
          <div class="mt25" style="font-size:15px">竞拍开始时间：{{ info.startTime }} | 起拍价：{{ info.startPrice }}元/{{ info.unit }} | 可拍数量：{{ info.productVbep }}{{ info.unit }}</div>
        </Col>
      </Row>
    </div>
    <!-- 竞拍人与竞拍状态 -->
    <Row type="flex" class="mt30 mb30 ml30">
      <Col :span="8">
        <Row type="flex" align="middle">
          <Col :span="6">
            <p style="font-size:16px" class="mr10">竞拍人</p>
          </Col>
          <Col :span="14">
            <Input v-model="memberName" @on-change="change" clearable />
          </Col>
        </Row>
      </Col>
      <Col :span="8" class="ml20">
        <Row type="flex" align="middle">
          <Col :span="6">
            <p style="font-size:16px" class="mr10">竞拍状态</p>
          </Col>
          <Col :span="14">
            <Select v-if="num === 1" v-model="status" @on-change="change" clearable>
              <Option v-for="item in auctionStatus" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
            <Select v-if="num === 2" v-model="status" @on-change="change" clearable>
              <Option v-for="item in auctionStatus2" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
            <Select v-if="num === 3" v-model="status" @on-change="change" clearable>
              <Option v-for="item in auctionStatus3" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Col>
        </Row>
      </Col>
    </Row>
    <!-- 竞拍结果批量操作 -->
    <Row type="flex" align="middle" class="ml30" v-if="num == 3">
      <Col>
        <p style="font-size: 16px">批量操作</p>
      </Col>
      <Col class="ml10">
        <Button type="success" @click="successAll">竞拍成功</Button>
        <Button type="error" class="ml10" @click="failAll">竞拍失败</Button>
      </Col>
    </Row>
    <!-- 文字说明 -->
    <Row type="flex" justify="space-between" align="middle" class="mt20">
      <Col>
        <Row type="flex" align="middle" class="ml30">
          <Col>
            <Icon type="ios-podium" size="20"/>
          </Col>
          <Col class="ml10">
            <p style="font-size:16px" v-if="num === 1">{{ info.bidCount }}人已确认保证金并具备竞拍资格</p>
            <p style="font-size:16px" v-if="num === 2 || num === 3">共{{ info.participantCount }}人参与竞拍；共出价{{ info.count }}次；最高价{{ info.maxPrice }}元/{{ info.unit }}；最低价{{ info.minPrice }}元/{{ info.unit }}；竞拍数量合计{{ info.sum }}{{ info.unit }}</p>
          </Col>
        </Row>
      </Col>
      <Col v-if="num === 3">
        <div class="soldCount mr20">可售数量剩余{{ info.remainNum }}{{ info.unit }}</div>
      </Col>
    </Row>
    <!-- 商品详情的表格 -->
    <detaiTable :data="data" :columns="columns1" v-if="num === 1"></detaiTable>
    <detaiTable :data="data" :columns="columns2" v-if="num === 2"></detaiTable>
    <Table class="mt20" :data="data" :columns="columns3" v-if="num === 3" @on-selection-change="tableChange"></Table>
    <Page class="mt30 tc" :current="pageNum" :page-size="pageSize" :total="total" @on-change="handleChange" />

    <!-- 保证金订单详情 -->
    <Modal
        v-model="bondOrderModal"
        :width="520"
        :mask-closable="false"
        title="订单详情">
        <div>
            <p class="pb5">订单编号：{{ biddingInfo.order }}</p>
            <p class="pb5">商品名称：{{ biddingInfo.productName }}</p>
            <p class="pb5">保证金：￥{{ biddingInfo.margin }}</p>
            <p class="pb5">送货至：{{ addressInfo.addArea }}，{{ addressInfo.addDetail }}，{{ addressInfo.linkman }}，{{ addressInfo.mobile }}</p>
            <p class="pb5">订单生成时间：{{ biddingInfo.createTime }}</p>
            <p class="pb5" v-if="biddingInfo.payTime !== ''">付款时间：{{ biddingInfo.payTime }}</p>
        </div>
        <div class="tc" slot="footer">
          <Button type="primary" @click="bondOrderModal=false">确定</Button>
        </div>
    </Modal>

    <!-- 买家进度 -->
    <Modal
        v-model="progressModal"
        :width="500"
        :mask-closable="false"
        title="买家当前订单进度">
        <div style="height: 30px;" class="ml40 mt10">
            <Steps :current="current">
                <Step title="待提交订单"></Step>
                <Step title="已提交订单"></Step>
                <Step title="已付款"></Step>
            </Steps>
        </div>
        <div class="tc" slot="footer">
          <Button type="primary" @click="progressModal=false">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
import detaiTable from "./detailTable"
import vuiClocker from '~components/clocker/clocker'
export default {
  props: ["num"],
  components: {
    detaiTable,
    vuiClocker
  },
  data() {
    return {
      current: 0,
      modal: false,
      modal2: false,
      bondOrderModal: false,
      progressModal: false,
      modalValue: '',
      auctionStatus: [{ label: '提交保证金订单', value: 1 }, { label: '准备抢拍', value: 2 }],
      auctionStatus2: [{ label: '提交保证金订单', value: 1 }, { label: '准备抢拍', value: 2 }, { label: '已出价', value: 3 }],
      auctionStatus3: [
          { label: '准备抢拍', value: 2 },
          { label: '已出价', value: 3 },
          { label: '竞拍成功', value: 4 },
          { label: '竞拍失败，保证金待返还', value: 5 },
          { label: '竞拍失败，保证金已返还', value: 6 },
          { label: '已转到商品订单，未付款', value: 7 },
          { label: '已转到商品订单，已付款', value: 8 }
      ],
      totalCount: '1000',
      columns1: [
        {
          title: '订单编号',
          key: 'order',
          align: 'center'
        },
        {
          title: '竞拍人',
          key: 'memberName',
          align: 'center'
        },
        {
          title: '竞拍状态',
          key: 'status',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('div', {
                style: {
                  display: 'inline-block',
                  width: '8px',
                  height: '8px',
                  borderRadius: '6px',
                  marginRight: '5px',
                  verticalAlign: 'middle',
                  background: params.row.status === 1 ? '#ff9900' : '#02c587'
                }
              }),
              h('span', params.row.status === 1 ? '提交保证金订单' : '准备抢拍')
            ])
          }
        },
        {
          title: '订单提交时间',
          key: 'createTime',
          align: 'center'
        },
        {
          title: '操作',
          key: 'operation',
          width: 300,
          align: 'center',
          render: (h, params) => {
            if (params.row.isExpire === 0 && params.row.status === 1) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            margin: '10px 0'
                        },
                        on: {
                            click: () => {
                                this.cancelbondOrder(params.row)
                            }
                        }
                    }, '取消订单'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情'),
                    h(vuiClocker, {
                        style: {
                            paddingBottom: '10px'
                        },
                        props: {
                            time: params.row.expireTime,
                            format: '%D天 %H小时 %M分 %S秒'
                        }
                    }),
                    h('span', '后订单自动关闭')
                ])
            } else if (params.row.isExpire === 1) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情'),
                    h ('Tag', {
                        style: {
                            marginLeft: '10px'
                        },
                        props: {
                            color: 'warning'
                        }
                    }, '订单已失效')
                ])
            } else if (params.row.status === 2) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情')
                ])
            }
          }
        }
      ],
      columns2: [
        {
          title: '订单编号',
          key: 'order',
          align: 'center',
          width: 160
        },
        {
          title: '竞拍人',
          key: 'memberName',
          align: 'center'
        },
        {
          title: '竞拍出价',
          key: 'price',
          align: 'center',
          width: 120
        },
        {
          title: '竞拍数量',
          key: 'number',
          align: 'center',
          width: 120
        },
        {
          title: '竞拍人状态',
          key: 'status',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('div', {
                style: {
                  display: 'inline-block',
                  width: '8px',
                  height: '8px',
                  borderRadius: '6px',
                  marginRight: '5px',
                  verticalAlign: 'middle',
                  background: params.row.status === 1 ? '#ff9900' : params.row.status === 2 ? '#02c587' : '#2db7f5'
                }
              }),
              h('span', params.row.status === 1 ? '提交保证金订单' : params.row.status === 2 ? '准备抢拍' : `第${params.row.count}次出价`)
            ])
          }
        },
        {
          title: '出价时间',
          key: 'payTime',
          align: 'center'
        },
        {
          title: '操作',
          key: 'operation',
          width: 300,
          align: 'center',
          render: (h, params) => {
            if (params.row.isExpire === 0 && params.row.status === 1) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            margin: '10px 0'
                        },
                        on: {
                            click: () => {
                                this.cancelbondOrder(params.row)
                            }
                        }
                    }, '取消订单'),
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情'),
                    h(vuiClocker, {
                        style: {
                            paddingBottom: '10px'
                        },
                        props: {
                            time: params.row.expireTime,
                            format: '%D天 %H小时 %M分 %S秒'
                        }
                    }),
                    h('span', '后订单自动关闭')
                ])
            } else if (params.row.isExpire === 1) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情'),
                    h ('Tag', {
                        style: {
                            marginLeft: '10px'
                        },
                        props: {
                            color: 'warning'
                        }
                    }, '订单已失效')
                ])
            } else if (params.row.status === 2 || params.row.status === 3) {
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '订单详情')
                ])
            }
          }
        }
      ],
      columns3: [
        {
            type: 'selection',
            width: 60
        },
        {
          title: '订单编号',
          key: 'order',
          align: 'center',
          width: 160
        },
        {
          title: '竞拍人',
          key: 'memberName',
          align: 'center'
        },
        {
          title: '竞拍出价',
          key: 'price',
          align: 'center',
          width: 120
        },
        {
          title: '竞拍数量',
          key: 'number',
          align: 'center',
          width: 120
        },
        {
          title: '竞拍人状态',
          key: 'status',
          align: 'center',
          render: (h, params) => {
            if (params.row.status === 2) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#ff9900'
                        }
                    }),
                    h('span', '准备抢拍')
                ])
            } else if (params.row.status === 3) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#2b85e4'
                        }
                    }),
                    h('span', '第' + params.row.count + '次出价')
                ])
            } else if (params.row.status === 4) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#19be6b'
                        }
                    }),
                    h('span', '竞拍成功')
                ])
            } else if (params.row.status === 5) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#ed4014'
                        }
                    }),
                    h('span', '竞拍失败，保证金待返还')
                ])
            } else if (params.row.status === 6) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#ed4014'
                        }
                    }),
                    h('span', '竞拍失败，保证金已返还')
                ])
            } else if (params.row.status === 7) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#2db7f5'
                        }
                    }),
                    h('span', '已转到商品订单，未付款')
                ])
            } else if (params.row.status === 8) {
                return h('div', [
                    h('div', {
                        style: {
                        display: 'inline-block',
                        width: '8px',
                        height: '8px',
                        borderRadius: '6px',
                        marginRight: '5px',
                        verticalAlign: 'middle',
                        background: '#2db7f5'
                        }
                    }),
                    h('span', '已转到商品订单，已付款')
                ])
            }
          }
        },
        {
          title: '出价时间',
          key: 'payTime',
          align: 'center'
        },
        {
          title: '操作',
          key: 'operation',
          width: 200,
          align: 'center',
          render: (h, params) => {
            if (params.row.status === 2 || params.row.status === 3) { // 准备抢拍、已出价
                return h('div', {
                    style: {
                        padding: '10px'
                    }
                }, [
                    h('Button', {
                        props: {
                            type: 'success',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.success(params.row)
                            }
                        }
                    }, '竞拍成功'),
                    h('Button', {
                        style: {
                            marginLeft: '10px'
                        },
                        props: {
                            type: 'error',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.fail(params.row)
                            }
                        }
                    }, '竞拍失败'),
                    h('div', {
                        style: {
                            marginTop: '10px'
                        }
                    }, [
                        h('Button', {
                            props: {
                                type: 'text',
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                    this.bondOrderDetail(params.row)
                                }
                            }
                        }, '保证金订单详情')
                    ])
                ])
            } else if (params.row.status === 4 || params.row.status === 7 || params.row.status === 8) { // 成功、已转到商品订单，未付款、已转到商品订单，已付款
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.checkProgress(params.row)
                            }
                        }
                    }, '查看买家进度')
                ])
            } else if (params.row.status === 5) { // 竞拍失败，保证金待返还
                return h('div', {
                    style: {
                        padding: '10px'
                    }
                }, [
                    h('Button', {
                        props: {
                            type: 'info',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.returnBond(params.row)
                            }
                        }
                    }, '退还保证金'),
                    h('Button', {
                        style: {
                            marginTop: '10px'
                        },
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '保证金订单详情')
                ])
            } else if (params.row.status === 6) { // 竞拍失败，保证金已返还
                return h('div', [
                    h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bondOrderDetail(params.row)
                            }
                        }
                    }, '保证金订单详情')
                ])
            }
          }
        }
      ],
      data: [],
      url: '',
      pageNum: 1,
      pageSize: 5,
      total: 0,
      commodityId: '',
      memberName: '',
      status: '',
      info: {},
      biddingInfo: {},
      addressInfo: {},
      chooseData: []
    }
  },
  created () {
    if (this.num === 1) {
      this.url = '/shop/shopBidding/launch/upcomingAuctionDetail'
    } else if (this.num === 2) {
      this.url = '/shop/shopBidding/launch/auctioningDetail'
    } else if (this.num === 3) {
      this.url = '/shop/shopBidding/launch/confirmDetail'
    }
  },
  methods: {
    init () {
      this.$api.post(this.url, {
        commodityId: this.commodityId,
        memberName: this.memberName, // 竞拍人
        status: this.status, // 1：提交订单未支付，2：具备竞拍资格
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(response => {
        console.log('bid', response)
        if (response.code === 200) {
            this.info = response.data
            this.data = response.data.list
            if (this.num === 2 || this.num === 3) {
                this.data.forEach(element => {
                    // 为空说明还没有出过价
                    element.num = element.number
                    element.price = element.price === '' ? '——' : element.price + '元/' + this.info.unit
                    element.number = element.number === '' ? '——' : element.number + this.info.unit
                    element.payTime = element.payTime === '' ? '——' : element.payTime
                    element._disabled = element.status === 2 || element.status === 3 ? false : true // 只有状态为准备抢拍或者已出价的可以操作竞拍成功或失败
                })
            }
            this.total = response.data.total
        }
      }).catch(error => {
        this.$Message.error('服务器异常！')
      })
    },
    // 保证金订单详情
    bondOrderDetail (item) {
        this.$api.get('/shop/shopBidding/launch/orderDetail?id=' + item.id).then(response => {
            console.log('保证金订单详情', response)
            if (response.code === 200) {
                this.biddingInfo = response.data
                this.addressInfo = response.data.addressInfo
                this.bondOrderModal = true
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    // 取消保证金订单
    cancelbondOrder (item) {
        this.$Modal.confirm({
            title: "操作提示",
            content: "<p>您是否确定取消该订单？</p>",
            cancelText: "取消",
            onOk: () => {
                // 取消
                this.$api.get('/shop/shopBidding/launch/cancelOrder?id=' + item.id).then(response => {
                    console.log('取消该订单', response)
                    if (response.code === 200) {
                        this.$Message.success('取消订单成功！')
                        this.init()
                    } else {
                        this.$Message.error('取消订单失败！')
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    sendShow() {
      this.$emit("changeStatus", false)
    },
    showModal(e) {
      this.modalValue = e
      console.log(e)
    },
    success (item) {
      console.log(item)
      if (item.num !== '' && parseFloat(item.num) > parseFloat(this.info.remainNum)) {
        this.$Message.info('竞拍数量超过剩余可售数量！')
        return
      }
      this.$Modal.confirm({
            title: '操作提示',
            content: `<p>是否确认以下竞拍人竞拍成功？</p><p>竞拍人：${item.memberName}；竞拍数量：${item.number}；竞拍价格：${item.price}；</p>`,
            okText:'确定',
            cancelText:'取消',
            width: '520',
            onOk: () => {
                this.$api.post('/shop/shopBidding/launch/handle', [{
                    id: item.id,
                    status: 4
                }]).then(response => {
                    console.log('del response', response)
                    if (response.code === 200) {
                        this.$Message.success('确认成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    fail (item) {
        this.$Modal.confirm({
            title: '操作提示',
            content: `<p>是否确认以下竞拍人竞拍失败？</p><p>竞拍人：${item.memberName}；竞拍数量：${item.number}；竞拍价格：${item.price}；</p>`,
            okText:'确定',
            cancelText:'取消',
            width: '520',
            onOk: () => {
                this.$api.post('/shop/shopBidding/launch/handle', [{
                    id: item.id,
                    status: 5
                }]).then(response => {
                    console.log('del response', response)
                    if (response.code === 200) {
                        this.$Message.success('确认成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    returnBond (item) {
        this.$Modal.confirm({
            title: '操作提示',
            content: `是否确认退还保证金？`,
            okText:'确定',
            cancelText:'取消',
            width: '520',
            onOk: () => {
                this.$api.post('/shop/shopBidding/launch/handle', [{
                    id: item.id,
                    status: 6
                }]).then(response => {
                    console.log('return response', response)
                    if (response.code === 200) {
                        this.$Message.success('保证金退还成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    checkProgress (item) {
        if (item.status === 4) {
            // 未提交订单
            this.current = 0
        } else if (item.status === 7) {
            // 已提交订单
            this.current = 1
        } else if (item.status === 8) {
            // 已提交订单并付款
            this.current = 2
        }
        this.progressModal = true
    },
    tableChange (arr) {
        this.chooseData = arr
    },
    successAll () {
        if (this.chooseData.length === 0) {
            this.$Message.info('请先勾选要操作的项目！')
            return
        }
        let content = `<p>是否确认以下竞拍人竞拍成功？</p>`
        let arr = []
        let sum = 0
        this.chooseData.forEach(item => {
            content += `<p>竞拍人：${item.memberName}；竞拍数量：${item.number}；竞拍价格：${item.price}；</p>`
            if (item.num !== '') sum += parseFloat(item.num)
            arr.push({
                id: item.id,
                status: 4
            })
        })
        // 判断是否超过剩余可售数量
        if (sum > parseFloat(this.info.remainNum)) {
            this.$Message.info('竞拍数量超过剩余可售数量！')
            return
        }
        this.$Modal.confirm({
            title: '操作提示',
            content: content,
            okText:'确定',
            cancelText:'取消',
            width: '520',
            onOk: () => {
                this.$api.post('/shop/shopBidding/launch/handle', arr).then(response => {
                    console.log('successAll response', response)
                    if (response.code === 200) {
                        this.$Message.success('确认成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    failAll () {
        if (this.chooseData.length === 0) {
            this.$Message.info('请先勾选要操作的项目！')
            return
        }
        let content = `<p>是否确认以下竞拍人竞拍失败？</p>`
        let arr = []
        this.chooseData.forEach(item => {
            content += `<p>竞拍人：${item.memberName}；竞拍数量：${item.number}；竞拍价格：${item.price}；</p>`
            arr.push({
                id: item.id,
                status: 5
            })
        })
        this.$Modal.confirm({
            title: '操作提示',
            content: content,
            okText:'确定',
            cancelText:'取消',
            width: '520',
            onOk: () => {
                this.$api.post('/shop/shopBidding/launch/handle', arr).then(response => {
                    console.log('successAll response', response)
                    if (response.code === 200) {
                        this.$Message.success('确认成功！')
                        this.init()
                    }
                }).catch(error => {
                    this.$Message.error('服务器异常！')
                })
            }
        })
    },
    change () {
        this.pageNum = 1
        this.init()
    },
    handleChange (e) {
      this.pageNum = e
      this.init()
    },
  }
}
</script>
<style lang="scss" scoped>
.detailTop {
  background: #f5f5f5;
  padding: 15px 30px;
}
.statusBtn,
.statusBtn2 {
  font-size: 14px;
  padding: 0 25px;
  height: 32px;
  line-height: 32px;
  border-radius: 16px;
  color: white;
}
.statusBtn {
  background: #ff9900;
}
.statusBtn2 {
  background: #02c587;
}
.soldCount {
  border: 1px dashed #b9b9b9;
  padding: 5px 10px;
  font-size: 16px;
}
</style>
<style lang="less">
    .vertical-center-modal{
        display: flex;
        align-items: center;
        justify-content: center;

        .ivu-modal{
            top: 0;
        }
    }
</style>
<style lang="less">
    .vertical-center-modal{
        display: flex;
        align-items: center;
        justify-content: center;

        .ivu-modal{
            top: 0;
        }
    }
</style>