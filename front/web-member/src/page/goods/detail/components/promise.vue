<template>
  <div class="bg-white pt30">
    <Row type="flex" justify="center" class="mb30">
      <Col span="16">
        <Card>
            <p slot="title" class="tc">产品质量标准承诺书</p>
            <p> 
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为向消费者提供安全放心产品，完善监督服务体系，本公司(人)郑重承诺： 本公司(人)在本网站上所发布的产品质量标准严格依据国家标准进行公布，并对标准以外可能影响产品品质的所有信息进行公开。本公司(人)确保所发布信息的真实性和完整性，并愿为之承担相应法律责任，接受各方监督。 消费者若认为本公司(人)所公布信息不完整，请及时与本公司(人)联系，我们会慎重对待您的意见并予以回复。若发现所购产品与公布的产品标准不符或有重大纰漏，也请及时与本公司(人)联系或向有关部门举报，一经查实，本公司(人)除按国家赔偿标准进行赔偿外，另给予因此产生的<br/>
              交通费（当地的士费标准）、<br/>
              误工费（当地最低工资标准）、<br/>
              检测费（法定检测机构检测费用标准）、<br/>
              所购产品价格 <Input v-model="info.money" readonly style="width:150px;" class="money-input"></Input>倍之和的额外补偿。<br/>
              提供放心的产品是我们永恒的追求，欢迎社会各界进行监督。
            </p>
        </Card>
      </Col>
    </Row>
  </div>
</template>
<script>
export default {
  data () {
    return {
      info: {
        integrity: '是',
        money: 1
      }
    }
  },
  created () {
      // 商品详情 第5步 
      this.$api.post('/shop/commodityDetail/findCommodityDetailFive', {
        pushShopCommodityId: this.$route.query.id
      }).then(response => {
        if (response.code === 200 && response.data.info) {
          this.info = response.data.info
          // let data1 = response.data.commodity[0]
          // 商品图片展示
        }
      })
  },
  methods: {
  }
}
</script>
<style lang="scss">
.money-input .ivu-input{
  border-color:#74bd94;
}
</style>
