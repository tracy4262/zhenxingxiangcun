<template>
  <div>
    <div style="background-color: #fafafa; padding-top: 1px; padding-bottom: 1px; margin-top: 20px;">
        <Row type="flex" align="middle">
            <Col span="16"><Title title="相关产品" class="ml10"></Title></Col>
            <Col span="8" class="tr">
                <a @click="goRelatedProduct" class="new-title-16 mr10">查看更多</a>
            </Col>
        </Row>
    </div>
    <Row :gutter="8" class="pt15">
        <Col span="8" v-for="(item, index) in data" :key="index" class="tc">
          <div @click.native="detail(item)" style="cursor: pointer;">
              <img v-if="item.notarizationCertificate" :src="item.notarizationCertificate[0]" style="width: 100%; height: 70px;">
              <img v-else src="../../../../../static/img/goods-list-no-picture1.png" style="width: 100%; height: 70px;" />
              <p style="font-size: 12px; color: #4A4A4A; line-height: 14px;" class="mt10 ell" :title="item.commodityName">{{ item.commodityName }}</p>
              <div class="mt10 t-orange">
                  <div v-if="item.salesWay === '竞价销售'" class="ell" :title="item.startPrice"><span style="font-size: 12px;">{{ item.startPrice }}</span></div>
                  <div v-if="item.salesWay === '预售'" class="ell"><span style="font-size: 12px;">{{ item.orderPrice }}</span></div>
                  <div v-if="item.salesWay === '定价销售'" class="ell"><span style="font-size: 12px;">{{ item.discountPrice === '' ? item.currentPrice : item.discountPrice }}</span></div>
                  <div v-if="item.salesWay === '团购销售'" class="ell"><span style="font-size: 12px;">{{ item.groupBuyingPrice === '' ? item.originalPrice : item.groupBuyingPrice }}</span></div>
                  <div v-if="item.salesWay === '面议'" class="ell"><span style="font-size: 12px;">面议</span></div>
              </div>
          </div>
        </Col>
    </Row>
  </div>
</template>
<script>
import Title from '../../components/title'
export default {
    components: {
      Title
    },
    props: {
        item: {
            type: Object
        },
        about: {
          type: [String, Boolean],
          default: false
        }
    },
    data () {
      return {
        data: []
      }
    },
    created() {
      this.handleGetProduct()
    },
    methods: {
        goRelatedProduct () {
            let url = `/goods/index`
            window.open(url, "_blank")
        },
        // 获取产品推荐的 内容
        handleGetProduct () {
            // default :1 默认排序。timePriceFlag: 0 价格正序 1价格倒序 ,name 关键字
            let list ={
                num: 1,
                size: 3,
                isHomeDisplay: 1
            }
            this.$api.post('/shop/pushShopCommodity/findProduct',list).then(response => {
                if (response.code == 200 && response.data.list) {
                    this.data = response.data.list
                }
            })
        },
        detail (item) {
            let url = `/goods/newDetail?id=${item.id}&account=${item.account}`
            window.open(url, '_blank')
        }
    }
}
</script>
<style lang="scss" scoped>
.new-title-16{
    color: #4A4A4A;
    font-size: 12px;
    &:hover{
        color: #00c587;
    }
}
</style>
