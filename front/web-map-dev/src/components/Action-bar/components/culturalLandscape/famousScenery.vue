<template>
  <div :class="view ? 'pd12' : 'pd20'">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="风景区名称" prop="FJQMC">
        <span v-if="view"> {{ data.FJQMC }} </span>
        <Input v-model="data.FJQMC"  v-else/>
      </Form-item>
      <Form-item label="接待能力" prop="JDNL">
        <span v-if="view"> {{ data.JDNL }} </span>
        <Input v-model="data.JDNL" v-else> </Input>
      </Form-item>
      <Form-item label="计量单位" prop="JLDW">
        <span v-if="view"> {{ data.JLDW }} </span>
        <vuiUnit v-else :value="data.JLDW" @on-get-data="onGetUnit($event)"></vuiUnit>
      </Form-item>
      <Form-item label="是否免费" prop="SFMF">
        <span v-if="view"> {{ data.SFMF }} </span>
        <Select v-model="data.SFMF" v-else>
            <Option value="是">是</Option>
            <Option value="否">否</Option>
        </Select>
      </Form-item>
      <Form-item label="票价" prop="PJ">
        <span v-if="view"> {{ data.PJ }} <span v-if="data.PJ">元</span></span>
        <Input v-model="data.PJ" v-else>
          <span slot="append">元</span>
        </Input>
      </Form-item>
      <Form-item label="联系人" prop="LXR">
        <span v-if="view"> {{ data.LXR }} </span>
        <Input v-model="data.LXR" v-else/>
      </Form-item>
      <Form-item label="介绍" prop="JS">
        <span v-if="view"> {{ data.JS }} </span>
        <Input v-else v-model="data.JS" type="textarea" :autosize="{minRows: 3,maxRows: 5}" :maxlength="200"  />
      </Form-item>
      <Form-item label="上传图片">
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
      title: '风景名胜设施信息',
      formItemInline: {
        FJQMC: [
          { required: true, message: '请输入风景区名称', trigger: 'blur' }
        ],
        JDNL: [
          {validator: isMoney3, trigger: 'blur'}
        ],
        PJ: [
          {validator: isMoney3, trigger: 'blur'}
        ]
      },
      data: {
        FJQMC: '', // 风景区名称
        JDNL: '', // 接待能力
        JLDW: '', // 计量单位
        JS: '', // 介绍
        LXR: '', // 联系人
        PJ: '', // 票价
        SCTP: '', // 上传图片
        SFMF: '' // 是否免费
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
        console.log('data', data)
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
    getList (e) {
      var arr = []
      e.forEach(element => {
            if(element.response){
              arr.push(element.response.data.picName)
          }
      })
      this.data.SCTP = arr.join(',')
    },
    // 保存单个风景名胜设施信息
    save () {
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    },
    // 取单位数据
    onGetUnit ($event) {
      this.data.JLDW = $event
    }
  }
}
</script>
