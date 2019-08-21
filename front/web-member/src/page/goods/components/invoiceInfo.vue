<template>
<div>
  <Form class="pl20" ref="invoiceInfo" :model="invoiceInfo" label-position="left" :label-width="100" :rules="ruleInline">
    <FormItem label="开票方式">
      <Select v-model="invoiceInfo.invoiceMode" style="width: 200px;" @on-change="change">
        <Option value="0">电子发票</Option>
        <Option value="1">纸质发票</Option>
      </Select>
    </FormItem>
    <FormItem label="开票类型">
      <Select v-model="invoiceInfo.invoiceType" style="width: 200px;" @on-change="change">
        <Option value="1">普通发票</Option>
        <Option value="2" :disabled="disableType">增值税专用发票</Option>
      </Select>
    </FormItem>
    <FormItem label="开票抬头">
      <RadioGroup v-model="invoiceInfo.title" @on-change="change">
        <Radio label="个人" :disabled="invoiceInfo.invoiceType == '2'"></Radio>
        <Radio label="公司"></Radio>
      </RadioGroup>
    </FormItem>
    <div v-if="invoiceInfo.title === '公司' && invoiceInfo.invoiceType == '2'">
      <FormItem label="单位名称" prop="unitName">
        <Input v-model="invoiceInfo.unitName" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
      <FormItem label="纳税人识别码" prop="identificationCode">
        <Input v-model="invoiceInfo.identificationCode" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
      <FormItem label="注册地址" prop="registerAddress">
        <Input v-model="invoiceInfo.registerAddress" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
      <FormItem label="注册电话" prop="registerTelephone">
        <Input v-model="invoiceInfo.registerTelephone" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
      <FormItem label="开户银行" prop="accountBank">
        <Input v-model="invoiceInfo.accountBank" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
      <FormItem label="银行账户" prop="bankAccount">
        <Input v-model="invoiceInfo.bankAccount" style="width: 300px;" :maxlength="50"></Input>
      </FormItem>
    </div>
    <FormItem label="单位名称" v-if="invoiceInfo.invoiceType == '1' && invoiceInfo.title === '公司'" prop="unitName">
      <Input v-model="invoiceInfo.unitName" style="width: 300px;" :maxlength="50"></Input>
    </FormItem>
    <FormItem label="纳税人识别码" v-if="invoiceInfo.invoiceType == '1' && invoiceInfo.title === '公司'" prop="identificationCode">
      <Input v-model="invoiceInfo.identificationCode" style="width: 300px;" :maxlength="50"></Input>
    </FormItem>
    <FormItem label="收票人手机号" v-if="invoiceInfo.invoiceType == '1'" prop="mobile">
      <Input v-model="invoiceInfo.mobile" style="width: 300px;" :maxlength="50" placeholder="收票人手机号"></Input>
    </FormItem>
    <FormItem label="收票人邮箱" v-if="invoiceInfo.invoiceType == '1'" prop="email">
      <Input v-model="invoiceInfo.email" style="width: 300px;" :maxlength="50" placeholder="收票人邮箱"></Input>
    </FormItem>
  </Form>
  <div class="pb30 pl20">
    <Button type="primary" @click="handleSaveInvoiceInfo">保存发票信息</Button>
  </div>
</div>
</template>

