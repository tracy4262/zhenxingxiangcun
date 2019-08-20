<template>
  <div style="background: #F4F4F4;">
    <div class="contcats">
      <div class="concat-list" v-for="(item, index) in data">
        <Row>
          <Col span="12" v-if="item.safeFormData[0].memberMameStatus">
            <div class="vui-flex li">
                <Icon type="ios-contact" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  会员全称：{{item.safeFormData[0].member_name}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].memberAbbreviationStatus">
            <div class="vui-flex li">
                <Icon type="ios-contact-outline" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  名称简写：{{item.safeFormData[0].member_abbreviation}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].contactNameStatus">
            <div class="vui-flex li">
                <Icon type="md-contact" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  联系人姓名：{{item.safeFormData[0].contact_name}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].cardStatus">
            <div class="vui-flex li">
                <Icon type="ios-card-outline" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  身份证号：{{item.safeFormData[0].card}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].seatPhoneStatus">
            <div class="vui-flex li">
                <Icon type="md-call" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  座机电话：{{item.safeFormData[0].seat_phone}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].phoneStatus">
            <div class="vui-flex li">
                <Icon type="md-phone-portrait" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  手机号码：{{item.safeFormData[0].phone}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].qqNumberStatus">
            <div class="vui-flex li">
                <Icon type="md-person" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  QQ号码：{{item.safeFormData[0].qq_number}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].wechatNumberStatus">
            <div class="vui-flex li">
                <Icon type="md-person" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  微信账号：{{item.safeFormData[0].wechat_number}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].emailStatus">
            <div class="vui-flex li">
                <Icon type="md-mail" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  邮箱：{{item.safeFormData[0].email}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].websiteUrlStatus">
            <div class="vui-flex li">
                <Icon type="md-infinite" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  网站地址：{{item.safeFormData[0].website_url}}
                </div>
              </div>
          </Col>
          <Col span="12" v-if="item.safeFormData[0].postalCodeStatus">
            <div class="vui-flex li">
                <Icon type="md-person" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  邮政编码：{{item.safeFormData[0].postal_code}}
                </div>
              </div>
          </Col>
          <Col span="24" v-if="item.safeFormData[0].image_status">
            <div class="vui-flex li">
                <Icon type="md-photos" color="#00c587" size="20" class="mr20 mt15"/>
                <div class="vui-flex-item pt15">
                  <Row>
                    <Col span="3">个人照片：</Col>
                    <Col span="21">
                      <Row>
                        <Col span="4" class="pb15" v-for="(e, i) in item.safeFormData[0].image">
                          <img :src="e" alt="" width="100%" height="90px">
                        </Col>
                      </Row>
                    </Col>
                  </Row>
                </div>
              </div>
          </Col>
          <Col span="24" v-if="item.safeFormData[0].detailAddressStatus">
            <div class="vui-flex li">
                <span><Icon type="md-pin" color="#00c587" size="20" class="mr20"/></span>
                <!-- <Icon type="md-person" color="#00c587" size="20" class="mr20"/> -->
                <div class="vui-flex-item">
                  <div class="vui-flex">
                    <div>会员详细地址：</div>
                    <div class="vui-flex-item">{{item.safeFormData[0].detailAddress}}</div>
                  </div>
                  <!-- <Row>
                    <Col span="4">会员详细地址：</Col>
                    <Col span="20">{{item.safeFormData[0].detailAddress}}</Col>
                  </Row> -->
                </div>
              </div>
          </Col>
        </Row>
        <Row v-if="item.safeFormData[0].longitudeStatus">
          <Col span="12">
            <div class="vui-flex li">
                <Icon type="ios-pin" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  东经：{{item.safeFormData[0].longitude}}
                </div>
              </div>
          </Col>
          <Col span="12">
            <div class="vui-flex li">
                <Icon type="ios-pin" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  北纬：{{item.safeFormData[0].latitude}}
                </div>
              </div>
          </Col>
          <div class="pb30 pd50 mt10" v-if="item.safeFormData[0].latitude">
              <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&title=我的位置&content=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&output=html`">
                <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}&zoom=15&scale=2&markers=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}`" alt="" width="100%" height="500px">
              </a>
            </div>
        </Row>
        <!-- <Row class="pt30">
          <Col span="11">
            <h3 class="pl10">{{item.safeFormData[0].tag}}</h3>
            <ul class="pl10 pr30">
              <li class="vui-flex" v-if="item.safeFormData[0].contact_name">
                <Icon type="md-person" color="#00c587" size="20" class="mr20"/>
                <div class="vui-flex-item">
                  联系人：{{item.safeFormData[0].contact_name}}
                </div>
              </li>
              <li class="vui-flex">
                <Icon type="md-call" color="#00c587" size="20" class="mr20"/>
                 <div class="vui-flex-item">
                  电话：{{item.safeFormData[0].phone}}
                </div>
              </li>
              <li class="vui-flex">
                <Icon type="ios-mail" color="#00c587" size="20" class="mr20"/>
                 <div class="vui-flex-item">
                  邮编：{{item.safeFormData[0].postal_code}}
                </div>
              </li>
              <li class="vui-flex">
                <Icon type="md-pin" color="#00c587" size="20" class="mr20"/>
                 <div class="vui-flex-item">
                  地址：{{item.safeFormData[0].detailAddress}}
                </div>
              </li>
            </ul>
            <view-panel
              :data="item.safeFormData[0].safeFormData"
              ></view-panel>
          </Col>
          <Col span="13">
            <div v-if="item.safeFormData[0].coordinate">
              <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&title=我的位置&content=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&output=html`">
                <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}&zoom=15&scale=2&markers=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}`" alt="" width="100%" height="260px">
              </a>
            </div>
          </Col>
        </Row> -->
      </div>
    </div>
    <div class="contcat">
    </div>
  </div>
