<template>
  <div class="new-concat-list">
        <div>
          <div class="pd20 mb20 pt30" v-for="(item, index) in data" :key="index">
          <Form :label-width="80" label-position="left">
            <Row :gutter="32" class="pb20">
              <Title :title="item.safeFormData[0].contact_name" class="mb30">
                  <div class="btn-toolbar" style="margin-top: -10px;">
                      <Button type="text" @click="edit(item)"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                      <Button type="text" @click="del(item, index)" v-if="data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
                  </div>
              </Title>
              <!-- <Col span="8" offset="16" class="tr">
                <Button type="text" @click="edit(item)"><Icon type="trash-a" size="16" class="pr5"></Icon> 编辑</Button> 
                <Button type="text" @click="del(item, index)" v-if="data.length > 1"><Icon type="trash-a" size="16" class="pr5"></Icon> 删除</Button> 
              </Col> -->
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="会员全称" prop="member_name">
                  {{item.safeFormData[0].member_name}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].memberMameStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
              <Col span="8">
                <FormItem label="名称简写" prop="member_abbreviation"> 
                  {{item.safeFormData[0].member_abbreviation}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].memberAbbreviationStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="联系人姓名" prop="contact_name">
                  {{item.safeFormData[0].contact_name}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].contactNameStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
              <Col span="8">
                <FormItem label="身份证号" prop="card">
                  {{item.safeFormData[0].card}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].cardStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="座机电话" prop="seat_phone">
                  {{item.safeFormData[0].seat_phone}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].seatPhoneStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
              <Col span="8">
                <FormItem label="手机号码" prop="phone">
                  {{item.safeFormData[0].phone}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].phoneStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="QQ号码" prop="qq_number">
                  {{item.safeFormData[0].qq_number}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].qqNumberStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
              <Col span="8">
                <FormItem label="微信账号" prop="wechat_number">
                  {{item.safeFormData[0].wechat_number}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].wechatNumberStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="邮箱" prop="email">
                  {{item.safeFormData[0].email}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].emailStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
              <Col span="8">
                <FormItem label="网站地址" prop="website_url">
                  {{item.safeFormData[0].website_url}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].websiteUrlStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="邮政编码" prop="postal_code">
                  {{item.safeFormData[0].postal_code}}
                </FormItem>
              </Col>
              <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].postalCodeStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                  </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="20">
                <FormItem label="个人照片" prop="image">
                  <Row v-if="item.safeFormData[0]" :gutter="16">
                    <Col span="6" v-for="(e, i) in item.safeFormData[0].image" :key="i" class="mb15">
                      <img :src="e" alt="" width="100%" height="90px">
                    </Col>
                  </Row>
                </FormItem>
                </Col>
                <Col span="4">
                  <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].image_status" >
                      <span slot="open">公开</span>
                      <span slot="close">隐藏</span>
                  </Switch>
                </Col>
            </Row>
            <Row :gutter="32">
              <Col span="20">
                <FormItem label="会员详细地址" prop="detailAddress">
                    {{item.safeFormData[0].detailAddress}}
                </FormItem>
              </Col>
              <Col span="4">
                <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].detailAddressStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                </Switch>
              </Col>
            </Row>
            <Row :gutter="32">
              <Col span="8">
                <FormItem label="东经" prop="longitude">
                  {{item.safeFormData[0].longitude}}
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label="北纬" prop="latitude">
                  {{item.safeFormData[0].latitude}}
                </FormItem>
              </Col>
              <Col span="4" offset="4">
                <Switch disabled class="ml20"  size="large" v-model="item.safeFormData[0].longitudeStatus">
                    <span slot="open">公开</span>
                    <span slot="close">隐藏</span>
                </Switch>
              </Col>
            </Row>
            <div class="pb30 pt10" v-if="item.safeFormData[0].latitude">
              <a target="_blank" :href="`http://api.map.baidu.com/marker?location=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&title=我的位置&content=${item.safeFormData[0].latitude},${item.safeFormData[0].longitude}&output=html`">
                <img :src="`//api.map.baidu.com/staticimage?height=200&center=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}&zoom=15&scale=2&markers=${item.safeFormData[0].longitude},${item.safeFormData[0].latitude}`" alt="" width="100%" height="500px">
              </a>
            </div>
          </Form>
        </div>
        </div>
  </div>
</template>
<script>
import Title from '../../auth/components/title'
  export default {
    components: {
      Title
    },
    data () {
      return {
        show: false,
        data:[],
        name: ''
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList () {
        this.$api.post('/member/columnSettings/findContact', {account: this.$user.loginAccount}).then(response => {
          if (response.code === 200) {
            this.data = response.data
          }
        })
      },
      // 编辑
      edit (item) {
        this.$emit('on-edit', item)
      },
      // 删除
      del (item, index) {
        this.$Modal.confirm({
          title: '操作提示',
          content: '是否确认删除？',
          onOk:()=>{
              if (item.id) {
                  this.$api.post('/member/columnSettings/deleteContact', {
                      id: item.id
                  }).then(response => {
                      if (response.code === 200) {
                          this.$Message.success('删除成功！')
                          this.data.splice(index, 1)
                      }
                  }).catch(error => {
                      this.$Message.error('服务器异常！')
                  })
              } else {
                this.data.splice(index, 1)
                this.$Message.success('删除成功！')
              }
          },
          okText:'确定',
          cancelText:'取消'
        })
      },
      // 点击取消
      refresh () {
        this.show = false
      }
    }
  }
</script>
<style>
.new-concat-list .ivu-form-item{
  margin-bottom: 5px;
}
</style>
