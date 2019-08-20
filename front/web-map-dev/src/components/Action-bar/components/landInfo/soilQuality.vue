<template>
  <div class="pd20">
    <Form ref="formItem" :model="data" :label-position="view ? 'left' : 'top'"  :rules="view ? {} : formItemInline">
      <Form-item label="检测时间" prop="TRZLJCRQ">
        <span v-if="view"> {{ data.TRZLJCRQ ? moment(data.TRZLJCRQ).format('YYYY-MM-DD') : '' }} </span>
        <DatePicker v-else class="ww100"  type="date" v-model="data.TRZLJCRQ" placeholder="请选择检测时间"></DatePicker>
      </Form-item>
      <Form-item label="pH值≤" prop="PH">
        <span v-if="view"> {{ data.PH }} </span>
          <Input v-else v-model="data.PH" :maxlength="20"></Input>
      </Form-item>
      <Form-item label="镉" prop="G">
        <span v-if="view"> {{ data.G }} <span v-if="data.G">mg/kg</span></span>
          <Input v-else v-model="data.G" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="汞" prop="GONG">
        <span v-if="view"> {{ data.GONG }} <span v-if="data.GONG">mg/kg</span></span>
          <Input v-else v-model="data.GONG" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="砷" prop="SHEN">
        <span v-if="view"> {{ data.SHEN }} <span v-if="data.SHEN">mg/kg</span></span>
          <Input v-else v-model="data.SHEN" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="铅" prop="QIAN">
        <span v-if="view"> {{ data.QIAN }} <span v-if="data.QIAN">mg/kg</span></span>
          <Input v-else v-model="data.QIAN" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="铬" prop="GE">
        <span v-if="view"> {{ data.GE }} <span v-if="data.GE">mg/kg</span></span>
          <Input v-else v-model="data.GE" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="铜" prop="TONG">
        <span v-if="view"> {{ data.TONG }} <span v-if="data.TONG">mg/kg</span></span>
          <Input v-else v-model="data.TONG" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="镍" prop="NIE">
        <span v-if="view"> {{ data.NIE }} <span v-if="data.NIE">mg/kg</span></span>
          <Input v-else v-model="data.NIE" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="锌" prop="XIN">
        <span v-if="view"> {{ data.XIN }} <span v-if="data.XIN">mg/kg</span></span>
          <Input v-else v-model="data.XIN" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="六六六总量" prop="LLLZL">
        <span v-if="view"> {{ data.LLLZL }} <span v-if="data.LLLZL">mg/kg</span></span>
          <Input v-else v-model="data.LLLZL" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="滴滴涕总量" prop="DDTZL">
        <span v-if="view"> {{ data.DDTZL }} <span v-if="data.DDTZL">mg/kg</span></span>
          <Input v-else v-model="data.DDTZL" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="苯并[a]芘" prop="BAP">
        <span v-if="view"> {{ data.BAP }} <span v-if="data.BAP">mg/kg</span></span>
        <Input v-else v-model="data.BAP" :maxlength="20"><span slot="append">mg/kg</span></Input>
      </Form-item>
      <Form-item label="土壤检测报告" prop="TRJCBG">
          <div v-if="view" >
            <Row :gutter="16">
              <Col span="8" v-for="(item, index) in data.TRJCBG" :key="index" class="pb16">
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
      <Form-item label="地块土壤质量描述" prop="DKTRZLMS">
        <span v-if="view"> {{ data.DKTRZLMS }} </span>
        <Input v-else v-model="data.DKTRZLMS" type="textarea" :maxlength="200"><span slot="append">mg/kg</span></Input>
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
      title: '地块土壤质量信息',
      formItemInline: {
        PH:[{validator:isMoney3,trigger: 'blur'}],
        G:[{validator:isMoney3,trigger: 'blur'}],
        GONG:[{validator:isMoney3,trigger: 'blur'}],
        SHEN:[{validator:isMoney3,trigger: 'blur'}],
        QIAN:[{validator:isMoney3,trigger: 'blur'}],
        GE:[{validator:isMoney3,trigger: 'blur'}],
        TONG:[{validator:isMoney3,trigger: 'blur'}],
        NIE:[{validator:isMoney3,trigger: 'blur'}],
        XIN:[{validator:isMoney3,trigger: 'blur'}],
        LLLZL:[{validator:isMoney3,trigger: 'blur'}],
        DDTZL:[{validator:isMoney3,trigger: 'blur'}],
        BAP:[{validator:isMoney3,trigger: 'blur'}]
      },
      data: {
        TRZLJCRQ: '', // 检测时间
        PH: '', // pH值≤
        G: '', // 镉
        GONG: '', // 汞
        SHEN: '', // 砷
        QIAN: '', // 铅
        GE: '', // 铬
        TONG: '', // 铜
        NIE: '', // 镍
        XIN: '', // 锌
        LLLZL: '', // 六六六总量
        DDTZL: '', // 滴滴涕总量
        BAP: '', // 苯并[a]芘
        TRJCBG: '', // 土壤检测报告
        DKTRZLMS: '' // 地块土壤质量描述
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
        data.TRJCBG = data.TRJCBG.split(',')
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
      console.log('TRZLJCRQ', this.data.TRZLJCRQ)
      if (this.data.TRZLJCRQ) {
        this.data.TRZLJCRQ = this.moment(this.data.TRZLJCRQ).format('YYYY/MM/DD HH:mm:ss')
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
      this.data.TRJCBG = arr.join(',')
    }
  }
}
</script>
<style lang="less" scoped>

</style>
