<template>
  <div class="pt30"  v-if="show">
    <div style="background: #F9F9F9;" class="mb20 pl30 pt30 pr30">
      <Row>
        <Col span="3">
          <div class="mr20 tc">
            <img :src="$user.avatar" alt="" width="75px" height="75px">
          </div>
        </Col>
        <Col span="21">
          <p class="pb5" v-if="registrationMessage.accountFlag">用户名：{{registrationMessage.account}}</p>
          <p class="pb5" v-if="registrationMessage.realNameFlag">昵称：{{registrationMessage.realName}}</p>
          <p class="pb5" v-if="registrationMessage.nswyIdFlag">农事无忧账号：{{registrationMessage.nswyId}}</p>
          <p class="pb5" v-if="registrationMessage.locationFlag">所在区域：{{registrationMessage.location}}</p>
        </Col>
      </Row>
       <Row class="mt10">
        <Col span="24"> 
          <Tabs v-model="active" @on-click="handleClick" class="carte-management-tabs-bar">
              <TabPane label="资质认证" name="certification" ></TabPane>
              <TabPane label="联系方式" name="concat" ></TabPane>
              <TabPane label="法人或个人身份" name="identity" ></TabPane>
              <TabPane label="法人或个人身份（管理员）" name="administrator" ></TabPane>
          </Tabs>
        </Col>
      </Row>

    </div>
    <div style="background: #F9F9F9;" class="mb20 pd10">
        <certification v-if="active === 'certification'" ref="certification" :data="certificationData"></certification>
        <concat v-if="active === 'concat'" ref="concat" :data="concatData"></concat>
        <identity v-if="active === 'identity'" ref="identity" :data="identityData"></identity>
        <administrator v-if="active === 'administrator'" ref="administrator" :data="administratorData"></administrator>
    </div>
  </div>
</template>
<script>
import certification from './certification'
import concat from './concat'
import identity from './identity'
import administrator from './administrator'
export default {
  components: {
    certification,
    concat,
    identity,
    administrator
  },
  props: {
    registrationMessage: {
      type: Object,
      default: () =>{
        return {}
      }
    },
    certificationData: {
      type: Array,
      default: () =>{
        return []
      }
    },
    concatData: {
      type: Array,
      default: () =>{
        return []
      }
    },
    identityData: {
      type: Array,
      default: () =>{
        return []
      }
    },
    administratorData: {
      type: Array,
      default: () =>{
        return []
      }
    }
  },
  data: () => ({
    active: 'certification',
    show: false,
    data: {}
  }),
  created () {
      // this.datas = this.data
  },
  methods: {
    preview () {
      this.active = 'certification'
      this.show = true
    },
    // 全选
    handleClick (e) {
      this.active = e
    }
  }
}
</script>
<style>
.carte-management-tabs-bar .ivu-tabs-bar{
  margin-bottom: 0px;
}
</style>
