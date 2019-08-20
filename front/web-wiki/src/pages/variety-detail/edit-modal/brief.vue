<template>
  <Form ref="data" :model="data" :label-width="110" :rules="ruleInline">
    <Row :gutter="32">
      <Col span="12">
        <Form-item prop="fname" label="品种名称">
          <Input v-model="data.fname" @on-blur="handleGetPinyin" readonly/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fpinyin" label="汉语拼音">
          <Input  v-model="data.fpinyin"  readonly/>
        </Form-item>
      </Col>
    </Row>
    <Row :gutter="32">
      <Col span="12">
        <Form-item label="物种名称">
          <Input  v-model="speciesName" readonly/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fvarietykind" label="品种类型">
          <Input  v-model="data.fvarietykind"  :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="24">
        <Form-item prop="fvarietyorigin" label="品种来源">
          <Input type="textarea" :autosize="{minRows: 3,maxRows: 5}" v-model="data.fvarietyorigin"  :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="24">
        <Form-item prop="fbreedingunit" label="选育单位">
          <Input v-model="data.fbreedingunit"  :maxlength="500"/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fistransgene" label="是否转基因">
            <!-- 0否 1是 -->
           <Select v-model="data.fistransgene" style="width: 100%">
                <Option v-for="item in fistransgenes" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
        </Form-item>
      </Col>
      <Col span="12">
        <Form-item prop="fapplydate" label="申请日期">
           <DatePicker v-model="data.fapplydate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fapplynumber" label="申请号">
          <Input v-model="data.fapplynumber"  :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="12">
        <Form-item prop="fapplyannouncedate" label="申请公众日">
          <DatePicker v-model="data.fapplyannouncedate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fapplyannouncenumber" label="申请公众号">
          <Input v-model="data.fapplyannouncenumber" :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="12">
        <Form-item prop="fauthdate" label="授权日">
          <DatePicker v-model="data.fauthdate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fauthnumber" label="品种授权号">
          <Input v-model="data.fauthnumber"  :maxlength="500"/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fauthannouncedate" label="授权公告日">
          <DatePicker v-model="data.fauthannouncedate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fauthannouncenumber" label="授权公告号">
          <Input v-model="data.fauthannouncenumber"  :maxlength="500"/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fvarietyowner" label="品种权(申请)人">
          <Input v-model="data.fvarietyowner" :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="24">
        <Form-item prop="fgrowpeople" label="培育人">
          <Input v-model="data.fgrowpeople" :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="12">
        <Form-item prop="fvarietyapprdate" label="审定年份">
           <DatePicker v-model="data.fvarietyapprdate" type="year"  placeholder="请选择" style="width: 100%"></DatePicker>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fvarietyapprunit" label="审定单位">
          <Input v-model="data.fvarietyapprunit" :maxlength="500"/>
        </Form-item>
      </Col>
       <Col span="12">
        <Form-item prop="fvarietyapprnum" label="审定编号">
          <Input v-model="data.fvarietyapprnum" :maxlength="500"/>
        </Form-item>
      </Col>
      <Col span="24">
        <Form-item prop="ficon" label="上传图片">
            <vui-upload
            ref="upload"
            :total="1"
            :size="[120, 90]"
            :hint="'图片大小小于2M'"
            @on-getPictureList="handleGetPhoto"
            ></vui-upload>
        </Form-item>
      </Col>
     
    </Row>
    <div class="tc mt30">
      <Button type="primary" @click="handleSave" class="mr10">保存</Button>
      <Button type="ghost" @click="handleCancel">取消</Button>
    </div>
  </Form>
</template>
<script>
import vuiUpload from '~components/vui-upload'
export default {
  components: {
    vuiUpload
  },
  props: {
    // data: {
    //   type: Object,
    //   default: () => {
    //     return {}
    //   }
    // }
  },
  data () {
    const checkName = (rule, value, callback) => {
      if (value) {
        if (value !== this.fname) {
          this.$api.post('wiki/api/wiki/listSpeciesVarietey', {validName: value}).then(response => {
            if (response.code === 200) {
              if (response.total) {
                callback(new Error('该品种名称已存在'))
              } else {
                callback()
              }
            }
          })
        } else {
          callback()
        }
      } else {
        callback(new Error('请填写品种名称'))
      }
    }
    return {
      speciesName: '',
      fistransgenes: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      options3: {
        disabledDate (date) {
          return date && date.valueOf() > Date.now()
        }
      },
      ruleInline: {
        fname: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        ficon: [
          { required: true, message: '请上传图片', trigger: 'change', type: 'array', min: 1 }
        ]
      },
      data: {},
      fname: '',
      loginUser: JSON.parse(sessionStorage.getItem(sessionStorage.getItem('key'))),
      account: ''
    }
  },
  created () {
    this.account = this.loginUser.loginAccount
    this.speciesName = this.$route.query.speciesName
  },
  methods: {
    getData (e) {
      this.data = e
      this.fname = this.data.fname
      this.$refs['upload'].handleGive(this.data.ficon)
    },
    handleGetPhoto (e) {
      var arr = []
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName)
        }
      })
      this.data.ficon = arr
    },
    // 保存
    handleSave () {
      this.data.fvarietyapprdate = this.data.fvarietyapprdate ? this.$fecha.format(new Date(this.data.fvarietyapprdate), 'YYYY') : ''
      // 表单验证
      this.$refs['data'].validate((valid) => {
        if (valid) {
          this.handleGetPinyin(this.data.fname, 1)
        }
      })
    },
    // 取消
    handleCancel () {
      this.parent.show = false
    },
    // 获取拼音
    handleGetPinyin (e, type) {
      this.$api.get('wiki/api/species/getSpeciesPinYin/' + this.data.fname).then(response => {
        this.data.fpinyin = ''
        this.data.fpinyin = response.data
        this.data.fcreatorid = this.account
        if (type) {
          this.$api.post('wiki/api/wiki/updateSpeciesVarietey', this.data).then(response => {
            if (response.code === 200) {
              this.$Message.success({
                content: '保存成功！请等待审核，审核通过后数据将会更新。',
                duration: 3
              })
              this.parent.show = false
              this.parent.handleReload()
            }
          })
        }
      })
    }
  },
  mounted () {
    this.parent = this.$parent.$parent.$parent.$parent
  }
}
</script>
