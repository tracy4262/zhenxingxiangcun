<template>
  <section>
    <top :address="false"></top>
    <section style="background: #F9F9F9;">
      <goods-head title="地址管理">
        <BreadcrumbItem>地址管理</BreadcrumbItem>
      </goods-head>
      <div class="layouts pb20">
        <section class="pd20 bg-white mt20" style="min-height: 600px">
          <Row type="flex" class="pb10 mb20" align="middle" style="border-bottom: 1px solid #ddd;">
            <Col span="20">
              <span class="b">已经保存的收货地址</span>
              <span class="t-grey">您已经创建</span>
              <span class="t-red">{{addressData.length}}</span>
              <span class="t-grey">个收货地址，最多可创建</span>
              <span class="t-red">10</span>
              <span class="t-grey">个</span>
            </Col>
            <Col span="4" class="tr">
              <Button type="primary" @click="handleAdd">新增收货地址</Button>
            </Col>
          </Row>
          <vui-address :data="addressData" @on-edit="handleEdit"></vui-address>
          <div class="mt20 pd10" v-if="editShow">
            <div class="pb10 mb20" style="border-bottom: 1px solid #ddd;">
              <span class="b" v-if="add">新增收货地址</span>
              <span class="b" v-else>编辑收货地址</span>
            </div>
            <Row type="flex" justify="center">
              <Col span="16">
                <edit :data="editData" @on-save="handleSave" @on-cancel="handleCancel"></edit>
              </Col>
            </Row>
          </div>
        </section>
      </div>
    </section>
  </section>
</template>

<script>
import top from '~src/top'
import goodsHead from './components/head'
import vuiAddress from './components/vui-address'
import edit from './components/vui-address/edit'
export default {
  components: {
    top,
    goodsHead,
    vuiAddress,
    edit
  },
  data () {
    return {
      editShow: false,
      addressData: [],
      editData: {},
      index: 0,
      account: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key')))
    }
  },
  created () {
    this.account = this.loginUser.loginAccount
    this.handleGetInit()
  },
  methods: {
    handleGetInit () {
      // order 0 按照时间默认排序， 1 按照时间+选装状态排序 订单管理需要的
      this.$api.post('/nswy-portal-service/shop/address/list',{account: this.account}).then(response => {
        if (response.code === 200) {
          this.addressData = response.data
        }
      })
    },
    // 新增
    handleAdd () {
      if (this.addressData.length < 10) {
        this.editShow = true
        this.add = true
        this.editData = {
          linkman: '',
          addArea: '',
          addDetail: '',
          mobile: '',
          email: '',
          telephone: '',
          addAlias: '',
          isDefault: false
        }
      } else {
        this.$Message.info('最多可创建10条收货地址')
      }
    },
    // 编辑
    handleEdit (index) {
      this.add = false
      this.index = index
      this.editShow = true
      this.editData = this.addressData[this.index]
    },
    // 保存
    handleSave (data) {
      let url = ''
      if (this.add) {
        // 新增
        url = '/nswy-portal-service/shop/address/add'
      } else {
        // 编辑
        url = '/nswy-portal-service/shop/address/update'
      }
      this.$api.post(url, {account: this.account, entity: data}).then(response => {
        console.log(response)
        if (response.code === 200) {
          this.$Message.success('保存成功')
          this.handleGetInit()
        } else {
          this.$Message.error('保存失败')
        }
        this.editShow = false
      })
    },
    // 取消
    handleCancel () {
      this.editShow = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
