<template>
  <Form ref="addressForm" :model="addressForm" :rules="ruleValidate" :label-width="80" label-position="left">
    <FormItem label="所在地区" prop="addArea">
      <vui-cascander :values="addressForm.addArea" @handle-get-result="handleGetData"></vui-cascander>
    </FormItem>
    <FormItem label="详细地址" prop="addDetail">
      <Input v-model.trim="addressForm.addDetail" type="textarea" placeholder="建议您如实填写详细收货地址，例如街道名称，门牌号码，楼层和房间号等信息" :autosize="{minRows: 2,maxRows: 5}" :maxlength="50"></Input>
    </FormItem>
    <FormItem label="联系人" prop="linkman">
      <Input v-model.trim="addressForm.linkman" placeholder="长度不超过20个字符" :maxlength="20"></Input>
    </FormItem>
    <FormItem label="邮箱" prop="email">
      <Input v-model.trim="addressForm.email"></Input>
    </FormItem>
    <FormItem label="手机号码" prop="mobile">
      <Input v-model.trim="addressForm.mobile" :maxlength="11"></Input>
    </FormItem>
    <FormItem label="固定号码" prop="telephone">
      <Input v-model.trim="addressForm.telephone" :maxlength="15" placeholder="027-8888888"></Input>
    </FormItem>
    <FormItem label="地址别名">
      <Input v-model.trim="addressForm.addAlias" :maxlength="20" placeholder="例如：家、公司"></Input>
    </FormItem>
    <div class="tc">
      <Button type="primary" class="mr20" @click="handleSave">保存</Button>
      <Button type="default" @click="handleCancel">取消</Button>
    </div>
  </Form>
</template>

<script>
import vuiCascander from '~components/vuiCascader/index'
import {isTelephone, isEmail2, isPhone2} from '~utils/validate'
export default {
  props: {
    data: Object
  },
  components: {
    vuiCascander
  },
  data () {
    return {
      addressForm: {},
      ruleValidate: {
        addArea: [{required:true, trigger: 'blur', message: '请填写所在地区'}],
        addDetail: [{required:true, trigger: 'blur', message: '请填写详细地址'}],
        linkman: [{required:true, trigger: 'blur', message: '请填写联系人'}],
        email: [{validator:isEmail2,trigger: 'blur'}],
        mobile: [{required:true, trigger: 'blur', message: '请填写手机号码'}, {validator:isPhone2,trigger: 'blur'}],
        telephone: [{validator:isTelephone,trigger: 'blur'}]
      },
      addArea: []
    }
  },
  created () {
    this.addressForm = Object.assign({}, this.data)
  },
  watch: {
    data (newVal, oldVal) {
      this.addressForm = newVal
    }
  },
  methods: {
    //地区
    handleGetData (value, selectedData) {
      let labelArr = []
      selectedData.forEach(element => {
        labelArr.push(element.label)
      })
      this.addressForm.addArea = labelArr.join('/')
    },
    // 点击保存
    handleSave () {
      this.$refs['addressForm'].validate((valid) => {
        if (valid) {          
          this.$emit('on-save', this.addressForm)
        } else {
          this.$Meaaage.error('请核对表单信息')
        }
      })
    },
    handleCancel () {
      this.$emit('on-cancel')
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
