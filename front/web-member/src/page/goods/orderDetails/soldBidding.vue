<template>
  <div>
    <div>
      <Tabs value="preBidding" :animated="false" @on-click="changeTabs">
        <TabPane label="即将竞拍" name="preBidding">
          <biddingTable ref="preBidding" :num="1"></biddingTable>
        </TabPane>
        <TabPane label="竞拍中" name="bidding">
          <biddingTable ref="bidding" :num="2"></biddingTable>
        </TabPane>
        <TabPane label="竞拍结果确认" name="biddingRes">
          <biddingTable ref="biddingRes" :num="3"></biddingTable>
        </TabPane>
      </Tabs>
    </div>
  </div>
</template>
<script>
import biddingTable from "./components/biddingTable";
export default {
  components: {
    biddingTable
  },
  data() {
    return {
      num: 1, //切换显示tab}
    }
  },
  methods: {
    changeTabs (e) {
      this.$refs[e].showDetail = false
    }
  }
}
</script>
<style lang="scss" scoped>
.topBanner {
  .topNav {
    display: inline-block;
    margin-right: 30px;
    color: rgb(194, 194, 194);
    font-size: 16px;
    &:hover {
      cursor: pointer;
    }
  }
}
.topNav2 {
  color: #02c587 !important;
}
</style>
