<template>
    <div>
        <h6 class="b mb20">{{ title }}：</h6>
        <Row :gutter="24">
            <Col span="6">
                <div class="add-btn" @click="handleAdd">
                    <Icon type="plus" size="30" color="#979797" class="mt20"></Icon>
                    <div class="mt10">{{ title }}</div>
                </div>
            </Col>
            <Col v-for="(item, index) in data.data" :key="index" span="6">
                <Row style="width:130px;height:100px;">
                  <img v-if="item.fimagesrc" :src="item.fimagesrc" style="width: 100%; height: 100%;">
                  <img v-else :src="item.ficon" style="width: 100%; height: 100%;">
                </Row>
                <Row class="tc mt5 ell mb10">{{ item.fname }}</Row>
            </Col>
        </Row>
        <div class="tr pd20" v-if="data.total > data.pageSize">
          <Page :current="data.current" :total="data.total" :page-size="data.pageSize" simple @on-change="handleChange"></Page>
        </div>
        <!-- 添加病虫害弹出框 -->
        <Modal v-model="modal" width="800" :title="title" :mask-closable="false">
          <div class="pr50">
            <Form ref="info" :model="info" label-position="right" :label-width="120" :rules="ruleInline" class="mt20">
                <!-- <Form ref="data" :model="data" :label-width="110"> -->
                <Row :gutter="32">
                  <Col span="12">
                    <Form-item prop="fname" label="品种名称">
                      <Input v-model="info.fname" :maxlength="10" @on-blur="handleGetPinyin(info.fname)"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fpinyin" label="汉语拼音">
                      <Input  v-model="info.fpinyin"  readonly/>
                    </Form-item>
                  </Col>
                </Row>
                <Row :gutter="32">
                  <Col span="12">
                    <Form-item  label="物种名称">
                      <Input  v-model="speciesName" readonly/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fvarietykind" label="品种类型">
                      <Input  v-model="info.fvarietykind"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                </Row>
                <Row :gutter="32">
                  <Col span="24">
                    <Form-item  prop="fvarietyorigin" label="品种来源">
                      <Input type="textarea" :autosize="{minRows: 3,maxRows: 5}" v-model="info.fvarietyorigin"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="24">
                    <Form-item prop="fbreedingunit" label="选育单位">
                      <Input v-model="info.fbreedingunit"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fistransgene"  label="是否转基因">
                        <!-- 0否 1是 -->
                      <Select v-model="info.fistransgene" style="width: 100%">
                            <Option v-for="item in fistransgenes" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fapplydate" label="申请日期">
                      <DatePicker v-model="info.fapplydate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fapplynumber" label="申请号">
                      <Input v-model="info.fapplynumber"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fapplyannouncedate" label="申请公众日">
                      <DatePicker v-model="info.fapplyannouncedate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fapplyannouncenumber" label="申请公众号">
                      <Input v-model="info.fapplyannouncenumber"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fauthdate" label="授权日">
                      <DatePicker v-model="info.fauthdate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fauthnumber" label="品种授权号">
                      <Input v-model="info.fauthnumber"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fauthannouncedate" label="授权公告日">
                      <DatePicker v-model="info.fauthannouncedate" type="date" :options="options3" placeholder="请选择" style="width: 100%"></DatePicker>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fauthannouncenumber"  label="授权公告号">
                      <Input v-model="info.fauthannouncenumber"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fvarietyowner" label="品种权(申请)人">
                      <Input v-model="info.fvarietyowner"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="24">
                    <Form-item prop="fgrowpeople" label="培育人">
                      <Input v-model="info.fgrowpeople"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fvarietyapprdate" label="审定年份">
                      <DatePicker v-model="info.fvarietyapprdate" type="year"  placeholder="请选择" style="width: 100%"></DatePicker>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fvarietyapprunit"  label="审定单位">
                      <Input v-model="info.fvarietyapprunit"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item  prop="fvarietyapprnum" label="审定编号">
                      <Input v-model="info.fvarietyapprnum"  :maxlength="500"/>
                    </Form-item>
                  </Col>
                </Row>
                <Form-item prop="ffeature" label="特征特性">
                    <Input v-model="info.ffeature" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500" />
                </Form-item>
                <Form-item prop="foutput" label="产量">
                    <Input v-model="info.foutput" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"  />
                </Form-item>
                <Form-item prop="fgrowteachology" label="栽培技术">
                    <Input v-model="info.fgrowteachology" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"  />
                </Form-item>
                <Form-item prop="fsuiteplatearea" label="适宜区域">
                    <Input v-model="info.fsuiteplatearea" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"  />
                </Form-item>
                <Form-item prop="fmarketsituation" label="推广现状">
                    <Input v-model="info.fmarketsituation" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"  />
                </Form-item>
                <Form-item prop="ficon" label="上传图片">
                    <vui-upload
                    ref="upload"
                    :total="1"
                    :size="[120, 90]"
                    :hint="'图片大小小于2M'"
                    @on-getPictureList="handleGetPhoto"
                    ></vui-upload>
                </Form-item>
            </Form>
          </div>
            <div slot="footer">
              <div class="tc">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
              </div>
            </div>
        </Modal>
    </div>
