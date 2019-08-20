<template>
  <div :class="view ? 'pd12' : 'pd20'">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="检测时间" prop="TRTLJHLJCRQ">
        <span v-if="view"> {{ data.TRTLJHLJCRQ ? moment(data.TRTLJHLJCRQ).format('YYYY-MM-DD') : '' }} </span>
        <DatePicker v-else class="ww100" type="date" v-model="data.TRTLJHLJCRQ" placeholder="请选择检测时间"></DatePicker>
      </Form-item>
      <Form-item label="有效磷含量" prop="YXLHL">
        <span v-if="view"> {{ data.YXLHL }} <span v-if="data.YXLHL">mg/kg</span></span>
        <Input v-else v-model="data.YXLHL" :maxlength="20" ><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="有效钾含量" prop="YXJHL">
        <span v-if="view"> {{ data.YXJHL }} <span v-if="data.YXJHL">mg/kg</span></span>
        <Input v-else v-model="data.YXJHL" :maxlength="20" ><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="有机质含量" prop="YJZHL">
        <span v-if="view"> {{ data.YJZHL }} <span v-if="data.YJZHL">mg/kg</span></span>
        <Input v-else v-model="data.YJZHL" :maxlength="20" ><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="PH值" prop="PHZ">
        <span v-if="view"> {{ data.PHZ }} <span v-if="data.PHZ">mg/kg</span></span>
        <Input v-else v-model="data.PHZ" :maxlength="20" ><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="地块检测报告" prop="DKJCBG">
          <div v-if="view" >
            <Row :gutter="16">
              <Col span="8" v-for="(item, index) in data.DKJCBG" :key="index" class="pb16">
                <img :src="item" alt="" height="80" width="100%">
              </Col>
            </Row>
          </div>
          <vui-upload
              v-else
              ref="picture"
              @on-getPictureList="getListgetList($event)"
              :hint="'图片大小小于2MB，支持后缀名png jpg'"
              :total="9999999"
              :size="[80,80]"
          ></vui-upload>
      </Form-item>
      <Form-item label="地块氮磷钾含量描述" prop="KDTLJHLMS">
        <span v-if="view"> {{ data.KDTLJHLMS }} </span>
        <Input v-else v-model="data.KDTLJHLMS" type="textarea" :maxlength="200"><span slot="append">mg/kg</span></Input>
      </Form-item>
    </Form>
  </div>
</template>
<script>
import vuiUpload from '@/components/vui-upload'
import {isNumber, isMoney3} from '@/utils/validate'
export default {
  components: {
    vuiUpload
  },
  props: {
    view: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '地块土壤氮磷钾含量信息',
      formItemInline: {
        YXLHL:[{validator:isMoney3,trigger: 'blur'}],
        YXJHL:[{validator:isMoney3,trigger: 'blur'}],
        YJZHL:[{validator:isMoney3,trigger: 'blur'}],
        PHZ:[{validator:isMoney3,trigger: 'blur'}]
      },
      data: {
        TRTLJHLJCRQ: '', // 检测时间
        YXLHL: '', // 有效磷含量
        YXJHL: '', // 有效钾含量
        YJZHL: '', // 有机质含量
        PHZ: '', // PH值
        DKJCBG: '', // 地块检测报告
        KDTLJHLMS: '' // 地块氮磷钾含量描述
      }
    }
  },
  created () {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      if (this.view) {
        let data = {}
        for (const key in list) {
          if (this.data.hasOwnProperty(key)) {
            data[key] = list[key]
          }
        }
        data.DKJCBG = data.DKJCBG.split(',')
        this.data = data
      } else {
        if (list) {
          for (const key in list) {
            if (this.data.hasOwnProperty(key)) {
              this.data[key] = list[key]
            }
          }
          this.$refs['picture'].handleGive(this.data.DKJCBG)
        } else {
          this.$refs.formItem.resetFields()
          this.$refs['picture'].handleGive(this.data.DKJCBG)
        }
      }
    },
    save () {
      if (this.data.TRTLJHLJCRQ) {
        this.data.TRTLJHLJCRQ = this.moment(this.data.TRTLJHLJCRQ).format('YYYY/MM/DD HH:mm:ss')
      }
      this.$refs['formItem'].validate(v => {
        this.$emit('on-save', v)
      })
    },
    getListgetList (e) {
      var arr = []
      e.forEach(element => {
        if(element.response){
          arr.push(element.response.data.picName)
        }
      })
      this.data.DKJCBG = arr.join(',')
    }
  }
}
</script>
<style lang="less" scoped>

</style>