</template>
<script>
import { navStatus } from './mixins/commonMixins'
import viewPanel from './components/view-panel'
  export default {
    mixins: [navStatus],
    components: {
      viewPanel
    },
    data () {
      return {
        loginAccount: '',
        data: []
      }
    },
    created() {
      this.loginAccount = this.$route.query.uid
      this.getList()
    },
    methods: {
      getList () {
        this.$api.post('/member/columnSettings/findContact', {account: this.loginAccount}).then(response => {
          if (response.code === 200) {
            this.data = response.data
          }
        })
      },
    }
  }
</script>
<style>
.contcat {
    background: -webkit-linear-gradient(left,#5096F7 , #B0E458 ); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(right,#5096F7 , #B0E458 ); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(right,#5096F7 , #B0E458 ); /* Firefox 3.6 - 15 */
    background: linear-gradient(to right,#5096F7 , #B0E458 ); /* 标准的语法 */
    height: 465px;
    margin-top: -236px; 
}
.contcats{
  width: 1072px;
  margin: 0 auto;
  padding-top: 80px;
  position: relative;
  min-height: 554px;
}
.concat-list{
  min-height: 336px;
  background: #FFFFFF;
  box-shadow: 0px 2px 34px 0px rgba(17,36,40,0.20);
  margin-bottom: 40px;
  padding:30px;
  border-radius: 4px;
  color:#4a4a4a;
}
.concat-list h3  {
  font-size: 24px;
  padding-bottom: 20px;
}
.concat-list li i{
  line-height: 40px;
}
.concat-list li div{
  /* display: inline-block; */
  border-bottom: 1px dashed #EEEEEE; 
  line-height: 40px;
  font-size: 16px;
}
.concat-list .li{
  padding: 0px 20px;
}
.concat-list .li i{
  line-height: 40px;
}
.concat-list .li .vui-flex-item{
  /* display: inline-block; */
  border-bottom: 1px dashed #EEEEEE; 
  line-height: 40px;
  font-size: 16px;
}
</style>
