<template>
  <div >
    <div class="certification pd20" v-for="(item, index) in data">
        <Row :gutter="38">
          <Col span="12" v-if="item.memberNameFlag" class="pb15">
            会员全称: {{item.memberName}}
          </Col>
          <Col span="12" v-if="item.memberAbbreviationFlag" class="pb15">
          名称简写: {{item.memberAbbreviation}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.contactNameFlag" class="pb15">
          联系人姓名: {{item.contactName}}
          </Col>
          <Col span="12" v-if="item.cardFlag" class="pb15">
          身份证号: {{item.card}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.setPhoneFlag" class="pb15">
          座机电话: {{item.setPhone}}
          </Col>
          <Col span="12" v-if="item.phoneFlag" class="pb15">
          手机号码: {{item.phone}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.qqNumberFlag" class="pb15">
          QQ号码: {{item.qqNumber}}
          </Col>
          <Col span="12" v-if="item.wechatNumberFlag" class="pb15">
          微信账号: {{item.wechatNumber}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.emailFlag" class="pb15">
          邮箱: {{item.email}}
          </Col>
          <Col span="12" v-if="item.websiteUrlFlag" class="pb15">
          网站地址: {{item.websiteUrl}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="12" v-if="item.postalCodeFlag" class="pb15">
          邮政编码: {{item.postalCode}}
          </Col>
          <Col span="12" v-if="item.longitudeFlag" class="pb15">
            东经：{{item.longitude}} 北纬：{{item.latitude}}
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="24" v-if="item.locationFlag" class="pb15">
            会员详细地址：{{item.location}}/{{item.address}}/{{item.house_number}}号
          </Col>
        </Row>
        <Row :gutter="38">
          <Col span="24" v-if="item.imageFlag" class="pb15">
            个人照片：<span class="t-green" @click="previewImages(item.image, '联系方式——个人照片')">查看</span>
          </Col>
        </Row>
    </div>
    <image-preview ref="imagePreview"></image-preview>
  </div>
</template>
<script>
import imagePreview from './imagePreview'
  export default {
  components: {
    imagePreview,
  },
    props: {
      data: {
        type: Array,
        default: () => {
          return []
        }
      }
    },
    data () {
      return {
        // data: [{
        //   user_id: this.account,
        //   status: true,
        //   member_name: '',
        //   member_abbreviation: '',
        //   contact_name: '',
        //   card: '',
        //   seat_phone: '',
        //   phone: '',
        //   qq_number: '',
        //   wechat_number: '',
        //   email: '',
        //   website_url: '',
        //   postal_code: '',
        //   image: [],
        //   image_status: true,
        //   location: '',
        //   location_status: true,
        //   address: '',
        //   addressPreview: '',
        //   house_number: '',
        //   address_status: true,
        //   longitude: '',
        //   latitude: '',
        //   qr_code_contact_http: '',
        //   qr_code_user_http: '',
        //   isEdit: true,
        //   member_name_id: '',
        //   positioning: ''
        // }],
      }
    },
    created() {
    },
    methods: {
      // 查看照片
      previewImages (img, title) {
        this.$refs['imagePreview'].previewImages(img, title)
      },
    }
  }
</script>

