<template>
    <div class="order-contents-list">
        <Row style="background:#f7f7f7" :gutter="32"  type="flex" justify="center" align="middle">
            <Col span="8"><div class="pd10">竞拍商品</div></Col>
            <Col span="3"><div class="pd10 tc">竞拍出价</div></Col>
            <Col span="3"><div class="pd10 tc">竞拍数量</div></Col>
            <Col span="3"><div class="pd10 tc">出价时间</div></Col>
            <Col span="7"><div class="pd10 tc">操作</div></Col>
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
                        <Col span="3"><div class="pd10 tc">{{ item.price === '' ? '——' : '￥'+item.price }}</div></Col>
                        <Col span="3">
                            <div class="pd10 tc">{{ item.number === '' ? '——' : item.number + item.unit }}</div>
                        </Col>
                        <Col span="3"><div class="pd10 tc">{{ item.payTime === '' ? '——' : item.payTime }}</div></Col>
                        <Col span="7">
                            <div class="pd10 tc">
                                <Button type="primary" @click="detail(item)">查看商品详情</Button>
                            </div>
                        </Col>
                    </Row>
                </div>
            </div>
            <div class="mt30 mb50 tc">
                <Page :total="total" :page-size="pageSize" :current="pageNum" @on-change="handleChangePage"></Page>
            </div>
        </div>
        <div v-else class="tc pd20">
            <p>暂无数据</p>
        </div>
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
            pageNum: 1,
            pageSize: 10
        }
    },
    created () {
    },
    methods: {
        init () {
            this.$api.post('/shop/shopBidding/missBid', {
                buyerAccount: this.$user.loginAccount,
                pageNum: this.pageNum,
                pageSize: this.pageSize
            }).then(response => {
                console.log('my notBidden list response', response)
                if (response.code === 200) {
                    this.data = response.data.list
                    this.total = response.data.total
                }
            }).catch(error => {
                this.$Message.error('服务器异常！')
            })
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
            this.pageNum = e
            this.init()
        },
        detail (item) {
            this.$router.push(`/goods/newDetail?id=${item.commodityId}&account=${item.sellerAccount}`)
        }
    }
}
</script>
