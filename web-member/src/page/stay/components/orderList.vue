<template>
    <div class="order-contents-list">
        <!-- checkType 0 在线自定义套餐 1 选择固定套餐 -->
        <Row style="background:#f7f7f7"  type="flex" justify="center" align="middle">
            <Col span="3"> <div class="pd10 tc">订单信息</div> </Col>
            <Col span="3"> <div class="pd10 tc">房间名称</div> </Col>
            <Col span="2"> <div class="pd10 tc">房间类型</div> </Col>
            <Col span="2"> <div class="pd10 tc">入住时间</div> </Col>
            <Col span="2"> <div class="pd10 tc">退房时间</div> </Col>
            <Col span="2"> <div class="pd10 tc">入住天数</div> </Col>
            <Col span="2"> <div class="pd10 tc">总价</div></Col>
            <Col span="3"> 
                <div class="pd10 tc">客户信息</div>
            </Col>
            <Col span="2"> <div class="pd10 tc">订单状态</div>   </Col>
            <Col span="3"> <div class="pd10 tc">订单操作</div>   </Col>
        </Row>
        <div  v-if="datas.length" class="pl10 pr10">
            <div v-for="(data, index) in datas" :key="index" class="mt20" >
                <p class="pd10" style="border:1px solid #f1f1f1; background:#FCFDFE"> 
                    <span  class="pl10">订单编号：{{data.orderCode}}</span>
                    <span class="pl30">下单时间：{{data.create_time}}</span>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle" >
                      <Col span="3">
                        <div class="pd10 tc">
                            <p :title="data.setMealName" class="ell-2 pb10">{{data.setMealName}}</p>
                        </div>
                      </Col>
                      <Col span="3">
                        <div class="pt10 tc" v-if="data.checkType === '0'">
                            <p v-for="(item, index) in data.setMeal" class="pb10">{{item.roomName}}</p>
                        </div>
                        <div class="pt10 tc" v-if="data.checkType === '1'">
                            <p v-for="(item, index) in data.setMeal[0].productList" class="pb10">{{item.name}}</p>
                        </div>
                      </Col>
                      <Col span="2">
                        <div class="pt10 tc" v-if="data.checkType === '0'">
                            <p v-for="(item, index) in data.setMeal" class="pb10">{{item.roomClassName}}</p>
                        </div>
                        <div class="pt10 tc" v-if="data.checkType === '1'">
                            <p v-for="(item, index) in data.setMeal[0].productList" class="pb10">{{item.roomClassName}}</p>
                        </div>
                      </Col>
                      <Col span="2">
                        <div class="pt10" v-if="data.checkType === '0'">
                            <div v-for="(item, index) in data.setMeal">
                                <div class="pb10 tc">{{moment(data.date).format('YYYY-MM-DD')}}</div>
                            </div>
                        </div>
                        <div class="pt10" v-if="data.checkType === '1'">
                            <div v-for="(item, index) in data.setMeal[0].productList">
                                <div  class="pb10 tc">{{moment(data.date).format('YYYY-MM-DD')}}</div>
                            </div>
                        </div>
                      </Col>
                      <Col span="2">
                        <div class="pt10" v-if="data.checkType === '0'">
                            <div v-for="(item, index) in data.setMeal">
                                <div class="pb10 tc">{{moment(data.userTime).format('YYYY-MM-DD')}}</div>
                            </div>
                        </div>
                        <div class="pt10" v-if="data.checkType === '1'">
                            <div v-for="(item, index) in data.setMeal[0].productList">
                                <div class="pb10 tc">{{moment(data.userTime).format('YYYY-MM-DD')}}</div>
                            </div>
                        </div>
                      </Col>
                      <Col span="2">
                        <div class="pt10" v-if="data.checkType === '0'">
                            <div v-for="(item, index) in data.setMeal">
                                <div class="pb10 tc">{{moment(data.userTime).diff(data.date, 'days')}} 天</div>
                            </div>
                        </div>
                        <div class="pt10" v-if="data.checkType === '1'">
                            <div v-for="(item, index) in data.setMeal[0].productList">
                                <div class="pb10 tc">{{moment(data.userTime).diff(data.date, 'days')}} 天</div>
                            </div>
                        </div>
                      </Col>
                      <!-- <Col span="2">
                        <div class="pd10 tc" v-if="data.discountPrice">￥{{!data.discountPrice ? parseFloat(0).toFixed(2) : parseFloat(data.discountPrice).toFixed(2)}}</div>
                        <div class="pd10 tc" v-else>￥{{!data.price ? parseFloat(0).toFixed(2) : parseFloat(data.price).toFixed(2)}}</div>
                      </Col> --> 
                      <Col span="2">
                        <div class="pt10 tc" v-if="data.checkType === '0'">
                            <div v-for="(item, index) in data.setMeal">
                                <div class="pb10 tc" v-if="item.discountPrice">￥{{!item.discountPrice ? parseFloat(0).toFixed(2) : parseFloat(item.discountPrice).toFixed(2)}}</div>
                                <div class="pb10 tc" v-else>￥{{!item.roomPrice ? parseFloat(0).toFixed(2) : parseFloat(item.roomPrice).toFixed(2)}}</div>
                            </div>
                        </div>
                        <div class="pt10 tc" v-if="data.checkType === '1'">
                            <div v-for="(item, index) in data.setMeal[0].productList">
                                <div class="pb10 tc" v-if="item.discount_price">￥{{!item.discount_price ? parseFloat(0).toFixed(2) : parseFloat(item.discount_price).toFixed(2)}}</div>
                                <div class="pb10 tc" v-else>￥{{!item.price ? parseFloat(0).toFixed(2) : parseFloat(item.price).toFixed(2)}}</div>
                            </div>
                        </div>
                      </Col>
                      <Col span="3">
                            <div class="pt10 tc">{{data.buyersName}}</div>
                            <div class="pt10 pb10 tc">{{data.buyersPhone}}</div>
                      </Col>
                      <Col span="2">
                      <!-- 状态，0.待付款，1.待使用，2.已完成 ，3.退款中，4，已拒绝，5.已退款 ，6.待评价 ， 7 已取消 8 已入住-->
                        <div class="pd10 tc" v-if="data.status == '0'">待付款</div>
                        <div class="pd10 tc" v-if="data.status == '1'">待入住</div>
                        <div class="pd10 tc" v-if="data.status == '2'">已退房</div>
                        <div class="pd10 tc" v-if="data.status == '3'">待退款</div>
                        <div class="pd10 tc" v-if="data.status == '4'">已拒绝</div>
                        <div class="pd10 tc" v-if="data.status == '5'">已退款</div>
                        <div class="pd10 tc" v-if="data.status == '6'">待评价</div> <!-- 待评价 -->
                        <div class="pd10 tc" v-if="data.status == '8'">已入住</div>
                        <div class="pd10 tc" v-if="data.status == '7'">已取消</div>
                      </Col>
                      <Col span="3"> 
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
                                <Button type="primary" @click="paymentOrder(data)">确认入住</Button>
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
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                        <div class="pd10 tc" v-if="data.status == '8'">
                            <div>
                                <Button type="primary" @click="checkOut(data)">退房</Button>
                            </div>
                            <div class="pt10">
                                <Button type="text" @click="handleOrderDetail(data)">订单详情</Button>
                            </div>
                        </div>
                      </Col>
                    </Row>
                </div>
                <div style="border:1px solid #f1f1f1;border-top:none;" v-if="(data.checkType === '0' && data.setMeal.length > 1) || (data.checkType === '1' && data.setMeal[0].productList.length > 1)">
                    <div class="pd15 tc" v-if="data.discountPrice">合计：￥{{!data.discountPrice ? parseFloat(0).toFixed(2) : parseFloat(data.discountPrice).toFixed(2)}}</div>
                    <div class="pd15 tc" v-else>合计：￥{{!data.price ? parseFloat(0).toFixed(2) : parseFloat(data.price).toFixed(2)}}</div>
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
        //  确认订单 已入住
        paymentOrder (data) {
          this.$Modal.confirm({
              title: `您是否确认${data.contact[0].contact_name}已入住`,
              content: `您是否确认${data.contact[0].contact_name}已入住`,
              onOk:()=>{
                this.$api.post('/member/fishing/updateOrderStatus', {id: data.id, status: '8'}).then(response => {
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
        checkOut (data) {
          this.$Modal.confirm({
              title: `您是否确认${data.contact[0].contact_name}已退房`,
              content: `您是否确认${data.contact[0].contact_name}已退房`,
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
