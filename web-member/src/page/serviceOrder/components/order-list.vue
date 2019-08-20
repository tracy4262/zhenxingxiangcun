<template>
    <div class="order-contents-list">
        <!-- checkType 0 在线自定义套餐 1 选择固定套餐 -->
        <Row style="background:#f7f7f7" type="flex" justify="center" align="middle">
            <Col span="8"> <div class="pd10 tc">订单信息</div> </Col>
            <Col span="3"> <div class="pd10 tc">总价</div></Col>
            <Col span="5"> 
                <div class="pd10 tc">商家信息</div>
            </Col>
            <Col span="3"> <div class="pd10 tc">订单状态</div>   </Col>
            <Col span="5"> <div class="pd10 tc">订单操作</div>   </Col>
        </Row>
        <div  v-if="datas.length" class="pl10 pr10">
            <div v-for="(data, index) in datas" :key="index" class="mt20" >
                <p class="pd10" style="border:1px solid #f1f1f1; background:#FCFDFE"> 
                    <span  class="pl10">订单编号：{{data.orderCode}}</span>
                    <!-- <span class="pl30">下单时间：{{data.createTime}}</span> -->
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle" >
                      <Col span="8">
                        <Row type="flex" align="middle" >
                            <Col span="8">
                              <div class="pd10">
                                <img v-if="data.imageUrl && data.imageUrl[0]" :src="data.imageUrl[0]" alt="" width="100%" height="80px">
                                <img v-else src="../../../../static/img/goods-list-no-picture1.png" alt="" width="100%" height="80px">
                              </div>
                            </Col>
                            <Col span="16">
                              <p :title="data.serviceName" class="ell-2 pb10" v-if="data.type == 5">{{data.serviceName}}</p>
                              <p :title="data.setMealName" class="ell-2 pb10" v-else>{{data.setMealName}}</p>
                              <p v-if="data.paymentTime">成交时间：{{data.paymentTime}}</p>
                              <p v-else>下单时间：{{data.create_time}}</p>
                            </Col>
                        </Row>
                      </Col>
                      <Col span="3">
                        <div class="pd10 tc" v-if="data.discountPrice">￥{{!data.discountPrice ? parseFloat(0).toFixed(2) : parseFloat(data.discountPrice).toFixed(2)}}</div>
                        <div class="pd10 tc" v-else>￥{{!data.price ? parseFloat(0).toFixed(2) : parseFloat(data.price).toFixed(2)}}</div>
                      </Col>
                      <Col span="5">
                        <div v-if="data.contact && data.contact[0]">
                            <div class="pt10 tc">{{data.contact[0].contact_name}}</div>
                            <div class="pt10 pb10 tc">{{data.contact[0].phone}}</div>
                        </div>
                      </Col>
                      <Col span="3">
                      <!-- 状态，0.待付款，1.待使用，2.已完成 ，3.退款中，4，已拒绝，5.已退款 ，6.待评价 ， 7 已取消 8 已入住-->
                        <div class="pd10 tc" v-if="data.status == '0'">
                            <p>
                                待付款
                            </p>
                            <p class="t-grey pt10" style="font-size:12px;">
                                <vui-clocker v-if="data.create_times" :time="data.create_times" @get-time="getTime($event, data)" format="%M分"/>钟后自动关闭
                            </p>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '1'">待使用</div>
                        <div class="pd10 tc" v-if="data.status == '2'">已完成</div>
                        <div class="pd10 tc" v-if="data.status == '3'">退款中</div>
                        <div class="pd10 tc" v-if="data.status == '4'">已拒绝</div>
                        <div class="pd10 tc" v-if="data.status == '5'">已退款</div>
                        <div class="pd10 tc" v-if="data.status == '6'">待评价</div>
                        <div class="pd10 tc" v-if="data.status == '7'">已取消</div>
                        <div class="pd10 tc" v-if="data.status == '8'">已入住</div>
                      </Col>
                      <Col span="5"> 
                        <div class="pd10 tc" v-if="data.status == '0'">
                            <div>
                                <Button type="primary" @click="paymentOrder(data)">付款</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="cancelOrder(data)">取消订单</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '1'">
                            <div>
                                <Button type="primary" @click="reimburse(data)">退款</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '2' || data.status == '7' || data.status == '8'">
                            <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '3' || data.status == '4' || data.status == '5'">
                            <Button type="text" @click="handleOrderDetail(data)">退款详情</Button>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '6'">
                            <div>
                                <Button type="primary" @click="evaluation(data)">评价</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                      </Col>
                    </Row>
                </div>
            </div>
        </div>
        <div v-else class="tc pd20">
            <p>暂无数据</p>
        </div>
        <stayDetail ref="stayDetail" isbuyer @on-save="init"></stayDetail>
        <restaurantDetail ref="restaurantDetail" isbuyer @on-save="init"></restaurantDetail>
        <scenicSpotDetail ref="scenicSpotDetail" isbuyer @on-save="init"></scenicSpotDetail>
        <consultationDetail ref="consultationDetail"></consultationDetail>
        <comments ref="comments" @on-save="updateComments"></comments>
    </div>
