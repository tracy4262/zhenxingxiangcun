<template>
<div class="pt20 pb20">
    <Form :label-width="80">
        <Row>
            <Col span="8" class="pr20">
                <FormItem label="商品名">
                    <Input v-model="info.productName" clearable></Input>
                </FormItem>
            </Col>
            <Col span="8" class="pr20" v-if="searchStatus">
                <FormItem label="交易状态">
                    <Select v-model="info.dealState" clearable>
                        <Option v-for="item in tradingStatuss" :value="item.value" :key="item.value">{{item.label}}</Option>
                    </Select>
                </FormItem>
            </Col>
            <Col span="8" class="pr20">
                <FormItem label="成交时间">
                    <DatePicker type="daterange" placement="bottom-end" v-model="info.dealTime" clearable :editable="false" :options="options3" style="width: 100%;" @on-change="handleChange"></DatePicker>
                </FormItem>
            </Col>
            <Col span="8" class="pr20" v-if="searchStatus">
                <FormItem label="评价状态">
                    <Select v-model="info.judgeState" clearable>
                        <Option v-for="item in evaluationStatuss" :value="item.value" :key="item.value">{{item.label}}</Option>
                    </Select>
                </FormItem>
            </Col>
            <Col span="8" class="pr20" v-if="!isSeller">
                <FormItem label="卖家名称">
                    <Input v-model="info.seller" clearable></Input>
                </FormItem>
            </Col>
            <Col span="8" class="pr20" v-if="isSeller">
                <FormItem label="买家名称">
                    <Input v-model="info.buyer" clearable></Input>
                </FormItem>
            </Col>
            <Col span="8" :offset="searchStatus ? 0 : 16">
                <FormItem>
                    <Button type="primary" @click="handleSearch">查询</Button>
                    <Button type="text" @click="handleClear">清空条件</Button>
                </FormItem>
            </Col>
        </Row>

    </Form>
</div>
</template>

<script>
export default {
    props: {
        searchStatus: {
            type: Boolean,
            default: false
        },
        tradingStatuss: {
            type: Array,
            default: ()=>{
                // 6 待付款 1 待发货  2 已发货 3 等待评价 4 成功的订单 5 关闭的订单 7退货退款
                return [
                    {label: '待付款', value: 6},
                    {label: '待发货', value: 1},
                    {label: '待收货', value: 2},
                    {label: '待评价', value: 3},
                    {label: '已完成', value: 4},
                    {label: '已关闭', value: 5},
                    {label: '退货/退款', value: 7},
                    // {label: '等待发货', value: 1},
                    // {label: '等待收货', value: 2},
                    // {label: '等待评价', value: 3},
                    // {label: '已取消', value: 4},
                    // {label: '已退货', value: 5},
                    // {label: '取消中的订单', value: 6},
                    // {label: '退货中的订单', value: 7}
                ]
            }
        },
        isSeller: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            evaluationStatuss: [
                {label: '未评价', value: 1},
                {label: '已评价', value: 2},
                {label: '双方已评价', value: 3}
            ],
            // 搜索条件
            info: {
                productName: '',
                dealState: 0,
                dealTime: [],
                judgeState: 0, 
                buyer: '',
                seller: '',
                // state: 0, // tab栏状态 0 全部 1等待发货 2等到收货 3等待评价
                // dealState: 0, // 交易状态 0 全部状态 1等待发货 2等到收货 3等待评价 4已取消
                // judgeState: 0, // 评价状态 0 全部状态 1未评价 2已评价 3双方已评价
                // productName: '', // 商品名称
                // buyer: '', // 买家名称 不支持模糊搜索
                // seller: '', // 卖家名称 不支持模糊搜索
                // startDate: '', // 成交时间 开始时间
                // endDate: '' // 成交时间 结束时间
            },
            options3: {
                    disabledDate (date) {
                        return date && date.valueOf() > Date.now()
                    }
                },
        }
    },
    methods: {
        handleChange () {

        },
        handleClear () {
            this.info = {
                productName: '',
                dealState: 0,
                dealTime: [],
                judgeState: 0,
                seller: ''
            }
        },
        handleSearch () {
            if (!this.info.judgeState) {
                this.info.judgeState = 0
            }
            if (!this.info.dealState) {
                this.info.dealState = 0
            }
            if (this.info.dealTime[0]) {
                this.info.startDate = this.moment(this.info.dealTime[0]).format('YYYY-MM-DD')
            } else {
                this.info.startDate = ''
            }
            if (this.info.dealTime[1]) {
                this.info.endDate = this.moment(this.info.dealTime[1]).format('YYYY-MM-DD')
            } else {
                this.info.endDate = ''
            }
            if (this.isSeller) { // 卖家
                this.info.from = 1
            } else { // 买家
                this.info.from = 0
            }
            this.$emit('on-search',this.info)
        }
    }
}
</script>

