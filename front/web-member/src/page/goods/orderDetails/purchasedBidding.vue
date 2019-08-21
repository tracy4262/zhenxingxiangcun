<template>
    <div>
        <Tabs :value="tabActive"  :animated="false" @on-click="handleTabsClick">
            <TabPane label="我的保证金" name="myBond">
                <my-bond-list ref="myBond"></my-bond-list>
            </TabPane>
            <TabPane label="竞拍中" name="bidding">
                <bidding-list ref="bidding"></bidding-list>
            </TabPane>
            <TabPane label="已获拍" name="isBidden">
                <is-bidden-list ref="isBidden"></is-bidden-list>
            </TabPane>
            <TabPane label="未获拍" name="notBidden">
                <not-bidden-list ref="notBidden"></not-bidden-list>
            </TabPane>
        </Tabs>
    </div>
</template>
<script>
import orderList from './components/orderList'
import myBondList from './components/myBondList'
import biddingList from './components/biddingList'
import isBiddenList from './components/isBiddenList'
import notBiddenList from './components/notBiddenList'
import {numMulti, numAdd} from '~utils/utils'
import {timeFormat} from './components/mixins'
export default {
    name: 'purchasedBidding',
    components: {
        orderList,
        myBondList,
        biddingList,
        isBiddenList,
        notBiddenList
    },
    data () {
        return {
            tabActive: 'myBond'
        }
    },
    methods: {
        handleTabsClick (e) {
            this.$refs[e].init()
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
