<template>
  <div class="ivu-new-card-detail">
    <div class="header-top">
      <div class="vui-flex vui-flex-middle">
        <div class="vui-flex-item">
          <span @click="close">×</span>
        </div>
        <div class="vui-flex-item-2 tc">
          <p><b>{{title}}</b></p>
        </div>
        <div class="vui-flex-item">
        </div>
      </div>
    </div>
    <div class="li-module">
      <div class="vui-flex vui-flex-middle bg-fff">
        <div class="vui-flex-item-2">
          <p><img src="../assets/icon-certification.png" alt="">资质认证 </p>
        </div>
        <div class="vui-flex-item tr">
          <span @click="certificationShow = !certificationShow">
            <img src="../assets/app-btn-bot.png" alt="" v-if="certificationShow">
            <img src="../assets/app-btn-top.png" alt="" v-else>
          </span>
        </div>
      </div>
      <ul :key="index" v-for="(item, index) in certificationData" v-if="certificationShow">
          <li v-if="item.memberClassFlag" >
            会员类别：{{item.memberClass}}
          </li>
          <li v-if="item.memberNameFlag" >
            会员名称全称：{{item.memberName}}
          </li>
          <li v-if="item.memberNamePinyinFlag" >
            全称拼音：{{item.memberNamePinyin}}
          </li>
          <li v-if="item.memberAbbreviationFlag" >
            会员名称简写：{{item.memberAbbreviation}}
          </li>
          <li v-if="item.abbreviationPinyinFlag" >
            会员名称简称拼音：{{item.abbreviationPinyin}}
          </li>
          <li v-if="item.aptitudeNameFlag" >
            资质名称：{{item.aptitudeName}}
          </li>
          <li v-if="item.aptitudeNumberFlag" >
            资质编号：{{item.aptitudeNumber}}
          </li>
          <li v-if="item.aptitudeImageFlag" >
            资质照片：
            <img :src="imgs" :key="index" v-for="(imgs, index) in item.aptitudeImage" width="50px" height="50px" class="mr10">
          </li>
          <li v-if="item.remarkFlag" >
            说明：{{item.remark}}
          </li>
      </ul>
    </div>
    <div class="li-module">
      <div class="vui-flex vui-flex-middle bg-fff">
        <div class="vui-flex-item-2">
          <p><img src="../assets/icon-concat.png" alt=""> 联系方式 </p>
        </div>
        <div class="vui-flex-item tr">
          <span @click="concatShow = !concatShow">
            <img src="../assets/app-btn-bot.png" alt="" v-if="concatShow">
            <img src="../assets/app-btn-top.png" alt="" v-else>
          </span>
        </div>
      </div>
      <ul :key="index" v-for="(item, index) in concatData" v-if="concatShow">
              <li v-if="item.memberNameFlag">
                会员全称：{{item.memberName}}
              </li>
              <li v-if="item.memberAbbreviationFlag">
                名称简写：{{item.memberAbbreviation}}
              </li>
              <li v-if="item.contactNameFlag">
                联系人姓名：{{item.contactName}}
              </li>
              <li v-if="item.cardFlag">
                身份证号：{{item.card}}
              </li>
              <li v-if="item.setPhoneFlag">
                座机电话：{{item.setPhone}}
              </li>
              <li v-if="item.phoneFlag">
                手机号码：{{item.phone}}
              </li>
              <li v-if="item.qqNumberFlag">
                QQ号码：{{item.qqNumber}}
              </li>
              <li v-if="item.wechatNumberFlag">
                微信账号：{{item.wechatNumber}}
              </li>
              <li v-if="item.emailFlag">
                邮箱：{{item.email}}
              </li>
              <li v-if="item.postalCodeFlag">
                邮政编码：{{item.postalCode}}
              </li>
              <li v-if="item.websiteUrlFlag">
                网站地址：{{item.websiteUrl}}
              </li>
              <li v-if="item.imageFlag">
                个人照片：
                <img :src="imgs" :key="index" v-for="(imgs, index) in item.image" width="50px" height="50px" class="mr10">
              </li>
              <li v-if="item.locationFlag">
                会员详细地址：{{item.location}}/{{item.address}}/{{item.house_number}}号
              </li>
              <li v-if="item.longitudeFlag">
                东经：{{item.longitude}} 北纬：{{item.latitude}}
              </li>
      </ul>
    </div>
    <div class="li-module">
      <div class="vui-flex vui-flex-middle bg-fff">
        <div class="vui-flex-item-2">
          <p><img src="../assets/icon-user.png" alt=""> 法人或个人身份 </p>
        </div>
        <div class="vui-flex-item tr">
          <span @click="identityShow = !identityShow">
            <img src="../assets/app-btn-bot.png" alt="" v-if="identityShow">
            <img src="../assets/app-btn-top.png" alt="" v-else>
          </span>
        </div>
      </div>
      <ul :key="index" v-for="(item, index) in identityData" v-if="identityShow">
              <li v-if="item.memberNameFlag">
                会员名称全称：{{item.memberName}}
              </li>
              <li v-if="item.memberAbbreviationFlag">
                会员名称简写：{{item.memberAbbreviation}}
              </li>
              <li v-if="item.nameFlag">
                姓名：{{item.name}}
              </li>
              <li v-if="item.sexFlag">
                性别：{{item.sex}}
              </li>
              <li v-if="item.cardFlag">
                身份证号：{{item.card}}
              </li>
              <li v-if="item.jobFlag">
                职务：{{item.job}}
              </li>
              <li v-if="item.jobTitleFlag">
                职称：{{item.jobTitle}}
              </li>
              <li v-if="item.phoneFlag">
                手机：{{item.phone}}
              </li>
              <li v-if="item.emailFlag">
                邮箱：{{item.email}}
              </li>
              <li v-if="item.dutyFlag">
                职责：{{item.duty}}
              </li>
              <li v-if="item.cardImageFlag">
                身份证照片：
                <img :src="imgs" :key="index" v-for="(imgs, index) in item.cardImage" width="50px" height="50px" class="mr10">
              </li>
      </ul>
    </div>
    <div class="li-module">
      <div class="vui-flex vui-flex-middle bg-fff">
        <div class="vui-flex-item-2">
          <p><img src="../assets/icon-admin.png" alt=""> 法人或个人身份（管理员） </p>
        </div>
        <div class="vui-flex-item tr">
          <span @click="administratorShow = !administratorShow">
            <img src="../assets/app-btn-bot.png" alt="" v-if="administratorShow">
            <img src="../assets/app-btn-top.png" alt="" v-else>
          </span>
        </div>
      </div>
      <ul :key="index" v-for="(item, index) in administratorData" v-if="administratorShow">
        <li v-if="item.memberNameFlag">
          会员名称全称：{{item.memberName}}
        </li>
        <li v-if="item.memberAbbreviationFlag">
          会员名称简写：{{item.memberAbbreviation}}
        </li>
        <li v-if="item.nameFlag">
          网站管理员姓名：{{item.name}}
        </li>
        <li v-if="item.sexFlag">
          性别：{{item.sex}}
        </li>
        <li v-if="item.cardFlag">
          身份证号：{{item.card}}
        </li>
        <li v-if="item.jobFlag">
          职务：{{item.job}}
        </li>
        <li v-if="item.jobTitleFlag">
          职称：{{item.jobTitle}}
        </li>
        <li v-if="item.phoneFlag">
          手机：{{item.phone}}
        </li>
        <li v-if="item.emailFlag">
          邮箱：{{item.email}}
        </li>
        <li v-if="item.dutyFlag">
          职责：{{item.duty}}
        </li>
        <li v-if="item.cardImageFlag">
          身份证照片：
          <img :src="imgs" :key="index" v-for="(imgs, index) in item.cardImage" width="50px" height="50px" class="mr10">
        </li>
        <li v-if="item.empowerImageFlag">
          授权书照片：
          <img :src="imgs" :key="index" v-for="(imgs, index) in item.empowerImage" width="50px" height="50px" class="mr10">
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    id: '',
    title: ''
  },
  data () {
    return {
      certificationShow: true,
      certificationData: [],
      concatShow: true,
      concatData: [],
      identityShow: true,
      identityData: [],
      administratorShow: true,
      administratorData: [],
      templateId: '',
      user_id: ''
    }
  },
  created () {
    this.user_id = this.getUrl('account')
    this.templateId = this.getUrl('templateId')
    this.init()
  },
  methods: {
    init () {
      this.$api.post('/member-reversion/uesr/visitingCard/findPhoneVisitingCard', {
        account: this.user_id,
        templateId: this.templateId,
        memberNameId: this.id
      }).then(response => {
        if (response.code === 200) {
          this.certificationData = response.data.memberAptitude
          this.concatData = response.data.memberContact
          this.identityData = response.data.memberIdentity
          this.administratorData = response.data.memberAdmin
        }
      })
    },
    close () {
      this.$emit('on-close')
    },
    // 解析URL
    getUrl (name) {
      var reg = new RegExp(`(^|\\?|&)${name}=([^&]*)(\\s|&|$)`, 'i')
      if (reg.test(location.href)) {
        return unescape(RegExp.$2.replace(/\+/g, ' '))
      } else {
        return ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.ivu-new-card-detail{
  .header-top{
    background: #F2F2F2;
    padding:15px 15px;
    color: #000;
    p{
      font-size: 20px;
    }
    span{
      font-size: 32px;
    }
  }
  .li-module {
    // background: #fff;
    margin-bottom: 25px;
    .bg-fff{
      background: #fff;
      padding: 0px 15px;
    }
    p{
      color:#00C587;
      padding: 30px 5px 10px 5px;
      font-weight: 700;
      font-size: 16px;
      img{
        width:16px;
        height: 16px;
        vertical-align: middle;
        margin-right:3px;
      }
    }
    ul {
      padding: 0px 15px;
      margin-bottom: 10px;
      background: #fff;
      padding-bottom: 15px;
      &:last-child{
        margin-bottom: 0px;
      }
      li{
        padding: 15px 8px;
        border-bottom: 1px solid #E7E7E7;
        font-size: 16px;
        color: #6D6D6D;
        &:last-child{
          border-bottom: none;
        }
        img{
          vertical-align: middle;
        }
      }
    }
  }
}
</style>
