<template>
  <div class="pl10 pr10">
    <Form :label-width="100" 
          v-for="(item, index) in data" :key="index"
          label-position="left" 
          class="pl20 pr20 mb30" 
          >
          <Row :gutter="16">
              <Col span="8">
                  <FormItem prop="networkName" label="网点名称">
                    {{item.networkName}}
                  </FormItem>
              </Col>
              <Col span="8">
                  <Form-item prop="networkType" label="网点类型">
                    {{item.networkType[0]}}
                  </Form-item>
              </Col>
          </Row>
           <Row :gutter="16">
              <Col span="16">
                  <Form-item prop="location" label="网点所在地" @click.native="activeMode = index">
                    {{item.location}}
                  </Form-item>
              </Col>
              <!-- <Col span="24">
                  <Form-item  label="详细地址">
                      <Row>
                        <Col span="10" class="pr10">
                          <Input v-model="item.address" :maxlength="50"  placeholder="请输入所在街道/湾组/楼栋" @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                        <Col span="5" class="pl10">
                          <Input v-model="item.houseNumber" :maxlength="20"  placeholder="请输入详细门牌号"  @on-change="changePerfectAddress(item, index)"></Input>
                        </Col>
                      </Row>
                  </Form-item>
              </Col> -->
               <Col span="16">
                  <Form-item  label="网点完整地址是">
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
          </Row>
          <div class="pb30 pt10" v-if="item.latitude">
            <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.latitude},${item.longitude}&title=我的位置&content=${item.longitude,item.latitude}&output=html`">
              <img :src="`//api.map.baidu.com/staticimage/v2?ak=7syPirZ2AWxacMfHeAfuujdDgFmxCB5g&center=${item.longitude},${item.latitude}&zoom=15&markers=${item.longitude},${item.latitude}&width=800`" width="100%" />
            </a>
          </div>
    </Form>
  </div>
</template>
<script>
export default {
  props: {
    datas: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      data: [],
    }
  },
  created() {
    this.data = this.datas
  },
  watch: {
    datas: {
      handler (newValue, oldValue) {
        console.log('watch title', oldValue, newValue)
        this.data = newValue
      },
      deep: true
    }
  },
  methods: {
  },
}
</script>
