<template>
<div class="dish-list-ivu">
   <Row class="mt20 pl20"  :gutter="18">
          <Col span="6" v-for="(item, index) in data" class="pb20">
            <Card >
              <div @click="handleDeatil(item)">
                <img :src="item.image_url" alt="" width="100%" height="184px">
                <div class="pl10 pr10">
                    <p class="pt10 ell" :title="item.product_name">{{item.product_name}}</p>
                    <!-- <p class="pt5" v-if="item.discount_price">
                    <span class="t-orange">¥ {{item.discount_price}}</span> 
                    <span class="t-grey pl5" style="text-decoration: line-through;">¥ {{item.product_price}}</span>
                    </p> -->
                    <p class="pt5">
                    <span>价格：<span class="t-orange">{{item.discount_price ? item.discount_price : item.product_price}}</span>元/{{item.unit}}</span>
                    </p>
                    <p class="pb15 pt5">
                        <span v-if="type == '0'">垂钓时间：</span>
                        <span v-else>采摘时间：</span>
                        {{item.fishing_time | filterTime}}
                    </p>
                </div>
              </div>
            </Card>
          </Col>
      </Row>
      <Modal
        v-model="isShow" width="900" top="10" :mask-closable="false"
        title="产品详细信息">
        <Title title="产品信息"></Title>
        <div class="pd20 vui-form-ssm">
           <Form label-position="left" :label-width="150" >
              <Row :gutter="16">
                  <Col span="12">
                      <Form-item  label="产品名称：">
                          {{activeData.product_name}}
                      </Form-item>
                  </Col>
                  <Col span="12">
                      <Form-item label="计量单位：">
                          {{activeData.unit}}
                      </Form-item>
                  </Col>
              </Row>
              <Row :gutter="16">
                  <Col span="12">
                      <Form-item  label="产品价格：">
                         <p class="pt5" v-if="activeData.discount_price">
                            <span class="t-orange">¥ {{activeData.discount_price}}</span> 
                            <span class="t-grey pl5" style="text-decoration: line-through;">原价：¥ {{activeData.product_price}}</span>
                          </p>
                          <p class="pt5" v-else>
                            <span class="t-orange">¥ {{activeData.product_price}}</span>
                          </p>
                      </Form-item>
                  </Col>
                  <Col span="12">
                      <Form-item  label="联系人：">
                          {{activeData.contact_name}}
                      </Form-item>
                  </Col>
              </Row>
              <Row :gutter="16">
                   <Col span="12">
                      <Form-item  label="联系电话：">
                          {{activeData.contact_phone}}
                      </Form-item>
                  </Col>
                  <Col span="12">
                      <Form-item  label="垂钓时间：">
                          {{activeData.fishing_time | filterTime}}
                      </Form-item>
                  </Col>
              </Row>
              <Row :gutter="16">
                  <Col span="12">
                      <Form-item  label="地址：">
                          {{activeData.address}}
                      </Form-item>
                  </Col>
              </Row>
            </Form>
        </div>
        <Title title="图文详情"></Title>
        <div class="pd20" v-html="activeData.images_detail"></div>
        <div slot="footer">
        </div>
    </Modal>
</div>
</template>
<script>
import Title from '~auth/components/title'
  export default {
    components: {
      Title
    },
    props: {
      typeId: String,
      data: {
          type: Array,
          default: () => {
              return []
          }
      },
      type: String
    },
    data () {
      return {
        activeData: {},
        isShow: false
      }
    },
    created() {
        this.account = this.$route.query.uid
        this.handleInit()
    },
    filters: {
      filterTime: function (value) {
        if (value) {
          value = value.replace(/[\u4e00-\u9fa5]/g, '/')
          let arr = value.split('-')
          let str = ''
          str += arr[0].substring(0, arr[0].length-2)
          str += '--'
          str += arr[1].substring(0, arr[1].length-1)
          return str
        }
      }
    },
    methods: {
      handleDeatil (item) {
        this.isShow = true
        this.activeData = Object.assign({}, item)
      },
      //初始化查询列表
      handleInit(){
            
        },
    }
  }
</script>
<style lang="scss">
.dish-list-ivu{
  .ivu-card-body{
    padding: 0px;
  }
}
</style>