<script>
import {isPhone2, isEmail2} from '~utils/validate'
export default {
  data() {
    return {
      ruleInline: {
        unitName: [
          {required: true, message: '请填写单位名称', trigger: 'blur' }
        ],
        identificationCode: [
          {required: true, message: '请填写纳税人识别码', trigger: 'blur' }
        ],
        registerAddress: [
          {required: true, message: '请填写注册地址', trigger: 'blur' }
        ],
        registerTelephone: [
          {required: true, message: '请填写注册电话', trigger: 'blur' }
        ],
        accountBank: [
          {required: true, message: '请填写开户银行', trigger: 'blur' }
        ],
        bankAccount: [
          {required: true, message: '请填写银行账户', trigger: 'blur' }
        ],
        mobile: [
          {required: true, message: '请填写收票人手机号', trigger: 'blur' },
          {validator: isPhone2, trigger: 'blur' }
        ],
        email: [
          {required: true, message: '请填写收票人邮箱', trigger: 'blur' },
          {validator: isEmail2, trigger: 'blur' }
        ],
      },
      invoiceInfo: {
        invoiceMode: '0', // 开票方式 0电子发票 
        invoiceType: '0', // 开票类型 0不要发票 1个人发票 2增值税发票
        title: '个人', // 开票抬头
        unitName: '', // 单位名称
        identificationCode: '', // 纳税人识别码
        registerAddress: '', // 注册地址
        registerTelephone: '', // 注册电话
        accountBank: '', // 开户银行
        bankAccount: '', // 银行账户
        mobile: '',
        email: ''
      },
      account: '',
      disableType: false,
      invoiceTaxId: '', // 增值税发票id
      invoicePersonalId: '', // 普通发票id
    }
  },
  created() {
    this.account = this.$user.loginAccount
    this.change()
    this.handleGetInvoiceInfo()
  },
  methods: {
    // 初始化获取发票信息
    handleGetInvoiceInfo() {
      this.$api.post('/nswy-portal-service/shop/invoice/default', {account: this.account}).then(response => {
        if (response.code === 200) {
          if (response.data.invoiceTax || response.data.invoicePersonal) {
            if (response.data.invoiceTax) {
              this.invoiceTaxId = response.data.invoiceTax.id // 增值税
            } else {
              this.invoicePersonalId = response.data.invoicePersonal.id // 普通
            }
            let list = response.data.invoicePersonal
            list = Object.assign(list, response.data.invoiceTax)
            list = Object.assign(list, response.data.invoicePersonal)
            this.invoiceInfo = Object.assign(this.invoiceInfo, list)
            this.change()
          }
        }
      })
    },
    // 保存发票信息
    handleSaveInvoiceInfo() {
      this.$refs['invoiceInfo'].validate((valid) => {
        if (valid) {
          let list = {
            account: this.account
          }
          let url = ''
          if (this.invoiceInfo.invoiceType == '1') { // 普通发票
            list.invoiceType = 1
            list.entity = {
              unitName: this.invoiceInfo.unitName,
              identificationCode: this.invoiceInfo.identificationCode,
              mobile: this.invoiceInfo.mobile,
              email: this.invoiceInfo.email,
              id: this.invoicePersonalId
            }
            if (this.invoicePersonalId) { // 修改
              url = 'update'
            } else { //新增
              url = 'add'
            }
          } else if (this.invoiceInfo.invoiceType == '2') { // 增值税发票
            list.invoiceType = 2
            list.entity = {
              unitName: this.invoiceInfo.unitName,
              identificationCode: this.invoiceInfo.identificationCode,
              registerAddress: this.invoiceInfo.registerAddress,
              registerTelephone: this.invoiceInfo.registerTelephone,
              accountBank: this.invoiceInfo.accountBank,
              bankAccount: this.invoiceInfo.bankAccount,
              id: this.invoiceTaxId
            }
            if (this.invoiceTaxId) { // 修改
              url = 'update'
            } else { //新增
              url = 'add'
            }
          }
          this.$api.post(`/nswy-portal-service/shop/invoice/${url}`, list).then(response => {
            if (response.code === 200) {
              this.$Message.success('保存成功')
            } else {
              this.$Message.info('保存失败')
            }
          })
        } else {
          this.$Message.error('请核对发票信息')
        }
      })
    },
    change () {
      if (this.invoiceInfo.invoiceMode == '0') { // 电子发票
        this.invoiceInfo.invoiceType = '1'
        this.disableType = true
      } else { // 纸质发票
        if (this.invoiceInfo.invoiceType == '2') {  // 增值税
          this.invoiceInfo.title = '公司'
          this.disableType = true
        } else {
          this.disableType = false
        }
      }
    },
  }
}
</script>