</template>
<script>
import vuiUpload from '~components/vui-upload'
export default {
  name: 'disease-pest',
  components: {
    vuiUpload
  },
  props: {
    title: {
      type: String
    },
    picData: {
      type: Object,
      default: () => {
        return {
          current: 1,
          total: 0,
          data: []
        }
      }
    },
    label: {
      type: String
    },
    speciesName: {
      type: String
    }
  },
  data () {
    const checkName = (rule, value, callback) => {
      if (value) {
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
        callback(new Error('请填写品种名称'))
      }
    }
    return {
      fistransgenes: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      options3: {
        disabledDate (date) {
          return date && date.valueOf() > Date.now()
        }
      },
      data: this.picData,
      modal: false,
      info: {
        fname: '',
        fpinyin: '',
        fvarietykind: '',
        fvarietyorigin: '',
        fbreedingunit: '',
        fistransgene: '',
        fapplydate: '',
        fapplynumber: '',
        fapplyannouncedate: '',
        fapplyannouncenumber: '',
        fauthdate: '',
        fauthnumber: '',
        fauthannouncedate: '',
        fauthannouncenumber: '',
        fvarietyowner: '',
        fgrowpeople: '',
        fvarietyapprdate: '',
        fvarietyapprunit: '',
        fvarietyapprnum: '',
        ffeature: '',
        foutput: '',
        fgrowteachology: '',
        fsuiteplatearea: '',
        fmarketsituation: '',
        ficon: []
      },
      ruleInline: {
        fname: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        ficon: [
          { required: true, message: '请上传图片', trigger: 'change', type: 'array', min: 1 }
        ]
      }
    }
  },
  created () {},
  watch: {
    picData (newVal, oldVal) {
      this.data = newVal
    }
  },
  methods: {
    // 翻页
    handleChange (e) {
      this.$emit('on-changePage', e)
    },
    handleAdd () {
      // 清空上一次填写的表单
      this.$refs['info'].resetFields()
      this.$refs.upload.handleGive('')
      this.modal = true
    },
    ok () {
      this.info.fvarietyapprdate = this.info.fvarietyapprdate ? this.$fecha.format(new Date(this.info.fvarietyapprdate), 'YYYY') : ''
      // 表单验证
      this.$refs['info'].validate((valid) => {
        if (valid) {
          this.$emit('success', this.info)
        }
      })
    },
    cancel () {
      this.modal = false
    },
    handleGetPhoto (e) {
      var arr = []
      e.forEach(element => {
        if (element.response) {
          arr.push(element.response.data.picName)
        }
      })
      this.info.ficon = arr
    },
    // 获取拼音
    handleGetPinyin (e) {
      if (e) {
        this.$api.get('wiki/api/species/getSpeciesPinYin/' + this.info.fname).then(response => {
          this.info.fpinyin = ''
          this.info.fpinyin = response.data
        })
      } else {
        this.info.fpinyin = ''
      }
    }
  }
}
</script>
<style scoped>
  .add-btn {
    width: 130px;
    height: 100px;
    border: 1px dotted #979797;
    text-align: center;
    cursor: pointer;
    color: #979797;
  }
</style>
