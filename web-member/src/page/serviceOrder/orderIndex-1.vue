<template>
    <div>
        <div ref="top">
            <top :address="false" />
        </div>
        <div class="main" :style="{'min-height': height}">
            <app-banner
            src="../../../static/img/app-banner-picking.png"
            title="订单管理">
            </app-banner>
            <Tabs :value="tabActive" :animated="false" @on-click="tabClick">
                <TabPane label="景区订单" name="spot">
                    <order-list :type="'2'" v-if="spot" />
                </TabPane>
                <TabPane label="农家乐订单" name="rest">
                    <order-list :type="'3'" v-if="rest" />
                </TabPane>
                <TabPane label="民宿订单" name="room">
                    <order-list :type="'4'" v-if="room" />
                </TabPane>
                <TabPane label="采摘订单" name="pick">
                    <order-list :type="'1'" v-if="pick" />
                </TabPane>
                <TabPane label="垂钓订单" name="fish">
                    <order-list :type="'0'" v-if="fish" />
                </TabPane>
            </Tabs>
            <router-view></router-view>
        </div>
        <div ref="foot">
            <foot></foot>
        </div>
    </div>
</template>
<script>
import top from '../../top'
import foot from '../../foot'
import appBanner from '~components/app-banner'
import orderList from './components/orderList'
export default {
    name: 'orderIndex',
    components: {
        top,
        foot,
        appBanner,
        orderList
    },
    data () {
        return {
            tabActive: 'spot',
            height: '',
            spot: true,
            rest: false,
            room: false,
            pick: false,
            fish: false,
            array: ['spot', 'rest', 'room', 'pick', 'fish']
        }
    },
    created () {},
    methods: {
        handleGetHeight () {
            let clientHeight = document.documentElement.clientHeight
            let topHeight = this.$refs.top.offsetHeight
            let footHeight = this.$refs.foot.offsetHeight
            this.height = `${clientHeight-topHeight-footHeight}px`
        },
        tabClick (name) {
            this.array.forEach(element => {
                if (element === name) {
                    this[element] = true
                } else {
                    this[element] = false
                }
            })
        }
    },
    mounted () {
        this.handleGetHeight()
    }
}
</script>
