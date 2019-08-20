<template>
  <div class="pd20">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="检测时间" prop="ZLJCRQ">
        <span v-if="view"> {{ data.ZLJCRQ ? moment(data.ZLJCRQ).format('YYYY-MM-DD') : '' }} </span>
        <DatePicker v-else style="width: 100%" type="date" v-model="data.ZLJCRQ" placeholder="请选择检测时间"></DatePicker>
      </Form-item>
      <Form-item label="重铬酸盐指数（CODcr）" prop="CODcr">
        <span v-if="view"> {{ data.CODcr }} <span v-if="data.CODcr">mg/kg</span></span>
        <Input v-else v-model="data.CODcr" :maxlength="20">
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="高锰酸盐指数(CODmn）"  prop="CODmn">
        <span v-if="view"> {{ data.CODmn }} <span v-if="data.CODmn">mg/kg</span></span>
        <Input v-else v-model="data.CODmn" :maxlength="20">
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="生化需氧量（BOD5）"  prop="BOD5">
        <span v-if="view"> {{ data.BOD5 }} <span v-if="data.BOD5">mg/kg</span></span>
        <Input v-else v-model="data.BOD5" :maxlength="20">
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="氨氮" prop="AD">
        <span v-if="view"> {{ data.AD }} <span v-if="data.AD">mg/kg</span></span>
        <Input v-else v-model="data.AD" :maxlength="20">
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="总氮" prop="ZD">
        <span v-if="view"> {{ data.ZD }} <span v-if="data.ZD">mg/kg</span></span>
        <Input v-else v-model="data.ZD" :maxlength="20">
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="总磷" prop="ZL">
        <span v-if="view"> {{ data.ZL }} <span v-if="data.ZL">mg/kg</span></span>
        <Input v-else v-model="data.ZL" :maxlength="20" >
            <span slot="append">mg/kg</span>
        </Input>
      </Form-item>
      <Form-item label="营养级别" prop="YJJB">
        <div v-if="view">
          <span v-if="data.YJJB == '1'">一级</span>
          <span v-if="data.YJJB == '2'">二级</span>
          <span v-if="data.YJJB == '3'">三级</span>
          <span v-if="data.YJJB == '4'">四级</span>
          <span v-if="data.YJJB == '5'">五级</span>
          <span v-if="data.YJJB == '6'">六级</span>
        </div>
        <Select v-else v-model="data.YJJB" >
            <Option value="1">一级</Option>
            <Option value="2">二级</Option>
            <Option value="3">三级</Option>
            <Option value="4">四级</Option>
            <Option value="5">五级</Option>
            <Option value="6">六级</Option>
        </Select>
      </Form-item>
      <Form-item label="地块水质检测报告" prop="DKSZJCBG">
          <div v-if="view" >
            <Row :gutter="16">
              <Col span="8" v-for="(item, index) in data.DKSZJCBG" :key="index" class="pb16">
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
      <Form-item label="地块水质质量描述" prop="DKSZZLMS">
        <span v-if="view"> {{ data.DKSZZLMS }} </span>
        <Input v-else v-model="data.DKSZZLMS" type="textarea" :maxlength="200" ></Input>
      </Form-item>
    </Form>
  </div>
</template>
<script>
import vuiUpload from '@/components/vui-upload'
import {isNumber, isMoney3} from '@/utils/validate'
export default {
  components: {
      vuiUpload,
  },
  props: {
    view: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '地块水质信息',
      formItemInline: {
        CODcr:[{validator:isMoney3,trigger: 'blur'}],
        CODmn:[{validator:isMoney3,trigger: 'blur'}],
        BOD5:[{validator:isMoney3,trigger: 'blur'}],
        AD:[{validator:isMoney3,trigger: 'blur'}],
        ZD:[{validator:isMoney3,trigger: 'blur'}],
        ZL:[{validator:isMoney3,trigger: 'blur'}],
        phosphorus:[{validator:isMoney3,trigger: 'blur'}],
      },
      data: {
        ZLJCRQ: '', // 检测时间
        CODcr: '', // 重铬酸盐指数（CODcr）
        CODmn: '', // 高锰酸盐指数(CODmn）
        BOD5: '', // 生化需氧量（BOD5）
        AD: '', // 氨氮
        ZD: '', // 总氮
        ZL: '', // 总磷
        YJJB: '', // 营养级别
        DKSZJCBG: '', // 地块水质检测报告
        DKSZZLMS: '' // 地块水质质量描述
      }
    }
  },
  created () {
  },
  methods: {
    initShow (list) {
      // 初始化页面置空
      // 初始化页面置空
      if (this.view) {
        let data = {}
        for (const key in list) {
          if (this.data.hasOwnProperty(key)) {
            data[key] = list[key]
          }
        }
        data.DKSZJCBG = data.DKSZJCBG.split(',')
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
      if (this.data.ZLJCRQ) {
        this.data.ZLJCRQ = this.moment(this.data.ZLJCRQ).format('YYYY/MM/DD HH:mm:ss')
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
      this.data.DKSZJCBG = arr.join(',')
    }
  }
}
</script>
<style lang="less" scoped>

</style>
