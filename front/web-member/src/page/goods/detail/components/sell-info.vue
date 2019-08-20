<template> 
  <div class="company-auth-5 mt5 vui-form-ssm">
    <Form ref="data" label-position="left" :label-width="90">
      <Form-item label="售后服务政策">
        {{info.servicePolicy}}
      </Form-item>
      <Form-item label="退换货政策">
          {{info.returnAndRepair}}
      </Form-item>
      <Form-item label="售后网点">
          <div v-for="(item, index) in info.networkStation" :key="index" style="background:#f9f9f9;" class="pd20 mb30">
            <Form :label-width="90" label-position="left">
                <Row :gutter="16">
                    <Col span="12">
                        <FormItem prop="networkName" label="网点名称">
                            {{item.networkName}}
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <Form-item prop="networkType" label="网点类型">
                            <span v-for="(e, i) in item.networkType">
                              {{e}}
                              <span v-if="i < (item.networkType.length -1)">，</span>
                              <span v-else>。</span>
                            </span>
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item prop="location" label="网点所在地">
                            {{item.location}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item label="详细地址">
                          {{item.address}} {{item.houseNumber}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="24">
                        <Form-item label="网点完整地址">
                            {{item.perfectAddress}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                        <Form-item prop="contact" label="联系人">
                            {{item.contact}}
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="officePhone" label="办公电话">
                            {{item.officePhone}}
                        </Form-item>
                    </Col>
                    <Col span="8">
                        <Form-item prop="phone" label="手机号码">
                            {{item.phone}}
                        </Form-item>
                    </Col>
                </Row>
                <Row :gutter="16">
                    <Col span="8">
                        <FormItem label="东经">
                            {{item.longitude}}
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem label="北纬">
                            {{item.latitude}}
                        </FormItem>
                    </Col>
                    <!-- <Col span="8" style="text-decoration: underline; color: #6C6C6C; cursor: pointer;" class="pt5">
                        <span @click="onSelectPoint(item, index)">定位获取</span>
                    </Col> -->
                </Row>
                <div class="pb30 pt10" v-if="item.latitude">
                    <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
                    <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
                    </a>
                </div>
            </Form>
        </div>
      </Form-item>
      <view-panel
            title="自定义表单"
            :edit="false"
            :data="info.formData">
        </view-panel>
      </Form>
  </div>
</template>
<script>
import Title from '~auth/components/title'
// import viewPanel from '../../../goods/components/vui-form-control/view-panel'
import viewPanel from './vui-form-control/view-panel'
export default {
  components: {
    Title,
    viewPanel
  },
  data() {
    return {
      id: '',
      info: {}
    }
  },
  created () {
    this.id = this.$route.query.id
    this.handleInit()
  },
  methods: {
    // 初始化查询
    handleInit  () {
      this.$api.post('/shop/commodityDetail/findCommodityDetailThree', {
        pushShopCommodityId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.info = response.data.afterSales
          this.categoryId = this.info.productCategoryId
        }
      })
    }
  }
};
</script>
