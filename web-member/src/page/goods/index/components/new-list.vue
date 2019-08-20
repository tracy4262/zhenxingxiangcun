<template>
<div>
  <!-- <filter-btn ref="btn" @on-search ="handleOnchange"></filter-btn> -->
  <ul class="goods-list">
    <li v-for="(item, index) in listData" :key="index">
      <div @click="handleDetail(item)">
        <div class="clocker" v-if="item.isDiscount">
          <template >
            距离结束还剩：
            <vui-clocker :time="item.discountEndTime" @get-time="getTimes($event, index)" format="%D天 %H小时 %M分 %S秒"/>
          </template>
        </div>
        <img v-if="item.notarizationCertificate[0]" :src="item.notarizationCertificate[0]" width="100%" height="216">
        <img v-else src="../../../../../static/img/goods-list-no-picture1.png" width="100%" height="216">
        <div class="pd5">
            <!-- 1 团购 2 竞价 3 预售 4 定价 5 面议 -->
            <div v-if="item.salesWay == '团购销售'">
                <div v-if="item.isDiscount">
                    <span class="t-orange">团购价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.groupBuyingPrice}}</b></span>
                    <span class="t-grey ml5"><span style="font-size: 12px; text-decoration: line-through;">￥{{item.originalPrice}}</span></span>
                </div>
                <div v-else>
                    <span class="t-orange">时价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.originalPrice}}</b></span>
                </div>
            </div>
            <div v-if="item.salesWay == '竞价销售'">
                <span class="t-orange">起拍价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.startPrice}}</b></span>
                <!-- <span class="t-grey ml10">时价：<span style="font-size: 12px; text-decoration: line-through;">￥{{item.originalPrice}}</span></span> -->
            </div>
            <div v-if="item.salesWay == '预售'">
                <span class="t-orange">预售价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.orderPrice}}</b></span>
                <!-- <span class="t-grey ml10">定价金额：<span style="font-size: 12px;">￥{{item.originalPrice}}</span></span> -->
            </div>
            <div v-if="item.salesWay == '定价销售'">
                <span class="t-orange" v-if="item.discountPrice && item.isDiscount">时价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.discountPrice}}</b></span>
                <span class="t-orange" v-else>时价：<b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.currentPrice}}</b></span>  
            </div>
            <div v-if="item.salesWay == '面议'">
                <span class="t-orange">价格：<b style="font-size: 12px">￥</b><b style="font-size: 20px">面议</b></span>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="vui-flex-item">
                    <p class="name ell pt5" :title="item.commodityName" style="margin-bottom:6px;">{{item.commodityName}}</p>
                </div>
                <Tag v-if="item.isRetrospect === '是'" color="green">可追溯</Tag>
            </div>
            <div class="vui-flex vui-flex-middle">
                <div class="vui-flex-item">
                    <p span="14" class="ell" :title="item.productLocation" style="cursor: default;">{{item.productLocation}}</p>
                </div>
                <!-- salesNumber 是已售数量  buyers 这个字段是人数-->
                <span class="pl10" v-if="item.salesWay == '团购销售'">{{item.buyers}} 人已购</span>
                <span class="pl10" v-if="item.salesWay == '竞价销售'">{{item.participantCount}} 人出价</span>
                <span class="pl10" v-if="item.salesWay == '预售'">{{item.buyers}} 人已预约</span>
                <span class="pl10" v-if="item.salesWay == '定价销售'">{{item.buyers}} 人已购</span>
                <span class="pl10" v-if="item.salesWay == '面议'">{{item.buyers}} 人已购</span>
            </div>
            <Row class="t-grey" style="font-size:12px;" type="flex" align="middle">
                <Col span="18" class="ell" :title="item.name" style="cursor: pointer;text-decoration: underline;">
                <span v-if="item.name">{{item.name}}</span>
                <span v-else>&nbsp;</span>
                </Col>
                <Col span="6" class="tr">
                <Button icon="ios-text-outline" type="text" @click.stop="webimchat(item.account)"></Button> 
                </Col>
            </Row>
          <!-- <div class="mt10 mb10" v-else>
            <span class="t-orange"><b style="font-size: 12px">￥</b><b style="font-size: 20px">{{item.discount}}</b></span>
          </div> -->
        </div>
      </div>
    </li>
  </ul>
  <div v-if="!listData.length">
    <div  class="tc pt30 pb50">
        <img src="../../../../img/no-content.png"> 
        <p style="margin-top: 10px;">暂无相关产品</p>
    </div>
  </div>
</div>
</template>

<script>
import vuiClocker from '~components/clocker/clocker'
export default {
  components: {
    vuiClocker
  },
  props: {
      listData: Array,
      type: {
          type: Number,
          default: 1
      }
  },
  data () {
    return {
      isShow: false,
      account: '',
      list: {default: '1'},
      pageSize: 20,
      total: 0,
      pageNum: 1,
      gateAccount: ''
    }
  },
  created() {
  },
  methods: {
      getTimes ($event, index) {
        if ($event === '00天 00小时 00分 00秒'){
            this.listData[index].isDiscount = false
        }
      },
    // 到详情页
    handleDetail (item) {
      this.$router.push(`/goods/newDetail?id=${item.id}&account=${item.account}`)
    },
    // 聊天
    webimchat (account) {
      if (!this.$user || !this.$user.loginAccount) {
        this.$Message.error('请登录后再发起聊天')
        this.$emit('on-login')
        return
      } else {
        this.$api.post("/member/fishing/findAvatar", { account: account }).then(response => {
            if (response.code == 200) {
                let sellerData = response.data

                layui.layim.chat({
                    id: sellerData.userId,
                    name: sellerData.name,
                    avatar: sellerData.avatar,
                    type: 'friend'
                })
            }
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-list{
  .clocker{
    background: rgba(254,121,34,1);
    color: #fff;
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    padding: 6px 2px;
    text-align: center;
  }
  li{
    height: 350px;
    position: relative;
    background: #fff;
    margin: 15px 15px 0 0;
    display: inline-block;
    width: calc(100% / 5 - 12px);
    list-style: none;
    padding: 2px;
    border: 1px solid rgba(237,237,237,0.62);
    transition: box-shadow .2s cubic-bezier(.47,0,.745,.715);
    vertical-align: middle;
    &:nth-child(5n){
      margin-right: 0;
    }
    .name{color:#4a4a4a;}
    &:hover{
      box-shadow: 0 0 0 2px #00c587;
    }
    a {
      display: block;
    }
  }
}
</style>