</template>
<script>
import stayDetail from './stayDetail'
import restaurantDetail from './restaurantDetail'
import scenicSpotDetail from './scenicSpotDetail'
import consultationDetail from './consultationDetail'
import comments from './comments'
import vuiClocker from '~components/clocker/clocker'
export default {
    props: {
        type: {
            type: String,
            default: '0'
        },
        datas: {
            type:Array,
            default: ()=>{
                return [{}]
            }
        },
    },
    components:{
        stayDetail,
        restaurantDetail,
        scenicSpotDetail,
        consultationDetail,
        comments,
        vuiClocker
    },
    data () {
        return {
        }
    },
    created() {
    },
    methods: {
        getTime ($event, data) {
            // console.log('ttttttt', $event)
            if ($event == '00分 00秒') {
                data.status = 7
            }
        },
        init () {
            this.$emit('on-init')
        },
        // 订单详情
        handleOrderDetail (data) {
            // if (data.)
            console.log(data.setMeal)
            console.log(data.type)
            if (data.type === '2') {
                this.$refs['scenicSpotDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '3') {
                this.$refs['restaurantDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '4') {
                this.$refs['stayDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '5') {
                this.$refs['consultationDetail'].init(data.consultDetailId)
            }
        },
        //  确认订单
        paymentOrder (data) {
          this.$Modal.confirm({
              title: '您是否确认付款',
              content: '您是否确认付款？',
              onOk:()=>{
                this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '1'}).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('付款成功')
                        this.$emit('on-init')
                    } else {
                        this.$Message.error('付款失败')
                    }
                })
              },
              okText:'确定',
              cancelText:'取消'
          })
        },
        // 待付款 点击取消订单
        cancelOrder (data) {
          this.$Modal.confirm({
              title: '您是否确认取消订单',
              content: '您是否确认取消订单？',
              onOk:()=>{
                this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '7'}).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('取消成功')
                        this.$emit('on-init')
                    } else {
                        this.$Message.error('取消失败')
                    }
                })
              },
              okText:'确定',
              cancelText:'取消'
          })
        },
        // 点击 退款
        reimburse (data) {
          this.$Modal.confirm({
              title: '您是否确认退款',
              content: '您是否确认退款？',
              onOk:()=>{
                this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '3'}).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('退款成功')
                        this.$emit('on-init')
                    } else {
                        this.$Message.error('退款失败')
                    }
                })
              },
              okText:'确定',
              cancelText:'取消'
          })
        },
        // 评价
        evaluation (data) {
            this.$refs['comments'].showComment(data)
        },
        updateComments (data) {
            this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '2'}).then(response => {
                if (response.code === 200) {
                    this.$emit('on-init')
                }
            })
        }
    }
}
</script>

<style lang="scss">
.order-contents-list {
    .bd-b-1{
        border-bottom:1px solid #f1f1f1;
    }
}
</style>
