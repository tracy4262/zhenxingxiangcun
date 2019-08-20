<template>
  <div>
    <div v-if="!showDetail">
      <!-- 顶部搜索框 ：商品名称 状态-->
      <Row type="flex" class="mt30 mb30">
        <Col :span="8">
          <Row type="flex" align="middle">
            <Col :span="6">
              <p style="font-size:16px" class="mr10">商品名称</p>
            </Col>
            <Col :span="18">
              <Input v-model="commodityName" @on-change="change" clearable />
            </Col>
          </Row>
        </Col>
      </Row>
      <Table border :columns="columns1" :data="data" v-if="num === 1"></Table>
      <Table border :columns="columns2" :data="data" v-if="num === 2"></Table>
      <Table border :columns="columns3" :data="data" v-if="num === 3"></Table>
      <Page class="mt30 tc" :current="pageNum" :page-size="pageSize" :total="total" @on-change="handleChange" />
    </div>

    <!-- 商品详情 -->
    <div v-else class="mt10">
      <detail ref="detail" :num="num" @changeStatus="changeStatus"></detail>
    </div>
  </div>
</template>
<script>
import detail from "./biddingDetail"
export default {
  components: {
    detail
  },
  props: {
    num: {}
    // columns:{
    // },
    // data:{}
  },
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 5,
      commodityName: '',
      showDetail: false, //是否显示表格详情
      columns1: [
        {
          title: "商品名称",
          key: "productName",
          align: "center"
        },
        {
          title: "竞拍开始时间",
          key: "startTime",
          align: "center"
        },
        {
          title: "可拍数量",
          key: "productVbep",
          align: "center"
        },
        {
          title: "操作",
          key: "operation",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.showDetail = true
                    this.$nextTick(() => {
                        this.$refs['detail'].commodityId = params.row.commodityId
                        this.$refs['detail'].init()
                    })
                  }
                }
              }, "查看详情")
            ])
          }
        }
      ],
      columns2: [
        {
          title: "商品名称",
          key: "productName",
          align: "center"
        },
        {
          title: "竞拍开始时间",
          key: "startTime",
          align: "center"
        },
        {
          title: "竞拍结束时间",
          key: "endTime",
          align: "center"
        },
        {
          title: "可拍数量",
          key: "productVbep",
          align: "center"
        },
        {
          title: "操作",
          key: "operation",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.showDetail = true
                    this.$nextTick(() => {
                        this.$refs['detail'].commodityId = params.row.commodityId
                        this.$refs['detail'].init()
                    })
                  }
                }
              }, "查看详情")
            ])
          }
        }
      ],
      columns3: [
        {
          title: "商品名称",
          key: "productName",
          align: "center"
        },
        {
          title: "竞拍开始时间",
          key: "startTime",
          align: "center"
        },
        {
          title: "竞拍结束时间",
          key: "endTime",
          align: "center"
        },
        {
          title: "可拍数量",
          key: "productVbep",
          align: "center"
        },
        {
          title: "操作",
          key: "operation",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Button", {
                props: {
                  type: "primary",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.showDetail = true
                    this.$nextTick(() => {
                        this.$refs['detail'].commodityId = params.row.commodityId
                        this.$refs['detail'].init()
                    })
                  }
                }
              }, "查看详情")
            ])
          }
        }
      ],
      data: [],
      url: ''
    }
  },
  created () {
    if (this.num === 1) {
      this.url = '/shop/shopBidding/launch/upcomingAuctionList'
    } else if (this.num === 2) {
      this.url = '/shop/shopBidding/launch/auctioningList'
    } else if (this.num === 3) {
      this.url = '/shop/shopBidding/launch/confirmList'
    }
    this.init()
  },
  methods: {
    init () {
        this.$api.post(this.url, {
            sellerAccount: this.$user.loginAccount,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            commodityName: this.commodityName
        }).then(response => {
            console.log('bid', response)
            if (response.code === 200) {
                this.data = response.data.list
                this.data.forEach(element => {
                    element.productVbep = element.productVbep + element.unit
                })
                this.total = response.data.total
            }
        }).catch(error => {
            this.$Message.error('服务器异常！')
        })
    },
    handleChange (e) {
      this.pageNum = e
      this.init()
    },
    changeStatus (res) {
      this.showDetail = res
    },
    change () {
      this.pageNum = 1
      this.init()
    }
  }
};
</script>
