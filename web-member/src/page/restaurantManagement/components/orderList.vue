<template>
    <div class="order-contents-list">
        <!-- checkType 0 在线自定义套餐 1 选择固定套餐 -->
        <Row style="background:#f7f7f7" :gutter="16"  type="flex" justify="center" align="middle">
            <Col span="4"> <div class="pd10 tc">订单信息</div> </Col>
            <Col span="3"> <div class="pd10 tc">预定包房</div> </Col>
            <!-- <Col span="2"> <div class="pd10 tc">预定餐桌</div> </Col> -->
            <Col span="2"> <div class="pd10 tc">用餐人数</div> </Col>
            <Col span="3"> <div class="pd10 tc">用餐时间</div> </Col>
            <Col span="2"> <div class="pd10 tc">总价</div></Col>
            <Col span="3"> 
                <div class="pd10 tc">客户信息</div>
            </Col>
            <Col span="3"> <div class="pd10 tc">订单状态</div>   </Col>
            <Col span="4"> <div class="pd10 tc">订单操作</div>   </Col>
        </Row>
        <div  v-if="datas.length" class="pl10 pr10">
            <div v-for="(data, index) in datas" :key="index" class="mt20" >
                <p class="pd10" style="border:1px solid #f1f1f1; background:#FCFDFE"> 
                    <span  class="pl10">订单编号：{{data.orderCode}}</span>
                    <span class="pl30">下单时间：{{data.create_time}}</span>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle" >
                      <Col span="4">
                        <div class="pd10 tc">
                            <p :title="data.setMealName" class="ell-2 pb10">{{data.setMealName}}</p>
                        </div>
                      </Col>
                      <Col span="3">
                        <div class="pd10 tc" v-if="data.checkType === '0'">
                            <span v-for="(item, index) in data.setMeal[0].tableData">{{item.roomName}}</span>
                        </div>
                        <div class="pd10 tc" v-if="data.checkType === '1'">
                            <span v-for="(item, index) in data.setMeal[0].selectedRoom">{{item.name}}</span>
                        </div>
                      </Col>
                      <!-- <Col span="2">
                        <div class="pd10 tc">45</div>
                      </Col> -->
                      <Col span="2">
                        <div class="pd10 tc" v-if="data.checkType === '0'">
                            <span span="19">{{data.setMeal[0].diningNumber}} 人</span>
                        </div>
                        <div class="pd10 tc" v-if="data.checkType === '1'">
                        <span span="19">{{data.setMeal[0].diningNumber}} 人</span>
                        </div>
                      </Col>
                      <Col span="3">
                        <div class="pd10 tc">{{moment(data.date).format('YYYY-MM-DD')}} {{data.time}}</div>
                      </Col>
                      <Col span="2">
                        <div class="pd10 tc" v-if="data.discountPrice">￥{{!data.discountPrice ? parseFloat(0).toFixed(2) : parseFloat(data.discountPrice).toFixed(2)}}</div>
                        <div class="pd10 tc" v-else>￥{{!data.price ? parseFloat(0).toFixed(2) : parseFloat(data.price).toFixed(2)}}</div>
                      </Col>
                      <Col span="3">
                            <div class="pt10 tc">{{data.buyersName}}</div>
                            <div class="pt10 pb10 tc">{{data.buyersPhone}}</div>
                      </Col>
                      <Col span="3">
                      <!-- 状态，0.待付款，1.待使用，2.已完成 ，3.退款中，4，已拒绝，5.已退款 ，6.待评价 ， 7 已取消 -->
                        <div class="pd10 tc" v-if="data.status == '0'">待付款</div>
                        <div class="pd10 tc" v-if="data.status == '1'">待处理</div>
                        <div class="pd10 tc" v-if="data.status == '2'">已完成</div>
                        <div class="pd10 tc" v-if="data.status == '3'">待退款</div>
                        <div class="pd10 tc" v-if="data.status == '4'">已拒绝</div>
                        <div class="pd10 tc" v-if="data.status == '5'">已退款</div>
                        <div class="pd10 tc" v-if="data.status == '6'">待评价</div>
                        <div class="pd10 tc" v-if="data.status == '7'">已取消</div>
                      </Col>
                      <Col span="4"> 
                        <div class="pd10 tc" v-if="data.status == '0'">
                            <div class="pt10">
                                <Button type="primary" @click="cancelOrder(data)">取消订单</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '1'">
                            <div>
                                <Button type="primary" @click="paymentOrder(data)">确认订单已消费</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '2' || data.status == '7'">
                            <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '3'">
                            <div>
                                <Button type="primary" @click="reimburse(data)">退款</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '4' || data.status == '5'">
                            <Button type="text" @click="handleOrderDetail(data)">退款详情</Button>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '6'">
                            <!-- <div>
                                <Button type="primary" @click="evaluation(data)">评价</Button>
                            </div> -->
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
        <stayDetail ref="stayDetail"></stayDetail>
        <restaurantDetail ref="restaurantDetail"></restaurantDetail>
        <scenicSpotDetail ref="scenicSpotDetail"></scenicSpotDetail>
    </div>
</template>
<script>
import stayDetail from '../../serviceOrder/components/stayDetail'
import restaurantDetail from '../../serviceOrder/components/restaurantDetail'
import scenicSpotDetail from '../../serviceOrder/components/scenicSpotDetail'
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
        scenicSpotDetail
    },
    data () {
        return {
        }
    },
    created() {
    },
    methods: {
        // 订单详情
        handleOrderDetail (data) {
            if (data.type === '2') {
                this.$refs['scenicSpotDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '3') {
                this.$refs['restaurantDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '4') {
                this.$refs['stayDetail'].checkOrder(data.setMeal, data)
            }
        },
        //  确认订单 已消费
        paymentOrder (data) {
          this.$Modal.confirm({
              title: '您是否确认订单已被消费',
              content: '您是否确认订单已被消费？',
              onOk:()=>{
                this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '6'}).then(response => {
                    if (response.code === 200) {
                        this.$Message.success('操作成功')
                        this.$emit('on-init')
                    } else {
                        this.$Message.error('操作失败')
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
            if (data.type === '2') {
                this.$refs['scenicSpotDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '3') {
                this.$refs['restaurantDetail'].checkOrder(data.setMeal, data)
            } else if (data.type === '4') {
                this.$refs['stayDetail'].checkOrder(data.setMeal, data)
            }
        },
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
