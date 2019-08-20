<template>
  <div :class="view ? 'pd12' : 'pd20'">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="设施名称" prop="SSMC">
        <span v-if="view"> {{ data.SSMC }} </span>
        <Input v-else v-model="data.SSMC" :maxlength="50"/>
      </Form-item>
      <Form-item label="能力参数" prop="NLCS">
        <span v-if="view"> {{ data.NLCS }} </span>
        <Input v-else v-model="data.NLCS" :maxlength="20"></Input>
      </Form-item>
      <Form-item label="计量单位" prop="JLDW">
        <span v-if="view"> {{ data.JLDW }} </span>
        <vuiUnit v-else :value="data.JLDW" @on-get-data="onGetUnit($event)"></vuiUnit>
      </Form-item>
      <Form-item label="投资额（万元）" prop="TZE">
        <span v-if="view"> {{ data.TZE }} <span v-if="data.TZE">万元</span></span>
        <Input v-else v-model="data.TZE" :maxlength="20">
          <span slot="append">万元</span>
        </Input>
      </Form-item>
      <Form-item label="责任人" prop="ZRR">
        <span v-if="view"> {{ data.ZRR }} </span>
        <Input v-else v-model="data.ZRR" :maxlength="20"/>
      </Form-item>
      <Form-item label="说明" prop="SM">
        <span v-if="view"> {{ data.SM }} </span>
        <Input v-else v-model="data.SM" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200" />
      </Form-item>
      <Form-item label="上传图片" prop="SCTP">
          <div v-if="view" >
            <Row :gutter="16">
              <Col span="8" v-for="(item, index) in data.SCTP" :key="index" class="pb16">
                <img :src="item" alt="" height="80" width="100%">
              </Col>
            </Row>
          </div>
          <vui-upload
              v-else
              ref="picture"
              @on-getPictureList="getList($event)"
              :hint="'图片大小小于2MB，支持后缀名png jpg'"
              :total="9999999"
              :size="[80,80]"
          ></vui-upload>
      </Form-item>
    </Form>
  </div>
</template>
<script>
import vuiUpload from '@/components/vui-upload'
import vuiUnit from '@/components/vui-unit'
import { isMoney3 } from '@/utils/validate'
export default {
  components: {
    vuiUpload,
    vuiUnit
  },
  props: {
    view: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '养老福利设施信息',
      formItemInline: {
        SSMC: [
          { required: true, message: '请输入设施名称', trigger: 'blur' }
        ],
        NLCS: [
          {validator: isMoney3, trigger: 'blur'}
        ],
        TZE: [
          {validator: isMoney3, trigger: 'blur'}
        ]
      },
      data: {
        SSMC: '', // 设施名称
        NLCS: '', // 能力参数
        JLDW: '', // 计量单位
        TZE: '', // 投资额（万元）
        ZRR: '', // 责任人
        SM: '', // 说明
        SCTP: '' // 上传图片
      }
    }
  },
  created () {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (this.view) {
        let data =  Object.assign(this.data, list)
        data.SCTP = data.SCTP.split(',')
        this.data = data
      } else {
        if (list) {
          this.data = Object.assign(this.data, list)
          this.$refs['picture'].handleGive(this.data.SCTP)
        } else {
          this.$refs.formItem.resetFields()
          this.$refs['picture'].handleGive(this.data.SCTP)
        }
      }
    },
    save () {
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    },
    getList (e, index) {
      var arr = []
      e.forEach(element => {
        if(element.response){
          arr.push(element.response.data.picName)
        }
      })
      this.data.SCTP = arr.join(',')
    },
    // 取单位数据
    onGetUnit ($event) {
      this.data.JLDW = $event
    }
  }
}
</script>
<style lang="less" scoped>
</style>
