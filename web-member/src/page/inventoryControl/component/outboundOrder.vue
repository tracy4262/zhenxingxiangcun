<template>
   <Modal
        v-model="isShow"
        :mask-closable="false"
        :width="1200"
        :closable="false"
        footer-hide>
        <div slot="header">
          <Row type="flex" align="middle" v-if="isShow">
            <Col span="12"><b class="t-green">{{title}}</b> </Col>
            <Col span="12" class="tr">
              <Button @click="isShow = false">关闭</Button>
              <!-- <Button @click="printDeal">打印</Button> -->
              <Button  v-print="'#printOutBoxs'">打印</Button>
            </Col>
          </Row>
        </div>
        <div id="printOutBoxs" class="pd20" ref="printOutBoxs" v-if="isShow">
          <h3 class="tc">{{title}}</h3>
          <p class="tr">单号：<span class="t-grey">{{info.order}}</span></p>
          <Row type="flex" align="middle" class="pt10 pb20">
            <Col span="12">
              <p class="">经手人：<span class="t-grey">{{info.operatorAccount}}</span></p>
            </Col>
            <!-- <Col span="6">
              <p class="">库房：<span class="t-grey">9999999999</span></p>
            </Col> -->
            <Col span="12">
              <p class="tr">出库日期：<span class="t-grey">{{info.createTime}}</span></p>
            </Col>
          </Row>
          <table class="ivu-table-storage">
              <tr>
                <th class="ivu-table-column-center" style="width: 15%;">
                  <div class="ivu-table-cell">
                    产品编码
                  </div>
                </th>
                <th class="ivu-table-column-center"  style="width: 15%;">
                  <div class="ivu-table-cell">
                    产品名称
                  </div>
                </th>
                <!-- <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    批次号
                  </div>
                </th> -->
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    仓库
                  </div>
                </th>
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    计量单位
                  </div>
                </th>
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    数量
                  </div>
                </th>
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    单价（元）
                  </div>
                </th>
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    金额
                  </div>
                </th>
                <th class="ivu-table-column-center" style="width: 10%;">
                  <div class="ivu-table-cell">
                    附注
                  </div>
                </th>
              </tr>
              <tr class="ivu-table-row" v-for="(item, index) in data">
                  <td class="ivu-table-column-center" style="width: 20%;">
                    <div class="ivu-table-cell">
                      {{item.productCode}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 15%;">
                    <div class="ivu-table-cell">
                      {{item.productName}}
                    </div>
                  </td>
                  <!-- <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.batchNumber}}
                    </div>
                  </td> -->
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.storeName}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.unit}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.number}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.price}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      {{item.totalPrice}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center">
                    <div class="ivu-table-cell">
                      {{item.note}}
                    </div>
                  </td>
              </tr>
              <tr class="ivu-table-row">
                  <td class="ivu-table-column-center" style="width: 20%;">
                    <div class="ivu-table-cell">
                      合计金额（大写）
                    </div>
                  </td>
                  <td class="" style="width: 15%;" colspan="4">
                    <div class="ivu-table-cell">
                      {{total}}
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                    </div>
                  </td>
                  <td class="ivu-table-column-center" style="width: 10%;">
                    <div class="ivu-table-cell">
                      
                    </div>
                  </td>
                  <td class="ivu-table-column-center">
                    <div class="ivu-table-cell">
                      
                    </div>
                  </td>
              </tr>
          </table>
        </div>
    </Modal>
</template>
<script>
import {numAdd, numMulti, convertCurrency} from '~utils/utils'
export default {
  data () {
    return {
      isShow: false,
      data: [],
      info: {
        order: '', // 单号：
        operatorAccount: '', // 经手人：
        storeName: '', // 库房：
        createTime: '', // 出库日期：
      },
      title: '出库单',
      total: null
    }
  },
  created() {
    this.compute()
  },
  methods: {
    init (info, data) {
      this.data = data
      this.info = info
      this.isShow = true
      this.compute()
    },
    // 计算金额
    compute () {
      let total = 0
      this.data.forEach(e => {
        total = numAdd(parseFloat(total).toFixed(2), parseFloat(e.totalPrice).toFixed(2)).toFixed(2)
      })
      console.log('totaltotaltotal', total)
      this.total = convertCurrency(total)
    },
    // printDeal () {
    //     var printBox = this.$refs['printOutBoxs']
    //     //拿到打印的区域的html内容
    //     var newContent =printBox.innerHTML;
    //     //将旧的页面储存起来，当打印完成后返给给页面。
    //     var oldContent = document.body.innerHTML;
    //     //赋值给body
    //     document.body.innerHTML = newContent;
    //     //执行window.print打印功能
    //     window.print();
    //     // 重新加载页面，以刷新数据。以防打印完之后，页面不能操作的问题
    //     window.location.reload();
    //     document.body.innerHTML = oldContent;
    //     return false;
    // },
  },
}
</script>