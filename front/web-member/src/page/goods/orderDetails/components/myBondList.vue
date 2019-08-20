<template>
    <div class="order-contents-list">
        <Row class="mb20 pd10">
            <label>保证金状态</label>
            <Select v-model="status" style="width: 300px" class="ml20" @on-change="change" clearable>
                <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
        </Row>
        <Row style="background:#f7f7f7" :gutter="32"  type="flex" justify="center" align="middle">
            <Col span="8"><div class="pd10">竞拍商品</div></Col>
            <Col span="3"><div class="pd10 tc">保证金金额</div></Col>
            <Col span="3"><div class="pd10 tc">保证金状态</div></Col>
            <Col span="5"><div class="pd10 tc">支付时间</div></Col>
            <Col span="5"><div class="pd10 tc">返还时间</div></Col>
        </Row>
        <div v-if="data.length">
            <div v-for="(item, index) in data" :key="index" class="mt20"  style="background:#FCFDFE">
                <p class="pd10" style="border:1px solid #f1f1f1"> 
                    <span class="pl10">商家：{{ item.memberName }}</span>
                    <Icon type="md-text" style="font-size:18px;" class="t-green mr5" @click="handleWebimchat(item.sellAccount)"></Icon>
                    <span class="pl30">竞拍编号：{{ item.order }}</span>
                    <span class="pl30">开拍时间：{{ item.startTime }}</span>
                </p>
                <div style="border:1px solid #f1f1f1;border-top:none;">
                    <Row type="flex" justify="center" align="middle">
                        <Col span="8">
                            <Row class="pd10" type="flex" justify="center" align="middle">
                                <Col span="6">
                                    <img v-if="item.image" :src="item.image[0]" width="80" height="80" />
                                    <img v-else src="../../../../../static/img/goods-list-no-picture.png" />
                                </Col>
                                <Col span="18">
                                    <p class="pd10">{{ item.productName }}</p>
                                </Col>
                            </Row>
                        </Col>
                        <Col span="3"><div class="pd10 tc">￥{{ item.margin }}</div></Col>
                        <Col span="3">
                            <div class="pd10 tc">
                                <span v-if="item.status === 1">未支付</span>
                                <span v-if="item.status === 2">已支付</span>
                                <span v-if="item.status === 4">已转货款</span>
                                <span v-if="item.status === 5">待返还</span>
                                <span v-if="item.status === 6">已返还</span>
                            </div>
                        </Col>
                        <Col span="5"><div class="pd10 tc">{{ item.payTime }}</div></Col>
                        <Col span="5"><div class="pd10 tc"></div></Col>
                    </Row>
                </div>
                <!-- 已生成保证金订单 但未支付 -->
                <div style="border: 1px solid #f1f1f1; border-top: none;" v-if="item.status === 1">
                    <Row type="flex" justify="center" align="middle" style="height: 50px;">
                        <Col span="19">
                            <div class="pd10">
                                距离订单支付截止时间还有：
                                <vui-clocker :time="item.expireTime" format="%D天 %H小时 %M分 %S秒"/>
                            </div>
                        </Col>
                        <Col span="5" class="tc"><Button type="primary" @click="payNow(item, index)" :disabled="item.disabled">立即支付</Button></Col>
                    </Row>
                </div>
            </div>
            <div class="mt30 mb50 tc">
                <Page :total="pages.total" :page-size="pages.pageSize" :current="pages.pageNum" @on-change="handleChangePage"></Page>
            </div>
        </div>
        <div v-else class="tc pd20">
            <p>暂无数据</p>
        </div>
        <Modal
            v-model="show"
            :width="520"
            :mask-closable="false"
            title="保证金支付">
            <div class="">
            </div>
            支付。。。
            <div class="tc" slot="footer">
            <Button @click="show=false">取消支付</Button>
            <Button type="primary" @click="ok">确认支付</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import vuiClocker from '~components/clocker/clocker'
export default {
    components: {
        vuiClocker
    },
    data () {
        return {
            data: [],
            pages: {},
            status: '',
            pageNum: 1,
            pageSize: 10,
            statusList: [
                { label: '未支付', value: 1 },
                { label: '已支付', value: 2 },
                { label: '已转货款', value: 4 },
                { label: '待返还', value: 5 },
                { label: '已返还', value: 6 },
            ],
            show: false,
        }
    },
    created () {
        this.init()
    },
    methods: {
        init () {
            this.$api.post('/shop/shopBidding/getMyMargin', {
                buyerAccount: this.$user.loginAccount,
                status: this.status,
                num: this.pageNum,
                size: this.pageSize
            }).then(response => {
                console.log('my bond list response', response)
                if (response.code === 200) {
                    let data = response.data.list
                    data.forEach(element => {
                        // 判断每一个立即支付按钮是否可点击
                        if (this.getTime(element.expireTime)) {
                            element.disabled = false
                        } else {
                            element.disabled = true
                        }
                    })
                    this.data = data
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
        },
        change () {
            this.pageNum = 1
            this.init()
        },
        handleWebimchat (account) {
            console.log('111')
            /* this.$api.post('/portal/shopCommdoity/findLoginUser',{account: account}).then(response => {
                if (response.code == 200) {
                    let data = response.data
                    this.webimchat(data.userId, data.name, data.avatar)
                }
            }) */
        },
        // 翻页
        handleChangePage (e) {
            this.currentPage = e
            this.$emit('on-change', e)
        },
        // 计算是否还在时间内
        getTime (end) {
            let flag = false
            let endTime = new Date(end)
            let currentTime = new Date()
            console.log(endTime)
            if (endTime > currentTime) {
                flag = true
            } else {
                flag = false
            }
            return flag
        },
        payNow (item, index) {
            if (this.getTime(item.expireTime)) {
                this.show = true
            } else {
                this.data[index].disabled = true
                this.$Message.info('已超过订单最后支付时间！')
            }
        },
        ok () {
            // 买家支付保证金
            this.$api.post('/shop/shopBidding/payMargin', {
                buyerAccount: this.$user.loginAccount,   //买家账号
                commodityId: this.commodityId,  //商品ID
            }).then(response => {
                if (response.code === 200) {
                    this.show = false
                    this.$Message.success('支付成功！')
                    this.init()
                }
            })
        }
    }
}
</script>
