<template>
    <div>
        <Modal
            v-model="show"
            title="订单详情"
            width="800"
            :mask-closable="false">
            <div style="font-size: 14px;" class="pd20 go-fishing-order-detail">
                <p>
                  <span class="pr20" v-if="type === '0'">
                    垂钓产品：草莓
                  </span>
                  <span class="pr20" v-else>
                    采摘产品：草莓
                  </span>
                  <span class="pr20">
                    折扣价：￥ 99.00 /公斤
                  </span>
                  <span class="pr20">
                    原价：<span style="text-decoration:line-through">￥ 99.00</span> /公斤
                  </span>
                  <span class="t-green">
                    省：￥ 99 元
                  </span>
                </p>
                <p style="font-size: 16px; font-weight: 600; padding-top: 20px;">个人信息</p>
                <Row class="pt20">
                  <Col span="3">联系人</Col>
                  <Col>联系人</Col>
                </Row>
                <Row class="pt20">
                  <Col span="3">联系方式</Col>
                  <Col>1878888888</Col>
                </Row>
                <Row class="pt20">
                  <Col span="3" v-if="type === '0'">预约垂钓时间</Col>
                  <Col span="3" v-else>预约采摘时间</Col>
                  <Col>2019-02-01 9:00:00</Col>
                </Row>
                <Row class="pt20" style="font-size: 20px;">
                  <p></p>
                  <Col span="3">预约金：</Col>
                  <Col>￥9999</Col>
                </Row>
                <p class="pt30 pb20 mt40" style="border-top: 1px solid #eee;" v-if="statusType === '4'">订单合计金额：9999</p>
                <!-- <Row class="pt20">
                  <Col span="3">合计订单金额：</Col>
                  <Col></Col>
                </Row> -->
                <Form ref="info" :label-width="90" label-position="left" :model="info" :rules="infoRuleInline" v-if="statusType === '4'">
                  <FormItem  label="退款金额：" prop="refundAmount">
                    <Input :maxlength="20" style="width: 220px;" v-model="info.refundAmount"/>
                  </FormItem>
                  <FormItem label="处理备注：">
                    <Input type="textarea" v-model="info.remarks" :maxlength="200" :autosize="{minRows: 3, maxRows: 5}" placeholder="请输入"/>
                  </FormItem>
                </Form>
                <p class="pb20 t-grey">处理人员：康罗娜</p>
                <p class="pb20 t-grey">处理时间：2019-2-21 09:52:51</p>
                <div class="tc pt20">
                  <Button type="primary" @click="onSave('1')">确认退款</Button>
                  <Button  @click="onSave('2')">拒绝退款</Button>
                </div>
            </div>
            <div slot="footer"></div>
        </Modal>
    </div>
</template>
<script>
import {numMulti} from '~utils/utils'
    export default {
        props: {
          statusType: {
              type: String,
              default: '0', // 0 全部 1 待付款 2 待处理 3 已完成 4 退款处理
          },
          type: {
            type: String,
            default: '0' // 0 垂钓 1 采摘
          }
        },
        data () {
            return {
                show: false,
                data: {
                },
                info: {
                  refundAmount: '',
                  remarks: '',
                },
                infoRuleInline: {
                  refundAmount: [
                    {required: true, message:'请填写退款金额', trigger: 'blur'}
                  ]
                }
            }
        },
        methods: {
            // 点击确认
            ok () {
                
            },
            onSave (type) {
              // 1 确认退款 2拒绝退款
              let flag = true
              if (type === '1') {
                this.$refs['info'].validate((v) => {
                  if (!v) {
                    flag = false
                  }
                })
              }
              if (flag) {
                this.show = false
                // this.$api.post('').then(response => {
                //   if (response.code == 200) {

                //   }
                // })
              } else {
                this.$Message.error('请核对表单信息！')
              }
            },
            // 对话框显示
            showModal () {
                // this.data = e
                this.show = true
            }
        }
    }
</script>
<style lang="scss">
.go-fishing-order-detail .ivu-form .ivu-form-item-label{
  font-size: 14px;
}
</style>
