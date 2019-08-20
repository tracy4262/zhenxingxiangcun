<template>
  <div class="main">
    <div style="color:#333;font-size: 16px;">
      <template class="payment">
        <Tabs type="card" :animated="false">
          <TabPane label="本条消息金额">
            <div class="tab-1">
              <Row>
                <Col span="5">
                <div class="text-left">应付金额：</div>
                </Col>
                <Col span="19">
                <em>{{$route.query.price}}</em>元
                </Col>
              </Row>
              <Row>
                <Col span="5">
                <div class="text-left" style="margin-top: 40px;">支付方式：</div>
                </Col>
                <Col span="17">
                <template>
                  <Tabs type="card" :animated="false">
                    <TabPane label="网上银行">
                      <div class="bank-pay">
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                      </div>
                    </TabPane>
                    <TabPane label="支付宝">标签二的内容</TabPane>
                    <TabPane label="微信支付">标签三的内容</TabPane>
                  </Tabs>
                </template>
                </Col>
              </Row>
              <Row>
                <Col span="5">
                <div class="text-left">实付金额：</div>
                </Col>
                <Col span="19">
                <em>{{$route.query.price}}</em>元
                </Col>
              </Row>
              <Row>
                <Col span="24">
                <div class="pay-now">
                  <Button type="warning" size="large">立即支付</Button>
                </div>
                </Col>
              </Row>
            </div>
          </TabPane>
          <TabPane label="订阅者发布">
            <div class="tab-1">
              <Row>
                <Col span="5">
                <div class="text-left">订阅时长：</div>
                </Col>
                <Col span="19">
                <RadioGroup v-model="checkMonth">
                  <Radio label="一个月"></Radio>
                  <Radio label="三个月"></Radio>
                  <Radio label="半年"></Radio>
                  <Radio label="一年"></Radio>
                </RadioGroup>
                </Col>
              </Row>
              <Row>
                <Col span="5">
                <div class="text-left">应付金额：</div>
                </Col>
                <Col span="19">
                <em>99.90</em>元
                </Col>
              </Row>
              <Row>
                <Col span="5">
                <div class="text-left" style="margin-top: 40px;">支付方式：</div>
                </Col>
                <Col span="17">
                <template>
                  <Tabs type="card" :animated="false">
                    <TabPane label="网上银行">
                      <div class="bank-pay">
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                        <Button type="default" icon="plus">更多银行</Button>
                      </div>
                    </TabPane>
                    <TabPane label="支付宝">标签二的内容</TabPane>
                    <TabPane label="微信支付">标签三的内容</TabPane>
                  </Tabs>
                </template>
                </Col>
              </Row>
              <Row>
                <Col span="5">
                <div class="text-left">实付金额：</div>
                </Col>
                <Col span="19">
                <em>99.90</em>元
                </Col>
              </Row>
              <Row>
                <Col span="24">
                <div class="pay-now">
                  <Button type="warning" size="large" @click="paynow">立即支付</Button>
                </div>
                </Col>
              </Row>
            </div>
          </TabPane>
        </Tabs>
      </template>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        checkMonth: ''
      }
    },
    methods: {
      paynow () {
        this.$router.push({path: '/accountDetails'})
      }
    }
  }
</script>
<style>
  .main{
    width:1200px;
    margin: 0 auto;
  }
.payment .ivu-tabs-nav-wrap{
  padding-left:20%
}
.payment em{
  color: #ff4643;
  font-weight: 700;
  font-style: normal;
}
.payment .ivu-row{
  margin-top:20px;
}
/*消息金额部分*/
  .tab-1{
    width: 70%;
    margin-left:15%;
  }
  .text-left{
    text-align: right;
    padding-right:20px;
  }
  .pay-now{
    width:80px;
    margin:0px auto;
  }
  .bank-pay Button{
    margin-bottom: 10px;
  }
  /*小计金额部分结束*/
  /*订阅者发布部分开始*/
.ivu-radio-wrapper{
  margin-right: 40px;
}
  /*订阅者发布部分结束*/
</style>
