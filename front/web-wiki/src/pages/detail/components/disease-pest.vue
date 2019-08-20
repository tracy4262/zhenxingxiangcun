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
        <Modal v-model="modal" width="700" :title="title" :mask-closable="false">
          <div class="pr50">
            <Form ref="info" :model="info" label-position="right" :label-width="120" :rules="ruleInline" class="mt20">
               <Row :gutter="32">
                  <Col span="12">
                    <Form-item prop="fname" :label="label">
                      <Input v-model="info.fname" :maxlength="10"  @on-blur="handleGetPinyin(info.fname)"/>
                    </Form-item>
                  </Col>
                  <Col span="12">
                    <Form-item prop="fpinyin" label="汉语拼音">
                      <Input v-model="info.fpinyin" readonly />
                    </Form-item>
                  </Col>
                </Row>
                <div v-if="classType == '植物'">
                  <Form-item prop="ffeature" label="危害症状">
                    <Input v-model="info.ffeature" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"/>
                  </Form-item>
                  <Form-item prop="fdiseaseregular" label="发生规律">
                    <Input v-model="info.fdiseaseregular" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"/>
                  </Form-item>
                  <Form-item prop="fprotectmethod" label="防治办法">
                    <Input v-model="info.fprotectmethod" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500" />
                  </Form-item>                  
                </div>
                <div v-else>
                  <Form-item prop="fcausediseasesubject" label="病原学">
                    <Input v-model="info.fcausediseasesubject" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"/>
                  </Form-item>
                  <Form-item prop="fcommonfeature" label="流行特点">
                    <Input v-model="info.fcommonfeature" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500"/>
                  </Form-item>
                  <Form-item prop="fpathologycheck" label="病理解剖">
                    <Input v-model="info.fpathologycheck" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500" />
                  </Form-item> 
                  <Form-item prop="fdiagnose" label="诊断">
                    <Input v-model="info.fdiagnose" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500" />
                  </Form-item> 
                  <Form-item prop="fprevention" label="防治">
                    <Input v-model="info.fprevention" type="textarea" placeholder="请输入最多500字" :autosize="{minRows: 3,maxRows: 6}" :maxlength="500" />
                  </Form-item>   
                </div>
                <Form-item prop="fimagesrc" label="上传图片">
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
            <div slot="footer" class="tc">
                <Button type="text" @click="cancel">取消</Button>
                <Button type="primary" @click="ok">确定</Button>
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
    classType: {
      type: String
    }
  },
  data () {
    const checkName = (rule, value, callback) => {
      if (value) {
        this.$api.post('wiki/api/wiki/listSpeciesDisease', {validName: value}).then(response => {
          if (response.code === 200) {
            if (response.total) {
              callback(new Error('该病害名称已存在'))
            } else {
              callback()
            }
          }
        })
      } else {
        callback(new Error('请填写病害名称'))
      }
    }
    return {
      data: this.picData,
      modal: false,
      info: {
        fname: '',
        fpinyin: '',
        fimagesrc: []
      },
      ruleInline: {
        fname: [
          { required: true, validator: checkName, trigger: 'blur' }
        ],
        fimagesrc: [
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
      this.info.fimagesrc = arr
    },
    // 获取拼音
    handleGetPinyin (e) {
      if (e) {
        this.$api.get('wiki/api/species/getSpeciesPinYin/' + e).then(response => {
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
